package com.neo.testtutorial.hilt

import android.content.Context
import androidx.room.Room
import com.neo.testtutorial.data.local.ShoppingItemDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Named

@Module
@InstallIn(ApplicationComponent::class)
object TestAppModule {

    /**
     * Room.inMemoryDatabaseBuilder is used so that the database is
     * built on the RAM not on the persistence storage of the
     * Android device
     */

    @Provides
    @Named("test_db")
    fun providesInMemoryDb(
        @ApplicationContext context: Context
    ): ShoppingItemDatabase{
        return Room.inMemoryDatabaseBuilder(
            context,
            ShoppingItemDatabase::class.java)
            .allowMainThreadQueries()
            .build()

    }

}