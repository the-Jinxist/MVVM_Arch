package com.neo.testtutorial.sample_funcs

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class RegistrationUtilTest{

    @Test
    fun `empty username should return false`(){
        val result  =  RegistrationUtil.validateRegistrationCredentials(
            "",
            "12334567",
            "12334567"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `valid username and correctly inserted password returns true`(){
        val result = RegistrationUtil.validateRegistrationCredentials(
            "neo",
            "12345678",
            "12345678"
        )

        assertThat(result).isTrue()
    }

    @Test
    fun `already used username returns false`(){
        val result = RegistrationUtil.validateRegistrationCredentials(
            "Carl",
            "12345678",
            "12345678"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `different passwords return false`(){
        val result = RegistrationUtil.validateRegistrationCredentials(
            "neo",
            "11234567",
            "qwerttyui"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `password with less than two digits return false`(){
        val result = RegistrationUtil.validateRegistrationCredentials(
            "neo",
            "qwertyui",
            "qwertyui"
        )

        assertThat(result).isFalse()
    }

    @Test
    fun `empty password should return false`(){
        val result  =  RegistrationUtil.validateRegistrationCredentials(
            "neo",
            "",
            ""
        )

        assertThat(result).isFalse()
    }



}