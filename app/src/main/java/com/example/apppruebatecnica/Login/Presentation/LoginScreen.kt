package com.example.apppruebatecnica.Login.Presentation

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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Mail
import androidx.compose.material.icons.filled.Password
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.example.apppruebatecnica.Core.Presentation.Screens
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(
    onNavigate: () -> Unit,
    navHostController: NavHostController,
    loginViewModel: LoginViewModel = hiltViewModel(),
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
                .height(200.dp)
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
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                modifier = Modifier.align(Alignment.Start),
                text = "Ingreso",
                fontWeight = FontWeight.W500,
                fontSize = 28.sp,
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

            ContainerRegisterInput(loginViewModel, navHostController, scope, snackbarHostState)

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "No tienes una cuenta?",
                    fontSize = 13.sp
                )
                TextButton(
                    contentPadding = PaddingValues(),
                    onClick = onNavigate
                ) {
                    Text(
                        modifier = Modifier.padding(start = 10.dp),
                        text = "Registrarse",
                        fontSize = 14.sp,
                    )
                }
            }
        }
    }
}

@Composable
fun ContainerRegisterInput(
    loginViewModel: LoginViewModel,
    navHostController: NavHostController,
    scope: CoroutineScope,
    snackbarHostState: SnackbarHostState
) {
    val email: String by loginViewModel.email.observeAsState(initial = "")
    val password: String by loginViewModel.password.observeAsState(initial = "")
    val checkedState = remember { mutableStateOf(false) }
    val loginEnable: Boolean by loginViewModel.loginEnable.observeAsState(initial = false)
    val isError by loginViewModel.isErrorValidate.collectAsState()
    val isErrorPassword by loginViewModel.isErrorPasswordValidator.collectAsState()

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        TextFieldEmail(email, isError) { loginViewModel.onLoginChanged(it, password) }

        TextFieldPassword(password, isErrorPassword) { loginViewModel.onLoginChanged(email, it)}

        Row {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = {
                            checkedState.value = it
                            if (checkedState.value){
                                scope.launch {
                                    snackbarHostState.showSnackbar("Se activo Recordar.")
                                }
                            }else {
                                scope.launch {
                                    snackbarHostState.showSnackbar("Se desactivo Recordar.")
                                }
                            }

                        })
                    Text(
                        text = "Recordarme",
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }

            }

            TextButton(
                contentPadding = PaddingValues(),
                onClick = {
                    scope.launch {
                        snackbarHostState.showSnackbar("Envia a resetear la Contraseña")
                    }
                }
            ) {
                Text(text = "Contraseña Olvidada?")
            }
        }

        Spacer(modifier = Modifier.height(5.dp))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 28.dp),
            onClick = {
                loginViewModel.onLoginChanged(email, password)
                if (loginEnable) {
                    navHostController.navigate(Screens.SingUp)
                } else {
                    scope.launch {
                        snackbarHostState.showSnackbar("Se presionó el botón Ingresar")
                    }
                }
            },
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Ingresar",
                fontWeight = FontWeight.Medium,
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun TextFieldEmail(email: String, loginEnable: Boolean,  onTextFieldChanged: (String) -> Unit) {
    val errorMessage = "Correo Invalido"

    TextFieldWithError(
        label = "Correo Electrónico",
        value = email,
        onValueChange = { onTextFieldChanged(it) },
        isError = loginEnable,
        errorMessage = errorMessage,
        leadingIcon = {
            Row {
                Icon(Icons.Filled.Mail, contentDescription = "Correo Electrónico")
                Spacer(modifier = Modifier.width(5.dp))
                VerticalDivider(
                    modifier = Modifier.height(25.dp),
                    thickness = 1.6.dp,
                    color = Color.DarkGray
                )
            }
        },
        placeholder = "Ingresar Correo electrónico",
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
    )
}

@Composable
fun TextFieldPassword(password: String, loginEnable: Boolean, onTextFieldChanged: (String) -> Unit) {
    var passwordHidden by rememberSaveable { mutableStateOf(true) }
    val errorMessagPassword = "Contraseña Incorrecta"

    TextFieldWithError(
        label = "Contraseña",
        value = password,
        onValueChange = { onTextFieldChanged(it) },
        isError = loginEnable,
        errorMessage = errorMessagPassword,
        leadingIcon = {
            Row {
                Icon(Icons.Filled.Password, contentDescription = "Contraseña")
                Spacer(modifier = Modifier.width(5.dp))
                VerticalDivider(
                    modifier = Modifier.height(25.dp),
                    thickness = 1.6.dp,
                    color = Color.DarkGray
                )
            }
        },
        placeholder = "EJ: abcABC#123",
        visualTransformation = if (passwordHidden) PasswordVisualTransformation() else VisualTransformation.None,
        trailingIcon = {
            val visibilityIcon =
                if (passwordHidden) Icons.Filled.Visibility else Icons.Filled.VisibilityOff
            val description = if (passwordHidden) "Show password" else "Hide password"
            IconButton(onClick = { passwordHidden = !passwordHidden }) {
                Icon(imageVector = visibilityIcon, contentDescription = description)
            }
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
    )
}

@Composable
fun TextFieldWithError(
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    isError: Boolean,
    errorMessage: String,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    placeholder: String = "",
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions
) {
    Text(
        modifier = Modifier.fillMaxWidth(),
        text = label,
        fontWeight = FontWeight.Medium,
        fontSize = 18.sp,
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Start
    )
    TextField(
        modifier = Modifier.fillMaxWidth(),
        value = value,
        singleLine = true,
        onValueChange = onValueChange,
        supportingText = {
            Row {
                Spacer(Modifier.weight(1f))
                Text(
                    if (isError) errorMessage else "",
                    fontSize = 14.sp
                )
            }
        },
        keyboardOptions = keyboardOptions,
        isError = isError,
        placeholder = { Text(text = placeholder) },
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        visualTransformation = visualTransformation
    )
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