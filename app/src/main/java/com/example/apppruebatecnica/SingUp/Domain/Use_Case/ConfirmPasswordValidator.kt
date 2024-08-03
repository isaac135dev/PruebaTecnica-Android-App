package com.example.apppruebatecnica.SingUp.Domain.Use_Case

class ConfirmPasswordValidator {
    operator fun invoke(password: String, confirmPassword: String): Boolean {
        return password == confirmPassword
    }
}