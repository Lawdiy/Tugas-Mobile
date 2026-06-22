package com.example.manhwalist.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ManhwaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertStatus(status: ReadStatus)

    @Query("SELECT * FROM read_status")
    fun getAllStatuses(): Flow<List<ReadStatus>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertManhwa(manhwa: Manhwa)

    @Update
    suspend fun updateManhwa(manhwa: Manhwa)

    @Delete
    suspend fun deleteManhwa(manhwa: Manhwa)

    @Query("SELECT * FROM manhwa WHERE manhwaId = :id")
    fun getManhwaById(id: Int): Flow<Manhwa?>

    @Query("""
        SELECT manhwa.*, read_status.statusName 
        FROM manhwa 
        INNER JOIN read_status ON manhwa.statusId = read_status.statusId
    """)
    fun getAllManhwaWithStatus(): Flow<List<ManhwaWithStatus>>

    @Query("""
        SELECT manhwa.*, read_status.statusName 
        FROM manhwa 
        INNER JOIN read_status ON manhwa.statusId = read_status.statusId
        WHERE manhwa.statusId = :statusId
    """)
    fun getManhwaByStatus(statusId: Int): Flow<List<ManhwaWithStatus>>
}
