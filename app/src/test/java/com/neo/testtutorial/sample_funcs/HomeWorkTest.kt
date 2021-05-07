package com.neo.testtutorial.sample_funcs

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class HomeWorkTest {

    @Test
    fun `fib should return 0 when input is 0`(){
        val result = HomeWork.fib(0)
        assertThat(result).isEqualTo(0)
    }

    @Test
    fun `fib should return 1 when input is 1`(){
        val result  =  HomeWork.fib(1)
        assertThat(result).isEqualTo(1)
    }

    @Test
    fun `fib should return fib(n-2) + fib(n-1)`(){

        val inputForChecking = 5

        val normalResult = HomeWork.fib(inputForChecking)
        val assertResult = HomeWork.fib(inputForChecking - 2) + HomeWork.fib(inputForChecking - 1)

        assertThat(normalResult).isEqualTo(assertResult)
    }

    @Test
    fun `uneven parentheses should return false`(){
        val result = HomeWork.checkBraces("(a*b))")
        assertThat(result).isFalse()
    }

    @Test
    fun `even parentheses should return true`(){
        val result =  HomeWork.checkBraces("()")
        assertThat(result).isTrue()
    }

    @Test
    fun `no parentheses should return true`(){
        val result = HomeWork.checkBraces("a*b")
        assertThat(result).isTrue()
    }
}