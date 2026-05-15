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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Class11PhysicsTheme {
                ChapterListScreen()
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChapterListScreen() {
    val context = LocalContext.current
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Class 11 Physics") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
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
                ChapterCard(chapter = chapter) {
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

@Composable
fun ChapterCard(chapter: Chapter, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
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
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = "${chapter.number}",
                        fontWeight = FontWeight.Bold,
                        fontSize = 20.sp,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Text(
                    text = "Chapter ${chapter.number}",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = chapter.title,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
    }
}
