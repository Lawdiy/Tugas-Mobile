package com.example.manhwalist.repository

import com.example.manhwalist.data.*
import kotlinx.coroutines.flow.Flow

class ManhwaRepository(private val manhwaDao: ManhwaDao) {
    val allStatuses: Flow<List<ReadStatus>> = manhwaDao.getAllStatuses()
    val allManhwa: Flow<List<ManhwaWithStatus>> = manhwaDao.getAllManhwaWithStatus()

    fun getManhwaByStatus(statusId: Int): Flow<List<ManhwaWithStatus>> {
        return manhwaDao.getManhwaByStatus(statusId)
    }

    fun getManhwaById(id: Int): Flow<Manhwa?> {
        return manhwaDao.getManhwaById(id)
    }

    suspend fun insertManhwa(manhwa: Manhwa) {
        manhwaDao.insertManhwa(manhwa)
    }

    suspend fun updateManhwa(manhwa: Manhwa) {
        manhwaDao.updateManhwa(manhwa)
    }

    suspend fun deleteManhwa(manhwa: Manhwa) {
        manhwaDao.deleteManhwa(manhwa)
    }
}
