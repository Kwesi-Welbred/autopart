package com.welbtech.autopart.data.dao

import androidx.room.*
import com.welbtech.autopart.model.CarAccessories
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
