package com.android.automobile.data.dao

import androidx.room.*
import com.welbtech.autopart.model.MotorAccessories
import kotlinx.coroutines.flow.Flow

@Dao
interface MotorDoa {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertToRoom(motor: MotorAccessories)

    @Update
    suspend fun updateList(motor: MotorAccessories)

    @Query("SELECT * FROM motor_accessories ORDER BY id ASC")
    fun getAllFromMotor(): Flow<List<MotorAccessories>>

    @Query("DELETE FROM motor_accessories")
    suspend fun deleteFromMotor()
}
