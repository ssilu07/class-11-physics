package com.royals.class11physics

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.royals.class11physics.ui.theme.Class11PhysicsTheme

class WebViewActivity : ComponentActivity() {
    private lateinit var rewardedAdManager: RewardedAdManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rewardedAdManager = RewardedAdManager(this)

        enableEdgeToEdge()

        val initialChapterNumber = intent.getIntExtra("chapter_number", 1)

        setContent {
            Class11PhysicsTheme {
                ChapterWebViewScreen(
                    initialChapterNumber = initialChapterNumber,
                    onBack = {
                        if (!BuildConfig.SHOW_ADS) {
                            finish()
                        } else {
                            InterstitialAdManager.onChapterClosed(this) { finish() }
                        }
                    },
                    onWatchAdToUnlock = { chapterNumber, onRewarded ->
                        rewardedAdManager.show(
                            activity = this,
                            onRewardEarned = {
                                UserDataManager.unlockChapter(this, chapterNumber)
                                onRewarded()
                                Toast.makeText(this, "Chapter unlocked!", Toast.LENGTH_SHORT).show()
                            },
                            onAdUnavailable = {
                                // Zero-friction fallback: unlock if ad takes too long or fails over network
                                UserDataManager.unlockChapter(this, chapterNumber)
                                onRewarded()
                                Toast.makeText(
                                    this,
                                    "Slow network detected. Chapter unlocked for you!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        )
                    }
                )
            }
        }
    }
}

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterWebViewScreen(
    initialChapterNumber: Int,
    onBack: () -> Unit,
    onWatchAdToUnlock: (Int, () -> Unit) -> Unit
) {
    val context = LocalContext.current
    var currentChapterNumber by remember { mutableStateOf(initialChapterNumber) }
    val currentChapter = remember(currentChapterNumber) {
        chapters.find { it.number == currentChapterNumber } ?: chapters[0]
    }

    var textZoom by remember { mutableStateOf(UserDataManager.getTextZoom(context)) }
    var isLoading by remember { mutableStateOf(true) }
    var webViewRef by remember { mutableStateOf<WebView?>(null) }
    var lockedTargetChapter by remember { mutableStateOf<Chapter?>(null) }

    BackHandler(onBack = onBack)

    // Unlock dialog for Next/Previous locked chapter navigation
    lockedTargetChapter?.let { target ->
        AlertDialog(
            onDismissRequest = { lockedTargetChapter = null },
            title = { Text("Unlock Chapter ${target.number}") },
            text = {
                Text(
                    "\"${target.title}\" is locked. Watch a short video ad to unlock and read this chapter now!"
                )
            },
            confirmButton = {
                Button(onClick = {
                    val chapterNum = target.number
                    lockedTargetChapter = null
                    onWatchAdToUnlock(chapterNum) {
                        currentChapterNumber = chapterNum
                        isLoading = true
                        webViewRef?.loadUrl("file:///android_asset/${target.fileName}")
                        Toast.makeText(context, "Chapter $chapterNum unlocked!", Toast.LENGTH_SHORT).show()
                    }
                }) {
                    Text("Watch Ad to Unlock")
                }
            },
            dismissButton = {
                TextButton(onClick = { lockedTargetChapter = null }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            text = currentChapter.title,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 17.sp
                        )
                        Text(
                            text = "Ch ${currentChapter.number} • ${currentChapter.unit}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    // Font Zoom Out Button
                    IconButton(
                        onClick = {
                            if (textZoom > 70) {
                                textZoom -= 10
                                webViewRef?.settings?.textZoom = textZoom
                                UserDataManager.setTextZoom(context, textZoom)
                                Toast.makeText(context, "Text size: $textZoom%", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(
                            text = "A⁻",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 16.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }

                    // Font Zoom In Button
                    IconButton(
                        onClick = {
                            if (textZoom < 160) {
                                textZoom += 10
                                webViewRef?.settings?.textZoom = textZoom
                                UserDataManager.setTextZoom(context, textZoom)
                                Toast.makeText(context, "Text size: $textZoom%", Toast.LENGTH_SHORT).show()
                            }
                        }
                    ) {
                        Text(
                            text = "A⁺",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 17.sp,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            // Non-blocking sleek progress bar
            if (isLoading) {
                LinearProgressIndicator(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(3.dp),
                    color = MaterialTheme.colorScheme.tertiary,
                    trackColor = MaterialTheme.colorScheme.primaryContainer
                )
            } else {
                Spacer(modifier = Modifier.height(3.dp))
            }

            // WebView content container
            Box(modifier = Modifier.weight(1f)) {
                AndroidView(
                    modifier = Modifier.fillMaxSize(),
                    factory = { ctx ->
                        WebView(ctx).apply {
                            webViewClient = object : WebViewClient() {
                                override fun onPageFinished(view: WebView?, url: String?) {
                                    isLoading = false
                                    view?.settings?.textZoom = textZoom
                                }
                            }
                            webChromeClient = WebChromeClient()
                            setLayerType(View.LAYER_TYPE_HARDWARE, null)
                            with(settings) {
                                javaScriptEnabled = true
                                domStorageEnabled = true
                                allowFileAccess = true
                                @Suppress("DEPRECATION")
                                allowFileAccessFromFileURLs = true
                                @Suppress("DEPRECATION")
                                allowUniversalAccessFromFileURLs = true
                                builtInZoomControls = true
                                displayZoomControls = false
                                loadWithOverviewMode = true
                                useWideViewPort = true
                                mediaPlaybackRequiresUserGesture = false
                                setTextZoom(textZoom)
                            }
                            webViewRef = this
                            loadUrl("file:///android_asset/${currentChapter.fileName}")
                        }
                    },
                    update = { view ->
                        webViewRef = view
                        view.settings.textZoom = textZoom
                    }
                )
            }

            // Chapter In-Reader Navigation Bar
            Surface(
                modifier = Modifier.fillMaxWidth(),
                tonalElevation = 4.dp,
                shadowElevation = 4.dp,
                color = MaterialTheme.colorScheme.surface
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Previous Chapter Button
                    OutlinedButton(
                        onClick = {
                            if (currentChapterNumber > 1) {
                                val prevNum = currentChapterNumber - 1
                                currentChapterNumber = prevNum
                                isLoading = true
                                val prev = chapters[prevNum - 1]
                                webViewRef?.loadUrl("file:///android_asset/${prev.fileName}")
                            }
                        },
                        enabled = currentChapterNumber > 1,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("◀ Prev", fontSize = 13.sp)
                    }

                    // Chapter Indicator
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.padding(horizontal = 4.dp)
                    ) {
                        Text(
                            text = "Ch ${currentChapter.number} of ${chapters.size}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 13.sp,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Text(
                            text = "Class 11 Physics",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    // Next Chapter Button
                    Button(
                        onClick = {
                            if (currentChapterNumber < chapters.size) {
                                val nextNum = currentChapterNumber + 1
                                val nextTarget = chapters[nextNum - 1]
                                val isUnlocked = UserDataManager.isChapterUnlocked(context, nextNum)
                                if (isUnlocked) {
                                    currentChapterNumber = nextNum
                                    isLoading = true
                                    webViewRef?.loadUrl("file:///android_asset/${nextTarget.fileName}")
                                } else {
                                    lockedTargetChapter = nextTarget
                                }
                            }
                        },
                        enabled = currentChapterNumber < chapters.size,
                        shape = RoundedCornerShape(12.dp)
                    ) {
                        Text("Next ▶", fontSize = 13.sp)
                    }
                }
            }

            // Banner Ad at the bottom
            if (BuildConfig.SHOW_ADS) {
                BannerAdView()
            }
        }
    }
}
