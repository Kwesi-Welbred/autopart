package com.android.automobile.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.welbtech.autopart.model.User
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(user: User)

    @Update
    suspend fun updateList(user: User)

    @Query("SELECT * FROM users ORDER BY uid ASC")
    fun getAllFromUsers(): Flow<List<User>>

    @Query("SELECT *FROM users WHERE email =:email AND password =:password")
     fun userEmailPassword(email:String, password: String):LiveData<List<User>>

    @Query("DELETE FROM users")
    suspend fun deleteAllFromUsers()
}