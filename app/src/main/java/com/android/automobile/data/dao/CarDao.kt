package com.android.automobile.data.dao

import androidx.room.*
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.Cart
import com.android.automobile.model.ProductModel
import kotlinx.coroutines.flow.Flow


@Dao
interface CarDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(cars: CarAccessories)

    @Update
    suspend fun updateList(cars: CarAccessories)

    @Query("SELECT * FROM car_accessories ORDER BY id ASC")
    fun getAllFromCars(): Flow<List<CarAccessories>>

    @Query("DELETE FROM car_accessories")
    suspend fun deleteAllFromCars()
}
