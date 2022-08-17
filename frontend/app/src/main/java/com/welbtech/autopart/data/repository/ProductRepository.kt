package com.android.automobile.data.repository

import com.android.automobile.data.dao.*
import com.android.automobile.model.*
import com.welbtech.autopart.data.dao.CarDao
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class  ProductRepository @Inject constructor(
    private val motorDao: MotorDoa,
    private val carDao: CarDao,
    private val coverDao: CoverDao,
    private val favoritesDao: FavoritesDao,
    private val cartDao: CartDao
) : MotorDoa, CarDao, CoverDao, FavoritesDao, CartDao {
    override suspend fun insertToRoom(cars: CarAccessories) {
        return carDao.insertToRoom(cars)
    }

    override suspend fun updateList(cars: CarAccessories) {
        return carDao.updateList(cars)
    }

    override fun getAllFromCars(): Flow<List<CarAccessories>> {
        return carDao.getAllFromCars()
    }

    override suspend fun insertIntoCart(cart: List<Cart>) {
        return cartDao.insertIntoCart(cart)
    }

    override suspend fun updateList(cart: Cart) {
       return cartDao.updateList(cart)
    }

    override fun getAllFromCart(): Flow<List<Cart>> {
      return cartDao.getAllFromCart()
    }

    override suspend fun deleteAllFromCart() {
        return cartDao.deleteAllFromCart()
    }

    override suspend fun insertToRoom(favorites: List<Favorites>) {
        return favoritesDao.insertToRoom(favorites)
    }

    override suspend fun updateList(favorites: Favorites) {
        return favoritesDao.updateList(favorites)
    }

    override fun getAllFromFav(): Flow<List<Favorites>> {
        return favoritesDao.getAllFromFav()
    }

    override suspend fun deleteAllFromFav() {
      return favoritesDao.deleteAllFromFav()
    }

    override suspend fun delete(cart: Favorites) {
        return favoritesDao.delete(cart)
    }


    override suspend fun deleteAllFromCars() {
        return carDao.deleteAllFromCars()
    }

    override suspend fun delete(cart: Cart) {
       cartDao.delete(cart)
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
