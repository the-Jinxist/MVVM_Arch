package com.neo.testtutorial.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.MainCoroutineRule
import com.neo.testtutorial.data.network.Status
import com.neo.testtutorial.getOrAwaitValueTest
import com.neo.testtutorial.repositories.FakeShoppingRepository
import com.neo.testtutorial.utils.Constants
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test


class ShoppingViewModelTest {

    /**
     * Used to make sure each test waits for the live data to produce the data
     * ..that it is observing
     */
    @get:Rule
    var instantTaskExecutor =  InstantTaskExecutorRule()

    /**
     * Applies the rule to all coroutines running in the test
     */
    @ExperimentalCoroutinesApi
    @get: Rule
    var mainCoroutineRule = MainCoroutineRule()

    private lateinit var viewModel: ShoppingViewModel

    /**
     * We use the FakeShoppingRepository we created before as it doesn't take
     * ..DAO and API constructor variables
     */
    @Before
    fun setup(){
        viewModel = ShoppingViewModel(FakeShoppingRepository())
    }

    @Test
    fun `insert shopping item with empty field, returns error`(){
        viewModel.insertShoppingItem("name", "", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)
    }

    @Test
    fun  `insert shopping item with a name that is too long, returns error`(){

        val string = buildString {
            for(i in 1..Constants.MAX_NAME_LENTGH + 1){
                append("a")
            }
        }

        viewModel.insertShoppingItem(string, "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }


    @Test
    fun  `insert shopping item with a price that is too long, returns error`(){

        val string = buildString {
            for(i in 1..Constants.MAX_PRICE_LENGTH + 1){
                append("5")
            }
        }

        viewModel.insertShoppingItem("name", "5", string)

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun  `insert shopping item with invalid amount, returns error`(){

        viewModel.insertShoppingItem("name", "99999999999999999999", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.ERROR)

    }

    @Test
    fun  `insert valid shopping item, returns success`(){

        viewModel.insertShoppingItem("name", "5", "3.0")

        val value = viewModel.insertShoppingItemStatus.getOrAwaitValueTest()

        assertThat(value.getContentIfNotHandled()?.status).isEqualTo(Status.SUCCESS)

    }

}