package com.example.apppruebatecnica.Core.Presentation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object Login : Screens

    @Serializable
    data object SingUp : Screens
}