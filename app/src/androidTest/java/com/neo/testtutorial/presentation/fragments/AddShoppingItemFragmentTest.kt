package com.neo.testtutorial.presentation.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.replaceText
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.R
import com.neo.testtutorial.domain.local.ShoppingItem
import com.neo.testtutorial.getOrAwaitValue
import com.neo.testtutorial.launchFragmentInHiltContainer
import com.neo.testtutorial.presentation.utils.ShoppingFragmentFactory
import com.neo.testtutorial.presentation.viewmodels.ShoppingViewModel
import com.neo.testtutorial.repositories.FakeShoppingRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    lateinit var navController: NavController

    @Before
    fun setup(){
        hiltAndroidRule.inject()
        navController = mock(NavController::class.java)
    }

    @Test
    fun clickInsertIntoDb_shoppingItemInsertedIntoDb(){
        val testViewModel =  ShoppingViewModel(FakeShoppingRepositoryAndroidTest())

        launchFragmentInHiltContainer<AddShoppingItemFragment>(
            fragmentFactory = fragmentFactory
        ) {

            shoppingViewModel = testViewModel

        }

        onView(withId(R.id.etShoppingItemName)).perform(replaceText("shopping item"))
        onView(withId(R.id.etShoppingItemAmount)).perform(replaceText("5"))
        onView(withId(R.id.etShoppingItemPrice)).perform(replaceText("5.5"))

        onView(withId(R.id.btnAddShoppingItem)).perform(click())

        assertThat(testViewModel.shoppingItems.getOrAwaitValue())
            .contains(ShoppingItem("shopping item", 5.5f, 5, ""))
    }

    @Test
    fun pressBackButton_popBackStack(){
        launchFragmentInHiltContainer<AddShoppingItemFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        pressBack()

        verify(navController).popBackStack()
    }

    @Test
    fun pressShoppingImage_navigateToImagePickFragment(){
         launchFragmentInHiltContainer<AddShoppingItemFragment> {
             Navigation.setViewNavController(requireView(), navController)
         }

        onView(withId(R.id.ivShoppingImage)).perform(click())

        verify(navController).navigate(
            AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
        )
    }

    //Add test code to test viewmodel later

}