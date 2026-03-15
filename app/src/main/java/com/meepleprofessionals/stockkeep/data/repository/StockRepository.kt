package com.meepleprofessionals.stockkeep.data.repository

import com.meepleprofessionals.stockkeep.data.database.StockItemDao
import com.meepleprofessionals.stockkeep.data.model.StockItem
import kotlinx.coroutines.flow.Flow

class StockRepository(private val stockItemDao: StockItemDao) {
    val allItems: Flow<List<StockItem>> = stockItemDao.getAllItems()

    suspend fun insert(item: StockItem): Long = stockItemDao.insert(item)

    suspend fun update(item: StockItem) = stockItemDao.update(item)

    suspend fun delete(item: StockItem) = stockItemDao.delete(item)

    suspend fun deleteAll() = stockItemDao.deleteAll()

    suspend fun getItemByBarcode(barcode: String): StockItem? = stockItemDao.getItemByBarcode(barcode)

    suspend fun incrementOrCreate(barcode: String): Long {
        val existing = stockItemDao.getItemByBarcode(barcode)
        return if (existing != null) {
            stockItemDao.update(existing.copy(quantity = existing.quantity + 1))
            existing.id
        } else {
            stockItemDao.insert(StockItem(barcode = barcode))
        }
    }

    suspend fun getCount(): Int = stockItemDao.getCount()
}
