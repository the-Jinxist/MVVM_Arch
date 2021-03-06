package com.neo.testtutorial.presentation.utils

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import com.androiddevs.shoppinglisttestingyt.adapters.ShoppingItemAdapter
import com.bumptech.glide.RequestManager
import com.neo.testtutorial.presentation.adapters.ImageAdapter
import com.neo.testtutorial.presentation.fragments.AddShoppingItemFragment
import com.neo.testtutorial.presentation.fragments.ImagePickFragment
import com.neo.testtutorial.presentation.fragments.ShoppingFragment
import com.neo.testtutorial.presentation.viewmodels.ShoppingViewModel
import javax.inject.Inject

class ShoppingFragmentFactory @Inject constructor(
    private val imageAdapter: ImageAdapter,
    private val glide: RequestManager,
    private val shoppingItemAdapter: ShoppingItemAdapter
): FragmentFactory() {

    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            ImagePickFragment::class.java.name -> ImagePickFragment(imageAdapter)
            AddShoppingItemFragment::class.java.name -> AddShoppingItemFragment(glide)
            ShoppingFragment::class.java.name -> ShoppingFragment(
                shoppingItemAdapter
            )
            else -> super.instantiate(classLoader, className)
        }
    }
}