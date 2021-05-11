package com.neo.testtutorial.domain.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName= "shopping_items")
data class ShoppingItem(
    var name: String,
    var price: Float,
    var amount: Int,
    var imageUrl: String,

    @PrimaryKey(autoGenerate = true)
    var id: Int?

)