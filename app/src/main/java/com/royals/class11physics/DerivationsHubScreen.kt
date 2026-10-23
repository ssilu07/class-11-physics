package com.royals.class11physics

import android.content.Intent
import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material.icons.filled.Search
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class DerivationFilter(val label: String) {
    ALL("All Derivations"),
    FIVE_MARKS("5-Markers 🏆"),
    BOOKMARKED("Saved ⭐")
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun DerivationsHubScreen(
    bookmarkedDerivations: Set<String>,
    onToggleBookmark: (String) -> Unit,
    initialChapterFilter: Int = 0,
    onOpenChapter: ((Int) -> Unit)? = null
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var searchQuery by remember { mutableStateOf("") }
    var selectedFilter by remember { mutableStateOf(DerivationFilter.ALL) }
    var selectedChapterFilter by remember(initialChapterFilter) { mutableIntStateOf(initialChapterFilter) }
    var expandedDerivationIds by remember { mutableStateOf(setOf<String>()) }
    val chapterMap = remember { chapters.associateBy { it.number } }
    val derivationChapters = remember { topDerivations.map { it.chapterNumber }.distinct().sorted() }

    Column(modifier = Modifier.fillMaxSize()) {
        // Search & Filter Header
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
                    placeholder = { Text("Search derivations (e.g. projectile, banking, bernoulli...)", fontSize = 14.sp) },
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

                // Status Filter Chips (All, 5-Markers, Saved)
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    items(DerivationFilter.values()) { filter ->
                        val isSelected = selectedFilter == filter
                        val label = when (filter) {
                            DerivationFilter.ALL -> "All (${topDerivations.size})"
                            DerivationFilter.FIVE_MARKS -> "5-Markers 🏆"
                            DerivationFilter.BOOKMARKED -> "Saved ⭐ (${bookmarkedDerivations.size})"
                        }
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedFilter = filter },
                            label = { Text(label, fontSize = 12.sp, fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(6.dp))

                // Chapter Filter Chips
                LazyRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 2.dp)
                ) {
                    item {
                        FilterChip(
                            selected = selectedChapterFilter == 0,
                            onClick = { selectedChapterFilter = 0 },
                            label = { Text("All Chapters", fontSize = 11.5.sp) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                    items(derivationChapters) { chNum ->
                        val chapter = chapterMap[chNum]
                        val isSelected = selectedChapterFilter == chNum
                        val label = "Ch $chNum: ${chapter?.title?.take(14) ?: ""}..."
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedChapterFilter = if (isSelected) 0 else chNum },
                            label = { Text(label, fontSize = 11.5.sp) },
                            shape = RoundedCornerShape(16.dp),
                            colors = FilterChipDefaults.filterChipColors(
                                selectedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                                selectedLabelColor = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        )
                    }
                }
            }
        }

        // Filtered derivations
        val filteredDerivations = remember(searchQuery, selectedFilter, selectedChapterFilter, bookmarkedDerivations) {
            val query = searchQuery.trim().lowercase()
            topDerivations.filter { derivation ->
                val chUnit = chapterMap[derivation.chapterNumber]?.unit.orEmpty().lowercase()
                val chNumStr = derivation.chapterNumber.toString()
                val matchesSearch = query.isBlank() ||
                    derivation.title.lowercase().contains(query) ||
                    derivation.chapterTitle.lowercase().contains(query) ||
                    derivation.statement.lowercase().contains(query) ||
                    derivation.finalExpression.lowercase().contains(query) ||
                    derivation.examTips.lowercase().contains(query) ||
                    chUnit.contains(query) ||
                    chNumStr == query ||
                    query == "ch $chNumStr" ||
                    query == "chapter $chNumStr" ||
                    derivation.steps.any { step ->
                        step.title.lowercase().contains(query) ||
                        step.explanation.lowercase().contains(query) ||
                        step.equation.lowercase().contains(query)
                    }

                val matchesStatus = when (selectedFilter) {
                    DerivationFilter.ALL -> true
                    DerivationFilter.FIVE_MARKS -> derivation.marksWeightage.contains("5")
                    DerivationFilter.BOOKMARKED -> derivation.id in bookmarkedDerivations
                }

                val matchesChapter = selectedChapterFilter == 0 || derivation.chapterNumber == selectedChapterFilter

                matchesSearch && matchesStatus && matchesChapter
            }
        }

        if (filteredDerivations.isEmpty()) {
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
                    Icon(
                        painter = painterResource(
                            id = if (selectedFilter == DerivationFilter.BOOKMARKED) R.drawable.ic_star_border else R.drawable.ic_science
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(56.dp),
                        tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    Text(
                        text = if (selectedFilter == DerivationFilter.BOOKMARKED) "No Saved Derivations" else "No Derivations Found",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.titleMedium
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = if (selectedFilter == DerivationFilter.BOOKMARKED) {
                            "Star key derivations to build your personalized exam-night cheat sheet."
                        } else {
                            "No derivations match \"$searchQuery\". Try checking the spelling or resetting filters."
                        },
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        textAlign = TextAlign.Center
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedButton(onClick = {
                        searchQuery = ""
                        selectedFilter = DerivationFilter.ALL
                        selectedChapterFilter = 0
                    }) {
                        Text("Reset Filters")
                    }
                }
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(filteredDerivations, key = { it.id }) { derivation ->
                    val isExpanded = derivation.id in expandedDerivationIds
                    val isBookmarked = derivation.id in bookmarkedDerivations

                    DerivationCard(
                        derivation = derivation,
                        isExpanded = isExpanded,
                        isBookmarked = isBookmarked,
                        onOpenChapter = onOpenChapter,
                        onToggleExpand = {
                            expandedDerivationIds = if (isExpanded) {
                                expandedDerivationIds - derivation.id
                            } else {
                                expandedDerivationIds + derivation.id
                            }
                        },
                        onBookmarkClick = { onToggleBookmark(derivation.id) },
                        onCopyClick = {
                            val textBuilder = StringBuilder()
                            textBuilder.append("📘 ${derivation.title}\n")
                            textBuilder.append("Chapter: Ch ${derivation.chapterNumber} - ${derivation.chapterTitle} (${derivation.marksWeightage})\n\n")
                            textBuilder.append("Aim/Statement:\n${derivation.statement}\n\n")
                            textBuilder.append("📐 Diagram Setup:\n${derivation.diagramDescription}\n\n")
                            textBuilder.append("--- STEP-BY-STEP DERIVATION ---\n")
                            derivation.steps.forEach { step ->
                                textBuilder.append("Step ${step.stepNumber}: ${step.title}\n")
                                textBuilder.append("${step.explanation}\n")
                                textBuilder.append("${step.equation}\n\n")
                            }
                            textBuilder.append("🎯 Final Result:\n${derivation.finalExpression}\n\n")
                            textBuilder.append("Exam Tips:\n${derivation.examTips}\n")

                            clipboardManager.setText(AnnotatedString(textBuilder.toString()))
                            Toast.makeText(context, "Full derivation copied to clipboard!", Toast.LENGTH_SHORT).show()
                        },
                        onShareClick = {
                            val shareText = "📘 Top Class 11 Physics Derivation: ${derivation.title}\n\n${derivation.statement}\n\n🎯 Final Expression:\n${derivation.finalExpression}\n\n💡 Pro Tip:\n${derivation.examTips}\n\nShared from Class 11 Physics NCERT Guide app."
                            val sendIntent = Intent().apply {
                                action = Intent.ACTION_SEND
                                putExtra(Intent.EXTRA_TEXT, shareText)
                                type = "text/plain"
                            }
                            context.startActivity(Intent.createChooser(sendIntent, "Share Derivation via"))
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun DerivationCard(
    derivation: Derivation,
    isExpanded: Boolean,
    isBookmarked: Boolean,
    onToggleExpand: () -> Unit,
    onBookmarkClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
    onOpenChapter: ((Int) -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(18.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.5.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header Tags: Chapter + Marks + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Ch ${derivation.chapterNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = if (isDark) Color(0xFF4A1010) else Color(0xFFFFEBEE)
                ) {
                    Text(
                        text = derivation.marksWeightage,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = if (isDark) Color(0xFFFF8A80) else Color(0xFFC62828),
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onBookmarkClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(
                            id = if (isBookmarked) R.drawable.ic_star else R.drawable.ic_star_border
                        ),
                        contentDescription = "Bookmark Derivation",
                        tint = if (isBookmarked) Color(0xFFF57F17) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            // Title
            Text(
                text = derivation.title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.ExtraBold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Statement / Aim
            Text(
                text = derivation.statement,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 13.sp,
                lineHeight = 18.sp
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Highlighted Final Expression Preview Box
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primary.copy(alpha = 0.08f),
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.3f))
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "🎯 FINAL EXPRESSION",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.ExtraBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = derivation.finalExpression,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 14.sp,
                            lineHeight = 20.sp
                        ),
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Expand/Collapse Button & Action buttons
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    onClick = onToggleExpand,
                    shape = RoundedCornerShape(12.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isExpanded) MaterialTheme.colorScheme.secondaryContainer else MaterialTheme.colorScheme.primaryContainer,
                        contentColor = if (isExpanded) MaterialTheme.colorScheme.onSecondaryContainer else MaterialTheme.colorScheme.onPrimaryContainer
                    ),
                    contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp)
                ) {
                    Text(
                        text = if (isExpanded) "Hide Proof ▲" else "View Proof (${derivation.steps.size} Steps) ▼",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                if (onOpenChapter != null) {
                    TextButton(
                        onClick = { onOpenChapter(derivation.chapterNumber) },
                        contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
                    ) {
                        Text(
                            text = "Read Ch ${derivation.chapterNumber} 📖",
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }

                Spacer(modifier = Modifier.weight(1f))

                IconButton(
                    onClick = onCopyClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_content_copy),
                        contentDescription = "Copy Derivation",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }

                IconButton(
                    onClick = onShareClick,
                    modifier = Modifier.size(36.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_share_24),
                        contentDescription = "Share Derivation",
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }

            // Expanded Step-by-Step Content
            AnimatedVisibility(
                visible = isExpanded,
                enter = expandVertically() + fadeIn(),
                exit = shrinkVertically() + fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                ) {
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
                    Spacer(modifier = Modifier.height(14.dp))

                    // Diagram Setup Guide
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.35f)
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "📐 EXAM DIAGRAM GUIDE",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = derivation.diagramDescription,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Step by step breakdown
                    derivation.steps.forEach { step ->
                        StepItemView(step = step)
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Key Assumptions
                    if (derivation.keyAssumptions.isNotEmpty()) {
                        Surface(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ) {
                            Column(modifier = Modifier.padding(12.dp)) {
                                Text(
                                    text = "📋 KEY ASSUMPTIONS & CONDITIONS",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                derivation.keyAssumptions.forEach { assumption ->
                                    Row(
                                        modifier = Modifier.padding(vertical = 2.dp),
                                        verticalAlignment = Alignment.Top
                                    ) {
                                        Text("• ", fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.primary)
                                        Text(
                                            text = assumption,
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurface,
                                            fontSize = 12.sp
                                        )
                                    }
                                }
                            }
                        }
                        Spacer(modifier = Modifier.height(12.dp))
                    }

                    // Exam Pro-Tips
                    Surface(
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        color = if (isDark) Color(0xFF332B00) else Color(0xFFFFF8E1),
                        border = androidx.compose.foundation.BorderStroke(1.dp, if (isDark) Color(0xFF806600) else Color(0xFFFFE082))
                    ) {
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(
                                text = "💡 BOARD EXAM PRO-TIP",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isDark) Color(0xFFFFD54F) else Color(0xFFF57F17)
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = derivation.examTips,
                                style = MaterialTheme.typography.bodySmall,
                                color = if (isDark) Color(0xFFFFF3E0) else Color(0xFF3E2723),
                                fontSize = 12.sp,
                                lineHeight = 17.sp
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun StepItemView(step: DerivationStep) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            // Step Number Badge + Title
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary
                ) {
                    Text(
                        text = "${step.stepNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(horizontal = 7.dp, vertical = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Text(
                    text = step.title,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = step.explanation,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.5.sp,
                lineHeight = 17.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Mathematical Equation block
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colorScheme.surface,
                border = androidx.compose.foundation.BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.4f))
            ) {
                Box(modifier = Modifier.padding(10.dp)) {
                    Text(
                        text = step.equation,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            fontSize = 13.sp,
                            lineHeight = 19.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }
        }
    }
}
