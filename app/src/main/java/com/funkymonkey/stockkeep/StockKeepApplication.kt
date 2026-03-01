package com.funkymonkey.stockkeep

import android.app.Application
import com.funkymonkey.stockkeep.data.database.StockDatabase
import com.funkymonkey.stockkeep.data.repository.StockRepository

class StockKeepApplication : Application() {
    val database by lazy { StockDatabase.getDatabase(this) }
    val repository by lazy { StockRepository(database.stockItemDao()) }
}
