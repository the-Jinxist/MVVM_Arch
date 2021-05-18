package com.neo.testtutorial.presentation.fragments


import android.os.Bundle
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.neo.testtutorial.R
import com.neo.testtutorial.presentation.viewmodels.ShoppingViewModel
import kotlinx.android.synthetic.main.fragment_add_shopping_item.*

class AddShoppingItemFragment: Fragment(R.layout.fragment_add_shopping_item) {

    lateinit var shoppingViewModel: ShoppingViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        shoppingViewModel = ViewModelProvider(requireActivity()).get(ShoppingViewModel::class.java)

        ivShoppingImage.setOnClickListener {
           findNavController().navigate(
               AddShoppingItemFragmentDirections.actionAddShoppingItemFragmentToImagePickFragment()
           )
        }

        //Create a onback pressed callback to make sure the image url is
        //reset-ed when the user goes back
        val  callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                shoppingViewModel.setCurImageUrl("")
                findNavController().popBackStack()
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(callback)
    }

}