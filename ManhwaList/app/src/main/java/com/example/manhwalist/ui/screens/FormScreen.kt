package com.example.manhwalist.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.manhwalist.data.Manhwa
import com.example.manhwalist.viewmodel.ManhwaViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormScreen(
    manhwaId: Int? = null,
    viewModel: ManhwaViewModel,
    onBack: () -> Unit,
    onSuccess: () -> Unit
) {
    val statuses by viewModel.allStatuses.collectAsState()
    
    var title by remember { mutableStateOf("") }
    var author by remember { mutableStateOf("") }
    var synopsis by remember { mutableStateOf("") }
    var coverUrl by remember { mutableStateOf("") }
    var chapter by remember { mutableStateOf("0") }
    var selectedStatusId by remember { mutableIntStateOf(0) }
    
    var expanded by remember { mutableStateOf(false) }

    val existingManhwaState = manhwaId?.let { viewModel.getManhwaById(it).collectAsState(initial = null) }
    
    LaunchedEffect(existingManhwaState?.value) {
        existingManhwaState?.value?.let { manhwa ->
            title = manhwa.title
            author = manhwa.author
            synopsis = manhwa.synopsis
            coverUrl = manhwa.coverImageUrl
            chapter = manhwa.currentChapter.toString()
            selectedStatusId = manhwa.statusId
        }
    }
    
    LaunchedEffect(statuses) {
        if (selectedStatusId == 0 && statuses.isNotEmpty()) {
            selectedStatusId = statuses.first().statusId
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (manhwaId == null) "Tambah Manhwa" else "Ubah Manhwa") },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("Judul") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = author,
                onValueChange = { author = it },
                label = { Text("Penulis") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = coverUrl,
                onValueChange = { coverUrl = it },
                label = { Text("URL Gambar Sampul") },
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = chapter,
                onValueChange = { chapter = it },
                label = { Text("Bab Saat Ini") },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = { expanded = !expanded }
            ) {
                OutlinedTextField(
                    value = statuses.find { it.statusId == selectedStatusId }?.statusName ?: "",
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Status") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor().fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    statuses.forEach { status ->
                        DropdownMenuItem(
                            text = { Text(status.statusName) },
                            onClick = {
                                selectedStatusId = status.statusId
                                expanded = false
                            }
                        )
                    }
                }
            }
            
            OutlinedTextField(
                value = synopsis,
                onValueChange = { synopsis = it },
                label = { Text("Sinopsis") },
                modifier = Modifier.fillMaxWidth(),
                minLines = 3
            )
            
            Button(
                onClick = {
                    val manhwa = Manhwa(
                        manhwaId = manhwaId ?: 0,
                        title = title,
                        author = author,
                        synopsis = synopsis,
                        coverImageUrl = coverUrl,
                        currentChapter = chapter.toIntOrNull() ?: 0,
                        statusId = selectedStatusId
                    )
                    if (manhwaId == null) {
                        viewModel.insertManhwa(manhwa)
                    } else {
                        viewModel.updateManhwa(manhwa)
                    }
                    onSuccess()
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = title.isNotBlank() && author.isNotBlank()
            ) {
                Text(if (manhwaId == null) "Simpan" else "Perbarui")
            }
        }
    }
}
