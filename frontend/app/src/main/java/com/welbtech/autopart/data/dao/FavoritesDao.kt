package com.android.automobile.data.dao

import androidx.room.*
import com.android.automobile.model.Favorites
import kotlinx.coroutines.flow.Flow


@Dao
interface FavoritesDao {
     @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(favorites: List<Favorites>)

    @Update
    suspend fun updateList(favorites: Favorites)

    @Query("SELECT * FROM favorites ORDER BY id ASC")
    fun getAllFromFav(): Flow<List<Favorites>>

    @Query("DELETE FROM favorites")
    suspend fun deleteAllFromFav()

    @Delete
    suspend fun delete(cart: Favorites)
}