package com.neo.testtutorial.sample_funcs

object RegistrationUtil {

    private val existingUser = listOf("Carl", "Peter")

    /**
     *The input is not valid if...
     * ...the username/password is empty
     * ...the username is already taken
     * ...confirm password is not equal to real password
     * ...password contains less than 2 digits
     */
    fun validateRegistrationCredentials(
        userName: String,
        password: String,
        confirmPassword: String
    ): Boolean{
        if(userName.isEmpty() || password.isEmpty()){
            return false
        }

        if(password != confirmPassword){
            return false
        }

        if(existingUser.contains(userName)){
            return false
        }

        if (password.count { it.isDigit() } < 2){
            return false
        }

        return true
    }


}