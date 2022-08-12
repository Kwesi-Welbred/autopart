package com.android.automobile.data.dao

import androidx.room.*
import com.android.automobile.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(user: User)

    @Update
    suspend fun updateList(user: User)

    @Query("SELECT * FROM users ORDER BY uid ASC")
    fun getAllFromUsers(): Flow<List<User>>

    @Query("DELETE FROM users")
    suspend fun deleteAllFromUsers()
}