package com.neo.testtutorial.presentation.fragments

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.fragment.app.FragmentFactory
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import com.neo.testtutorial.R
import com.neo.testtutorial.launchFragmentInHiltContainer
import com.neo.testtutorial.presentation.adapters.ImageAdapter
import com.neo.testtutorial.presentation.viewmodels.ShoppingViewModel
import com.neo.testtutorial.repositories.FakeShoppingRepositoryAndroidTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import javax.inject.Inject
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.getOrAwaitValue
import com.neo.testtutorial.presentation.utils.ShoppingFragmentFactory


@ExperimentalCoroutinesApi
@MediumTest
@HiltAndroidTest
class ImagePickFragmentTest{

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var fragmentFactory: ShoppingFragmentFactory

    @Before
    fun setup(){
        hiltRule.inject()
    }

    @Test
    fun clickImage_popBackStackAndSetImageURL(){
        val navController = mock(NavController::class.java)
        val testViewModel = ShoppingViewModel(FakeShoppingRepositoryAndroidTest())
        val imageUrl = "TEST"

        launchFragmentInHiltContainer<ImagePickFragment>(fragmentFactory = fragmentFactory) {
            Navigation.setViewNavController(requireView(), navController)
            imageAdapter.images = listOf(imageUrl)
            viewModel = testViewModel
        }

        onView(withId(R.id.rvImages)).perform(
            RecyclerViewActions.actionOnItemAtPosition<ImageAdapter.ImageViewHolder>(
                0,
                click()
            )
        )

        verify(navController).popBackStack()
        assertThat(testViewModel.curImageUrl.getOrAwaitValue()).isEqualTo("TEST")
    }

}