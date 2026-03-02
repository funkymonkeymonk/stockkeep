package com.meepleprofessionals.stockkeep.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Instant

@Entity(tableName = "stock_items")
data class StockItem(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val barcode: String,
    val name: String? = null,
    val description: String? = null,
    val quantity: Int = 1,
    val scannedAt: Long = System.currentTimeMillis(),
    val notes: String? = null
)
