package com.royals.class11physics

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
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
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class FormulaSubTab(val title: String) {
    FORMULAS("Cheat Sheet"),
    CONSTANTS("Physical Constants"),
    DIMENSIONS("Units & Dimensions")
}

@OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)
@Composable
fun FormulaHubScreen(
    bookmarkedFormulas: Set<String>,
    onToggleBookmark: (String) -> Unit,
    initialChapterFilter: Int = 0,
    onOpenChapter: ((Int) -> Unit)? = null
) {
    val context = LocalContext.current
    val clipboardManager = LocalClipboardManager.current
    var selectedTab by remember { mutableIntStateOf(0) }
    var searchQuery by remember { mutableStateOf("") }
    var selectedChapterFilter by remember(initialChapterFilter) { mutableIntStateOf(initialChapterFilter) }
    val chapterMap = remember { chapters.associateBy { it.number } }

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        androidx.compose.material3.TabRow(
            selectedTabIndex = selectedTab,
            containerColor = MaterialTheme.colorScheme.surface,
            contentColor = MaterialTheme.colorScheme.primary
        ) {
            FormulaSubTab.values().forEachIndexed { index, tab ->
                Tab(
                    selected = selectedTab == index,
                    onClick = {
                        selectedTab = index
                        searchQuery = ""
                    },
                    text = {
                        Text(
                            text = tab.title,
                            fontWeight = if (selectedTab == index) FontWeight.Bold else FontWeight.Medium,
                            fontSize = 13.sp,
                            maxLines = 1
                        )
                    }
                )
            }
        }

        // Search Bar
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
                    placeholder = {
                        val placeholder = when (selectedTab) {
                            0 -> "Search formulas, topics or chapters..."
                            1 -> "Search physical constants (G, g, R, c...)"
                            else -> "Search quantities, dimensions or units..."
                        }
                        Text(placeholder, fontSize = 14.sp)
                    },
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

                // Chapter filter chips for Tab 0 (Formulas)
                if (selectedTab == 0) {
                    Spacer(modifier = Modifier.height(8.dp))
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
                        contentPadding = PaddingValues(vertical = 4.dp)
                    ) {
                        item {
                            FilterChip(
                                selected = selectedChapterFilter == 0,
                                onClick = { selectedChapterFilter = 0 },
                                label = { Text("All Chapters", fontSize = 12.sp) },
                                shape = RoundedCornerShape(16.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                        item {
                            FilterChip(
                                selected = selectedChapterFilter == -1,
                                onClick = { selectedChapterFilter = -1 },
                                label = { Text("Saved ⭐ (${bookmarkedFormulas.size})", fontSize = 12.sp) },
                                shape = RoundedCornerShape(16.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primaryContainer,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimaryContainer
                                )
                            )
                        }
                        items(chapters) { chapter ->
                            FilterChip(
                                selected = selectedChapterFilter == chapter.number,
                                onClick = { selectedChapterFilter = chapter.number },
                                label = { Text("Ch ${chapter.number}: ${chapter.title.take(16)}...", fontSize = 12.sp) },
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
        }

        // Tab Content
        when (selectedTab) {
            0 -> {
                // Formulas Cheat Sheet List
                val filteredFormulas = remember(searchQuery, selectedChapterFilter, bookmarkedFormulas) {
                    val query = searchQuery.trim().lowercase()
                    physicsFormulas.filter { formula ->
                        val chUnit = chapterMap[formula.chapterNumber]?.unit.orEmpty().lowercase()
                        val chNumStr = formula.chapterNumber.toString()
                        val matchesSearch = query.isBlank() ||
                            formula.topic.lowercase().contains(query) ||
                            formula.formula.lowercase().contains(query) ||
                            formula.description.lowercase().contains(query) ||
                            formula.chapterTitle.lowercase().contains(query) ||
                            chUnit.contains(query) ||
                            formula.siUnit.lowercase().contains(query) ||
                            formula.dimensionalFormula.lowercase().contains(query) ||
                            formula.symbolMeanings.lowercase().contains(query) ||
                            chNumStr == query ||
                            query == "ch $chNumStr" ||
                            query == "chapter $chNumStr"

                        val matchesFilter = when (selectedChapterFilter) {
                            0 -> true
                            -1 -> formula.id in bookmarkedFormulas
                            else -> formula.chapterNumber == selectedChapterFilter
                        }

                        matchesSearch && matchesFilter
                    }
                }

                if (filteredFormulas.isEmpty()) {
                    EmptyFormulaState(
                        isBookmarkFilter = selectedChapterFilter == -1,
                        searchQuery = searchQuery,
                        onReset = {
                            searchQuery = ""
                            selectedChapterFilter = 0
                        }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        items(filteredFormulas, key = { it.id }) { formulaItem ->
                            val isBookmarked = formulaItem.id in bookmarkedFormulas
                            FormulaCard(
                                item = formulaItem,
                                isBookmarked = isBookmarked,
                                onBookmarkClick = { onToggleBookmark(formulaItem.id) },
                                onOpenChapter = onOpenChapter,
                                onCopyClick = {
                                    val copyText = "${formulaItem.topic}\nFormula: ${formulaItem.formula}\nUnits: ${formulaItem.siUnit}\nDimensions: ${formulaItem.dimensionalFormula}\n${formulaItem.symbolMeanings}"
                                    clipboardManager.setText(AnnotatedString(copyText))
                                    Toast.makeText(context, "Formula copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                onShareClick = {
                                    val shareText = "⚡ Class 11 Physics Formula: ${formulaItem.topic}\n\n${formulaItem.formula}\n\n• SI Unit: ${formulaItem.siUnit}\n• Dimensions: ${formulaItem.dimensionalFormula}\n• Where: ${formulaItem.symbolMeanings}\n\nFrom Class 11 Physics Guide app."
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, shareText)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Share Formula via"))
                                }
                            )
                        }
                    }
                }
            }
            1 -> {
                // Physical Constants
                val filteredConstants = remember(searchQuery) {
                    val query = searchQuery.trim().lowercase()
                    physicalConstants.filter {
                        query.isBlank() ||
                            it.name.lowercase().contains(query) ||
                            it.symbol.lowercase().contains(query) ||
                            it.category.lowercase().contains(query) ||
                            it.unit.lowercase().contains(query) ||
                            it.value.lowercase().contains(query)
                    }
                }

                if (filteredConstants.isEmpty()) {
                    EmptyGenericState(
                        iconRes = R.drawable.ic_calculate,
                        title = "No Constants Found",
                        description = "No physical constants match \"$searchQuery\". Try checking the symbol (e.g. G, g, R, c, h) or clear search.",
                        onReset = { searchQuery = "" }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredConstants, key = { it.symbol + it.name }) { constant ->
                            ConstantCard(
                                constant = constant,
                                onCopyClick = {
                                    val text = "${constant.name} (${constant.symbol}) = ${constant.value} ${constant.unit}"
                                    clipboardManager.setText(AnnotatedString(text))
                                    Toast.makeText(context, "Constant copied!", Toast.LENGTH_SHORT).show()
                                },
                                onShareClick = {
                                    val text = "🔬 Physical Constant: ${constant.name} (${constant.symbol}) = ${constant.value} ${constant.unit} [${constant.category}]\n\nShared from Class 11 Physics Guide app."
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, text)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Share Constant via"))
                                }
                            )
                        }
                    }
                }
            }
            2 -> {
                // Units and Dimensions
                val filteredDimensions = remember(searchQuery) {
                    val query = searchQuery.trim().lowercase()
                    quantityDimensions.filter {
                        query.isBlank() ||
                            it.quantity.lowercase().contains(query) ||
                            it.symbol.lowercase().contains(query) ||
                            it.siUnit.lowercase().contains(query) ||
                            it.dimensionalFormula.lowercase().contains(query) ||
                            it.formula.lowercase().contains(query)
                    }
                }

                if (filteredDimensions.isEmpty()) {
                    EmptyGenericState(
                        iconRes = R.drawable.ic_calculate,
                        title = "No Dimensions Found",
                        description = "No physical quantities match \"$searchQuery\". Try searching by name, symbol, or unit (e.g. Force, Joule, Pascal).",
                        onReset = { searchQuery = "" }
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        items(filteredDimensions, key = { it.quantity }) { dimension ->
                            DimensionCard(
                                dimension = dimension,
                                onCopyClick = {
                                    val text = "${dimension.quantity} (${dimension.symbol})\nFormula: ${dimension.formula}\nSI Unit: ${dimension.siUnit}\nDimensions: ${dimension.dimensionalFormula}"
                                    clipboardManager.setText(AnnotatedString(text))
                                    Toast.makeText(context, "Copied to clipboard!", Toast.LENGTH_SHORT).show()
                                },
                                onShareClick = {
                                    val text = "📐 Physical Quantity & Dimensions: ${dimension.quantity} (${dimension.symbol})\nFormula: ${dimension.formula}\nSI Unit: ${dimension.siUnit}\nDimensions: ${dimension.dimensionalFormula}\n\nShared from Class 11 Physics Guide app."
                                    val sendIntent = Intent().apply {
                                        action = Intent.ACTION_SEND
                                        putExtra(Intent.EXTRA_TEXT, text)
                                        type = "text/plain"
                                    }
                                    context.startActivity(Intent.createChooser(sendIntent, "Share Dimensions via"))
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun FormulaCard(
    item: FormulaItem,
    isBookmarked: Boolean,
    onBookmarkClick: () -> Unit,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit,
    onOpenChapter: ((Int) -> Unit)? = null
) {
    val isDark = isSystemInDarkTheme()

    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.45f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            // Header: Chapter Badge + Topic + Bookmark
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = MaterialTheme.colorScheme.primaryContainer
                ) {
                    Text(
                        text = "Ch ${item.chapterNumber}",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer,
                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                Surface(
                    shape = RoundedCornerShape(8.dp),
                    color = when (item.examImportance) {
                        "Crucial" -> if (isDark) Color(0xFF4A1010) else Color(0xFFFFEBEE)
                        "Must Know" -> if (isDark) Color(0xFF4A2B00) else Color(0xFFFFF3E0)
                        else -> if (isDark) Color(0xFF0F3615) else Color(0xFFE8F5E9)
                    }
                ) {
                    Text(
                        text = item.examImportance.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = when (item.examImportance) {
                            "Crucial" -> if (isDark) Color(0xFFFF8A80) else Color(0xFFC62828)
                            "Must Know" -> if (isDark) Color(0xFFFFB74D) else Color(0xFFE65100)
                            else -> if (isDark) Color(0xFF81C784) else Color(0xFF2E7D32)
                        },
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
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
                        contentDescription = "Bookmark Formula",
                        tint = if (isBookmarked) Color(0xFFF57F17) else MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Topic Name
            Text(
                text = item.topic,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = item.description,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Formula Box (Prominently styled)
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.surface,
                tonalElevation = 1.dp
            ) {
                Box(modifier = Modifier.padding(14.dp)) {
                    Text(
                        text = item.formula,
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            lineHeight = 22.sp
                        ),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Variable definitions & symbols
            Text(
                text = "Where: ${item.symbolMeanings}",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                fontSize = 12.sp,
                lineHeight = 16.sp
            )

            Spacer(modifier = Modifier.height(10.dp))

            // Badges row: SI Unit & Dimension
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                if (item.siUnit.isNotBlank()) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = "SI: ${item.siUnit}",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                if (item.dimensionalFormula.isNotBlank()) {
                    Surface(
                        shape = CircleShape,
                        color = MaterialTheme.colorScheme.tertiaryContainer.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = item.dimensionalFormula,
                            style = MaterialTheme.typography.labelSmall,
                            fontFamily = FontFamily.Monospace,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(10.dp))
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(4.dp))

            // Action row (Read Chapter, Copy, Share)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (onOpenChapter != null) {
                    TextButton(
                        onClick = { onOpenChapter(item.chapterNumber) },
                        contentPadding = PaddingValues(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "Read Chapter ${item.chapterNumber} 📖",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.width(1.dp))
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = onCopyClick,
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_content_copy),
                            contentDescription = "Copy Formula",
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
                            contentDescription = "Share Formula",
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun ConstantCard(
    constant: PhysicalConstant,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Symbol badge
            Surface(
                modifier = Modifier.size(48.dp),
                shape = RoundedCornerShape(12.dp),
                color = MaterialTheme.colorScheme.primaryContainer
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        text = constant.symbol,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }

            Spacer(modifier = Modifier.width(14.dp))

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = constant.name,
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${constant.value} ${constant.unit}",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold
                    ),
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(2.dp))
                Surface(
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.6f)
                ) {
                    Text(
                        text = constant.category,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant,
                        modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                    )
                }
            }

            IconButton(
                onClick = onCopyClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_content_copy),
                    contentDescription = "Copy Constant",
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
                    contentDescription = "Share Constant",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun DimensionCard(
    dimension: QuantityDimension,
    onCopyClick: () -> Unit,
    onShareClick: () -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        elevation = CardDefaults.cardElevation(defaultElevation = 1.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = dimension.quantity,
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "(${dimension.symbol})",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                Spacer(modifier = Modifier.height(3.dp))

                Text(
                    text = dimension.formula,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(6.dp))

                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.5f)
                    ) {
                        Text(
                            text = dimension.siUnit,
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSecondaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }

                    Surface(
                        shape = RoundedCornerShape(6.dp),
                        color = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Text(
                            text = dimension.dimensionalFormula,
                            style = MaterialTheme.typography.labelSmall.copy(
                                fontFamily = FontFamily.Monospace,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.onPrimaryContainer,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 3.dp)
                        )
                    }
                }
            }

            IconButton(
                onClick = onCopyClick,
                modifier = Modifier.size(36.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_content_copy),
                    contentDescription = "Copy Dimension",
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
                    contentDescription = "Share Dimension",
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
fun EmptyFormulaState(
    isBookmarkFilter: Boolean,
    searchQuery: String,
    onReset: () -> Unit
) {
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
                    id = if (isBookmarkFilter) R.drawable.ic_star_border else R.drawable.ic_calculate
                ),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = if (isBookmarkFilter) "No Saved Formulas Yet" else "No Formulas Found",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = if (isBookmarkFilter) {
                    "Tap the star ⭐ icon on any formula card to keep key equations handy for rapid exam revision."
                } else {
                    "No formulas match \"$searchQuery\". Try checking the spelling or resetting chapter filters."
                },
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onReset) {
                Text("Reset Filters")
            }
        }
    }
}

@Composable
fun EmptyGenericState(
    iconRes: Int,
    title: String,
    description: String,
    onReset: () -> Unit
) {
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
                painter = painterResource(id = iconRes),
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.primary.copy(alpha = 0.6f)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = title,
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium
            )
            Spacer(modifier = Modifier.height(6.dp))
            Text(
                text = description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(16.dp))
            OutlinedButton(onClick = onReset) {
                Text("Clear Search")
            }
        }
    }
}
