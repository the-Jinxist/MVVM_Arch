package com.neo.testtutorial.sample_funcs

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.google.common.truth.Truth.assertThat
import com.neo.testtutorial.R
import org.junit.After
import org.junit.Before
import org.junit.Test

class ResourceComparatorTest{

    private lateinit var resourceComparator: ResourceComparator

    @Before
    fun setup(){
        resourceComparator = ResourceComparator()
    }

    @After
    fun tearDown(){

    }

    @Test
    fun stringResourceSameAsGivenString_returnsTrue(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparator.isEqual(context, R.string.app_name, "TestTutorial")

        assertThat(result).isTrue()
    }

    @Test
    fun stringResourceDifferFromGivenString_returnsFalse(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        val result = resourceComparator.isEqual(context, R.string.app_name, "TestTutoriale")

        assertThat(result).isFalse()
    }

}