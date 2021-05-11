package com.neo.testtutorial.data.hilt

import android.content.Context
import androidx.room.Room
import com.neo.testtutorial.data.local.ShoppingDao
import com.neo.testtutorial.data.local.ShoppingItemDatabase
import com.neo.testtutorial.data.network.PixabayAPI
import com.neo.testtutorial.repositories.DefaultShoppingRepository
import com.neo.testtutorial.repositories.ShoppingRepository
import com.neo.testtutorial.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideShoppingItemDatabase(
        @ApplicationContext context: Context
    ): ShoppingItemDatabase{
        return Room.databaseBuilder(context, ShoppingItemDatabase::class.java, Constants.DATABASE_NAME).build()
    }

    @Singleton
    @Provides
    fun providesShoppingItemDao(
        database: ShoppingItemDatabase
    ): ShoppingDao{
        return database.shoppingDao()
    }

    @Singleton
    @Provides
    fun providesDefaultShoppingRepository(
        dao: ShoppingDao,
        api: PixabayAPI
    ): ShoppingRepository{
        return DefaultShoppingRepository(dao, api)
    }

    @Provides
    @Singleton
    fun providesPexelBayAPI(): PixabayAPI{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(Constants.BASE_URL)
            .build()
            .create(PixabayAPI::class.java)
    }

}