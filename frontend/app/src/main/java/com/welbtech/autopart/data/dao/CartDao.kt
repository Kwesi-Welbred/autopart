package com.android.automobile.data.dao

import androidx.room.*
import com.welbtech.autopart.model.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIntoCart(cart: List<Cart>)

    @Update
    suspend fun updateList(cart: Cart)

    @Query("SELECT * FROM cart ORDER BY id ASC")
    fun getAllFromCart(): Flow<List<Cart>>

    @Query("DELETE FROM cart")
    suspend fun deleteAllFromCart()

    @Delete
    suspend fun delete(cart: Cart)
}