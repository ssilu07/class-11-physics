package com.royals.class11physics

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.royals.class11physics.ui.theme.Class11PhysicsTheme

enum class ChapterFilter(val label: String) {
    ALL("All (15)"),
    FREE("Free (3)"),
    BOOKMARKED("Saved ⭐"),
    LOCKED("Locked 🔒")
}

private val cardBgColors = listOf(
    Color(0xFFFFCDD2), Color(0xFFFFE0B2), Color(0xFFFFF9C4),
    Color(0xFFC8E6C9), Color(0xFFB2EBF2), Color(0xFFB3E5FC),
    Color(0xFFBBDEFB), Color(0xFFD1C4E9), Color(0xFFF8BBD9),
    Color(0xFFDCEDC8), Color(0xFFB2DFDB), Color(0xFFFFCCBC),
    Color(0xFFE1BEE7), Color(0xFFB3E5FC), Color(0xFFFFE0B2),
)

private val cardNumberColors = listOf(
    Color(0xFFE53935), Color(0xFFFB8C00), Color(0xFFF9A825),
    Color(0xFF43A047), Color(0xFF00ACC1), Color(0xFF039BE5),
    Color(0xFF1E88E5), Color(0xFF5E35B1), Color(0xFFE91E63),
    Color(0xFF7CB342), Color(0xFF00897B), Color(0xFFF4511E),
    Color(0xFF8E24AA), Color(0xFF0288D1), Color(0xFFF57C00),
)

class MainActivity : ComponentActivity() {
    private lateinit var billingManager: BillingManager
    private lateinit var rewardedAdManager: RewardedAdManager
    private lateinit var inAppReviewManager: InAppReviewManager
    private var hasOpenedChapter = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billingManager = BillingManager(this)
        billingManager.startConnection()
        rewardedAdManager = RewardedAdManager(this)
        inAppReviewManager = InAppReviewManager(this)
        enableEdgeToEdge()
        setContent {
            val isPremium by billingManager.isPremium.collectAsState()
            var unlockedChapters by remember {
                mutableStateOf(UserDataManager.getUnlockedChapters(this@MainActivity))
            }
            var bookmarkedChapters by remember {
                mutableStateOf(UserDataManager.getBookmarkedChapters(this@MainActivity))
            }
            var bookmarkedFormulas by remember {
                mutableStateOf(UserDataManager.getBookmarkedFormulas(this@MainActivity))
            }
            var bookmarkedDerivations by remember {
                mutableStateOf(UserDataManager.getBookmarkedDerivations(this@MainActivity))
            }

            Class11PhysicsTheme {
                MainAppScreen(
                    isPremium = isPremium,
                    unlockedChapters = unlockedChapters,
                    bookmarkedChapters = bookmarkedChapters,
                    bookmarkedFormulas = bookmarkedFormulas,
                    bookmarkedDerivations = bookmarkedDerivations,
                    onToggleChapterBookmark = { chapterNumber ->
                        UserDataManager.toggleBookmark(this@MainActivity, chapterNumber)
                        bookmarkedChapters = UserDataManager.getBookmarkedChapters(this@MainActivity)
                    },
                    onToggleFormulaBookmark = { formulaId ->
                        UserDataManager.toggleFormulaBookmark(this@MainActivity, formulaId)
                        bookmarkedFormulas = UserDataManager.getBookmarkedFormulas(this@MainActivity)
                    },
                    onToggleDerivationBookmark = { derivationId ->
                        UserDataManager.toggleDerivationBookmark(this@MainActivity, derivationId)
                        bookmarkedDerivations = UserDataManager.getBookmarkedDerivations(this@MainActivity)
                    },
                    onPurchaseClick = {
                        if (billingManager.isReadyToPurchase()) {
                            billingManager.launchPurchaseFlow(this)
                        } else {
                            Toast.makeText(
                                this,
                                "Connecting to Google Play Store...",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onWatchAdToUnlock = { chapterNumber ->
                        rewardedAdManager.show(
                            activity = this,
                            onRewardEarned = {
                                UserDataManager.unlockChapter(this@MainActivity, chapterNumber)
                                unlockedChapters = UserDataManager.getUnlockedChapters(this@MainActivity)
                                Toast.makeText(
                                    this,
                                    "Chapter $chapterNumber unlocked for this session!",
                                    Toast.LENGTH_SHORT
                                ).show()
                            },
                            onAdUnavailable = {
                                Toast.makeText(
                                    this,
                                    "Ad not ready yet, please try again in a moment.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    },
                    onChapterOpened = {
                        hasOpenedChapter = true
                    }
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (hasOpenedChapter) {
            hasOpenedChapter = false
            inAppReviewManager.onChapterRead(this)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        billingManager.destroy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainAppScreen(
    isPremium: Boolean,
    unlockedChapters: Set<Int>,
    bookmarkedChapters: Set<Int>,
    bookmarkedFormulas: Set<String>,
    bookmarkedDerivations: Set<String>,
    onToggleChapterBookmark: (Int) -> Unit,
    onToggleFormulaBookmark: (String) -> Unit,
    onToggleDerivationBookmark: (String) -> Unit,
    onPurchaseClick: () -> Unit,
    onWatchAdToUnlock: (Int) -> Unit,
    onChapterOpened: () -> Unit = {}
) {
    val context = LocalContext.current
    var selectedNavTab by rememberSaveable { mutableIntStateOf(0) }
    var targetFormulaChapter by rememberSaveable { mutableIntStateOf(0) }
    var targetDerivationChapter by rememberSaveable { mutableIntStateOf(0) }
    var lockedChapter by remember { mutableStateOf<Chapter?>(null) }

    val handleOpenChapter: (Int) -> Unit = { chapterNumber ->
        val chapter = chapters.find { it.number == chapterNumber }
        if (chapter != null) {
            val isUnlocked = chapter.number <= 3 || isPremium || chapter.number in unlockedChapters
            if (isUnlocked) {
                onChapterOpened()
                val intent = Intent(context, WebViewActivity::class.java).apply {
                    putExtra("file_name", chapter.fileName)
                    putExtra("chapter_title", chapter.title)
                    putExtra("chapter_number", chapter.number)
                }
                context.startActivity(intent)
            } else {
                lockedChapter = chapter
            }
        }
    }

    // Intercept back button to return to Chapters tab if currently on other tabs
    BackHandler(enabled = selectedNavTab != 0) {
        selectedNavTab = 0
    }

    lockedChapter?.let { chapter ->
        AlertDialog(
            onDismissRequest = { lockedChapter = null },
            title = { Text("Unlock Chapter ${chapter.number}") },
            text = {
                Text(
                    "\"${chapter.title}\" is a premium chapter. " +
                    "Purchase once to unlock all 15 chapters forever with an ad-free experience, " +
                    "or watch a quick video ad to unlock this chapter for your study session."
                )
            },
            confirmButton = {
                Button(onClick = {
                    lockedChapter = null
                    onPurchaseClick()
                }) {
                    Text("Buy All - Unlock Forever")
                }
            },
            dismissButton = {
                if (BuildConfig.SHOW_ADS) {
                    TextButton(onClick = {
                        lockedChapter = null
                        onWatchAdToUnlock(chapter.number)
                    }) {
                        Text("Watch Ad to Unlock")
                    }
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
                            text = when (selectedNavTab) {
                                0 -> "Class 11 Physics"
                                1 -> "Formula Hub"
                                else -> "Exam Derivations"
                            },
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                        Text(
                            text = when (selectedNavTab) {
                                0 -> "NCERT Complete Guide & 3D Lab"
                                1 -> "Cheat Sheet • Constants • Dimensions"
                                else -> "Step-by-Step Board Proofs"
                            },
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                },
                actions = {
                    if (!isPremium) {
                        Surface(
                            shape = RoundedCornerShape(16.dp),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f),
                            modifier = Modifier
                                .padding(end = 12.dp)
                                .clickable { onPurchaseClick() }
                        ) {
                            Text(
                                text = "⭐ PRO",
                                fontSize = 12.sp,
                                fontWeight = FontWeight.ExtraBold,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            Column {
                if (!isPremium && BuildConfig.SHOW_ADS) {
                    BannerAdView()
                }
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surface,
                    tonalElevation = 6.dp
                ) {
                    NavigationBarItem(
                        selected = selectedNavTab == 0,
                        onClick = { selectedNavTab = 0 },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_menu_book),
                                contentDescription = "Chapters",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                "Chapters",
                                fontWeight = if (selectedNavTab == 0) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = selectedNavTab == 1,
                        onClick = { selectedNavTab = 1 },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_calculate),
                                contentDescription = "Formula Hub",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                "Formula Hub",
                                fontWeight = if (selectedNavTab == 1) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )

                    NavigationBarItem(
                        selected = selectedNavTab == 2,
                        onClick = { selectedNavTab = 2 },
                        icon = {
                            Icon(
                                painter = painterResource(id = R.drawable.ic_science),
                                contentDescription = "Derivations",
                                modifier = Modifier.size(24.dp)
                            )
                        },
                        label = {
                            Text(
                                "Derivations",
                                fontWeight = if (selectedNavTab == 2) FontWeight.Bold else FontWeight.Normal,
                                fontSize = 12.sp
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (selectedNavTab) {
                0 -> {
                    ChapterListContent(
                        isPremium = isPremium,
                        unlockedChapters = unlockedChapters,
                        bookmarkedChapters = bookmarkedChapters,
                        onToggleBookmark = onToggleChapterBookmark,
                        onOpenChapter = handleOpenChapter,
                        onOpenFormulas = { chNum ->
                            targetFormulaChapter = chNum
                            selectedNavTab = 1
                        },
                        onOpenDerivations = { chNum ->
                            targetDerivationChapter = chNum
                            selectedNavTab = 2
                        }
                    )
                }
                1 -> {
                    FormulaHubScreen(
                        bookmarkedFormulas = bookmarkedFormulas,
                        onToggleBookmark = onToggleFormulaBookmark,
                        initialChapterFilter = targetFormulaChapter,
                        onOpenChapter = handleOpenChapter
                    )
                }
                2 -> {
                    DerivationsHubScreen(
                        bookmarkedDerivations = bookmarkedDerivations,
                        onToggleBookmark = onToggleDerivationBookmark,
                        initialChapterFilter = targetDerivationChapter,
                        onOpenChapter = handleOpenChapter
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterListContent(
    isPremium: Boolean,
    unlockedChapters: Set<Int>,
    bookmarkedChapters: Set<Int>,
    onToggleBookmark: (Int) -> Unit,
    onOpenChapter: (Int) -> Unit,
    onOpenFormulas: (Int) -> Unit,
    onOpenDerivations: (Int) -> Unit
) {
    val context = LocalContext.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(ChapterFilter.ALL) }

    val filteredChapters = remember(searchQuery, selectedFilter, bookmarkedChapters, unlockedChapters, isPremium) {
        chapters.filter { chapter ->
            val matchesSearch = searchQuery.isBlank() ||
                chapter.title.contains(searchQuery, ignoreCase = true) ||
                chapter.unit.contains(searchQuery, ignoreCase = true) ||
                chapter.number.toString() == searchQuery.trim()

            val isUnlocked = chapter.number <= 3 || isPremium || chapter.number in unlockedChapters

            val matchesFilter = when (selectedFilter) {
                ChapterFilter.ALL -> true
                ChapterFilter.FREE -> chapter.number <= 3
                ChapterFilter.BOOKMARKED -> chapter.number in bookmarkedChapters
                ChapterFilter.LOCKED -> !isUnlocked
            }

            matchesSearch && matchesFilter
        }
    }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search Bar & Filter Chips
        Surface(
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 2.dp,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                OutlinedTextField(
                    value = searchQuery,
                    onValueChange = { searchQuery = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text("Search chapters or topics...", fontSize = 14.sp) },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = "Search",
                            tint = MaterialTheme.colorScheme.primary
                        )
                    },
                    trailingIcon = {
                        if (searchQuery.isNotEmpty()) {
                            IconButton(onClick = { searchQuery = "" }) {
                                Icon(
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = "Clear",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
                    singleLine = true,
                    shape = RoundedCornerShape(24.dp),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f),
                        unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.25f),
                        focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                        unfocusedIndicatorColor = Color.Transparent
                    )
                )

                Spacer(modifier = Modifier.height(8.dp))

                // Filter Chips Row
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 4.dp)
                ) {
                    items(ChapterFilter.values()) { filter ->
                        val isSelected = selectedFilter == filter
                        val count = when (filter) {
                            ChapterFilter.ALL -> chapters.size
                            ChapterFilter.FREE -> 3
                            ChapterFilter.BOOKMARKED -> bookmarkedChapters.size
                            ChapterFilter.LOCKED -> {
                                if (isPremium) 0 else (chapters.size - 3 - unlockedChapters.count { it > 3 }).coerceAtLeast(0)
                            }
                        }
                        val labelText = when (filter) {
                            ChapterFilter.ALL -> "All (15)"
                            ChapterFilter.FREE -> "Free (3)"
                            ChapterFilter.BOOKMARKED -> "Saved ($count)"
                            ChapterFilter.LOCKED -> "Locked ($count)"
                        }

                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFilter = filter },
                            label = { Text(labelText, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }
            }
        }

        // Chapter List or Empty State
        if (filteredChapters.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(32.dp),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    if (selectedFilter == ChapterFilter.BOOKMARKED) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_star_border),
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Saved Chapters Yet",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "Tap the star icon on any chapter card to save it for quick revision before exams.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                    } else {
                        Icon(
                            imageVector = Icons.Filled.Search,
                            contentDescription = null,
                            modifier = Modifier.size(56.dp),
                            tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "No Chapters Found",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.titleMedium
                        )
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "No results match \"$searchQuery\". Try checking the spelling or resetting filters.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = androidx.compose.ui.text.style.TextAlign.Center
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        OutlinedButton(onClick = {
                            searchQuery = ""
                            selectedFilter = ChapterFilter.ALL
                        }) {
                            Text("Reset Filters")
                        }
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(filteredChapters, key = { it.number }) { chapter ->
                    val isUnlocked = chapter.number <= 3 || isPremium || chapter.number in unlockedChapters
                    val isLocked = !isUnlocked
                    val isAdUnlocked = !isPremium && chapter.number > 3 && chapter.number in unlockedChapters
                    val isBookmarked = chapter.number in bookmarkedChapters

                    ChapterCard(
                        chapter = chapter,
                        isLocked = isLocked,
                        isAdUnlocked = isAdUnlocked,
                        isBookmarked = isBookmarked,
                        onBookmarkClick = { onToggleBookmark(chapter.number) },
                        onFormulasClick = { onOpenFormulas(chapter.number) },
                        onDerivationsClick = { onOpenDerivations(chapter.number) },
                        onClick = { onOpenChapter(chapter.number) }
                    )
                }
            }
        }
    }
}

@Composable
fun ChapterCard(
    chapter: Chapter,
    isLocked: Boolean,
    isAdUnlocked: Boolean,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onClick: () -> Unit,
    onFormulasClick: () -> Unit = {},
    onDerivationsClick: () -> Unit = {}
) {
    val colorIndex = (chapter.number - 1).coerceIn(0, cardBgColors.lastIndex)
    val baseBgColor = cardBgColors[colorIndex]
    val numberColor = cardNumberColors[colorIndex]
    val bgColor = if (isLocked) baseBgColor.copy(alpha = 0.55f) else baseBgColor

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isLocked) 1.dp else 3.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor),
        shape = RoundedCornerShape(16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Chapter Number Badge
            Surface(
                modifier = Modifier.size(50.dp),
                shape = RoundedCornerShape(12.dp),
                color = if (isLocked) Color(0xFF9E9E9E) else numberColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp)
                        )
                    } else {
                        Text(
                            text = "${chapter.number}",
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            // Details
            Column(modifier = Modifier.weight(1f)) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp)
                ) {
                    Text(
                        text = "CH ${chapter.number}",
                        style = MaterialTheme.typography.labelSmall,
                        color = if (isLocked) Color(0xFF757575) else numberColor,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "•",
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF757575)
                    )
                    Text(
                        text = chapter.unit,
                        style = MaterialTheme.typography.labelSmall,
                        color = Color(0xFF616161),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = if (isLocked) Color(0xFF616161) else Color(0xFF1E1E1E),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                // Status pill
                if (isLocked) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0x33000000),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "LOCKED",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF616161),
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else if (isAdUnlocked) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF4CAF50).copy(alpha = 0.2f),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "UNLOCKED (AD)",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF2E7D32),
                            fontWeight = FontWeight.Bold
                        )
                    }
                } else if (chapter.number <= 3) {
                    Surface(
                        shape = CircleShape,
                        color = Color(0xFF1976D2).copy(alpha = 0.15f),
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        Text(
                            text = "FREE",
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall,
                            color = Color(0xFF1565C0),
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                // Quick shortcuts row for Formulas and Derivations
                val formulaCount = remember(chapter.number) {
                    physicsFormulas.count { it.chapterNumber == chapter.number }
                }
                val derivationCount = remember(chapter.number) {
                    topDerivations.count { it.chapterNumber == chapter.number }
                }

                if (formulaCount > 0 || derivationCount > 0) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (formulaCount > 0) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.clickable { onFormulasClick() }
                            ) {
                                Text(
                                    text = "⚡ $formulaCount Formulas",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFF1565C0),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                        if (derivationCount > 0) {
                            Surface(
                                shape = RoundedCornerShape(8.dp),
                                color = Color.White.copy(alpha = 0.85f),
                                modifier = Modifier.clickable { onDerivationsClick() }
                            ) {
                                Text(
                                    text = "📐 $derivationCount Proofs",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = Color(0xFFC62828),
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }
                }
            }

            // Bookmark Button
            IconButton(
                onClick = onBookmarkClick,
                modifier = Modifier.size(38.dp)
            ) {
                Icon(
                    painter = painterResource(
                        id = if (isBookmarked) R.drawable.ic_star else R.drawable.ic_star_border
                    ),
                    contentDescription = if (isBookmarked) "Unbookmark" else "Bookmark",
                    tint = if (isBookmarked) Color(0xFFF57F17) else Color(0xFF9E9E9E),
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}
