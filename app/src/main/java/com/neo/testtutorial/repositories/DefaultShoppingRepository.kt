package com.neo.testtutorial.repositories

import androidx.lifecycle.LiveData
import com.neo.testtutorial.data.local.ShoppingDao
import com.neo.testtutorial.data.network.PixabayAPI
import com.neo.testtutorial.data.network.Resource
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.domain.network.ImageResponse
import retrofit2.Response
import java.lang.Exception
import javax.inject.Inject

class DefaultShoppingRepository @Inject constructor(
    private val shoppingDao: ShoppingDao,
    private val pixabayAPI: PixabayAPI): ShoppingRepository {

    override suspend fun insertShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.insertShoppingItem(shoppingItem)
    }

    override suspend fun deleteShoppingItem(shoppingItem: ShoppingItem) {
        shoppingDao.deleteShoppingItem(shoppingItem)
    }

    override fun observeAllShoppingItems(): LiveData<List<ShoppingItem>> {
        return shoppingDao.observeAllShoppingItems()
    }

    override fun observeTotalPrice(): LiveData<Float> {
        return shoppingDao.observeTotalPrice()
    }

    override suspend fun searchForImage(imageQuery: String): Resource<ImageResponse> {
        return try {
            val response = pixabayAPI.searchForImage(imageQuery)
            if(response.isSuccessful){
                response.body()?.let {
                    return@let Resource.success(it)
                } ?: Resource.error("An unknown error occurred", null)
            }else{
                Resource.error(response.errorBody().toString(), null)
            }
        }catch (e: Exception){
            Resource.error(e.toString(), null)
        }


    }
}