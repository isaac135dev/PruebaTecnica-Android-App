package com.example.apppruebatecnica.SingUp.Domain.Use_Case

class PasswordValidator {

    operator fun invoke(password: String): Boolean {

        if (password.isEmpty()) {
            return false
        }

        if (password.contains(" ")){
            return false
        }

        if (password.length < 8) {
            return false
        }

        if (!password.any { it.isUpperCase() }) {
            return false
        }

        if (!password.any { it.isDigit() }) {
            return false
        }

        return true
    }
}