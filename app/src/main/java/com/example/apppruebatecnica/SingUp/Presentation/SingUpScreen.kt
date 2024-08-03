package com.example.apppruebatecnica.SingUp.Presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apppruebatecnica.Core.Presentation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.math.sin

@Composable
fun SingUpScreen(
    onNavigate: () -> Unit,
    navHostController: NavHostController,
    signUpViewModel: SingUpViewModel = hiltViewModel(),
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        modifier = Modifier.fillMaxSize()
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(170.dp)
        ) {
            WavyShape()
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = paddingValues.calculateTopPadding())
                .padding(horizontal = 23.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(modifier = Modifier.height(85.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Registro",
                fontWeight = FontWeight.W500,
                fontSize = 25.sp,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Start
            )
            HorizontalDivider(
                modifier = Modifier
                    .width(55.dp)
                    .align(Alignment.Start),
                thickness = 2.5.dp,
            )
            Spacer(modifier = Modifier.height(25.dp))

            RegisterInputContainer(signUpViewModel, scope, navHostController, snackbarHostState)

            Spacer(modifier = Modifier.height(5.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "¿Ya tienes una cuenta?",
                    fontSize = 13.sp
                )
                TextButton(
                    contentPadding = PaddingValues(),
                    onClick = onNavigate
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Ingresar",
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun RegisterInputContainer(
    signUpViewModel: SingUpViewModel,
    scope: CoroutineScope,
    navHostController: NavHostController,
    snackbarHostState: SnackbarHostState
) {
    val email: String by signUpViewModel.email.observeAsState(initial = "")
    val phone: String by signUpViewModel.phone.observeAsState(initial = "")
    val password: String by signUpViewModel.password.observeAsState(initial = "")
    val confirmPassword: String by signUpViewModel.passwordConfirm.observeAsState(initial = "")
    val loginEnable: Boolean by signUpViewModel.loginEnable.observeAsState(initial = false)

    val isErrorEmail by signUpViewModel.isErrorValidateEmail.collectAsState()
    val isErrorPhone by signUpViewModel.isErrorValidatePhoneNumber.collectAsState()
    val isErrorPassword by signUpViewModel.isErrorPassword.collectAsState()
    val isErrorPasswordConfirm by signUpViewModel.isErrorPasswordConfirm.collectAsState()

    val emailErrorMessage = "Correo Inválido"
    val phoneNumberErrorMessage = "Número de Teléfono Inválido"
    val passwordErrorMessage = "Contraseña débil"
    val confirmPasswordErrorMessage = "La contraseña no coincide"

    var isPasswordHidden by rememberSaveable { mutableStateOf(true) }
    var isConfirmPasswordHidden by rememberSaveable { mutableStateOf(true) }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        InputField(
            label = "Correo electrónico",
            value = email,
            onValueChange = { signUpViewModel.onSingUpChanged(it, phone, password, confirmPassword) },
            isError = isErrorEmail,
            errorMessage = emailErrorMessage,
            leadingIcon = Icons.Filled.Mail,
            placeholder = "Ingresar Correo Electrónico"
        )

        InputField(
            label = "Número de Teléfono",
            value = phone,
            onValueChange = { signUpViewModel.onSingUpChanged(email, it, password, confirmPassword) },
            isError = isErrorPhone,
            errorMessage = phoneNumberErrorMessage,
            leadingIcon = Icons.Filled.Phone,
            placeholder = "Ej: +5412345678"
        )

        PasswordInputField(
            label = "Contraseña",
            value = password,
            onValueChange = { signUpViewModel.onSingUpChanged(email, phone, it, confirmPassword) },
            isPasswordHidden = isPasswordHidden,
            onVisibilityChange = { isPasswordHidden = it },
            isError = isErrorPassword,
            errorMessage = passwordErrorMessage,
            placeholder = "Ej: abcABC#123"
        )

        PasswordInputField(
            label = "Confirmar Contraseña",
            value = confirmPassword,
            onValueChange = { signUpViewModel.onSingUpChanged(email, phone, password, it) },
            isPasswordHidden = isConfirmPasswordHidden,
            onVisibilityChange = { isConfirmPasswordHidden = it },
            isError = isErrorPasswordConfirm,
            errorMessage = confirmPasswordErrorMessage,
            placeholder = "Reingresar Contraseña"
        )

        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            onClick = {
                signUpViewModel.onSingUpChanged(email, phone, password, confirmPassword)
                if (loginEnable) {
                    navHostController.popBackStack()
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Se presionó el botón de crear cuenta")
                    }
                }
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Crear Cuenta",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun InputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    leadingIcon: ImageVector,
    placeholder: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Row(
                    ) {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = errorMessage,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            placeholder = { Text(text = placeholder) },
            leadingIcon = {
                Row {
                    Icon(leadingIcon, contentDescription = null)
                    Spacer(modifier = Modifier.width(5.dp))
                    VerticalDivider(
                        modifier = Modifier.height(25.dp),
                        thickness = 1.6.dp,
                        color = Color.DarkGray
                    )
                }
            }
        )
    }
}

@Composable
fun PasswordInputField(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isPasswordHidden: Boolean,
    onVisibilityChange: (Boolean) -> Unit,
    isError: Boolean,
    errorMessage: String,
    placeholder: String,
) {
    Column(
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = label,
            fontWeight = FontWeight.Medium,
            fontSize = 18.sp,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Start
        )
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = value,
            onValueChange = onValueChange,
            singleLine = true,
            isError = isError,
            supportingText = {
                if (isError) {
                    Row {
                        Spacer(modifier = Modifier.weight(1f))
                        Text(
                            text = errorMessage,
                            fontSize = 14.sp,
                            color = MaterialTheme.colorScheme.error
                        )
                    }
                }
            },
            placeholder = { Text(text = placeholder) },
            visualTransformation = if (isPasswordHidden) PasswordVisualTransformation() else VisualTransformation.None,
            leadingIcon = {
                Row {
                    Icon(Icons.Filled.Password, contentDescription = null)
                    Spacer(modifier = Modifier.width(5.dp))
                    VerticalDivider(
                        modifier = Modifier.height(25.dp),
                        thickness = 1.6.dp,
                        color = Color.DarkGray
                    )
                }
            },
            trailingIcon = {
                IconButton(onClick = { onVisibilityChange(!isPasswordHidden) }) {
                    Icon(
                        imageVector = if (isPasswordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
                        contentDescription = "Toggle Password Visibility"
                    )
                }
            }
        )
    }
}

@Composable
fun WavyShape() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val width = size.width
        val height = size.height

        val path = Path().apply {
            moveTo(0f, height * 1f)
            cubicTo(
                width * 0.3f, height * 0.6f,
                width * 0.7f, height * 1.7f,
                width, height * 0.9f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }
        val path2 = Path().apply {
            moveTo(0f, height * 1.02f)
            cubicTo(
                width * 0.34f, height * 0.64f,
                width * 0.71f, height * 1.74f,
                width, height * 0.9f
            )
            lineTo(width, 0f)
            lineTo(0f, 0f)
            close()
        }

        drawPath(
            path = path2,
            color = Color(0x97626163),
        )
        drawPath(
            path = path,
            color = Color(0xDF5C3F8F),
        )

    }
}