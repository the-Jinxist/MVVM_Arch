package com.neo.testtutorial.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.neo.testtutorial.domain.local.ShoppingItem

@Database(
    entities = [ShoppingItem::class],
    version= 1
)
abstract class ShoppingItemDatabase: RoomDatabase() {

    abstract fun shoppingDao(): ShoppingDao

}