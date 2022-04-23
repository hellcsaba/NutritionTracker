package hu.bme.aut.nutritiontracker

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.nutritiontracker.ui.screen.authentication.AuthenticationViewModel

@Composable
fun SignUpScreen(authenticationViewModel: AuthenticationViewModel = AuthenticationViewModel(), navController: NavController)
{
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    val focusManager = LocalFocusManager.current
    val context = LocalContext.current

    var passwordVisibility by remember { mutableStateOf(false)}
    val icon = if(passwordVisibility)
        painterResource(id = R.drawable.ic_baseline_visibility_24)
    else
        painterResource(id = R.drawable.ic_baseline_visibility_off_24)

    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        Text(
            text = "Register",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(70.dp)
        )

        OutlinedTextField(
            value = email,
            onValueChange = {
                email = it
                Log.d("Email", email)
            },
            label = {Text(text = "Email")},
            placeholder = {
                Text("Email")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Email,
                    contentDescription = "Email icon" )
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            )
        )
        Spacer(Modifier.padding(5.dp))
        OutlinedTextField(
            value = password,
            onValueChange = {
                password = it
                Log.d("Password",password)
            },
            label = {Text(text = "Password")},
            placeholder = {
                Text("Password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Lock icon" )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {focusManager.moveFocus(FocusDirection.Down)}
            ),
            visualTransformation =
            if(passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
        Spacer(Modifier.padding(5.dp))
        OutlinedTextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                Log.d("confirmPassword",confirmPassword)
            },
            label = {Text(text = "Confirm password")},
            placeholder = {
                Text("Confirm password")
            },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Filled.Lock,
                    contentDescription = "Lock icon" )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility = !passwordVisibility
                }) {
                    Icon(
                        painter = icon,
                        contentDescription = "Visibility Icon"
                    )
                }
            },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Password
            ),
            visualTransformation =
            if(passwordVisibility)
                VisualTransformation.None
            else
                PasswordVisualTransformation()
        )
        Spacer(Modifier.padding(5.dp))
        Text(
            text = "You already have an account? Sign in",
            modifier =
            Modifier.clickable(onClick = {
                navController.navigate(route = Screen.Login.route) {
                    popUpTo(navController.graph.startDestinationId)
                    launchSingleTop = true
                }
                Log.d("SignUpScreen", "SignUp clicked")
            })
        )

        Spacer(Modifier.padding(10.dp))
        Button(
            modifier = Modifier.fillMaxWidth(0.5f),
            onClick = {
                Log.d("SignUpScreen", "SignUp button clicked")
                if(authenticationViewModel.signUp(email = email, password = password, confirmPassword = confirmPassword)) {
                    //sendEmailVerification()
                    showToast(context, "Registration was successful.")
                } else {
                    showToast(context, "Registration failed.")
                }

            }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Sign up", color = MaterialTheme.colors.secondary)
        }
    }
}



@Composable
@Preview(showBackground = true)
fun SignUpScreenPreview() {
    SignUpScreen(
        navController = rememberNavController()
    )
}