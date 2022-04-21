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
import hu.bme.aut.nutritiontracker.ui.navigation.BottomBarScreen

@Composable
fun LoginScreen( navController: NavController,
                 //viewModel : FirebaseAuthViewModel = FirebaseAuthViewModel()
) {
    Column(modifier = Modifier.fillMaxSize(), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        Text(
            text = "Sign in",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(100.dp)
        )

        TextField(
            value = email,
            onValueChange = {
                email = it
                Log.d("Email", email)
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

        Spacer(Modifier.padding(5.dp))

        Text(
            text = "Don't have an account? Sign up",
            modifier =
            Modifier.clickable(onClick = {
                navController.navigate(route = Screen.SignUp.route)

                Log.d("LoginScreen", "SignUp clicked")
            })
        )

        Spacer(Modifier.padding(10.dp))

        Button(
            onClick = {
                if(FirebaseAuthRepository.signIn(email, password)){
                    navController.navigate(Screen.MainScreen.route) {

                        popUpTo(navController.graph.startDestinationId)
                        launchSingleTop = true
                    }
                }
                    //navController.navigate(route = BottomBarScreen.Diary.route)
                //sendEmailVerification()
                Log.d("LoginScreen", "SignIn button clicked")
            }, colors = ButtonDefaults.textButtonColors(
                backgroundColor = MaterialTheme.colors.primary
            ),
            shape = RoundedCornerShape(10.dp)
        ) {
            Text(text = "Sign in", color = MaterialTheme.colors.secondary)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun LoginScreenPreview() {
    LoginScreen(
        navController = rememberNavController()
    )
}