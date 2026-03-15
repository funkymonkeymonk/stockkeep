package com.meepleprofessionals.stockkeep

import android.app.Application
import com.meepleprofessionals.stockkeep.data.database.StockDatabase
import com.meepleprofessionals.stockkeep.data.repository.StockRepository
import com.meepleprofessionals.stockkeep.update.UpdateManager
import com.google.firebase.FirebaseApp

class StockKeepApplication : Application() {
    val database by lazy { StockDatabase.getDatabase(this) }
    val repository by lazy { StockRepository(database.stockItemDao()) }
    
    override fun onCreate() {
        super.onCreate()
        
        // Initialize Firebase
        FirebaseApp.initializeApp(this)
        
        // Initialize update manager for in-app update checks
        UpdateManager.getInstance().initialize(this)
    }
}
