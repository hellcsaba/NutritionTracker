package hu.bme.aut.nutritiontracker

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import hu.bme.aut.nutritiontracker.model.FirebaseAuthRepository

@Composable
fun SignUpScreen( navController: NavController) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {

        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var confirmPassword by remember { mutableStateOf("") }

        Text(
            text = "Register",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(70.dp)
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
                Log.d("Email",email)
            },
            placeholder = {
                Text("Email")
            }
        )
        Spacer(Modifier.padding(10.dp))
        TextField(
            value = password,
            onValueChange = {
                password = it
                Log.d("Password",password)
            },
            placeholder = {
                Text("Password")
            }
        )
        Spacer(Modifier.padding(10.dp))
        TextField(
            value = confirmPassword,
            onValueChange = {
                confirmPassword = it
                Log.d("confirmPassword", confirmPassword)
            },
            placeholder = {
                Text("Confirm Password")
            }
        )

        Spacer(Modifier.padding(5.dp))
        Text(
            text = "You already have an account? Sign in",
            modifier =
            Modifier.clickable(onClick = {
                navController.navigate(route = Screen.Login.route) {
                    popUpTo = navController.graph.startDestinationId
                    launchSingleTop = true
                }
                Log.d("SignUpScreen", "SignUp clicked")
            })
        )

        Spacer(Modifier.padding(10.dp))
        Button(
            onClick = {
                FirebaseAuthRepository.signUp(email = email, password = password, confirmPassword = confirmPassword)
                //sendEmailVerification()
                Log.d("SignUpScreen", "SignUp button clicked")

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