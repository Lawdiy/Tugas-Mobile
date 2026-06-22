package com.example.manhwalist.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [ReadStatus::class, Manhwa::class], version = 1, exportSchema = false)
abstract class ManhwaDatabase : RoomDatabase() {
    abstract fun manhwaDao(): ManhwaDao

    companion object {
        @Volatile
        private var INSTANCE: ManhwaDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ManhwaDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ManhwaDatabase::class.java,
                    "manhwa_database"
                )
                .addCallback(ManhwaDatabaseCallback(scope))
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class ManhwaDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch(Dispatchers.IO) {
                    val dao = database.manhwaDao()
                    dao.insertStatus(ReadStatus(statusName = "Sedang Dibaca"))
                    dao.insertStatus(ReadStatus(statusName = "Selesai"))
                    dao.insertStatus(ReadStatus(statusName = "Rencana Dibaca"))
                    dao.insertStatus(ReadStatus(statusName = "Berhenti"))
                }
            }
        }
    }
}
