package com.neo.testtutorial.repositories

import androidx.lifecycle.LiveData
import com.neo.testtutorial.data.network.Resource
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.domain.network.ImageResponse

interface ShoppingRepository {

    suspend fun insertShoppingItem(shoppingItem: ShoppingItem)

    suspend fun deleteShoppingItem(shoppingItem: ShoppingItem)

    fun observeAllShoppingItems(): LiveData<List<ShoppingItem>>

    fun observeTotalPrice(): LiveData<Float>

    suspend fun searchForImage(imageQuery: String): Resource<ImageResponse>

}