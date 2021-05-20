package com.neo.testtutorial.presentation.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.androiddevs.shoppinglisttestingyt.adapters.ShoppingItemAdapter
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.R
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.getOrAwaitValue
import com.neo.testtutorial.launchFragmentInHiltContainer
import com.neo.testtutorial.presentation.TestShoppingFragmentFactory
import com.neo.testtutorial.presentation.viewmodels.ShoppingViewModel
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class ShoppingFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var testShoppingFragmentFactory: TestShoppingFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun swipeShoppingItem_deleteItemInDb(){
        val shoppingItem  = ShoppingItem("TEST", 5.5f, 4, "", 1)
        var testViewModel: ShoppingViewModel? = null

        launchFragmentInHiltContainer<ShoppingFragment>(
            fragmentFactory = testShoppingFragmentFactory) {

            testViewModel = viewModel
            viewModel?.insertShoppingItemIntoDb(shoppingItem)
        }

        onView(withId(R.id.rvShoppingItems)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ShoppingItemAdapter.ShoppingItemViewHolder>(
                0,
                swipeLeft()
            )
        )

        assertThat(testViewModel?.shoppingItems?.getOrAwaitValue()).isEmpty()
    }

    @Test
    fun clickAddShoppingItemButton_navigateToAddShoppingItemFragment(){
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<ShoppingFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.fabAddShoppingItem)).perform(click())

        verify(navController).navigate(
            ShoppingFragmentDirections.actionShoppingFragmentToAddShoppingItemFragment()
        )
    }

}