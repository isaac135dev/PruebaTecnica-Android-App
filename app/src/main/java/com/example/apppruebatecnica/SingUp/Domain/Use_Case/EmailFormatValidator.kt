package com.example.apppruebatecnica.SingUp.Domain.Use_Case

import android.content.Context
import android.util.Patterns

class EmailFormatValidator(
    private val context: Context
) {
    suspend operator fun invoke(email: String): Boolean {

        if (email.isEmpty()) {
            return false
        }

        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}