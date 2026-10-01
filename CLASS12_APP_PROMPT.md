# Prompt: Build "Class 12 Physics" Android App (clone of Class 11 Physics app architecture)

Build a native Android app called **Class 12 Physics** (package: `com.royals.class12physics`), replicating the exact architecture, tech stack, and monetization model of my existing "Class 11 Physics" app, adapted for the NCERT Class 12 Physics syllabus.

## Tech stack
- Kotlin + Jetpack Compose (Material3), single-activity-per-screen pattern
- minSdk 24, targetSdk/compileSdk 36
- Gradle version catalogs (`libs.versions.toml`)
- Dependencies: `androidx.core:core-ktx`, `androidx.lifecycle:lifecycle-runtime-ktx`, `androidx.activity:activity-compose`, Compose BOM, `androidx.compose.material3`, `androidx.compose.material:material-icons-core`, `com.android.billingclient:billing-ktx` (v7.1.1+), `com.google.android.gms:play-services-ads` (v23.6.0+)

## App structure

### 1. `Class12PhysicsApp.kt` (Application class)
Initializes `MobileAds.initialize(this)` in `onCreate`.

### 2. `MainActivity.kt` — Chapter list screen
- Data class `Chapter(val number: Int, val title: String, val fileName: String)`
- Static list of chapters mapping to NCERT Class 12 Physics (rationalized syllabus, 14 chapters):
  1. Electric Charges and Fields — chapter_01.html
  2. Electrostatic Potential and Capacitance — chapter_02.html
  3. Current Electricity — chapter_03.html
  4. Moving Charges and Magnetism — chapter_04.html
  5. Magnetism and Matter — chapter_05.html
  6. Electromagnetic Induction — chapter_06.html
  7. Alternating Current — chapter_07.html
  8. Electromagnetic Waves — chapter_08.html
  9. Ray Optics and Optical Instruments — chapter_09.html
  10. Wave Optics — chapter_10.html
  11. Dual Nature of Radiation and Matter — chapter_11.html
  12. Atoms — chapter_12.html
  13. Nuclei — chapter_13.html
  14. Semiconductor Electronics: Materials, Devices and Simple Circuits — chapter_14.html
- `ChapterListScreen` composable: `Scaffold` with `TopAppBar` ("Class 12 Physics"), `LazyColumn` of `ChapterCard`s (rounded card, colored number badge, title), bottom `BannerAdView` shown only when not premium.
- Rotating palette of pastel background colors + matching accent colors per chapter card (cycle through a list of ~14-15 color pairs).
- Lock logic: **first 3 chapters free**, chapters 4+ locked unless `isPremium == true` or chapter number is in an in-memory `unlockedChapters: Set<Int>` (unlocked via rewarded ad for that session).
- Tapping a locked chapter shows an `AlertDialog`: "Buy All - Unlock Forever" (triggers billing purchase flow) vs "Watch Ad to Unlock" (triggers rewarded ad, adds chapter number to `unlockedChapters` on reward).
- Tapping an unlocked chapter launches `WebViewActivity` with `file_name` and `chapter_title` extras.
- Owns `BillingManager` (started/connected in `onCreate`, destroyed in `onDestroy`) and `RewardedAdManager`.

### 3. `WebViewActivity.kt` — Chapter content screen
- Reads `file_name` and `chapter_title` from intent extras.
- Loads `WebView` via `AndroidView`, pointed at `file:///android_asset/<file_name>`.
- WebView settings: `javaScriptEnabled = true`, `domStorageEnabled = true`, `allowFileAccess = true`, `allowFileAccessFromFileURLs = true`, `allowUniversalAccessFromFileURLs = true`, `builtInZoomControls = true`, `loadWithOverviewMode = true`, `useWideViewPort = true`, hardware layer type.
- Shows a loading `AlertDialog` with `CircularProgressIndicator` until `onPageFinished` fires.
- `TopAppBar` with back arrow; back button (and system back via `BackHandler`) triggers `InterstitialAdManager.onChapterClosed()` before finishing (skipped if premium).
- Bottom `BannerAdView` shown only when not premium.

### 4. `BillingManager.kt`
- Wraps Google Play `BillingClient` with a single INAPP one-time product, e.g. product ID `unlock_all_chapters`.
- Persists premium flag in `SharedPreferences` (`billing_prefs` / `is_premium`), exposes `isPremium: StateFlow<Boolean>`.
- Static helper `isPremiumUser(context)` for reading the flag outside Compose (used in `WebViewActivity`).
- Handles connection lifecycle, product details query, purchase flow launch, purchase acknowledgment.

### 5. `BannerAdView.kt`
Composable wrapping AdMob `AdView` via `AndroidView`, `AdSize.BANNER`, ad unit ID from `BuildConfig`.

### 6. `InterstitialAdManager.kt`
Loads an interstitial ad; shows it every **3rd** chapter-close (`CHAPTERS_PER_AD = 3`), tracked via a `SharedPreferences` counter (`ad_prefs` / `chapter_close_count`). Reloads next ad after show/failure. Always calls the `onDone` callback (whether or not an ad was shown) so navigation isn't blocked.

### 7. `RewardedAdManager.kt`
Loads a rewarded ad; `show(activity, onRewardEarned, onAdUnavailable)` shows it if ready, calls `onRewardEarned` when the user earns the reward, reloads the next ad afterward.

### 8. Gradle / manifest wiring
- `app/build.gradle.kts`: debug and release build types both set `manifestPlaceholders["admobAppId"]` and `BuildConfig` fields `BANNER_AD_UNIT_ID`, `INTERSTITIAL_AD_UNIT_ID`, `REWARDED_AD_UNIT_ID` — use Google's official **test ad unit IDs** for debug, with a `TODO` comment to replace with real AdMob IDs before release.
- `AndroidManifest.xml`: `INTERNET` permission, `<meta-data android:name="com.google.android.gms.ads.APPLICATION_ID" android:value="${admobAppId}" />`, `MainActivity` as launcher, `WebViewActivity` as non-exported child activity.
- App icon / label: "Class 12 Physics".

### 9. Theme
Reuse the same Material3 theme setup pattern (`ui/theme/Color.kt`, `Theme.kt`, `Type.kt`) — just rename the theme composable to `Class12PhysicsTheme`.

## Chapter content (assets/chapter_XX.html)
For each of the 14 chapters, generate a **self-contained HTML file** (no external JS framework, inline `<style>`/`<script>`) styled like a dark "Physics Explorer" theme:
- Google Fonts: Nunito (body), Exo 2 (headings), Share Tech Mono (mono/stat chips) — loaded via `<link>`, with CSS variables for a dark cyan/teal glassmorphism palette (`--accent`, `--bg`, `--glass`, etc.) — can vary the accent color per chapter for variety.
- Animated starfield background (`#stars` div with twinkling `.star` divs).
- Header with gradient-text `<h1>` title and small stat chips.
- **Three tabs** (`Theory` / `Simulation` / `Quiz`), switched via a small `sw(tabName, buttonEl)` JS function toggling `.active` classes — no page reload.
  - **Theory tab**: `.card` sections with headings, bullet lists, and formula callouts (`.fml` divs) covering the chapter's key concepts, laws, and formulas from the NCERT syllabus.
  - **Simulation tab**: an interactive `<canvas>`-based JS simulation relevant to the chapter's topic (e.g. field-line visualizer for Electric Charges and Fields, circuit simulator for Current Electricity, ray diagram for Ray Optics, oscilloscope-style wave for Alternating Current, photoelectric effect demo for Dual Nature of Radiation, etc.), with a live info panel (`.sinfo`/`.igrid`) showing computed values as the user interacts (drag/click/slider).
  - **Quiz tab**: 8-10 MCQs with progress dots (`.qdot`), option buttons that highlight correct/incorrect on click, an explanation panel (`.expl`) shown after answering, next/previous navigation, and a final score panel (`.spanel`) with score and retry button.
- Include `three.min.js` as a shared asset only if a chapter's simulation needs 3D (e.g. Atoms/Nuclei orbital models); otherwise keep simulations to 2D canvas for simplicity and performance.
- Mobile-responsive via `clamp()`/`@media` queries; must work fully offline (all JS/CSS inline except Google Fonts link).

## Deliverables
1. Full Android Studio project matching this structure, buildable and runnable on a device/emulator.
2. All 14 chapter HTML files with real, accurate NCERT Class 12 Physics content (not placeholder text) in the Theory tab, a working topic-relevant simulation, and a working quiz with correct answers/explanations.
3. Keep the free/locked chapter split configurable in one place (currently: first 3 free) so it's easy to change later.
