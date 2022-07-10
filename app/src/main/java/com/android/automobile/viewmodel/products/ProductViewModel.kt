package com.android.automobile.viewmodel.products

import android.util.Log
import androidx.lifecycle.*
import com.android.automobile.data.repository.ProductRepository
import com.android.automobile.model.*
import kotlinx.coroutines.launch


class ProductViewModel(
    private val repository: ProductRepository
) : ViewModel() {

    private var qty: Int = 0
    private var amt: Int = 0
    private val _currentExpenses = MutableLiveData<Int>()
    val currentExpenses: LiveData<Int>
        get() = _currentExpenses

    private val _cartItems = MutableLiveData<Cart>()
    val cartItems: LiveData<Cart>
        get() = _cartItems


    //Cars
    fun insertIntoDatabase(accessories: CarAccessories) = viewModelScope.launch {
        repository.insertToRoom(accessories)
    }

    fun updateDatabase(accessories: CarAccessories) = viewModelScope.launch {
        repository.updateList(accessories)
    }

    fun deleteFromCars() = viewModelScope.launch {
        repository.deleteAllFromCars()
    }

    val getAllFromCars: LiveData<List<CarAccessories>> = repository.getAllFromCars().asLiveData()


    //Motor
    fun insertIntoDatabase(accessories: MotorAccessories) = viewModelScope.launch {
        repository.insertToRoom(accessories)
    }

    fun updateDatabase(accessories: MotorAccessories) = viewModelScope.launch {
        repository.updateList(accessories)
    }

    fun deleteFromMotors() = viewModelScope.launch {
        repository.deleteAllFromCars()
    }

    val getAllFromMotors: LiveData<List<MotorAccessories>> =
        repository.getAllFromMotor().asLiveData()


    //Cover page
    fun insertIntoDatabase(accessories: CoverPage) = viewModelScope.launch {
        repository.insertToRoom(accessories)
    }

    fun updateDatabase(accessories: CoverPage) = viewModelScope.launch {
        repository.updateList(accessories)
    }

    fun deleteFromCover() = viewModelScope.launch {
        repository.deleteAllFromCars()
    }

    val getAllFromCover: LiveData<List<CoverPage>> = repository.getAllFromCover().asLiveData()


    //Cart
    fun insertIntoCart(accessories: List<Cart>) = viewModelScope.launch {
        repository.insertIntoCart(accessories)
        accessories.let {
            it.forEach { cart ->
                qty += cart.quantity
                amt += cart.price.toString().toInt()
                _currentExpenses.value = qty.times(amt)

                Log.d("productViewmodel.", "${currentExpenses.value}")
            }
        }

    }

    fun updateDatabase(accessories: Cart) = viewModelScope.launch {
        repository.updateList(accessories)
    }

    fun deleteAllFromCart() = viewModelScope.launch {
        repository.deleteAllFromCars()
    }

    fun deleteSingleCart(cart: Cart) = viewModelScope.launch {
        repository.delete(cart)
    }


    val getAllFromCart: LiveData<List<Cart>> = repository.getAllFromCart().asLiveData()



    //Favorites
    fun insertIntoDatabase(accessories: List<Favorites>) = viewModelScope.launch {
        repository.insertToRoom(accessories)
    }

    fun updateDatabase(accessories: Favorites) = viewModelScope.launch {
        repository.updateList(accessories)
    }

    fun deleteFromFav() = viewModelScope.launch {
        repository.deleteAllFromCars()
    }

    val getAllFromFav: LiveData<List<Favorites>> = repository.getAllFromFav().asLiveData()
    fun deleteSingleFav(fav: Favorites) = viewModelScope.launch {
        repository.delete(fav)
    }

}