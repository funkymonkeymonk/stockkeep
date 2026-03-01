package com.funkymonkey.stockkeep.viewmodel

import android.app.Application
import android.content.Intent
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.funkymonkey.stockkeep.StockKeepApplication
import com.funkymonkey.stockkeep.data.model.StockItem
import com.funkymonkey.stockkeep.data.repository.StockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class StockViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StockRepository = (application as StockKeepApplication).repository

    val allItems: Flow<List<StockItem>> = repository.allItems

    private val _scanResult = MutableStateFlow<ScanResult?>(null)
    val scanResult: StateFlow<ScanResult?> = _scanResult.asStateFlow()

    private val _exportStatus = MutableStateFlow<ExportStatus?>(null)
    val exportStatus: StateFlow<ExportStatus?> = _exportStatus.asStateFlow()

    fun scanBarcode(barcode: String) {
        viewModelScope.launch {
            val id = repository.incrementOrCreate(barcode)
            _scanResult.value = ScanResult.Success(barcode, id)
        }
    }

    fun updateItem(item: StockItem) {
        viewModelScope.launch {
            repository.update(item)
        }
    }

    fun deleteItem(item: StockItem) {
        viewModelScope.launch {
            repository.delete(item)
        }
    }

    fun clearScanResult() {
        _scanResult.value = null
    }

    fun exportToCsv(share: Boolean = false) {
        viewModelScope.launch {
            try {
                val items = repository.allItems.first()
                val csv = buildCsv(items)

                val filename = "stockkeep_export_${System.currentTimeMillis()}.csv"
                val file = java.io.File(getApplication<Application>().filesDir, filename)
                file.writeText(csv)

                if (share) {
                    shareFile(file)
                }

                _exportStatus.value = ExportStatus.Success(file.absolutePath, items.size)
            } catch (e: Exception) {
                _exportStatus.value = ExportStatus.Error(e.message ?: "Export failed")
            }
        }
    }

    private fun buildCsv(items: List<StockItem>): String {
        val header = "ID,Barcode,Name,Description,Quantity,Scanned At,Notes\n"
        
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault())

        return header + items.joinToString("\n") { item ->
            val scannedAt = Instant.ofEpochMilli(item.scannedAt)
            val name = item.name?.replace("\"", "\"\"") ?: ""
            val desc = item.description?.replace("\"", "\"\"") ?: ""
            val notes = item.notes?.replace("\"", "\"\"") ?: ""
            "${item.id},\"${item.barcode}\",\"$name\",\"$desc\",${item.quantity},\"${formatter.format(scannedAt)}\",\"$notes\""
        }
    }

    private fun shareFile(file: java.io.File) {
        val context = getApplication<Application>()
        val uri = androidx.core.content.FileProvider.getUriForFile(
            context,
            "${context.packageName}.fileprovider",
            file
        )

        val shareIntent = Intent(Intent.ACTION_SEND).apply {
            type = "text/csv"
            putExtra(Intent.EXTRA_STREAM, uri)
            putExtra(Intent.EXTRA_SUBJECT, "StockKeep Export")
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
        }

        val chooser = Intent.createChooser(shareIntent, "Share CSV")
        chooser.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(chooser)
    }

    fun clearExportStatus() {
        _exportStatus.value = null
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                StockViewModel(application)
            }
        }
    }
}

sealed class ScanResult {
    data class Success(val barcode: String, val id: Long) : ScanResult()
    data class Error(val message: String) : ScanResult()
}

sealed class ExportStatus {
    data class Success(val filepath: String, val count: Int) : ExportStatus()
    data class Error(val message: String) : ExportStatus()
}
