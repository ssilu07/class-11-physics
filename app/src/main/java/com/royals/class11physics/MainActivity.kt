package com.royals.class11physics

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.royals.class11physics.ui.theme.Class11PhysicsTheme

data class Chapter(val number: Int, val title: String, val fileName: String)

val chapters = listOf(
    Chapter(1, "Physical World", "chapter_01.html"),
    Chapter(2, "Units and Measurements", "chapter_02.html"),
    Chapter(3, "Motion in a Straight Line", "chapter_03.html"),
    Chapter(4, "Motion in a Plane", "chapter_04.html"),
    Chapter(5, "Laws of Motion", "chapter_05.html"),
    Chapter(6, "Work, Energy and Power", "chapter_06.html"),
    Chapter(7, "System of Particles and Rotational Motion", "chapter_07.html"),
    Chapter(8, "Gravitation", "chapter_08.html"),
    Chapter(9, "Mechanical Properties of Solids", "chapter_09.html"),
    Chapter(10, "Mechanical Properties of Fluids", "chapter_10.html"),
    Chapter(11, "Thermal Properties of Matter", "chapter_11.html"),
    Chapter(12, "Thermodynamics", "chapter_12.html"),
    Chapter(13, "Kinetic Theory", "chapter_13.html"),
    Chapter(14, "Oscillations", "chapter_14.html"),
    Chapter(15, "Waves", "chapter_15.html")
)

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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        billingManager = BillingManager(this)
        billingManager.startConnection()
        rewardedAdManager = RewardedAdManager(this)
        enableEdgeToEdge()
        setContent {
            val isPremium by billingManager.isPremium.collectAsState()
            var unlockedChapters by remember { mutableStateOf(setOf<Int>()) }
            Class11PhysicsTheme {
                ChapterListScreen(
                    isPremium = isPremium,
                    unlockedChapters = unlockedChapters,
                    onPurchaseClick = {
                        if (billingManager.isReadyToPurchase()) {
                            billingManager.launchPurchaseFlow(this)
                        } else {
                            android.widget.Toast.makeText(
                                this,
                                "Please wait, connecting to Play Store...",
                                android.widget.Toast.LENGTH_SHORT
                            ).show()
                        }
                    },
                    onWatchAdToUnlock = { chapterNumber ->
                        rewardedAdManager.show(
                            activity = this,
                            onRewardEarned = { unlockedChapters = unlockedChapters + chapterNumber },
                            onAdUnavailable = {
                                android.widget.Toast.makeText(
                                    this,
                                    "Ad not ready yet, please try again in a moment.",
                                    android.widget.Toast.LENGTH_SHORT
                                ).show()
                            }
                        )
                    }
                )
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        billingManager.destroy()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen(
    isPremium: Boolean,
    unlockedChapters: Set<Int>,
    onPurchaseClick: () -> Unit,
    onWatchAdToUnlock: (Int) -> Unit
) {
    val context = LocalContext.current
    var lockedChapter by remember { mutableStateOf<Chapter?>(null) }

    lockedChapter?.let { chapter ->
        AlertDialog(
            onDismissRequest = { lockedChapter = null },
            title = { Text("Unlock ${chapter.title}") },
            text = { Text("This chapter is locked. Purchase once to unlock all chapters forever, or watch a short ad to unlock just this chapter.") },
            confirmButton = {
                Button(onClick = {
                    lockedChapter = null
                    onPurchaseClick()
                }) {
                    Text("Buy All - Unlock Forever")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    lockedChapter = null
                    onWatchAdToUnlock(chapter.number)
                }) {
                    Text("Watch Ad to Unlock")
                }
            }
        )
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Class 11 Physics") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        },
        bottomBar = {
            if (!isPremium) {
                BannerAdView()
            }
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(chapters) { chapter ->
                val isLocked = chapter.number > 3 && !isPremium && chapter.number !in unlockedChapters
                ChapterCard(chapter = chapter, isLocked = isLocked) {
                    if (isLocked) {
                        lockedChapter = chapter
                    } else {
                        val intent = Intent(context, WebViewActivity::class.java).apply {
                            putExtra("file_name", chapter.fileName)
                            putExtra("chapter_title", chapter.title)
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }
}

@Composable
fun ChapterCard(chapter: Chapter, isLocked: Boolean, onClick: () -> Unit) {
    val colorIndex = (chapter.number - 1).coerceIn(0, cardBgColors.lastIndex)
    val bgColor = if (isLocked) cardBgColors[colorIndex].copy(alpha = 0.5f) else cardBgColors[colorIndex]
    val numberColor = cardNumberColors[colorIndex]

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = if (isLocked) 1.dp else 4.dp),
        colors = CardDefaults.cardColors(containerColor = bgColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(52.dp),
                shape = MaterialTheme.shapes.medium,
                color = if (isLocked) Color(0xFFBDBDBD) else numberColor
            ) {
                Box(contentAlignment = Alignment.Center) {
                    if (isLocked) {
                        Icon(
                            imageVector = Icons.Filled.Lock,
                            contentDescription = "Locked",
                            tint = Color.White,
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Text(
                            text = "${chapter.number}",
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            color = Color.White
                        )
                    }
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Chapter ${chapter.number}",
                    style = MaterialTheme.typography.labelMedium,
                    color = if (isLocked) Color(0xFF9E9E9E) else numberColor,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold,
                    color = if (isLocked) Color(0xFF757575) else Color(0xFF212121)
                )
            }
            if (isLocked) {
                Text(
                    text = "LOCKED",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF9E9E9E),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
