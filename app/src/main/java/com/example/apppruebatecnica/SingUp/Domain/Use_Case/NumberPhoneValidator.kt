package com.example.apppruebatecnica.SingUp.Domain.Use_Case

import android.util.Patterns

class NumberPhoneValidator {

    operator fun invoke(numberPhone: String): Boolean {
        if (numberPhone.isEmpty()) {
            return false
        }
        return  Patterns.PHONE.matcher(numberPhone).matches()
    }

}