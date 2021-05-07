package com.neo.testtutorial.sample_funcs

import android.content.Context

class ResourceComparator {

    fun isEqual(context: Context, resID: Int, string: String): Boolean{
        return context.getString(resID) == string
    }

}