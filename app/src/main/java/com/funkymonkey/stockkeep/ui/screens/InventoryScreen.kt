package com.funkymonkey.stockkeep.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.funkymonkey.stockkeep.data.model.StockItem
import com.funkymonkey.stockkeep.viewmodel.ExportStatus
import com.funkymonkey.stockkeep.viewmodel.ScanResult
import com.funkymonkey.stockkeep.viewmodel.StockViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockKeepApp(
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        navController = navController,
        startDestination = "inventory"
    ) {
        composable("inventory") { InventoryScreen(navController) }
        composable("scan") { ScanScreen(navController) }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InventoryScreen(
    navController: NavHostController,
    viewModel: StockViewModel = viewModel(factory = StockViewModel.Factory)
) {
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val items by viewModel.allItems.collectAsState(initial = emptyList())
    val scanResult by viewModel.scanResult.collectAsState()
    val exportStatus by viewModel.exportStatus.collectAsState()
    var showDeleteConfirm by remember { mutableStateOf<StockItem?>(null) }
    var showEditDialog by remember { mutableStateOf<StockItem?>(null) }

    // Handle scan results
    LaunchedEffect(scanResult) {
        scanResult?.let { result ->
            when (result) {
                is ScanResult.Success -> {
                    snackbarHostState.showSnackbar("Added: ${result.barcode}")
                    viewModel.clearScanResult()
                    navController.popBackStack()
                }
                is ScanResult.Error -> {
                    snackbarHostState.showSnackbar("Error: ${result.message}")
                    viewModel.clearScanResult()
                }
            }
        }
    }

    // Handle export status
    LaunchedEffect(exportStatus) {
        exportStatus?.let { status ->
            when (status) {
                is ExportStatus.Success -> {
                    snackbarHostState.showSnackbar("Exported ${status.count} items")
                    viewModel.clearExportStatus()
                }
                is ExportStatus.Error -> {
                    snackbarHostState.showSnackbar("Export failed: ${status.message}")
                    viewModel.clearExportStatus()
                }
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("StockKeep (${items.size} items)") },
                actions = {
                    IconButton(onClick = { viewModel.exportToCsv(share = true) }) {
                        Icon(Icons.Default.Share, contentDescription = "Export CSV")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("scan") }) {
                Icon(Icons.Default.Add, contentDescription = "Scan")
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (items.isEmpty()) {
            EmptyState(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
        } else {
            LazyColumn(
                modifier = Modifier.padding(padding)
            ) {
                items(items, key = { it.id }) { item ->
                    StockItemCard(
                        item = item,
                        onEdit = { showEditDialog = item },
                        onDelete = { showDeleteConfirm = item }
                    )
                }
            }
        }
    }

    // Delete confirmation dialog
    showDeleteConfirm?.let { item ->
        AlertDialog(
            onDismissRequest = { showDeleteConfirm = null },
            title = { Text("Delete Item?") },
            text = { Text("Delete ${item.barcode}?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        viewModel.deleteItem(item)
                        scope.launch { snackbarHostState.showSnackbar("Deleted ${item.barcode}") }
                        showDeleteConfirm = null
                    }
                ) { Text("Delete") }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteConfirm = null }) { Text("Cancel") }
            }
        )
    }

    // Edit dialog
    showEditDialog?.let { item ->
        EditItemDialog(
            item = item,
            onDismiss = { showEditDialog = null },
            onSave = { updated ->
                viewModel.updateItem(updated)
                showEditDialog = null
            }
        )
    }
}

@Composable
fun EmptyState(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No items yet",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = "Tap + to scan your first barcode",
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun StockItemCard(
    item: StockItem,
    onEdit: () -> Unit,
    onDelete: () -> Unit
) {
    val formatter = remember {
        DateTimeFormatter.ofPattern("MMM dd, HH:mm")
            .withZone(ZoneId.systemDefault())
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = item.barcode,
                    style = MaterialTheme.typography.titleMedium
                )
                item.name?.let {
                    Text(
                        text = it,
                        style = MaterialTheme.typography.bodyMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
                Text(
                    text = "Qty: ${item.quantity} • ${formatter.format(Instant.ofEpochMilli(item.scannedAt))}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onEdit) {
                Icon(Icons.Default.Edit, contentDescription = "Edit")
            }
            IconButton(onClick = onDelete) {
                Icon(Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Composable
fun EditItemDialog(
    item: StockItem,
    onDismiss: () -> Unit,
    onSave: (StockItem) -> Unit
) {
    var name by remember { mutableStateOf(item.name ?: "") }
    var description by remember { mutableStateOf(item.description ?: "") }
    var quantity by remember { mutableStateOf(item.quantity.toString()) }
    var notes by remember { mutableStateOf(item.notes ?: "") }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Item") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                androidx.compose.material3.OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") },
                    singleLine = true
                )
                androidx.compose.material3.OutlinedTextField(
                    value = description,
                    onValueChange = { description = it },
                    label = { Text("Description") },
                    singleLine = true
                )
                androidx.compose.material3.OutlinedTextField(
                    value = quantity,
                    onValueChange = { quantity = it },
                    label = { Text("Quantity") },
                    singleLine = true
                )
                androidx.compose.material3.OutlinedTextField(
                    value = notes,
                    onValueChange = { notes = it },
                    label = { Text("Notes") },
                    minLines = 2
                )
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onSave(
                        item.copy(
                            name = name.takeIf { it.isNotBlank() },
                            description = description.takeIf { it.isNotBlank() },
                            quantity = quantity.toIntOrNull() ?: item.quantity,
                            notes = notes.takeIf { it.isNotBlank() }
                        )
                    )
                }
            ) { Text("Save") }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
