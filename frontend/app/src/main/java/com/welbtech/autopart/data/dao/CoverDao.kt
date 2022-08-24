package com.android.automobile.data.dao

import androidx.room.*
import com.welbtech.autopart.model.CoverPage
import kotlinx.coroutines.flow.Flow

@Dao
interface CoverDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(coverPage: CoverPage)

    @Update
    suspend fun updateList(coverPage: CoverPage)

    @Query("SELECT * FROM cover_page ORDER BY id ASC")
    fun getAllFromCover(): Flow<List<CoverPage>>

    @Query("DELETE FROM cover_page")
    suspend fun deleteFromCover()
}
