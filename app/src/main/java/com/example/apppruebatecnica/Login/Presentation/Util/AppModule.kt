package com.example.apppruebatecnica.Login.Presentation.Util

import android.content.Context
import com.example.apppruebatecnica.Login.Domain.Use_Case.EmailFormatValidator
import com.example.apppruebatecnica.Login.Domain.Use_Case.PasswordValidator
import com.example.apppruebatecnica.SingUp.Domain.Use_Case.ConfirmPasswordValidator
import com.example.apppruebatecnica.SingUp.Domain.Use_Case.NumberPhoneValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideEmailFormatValidator(@ApplicationContext context: Context): EmailFormatValidator {
        return EmailFormatValidator(context)
    }

    @Provides
    fun numberPhoneValidator(): NumberPhoneValidator {
        return NumberPhoneValidator()
    }

    @Provides
    fun providePasswordValidator(): PasswordValidator {
        return PasswordValidator()
    }

    @Provides
    fun provideConfirmPasswordValidator(): ConfirmPasswordValidator {
        return ConfirmPasswordValidator()
    }
}