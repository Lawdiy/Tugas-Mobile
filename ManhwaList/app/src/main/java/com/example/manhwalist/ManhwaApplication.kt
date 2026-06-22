package com.example.manhwalist

import android.app.Application
import com.example.manhwalist.data.ManhwaDatabase
import com.example.manhwalist.repository.ManhwaRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ManhwaApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { ManhwaDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { ManhwaRepository(database.manhwaDao()) }
}