package com.example.manhwalist.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.manhwalist.data.ManhwaWithStatus
import com.example.manhwalist.data.ReadStatus
import com.example.manhwalist.viewmodel.ManhwaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: ManhwaViewModel,
    onAddClick: () -> Unit,
    onManhwaClick: (Int) -> Unit
) {
    val manhwaList by viewModel.manhwaList.collectAsState()
    val statuses by viewModel.allStatuses.collectAsState()
    val selectedStatusId by viewModel.selectedStatusId.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Manhwa List") })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = onAddClick) {
                Icon(Icons.Default.Add, contentDescription = "Tambah Manhwa")
            }
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            FilterSection(
                statuses = statuses,
                selectedStatusId = selectedStatusId,
                onStatusSelected = { viewModel.selectStatus(it) }
            )
            
            if (manhwaList.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Manhwa tidak ditemukan. Silakan tambah data.")
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(manhwaList) { manhwa ->
                        ManhwaCard(manhwa = manhwa, onClick = { onManhwaClick(manhwa.manhwaId) })
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FilterSection(
    statuses: List<ReadStatus>,
    selectedStatusId: Int?,
    onStatusSelected: (Int?) -> Unit
) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        item {
            FilterChip(
                selected = selectedStatusId == null,
                onClick = { onStatusSelected(null) },
                label = { Text("Semua") }
            )
        }
        items(statuses) { status ->
            FilterChip(
                selected = selectedStatusId == status.statusId,
                onClick = { onStatusSelected(status.statusId) },
                label = { Text(status.statusName) }
            )
        }
    }
}

@Composable
fun ManhwaCard(manhwa: ManhwaWithStatus, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(8.dp)
                .heightIn(min = 120.dp)
        ) {
            AsyncImage(
                model = manhwa.coverImageUrl,
                contentDescription = null,
                modifier = Modifier
                    .width(80.dp)
                    .height(120.dp),
                contentScale = ContentScale.Crop
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Column {
                    Text(
                        text = manhwa.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                    Text(
                        text = "Author: ${manhwa.author}",
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 1,
                        overflow = androidx.compose.ui.text.style.TextOverflow.Ellipsis
                    )
                }
                
                Spacer(modifier = Modifier.weight(1f))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Ch. ${manhwa.currentChapter}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.SemiBold
                    )
                    Surface(
                        shape = MaterialTheme.shapes.extraSmall,
                        color = MaterialTheme.colorScheme.secondaryContainer
                    ) {
                        Text(
                            text = manhwa.statusName,
                            modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp),
                            style = MaterialTheme.typography.labelSmall
                        )
                    }
                }
            }
        }
    }
}
