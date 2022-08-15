package com.android.automobile.data.source.local

import android.content.Context
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.android.automobile.data.dao.CarDao
import com.android.automobile.data.dao.CoverDao
import com.android.automobile.data.dao.MotorDoa
import com.android.automobile.data.source.local.AppDatabase.Companion.INSTANCE
import com.android.automobile.model.CarAccessories
import com.android.automobile.model.CoverPage
import com.android.automobile.model.MotorAccessories
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.json.JSONArray
import org.json.JSONObject
import timber.log.Timber

@Suppress("BlockingMethodInNonBlockingContext")
class DatabaseCallback(
    private val application: Context,
    private val scope: CoroutineScope
) : RoomDatabase.Callback() {
    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        INSTANCE?.let { database ->
            scope.launch {
                populateCar(database.carDao())
                populateMotor(database.motorDao())
                populateCover(database.coverDao())
            }
        }
    }//

    private suspend fun populateCar(carDao: CarDao) {
        val bufferReader = application.assets.open("car_products.json").bufferedReader()
        val jsonString = bufferReader.use {
            it.readText()
        }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val price = jsonObject.getString("price")
            val imgUrl = jsonObject.getString("imgUrl")
            val cars = CarAccessories(
                imgSrcUrl = imgUrl,
                brandName = name,
                price = price,
                id = id.toInt()
            )
            carDao.insertToRoom(cars)
            Timber.d("image: $imgUrl  name: $price || version : $name  \n")
        }
    }

    private suspend fun populateMotor(motorDoa: MotorDoa) {
        val bufferReader = application.assets.open("motor_products.json").bufferedReader()
        val jsonString = bufferReader.use {
            it.readText()
        }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val price = jsonObject.getString("price")
            val imgUrl = jsonObject.getString("imgUrl")
            val motor = MotorAccessories(
                imgUrl = imgUrl,
                brandName = name,
                price = price,
                id = id.toInt()
            )
            motorDoa.insertToRoom(motor)
            Timber.d("image: $imgUrl  name: $price || version : $name  \n")
        }
    }

    private suspend fun populateCover(coverDao: CoverDao) {
        val bufferReader = application.assets.open("cover_page.json").bufferedReader()
        val jsonString = bufferReader.use {
            it.readText()
        }
        val jsonArray = JSONArray(jsonString)
        for (i in 0 until jsonArray.length()) {
            val jsonObject: JSONObject = jsonArray.getJSONObject(i)
            val id = jsonObject.getString("id")
            val name = jsonObject.getString("name")
            val price = jsonObject.getString("price")
            val imgUrl = jsonObject.getString("imgUrl")
            val page = CoverPage(
                imgUrl = imgUrl,
                brandName = name,
                price = price,
                id = id.toInt()
            )
            coverDao.insertToRoom(page)
            Timber.d("image: $imgUrl  name: $price || version : $name  \n")
        }
    }
}
