package com.android.automobile.data.source.local

import com.android.automobile.data.dao.CarDao
import com.android.automobile.data.dao.CoverDao
import com.android.automobile.data.dao.MotorDoa
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.CoverPage
import com.android.automobile.model.MotorAccessories
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class DatabaseImpl @Inject constructor(
    private val motorDao: MotorDoa,
    private val carDao: CarDao,
    private val coverDao: CoverDao
) : MotorDoa, CarDao, CoverDao {
    override suspend fun insertToRoom(cars: CarAccessories) {
        return carDao.insertToRoom(cars)
    }

    override suspend fun updateList(cars: CarAccessories) {
        return carDao.updateList(cars)
    }

    override fun getAllFromCars(): Flow<List<CarAccessories>> {
        return carDao.getAllFromCars()
    }

    override suspend fun deleteAllFromCars() {
        return carDao.deleteAllFromCars()
    }

    override suspend fun insertToRoom(coverPage: CoverPage) {
        return coverDao.insertToRoom(coverPage)
    }

    override suspend fun updateList(coverPage: CoverPage) {
        return coverDao.updateList(coverPage)
    }

    override fun getAllFromCover(): Flow<List<CoverPage>> {
        return coverDao.getAllFromCover()
    }

    override suspend fun deleteFromCover() {
        return coverDao.deleteFromCover()
    }

    override suspend fun insertToRoom(motor: MotorAccessories) {
        return motorDao.insertToRoom(motor)
    }

    override suspend fun updateList(motor: MotorAccessories) {
        return motorDao.updateList(motor)
    }

    override fun getAllFromMotor(): Flow<List<MotorAccessories>> {
        return motorDao.getAllFromMotor()
    }

    override suspend fun deleteFromMotor() {
        return motorDao.deleteFromMotor()
    }
}
