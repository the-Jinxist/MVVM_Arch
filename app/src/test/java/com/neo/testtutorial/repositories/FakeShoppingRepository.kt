package com.neo.testtutorial.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.neo.testtutorial.data.network.Resource
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.domain.network.ImageResponse

class FakeShoppingRepository : ShoppingRepository{

    /**
     * Used as a database to take in our items and delete items too
     */
    private val shoppingItems = mutableListOf<ShoppingItem>()

    private val observableShoppingItems = MutableLiveData<List<ShoppingItem>>(shoppingItems)
    private val observableTotalPrice = MutableLiveData<Float>()

    private var shouldReturnNetworkError =  false

    /**
     * Should force the network error for the resource
     */
    private fun setShouldReturnNetworkError(value: Boolean){
        shouldReturnNetworkError = value
    }

    private fun refreshLiveData(){
        observableShoppingItems.postValue(shoppingItems)
        observableTotalPrice.postValue(getTotalPrice())
    }

    private fun getTotalPrice(): Float {
        return shoppingItems.sumByDouble { it.price.toDouble() }.toFloat()
    }

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.add(shoppingItem)
        refreshLiveData()
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingItems.remove(shoppingItem)
        refreshLiveData()
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return observableShoppingItems
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return observableTotalPrice
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return if(shouldReturnNetworkError){
            Resource.error("Error", null)
        } else {
            Resource.success(ImageResponse(listOf(), 0, 0))
        }
    }
}