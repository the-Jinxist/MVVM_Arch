package com.neo.testtutorial.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.getOrAwaitValue
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

/**
 * @RunWith(AndroidJUnit4::class) is basically used to test Java/Kotlin code and we need that functionality
 * when executing our test code on the Android framework
 */

/**
 * @SmallTest is used to specify that we're writing unit tests
 * Integrated tests should be annotated with Medium Tests and
 * UI Tests should be annotated with Large Tests
 */

/**
 * @ExperimentalCoroutinesApi removes the warning on out test-optimized bad boy
 * ..@runBlockingTest
 */
@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class ShoppingDaoTest {

    /**
     * @instantTaskExecutor This rule makes sure everything runs on the main thread
     * ..so we can properly get the value of our LiveData-s
     */
    @get:Rule
    var instantTaskExecutor =  InstantTaskExecutorRule()

    private lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    /**
     * A new database is setup before each and every test case
     * ..providing every test case with a brand new database.
     *
     * Room.inMemoryDatabaseBuilder is used so that the database is
     * built on the RAM not on the persistence storage of the
     * Android device
     */

    @Before
    fun setup(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            ShoppingItemDatabase::class.java
        ).allowMainThreadQueries()
        .build()

        dao = database.shoppingDao()
    }

    @After
    fun tearDown(){
        database.close()
    }

    /**
     * {@runBlockingTest} has been optimized for testing, baby!
     */

    @Test
    fun insertShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name",
            1f,
            1,
            "url",
            id = 1
        )
        dao.insertShoppingItem(shoppingItem)

        /**
         * Live Data runs asynchronously which can cause race condition, I think.
         * So we need to wait and get the data to make sure we are aware of the
         * current state of the data. {LiveData.@getOrAwaitValue} is the answer, it waits
         * for the data to be updated from the data-source then sets the value
         */
        val allShoppingItem = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItem).contains(shoppingItem)
    }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem(
            "name",
            1f,
            1,
            "url",
            id = 1
        )
        /**
         * Add one item and delete the item immediately
         * ..so the DB will only contain nothing
         */
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao.observeAllShoppingItems().getOrAwaitValue()

        assertThat(allShoppingItems).isEmpty()
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem(
            "name",
            10f,
            2,
            "url",
            id = 1
        )
        val shoppingItem2 = ShoppingItem(
            "name",
            5.5f,
            4,
            "url",
            id = 2
        )
        val shoppingItem3 = ShoppingItem(
            "name",
            100f,
            0,
            "url",
            id = 3
        )

        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum = dao.observeTotalPrice().getOrAwaitValue()

        assertThat(totalPriceSum).isEqualTo((2 * 10f) + (4 * 5.5f))

    }

}