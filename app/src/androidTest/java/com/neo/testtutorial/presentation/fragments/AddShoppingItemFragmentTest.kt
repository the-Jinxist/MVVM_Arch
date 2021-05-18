package com.neo.testtutorial.presentation.fragments

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.neo.testtutorial.R
import com.neo.testtutorial.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class AddShoppingItemFragmentTest {

    @get:Rule
    var hiltAndroidRule = HiltAndroidRule(this)

    lateinit var navController: NavController

    @Before
    fun setup(){
        hiltAndroidRule.inject()
        navController = mock(NavController::class.java)
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