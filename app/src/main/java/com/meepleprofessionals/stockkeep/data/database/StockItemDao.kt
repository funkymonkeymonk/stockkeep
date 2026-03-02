package com.meepleprofessionals.stockkeep.data.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.meepleprofessionals.stockkeep.data.model.StockItem
import kotlinx.coroutines.flow.Flow

@Dao
interface StockItemDao {
    @Query("SELECT * FROM stock_items ORDER BY scannedAt DESC")
    fun getAllItems(): Flow<List<StockItem>>

    @Query("SELECT * FROM stock_items WHERE barcode = :barcode")
    suspend fun getItemByBarcode(barcode: String): StockItem?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: StockItem): Long

    @Update
    suspend fun update(item: StockItem)

    @Delete
    suspend fun delete(item: StockItem)

    @Query("DELETE FROM stock_items")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM stock_items")
    suspend fun getCount(): Int
}
