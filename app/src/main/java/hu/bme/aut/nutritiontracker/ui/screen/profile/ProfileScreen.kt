package hu.bme.aut.nutritiontracker.ui.screen

import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.nutritiontracker.data.User
import hu.bme.aut.nutritiontracker.showToast
import hu.bme.aut.nutritiontracker.ui.screen.profile.ProfileViewModel
import hu.bme.aut.nutritiontracker.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun ProfileScreen(profileViewModel: ProfileViewModel) {
    val user by profileViewModel.user.observeAsState()
    val context = LocalContext.current
    Scaffold(
        topBar ={
            TopAppBar(
            title = {
                Text(
                    text = "Profile"
                )
            }
        )
        }
    ){
        Column(
            modifier = Modifier.padding(vertical = 8.dp, horizontal = 4.dp)
        ){
            user?.let {
                UserDataCard(
                    user = it,
                    profileVewModel = profileViewModel
                )
                UserGoalCard(profileViewModel, context)
            }

        }


    }
}

@ExperimentalMaterialApi
@Composable
fun UserDataCard(user: User, profileVewModel: ProfileViewModel){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color.LightGray,
        shape = Shapes.small,
        elevation = 8.dp,
        onClick = {}
    ){
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Text(text = user.email,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(horizontal = 4.dp, vertical = 6.dp)
            )

            KeyValueRow("Age", user.bodyComposition.age.toString(), 1.0f)
            KeyValueRow("Height", user.bodyComposition.height.toString() +" cm", 1.0f)
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun UserGoalCard(
    profileViewModel: ProfileViewModel,
    context: Context
){
    var weight by remember { mutableStateOf("") }
    var target by remember {mutableStateOf("")}
    var age by remember { mutableStateOf("") }
    var height by remember {mutableStateOf("")}
    val gender = remember {mutableStateOf("")}
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        backgroundColor = Color.LightGray,
        shape = Shapes.small,
        elevation = 8.dp,
        onClick = {}
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Set goal",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.weight(4f)
                )

                Button(
                    modifier = Modifier
                        .height(28.dp)
                        .width(50.dp)
                        .weight(1f)
                        .padding(0.dp),
                    onClick = {
                        if(profileViewModel.validateInput(
                            weight = weight,
                            height = height,
                            age = age,
                            gender = gender.value,
                            target = target
                        )) {
                            profileViewModel.calculateKcalLimit(
                                weight = weight,
                                height = height,
                                age = age,
                                gender = gender.value,
                                target = target
                            )
                            profileViewModel.getUser()
                        } else
                            showToast(context, "You should add all data")
                        weight = ""
                        height = ""
                        age = ""
                        target=""
                        gender.value = ""

                    },
                    colors = ButtonDefaults.textButtonColors(
                        backgroundColor = MaterialTheme.colors.primary
                    ),
                    shape = Shapes.small,
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        text = "Save",
                        color = MaterialTheme.colors.secondary,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(0.dp),
                        textAlign = TextAlign.Center
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Age",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color.White)
                ) {
                    BasicTextField(
                        value = age,
                        onValueChange = {
                            age = it
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .align(Alignment.Center)
                            .padding(5.dp),
                        textStyle = TextStyle(fontSize = 16.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Height",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color.White)
                ) {
                    BasicTextField(
                        value = height,
                        onValueChange = {
                            height = it
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .align(Alignment.Center)
                            .padding(5.dp),
                        textStyle = TextStyle(fontSize = 16.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

            }

            GenderRadioButton(gender = gender)


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                Text(
                    text = "Current weight",
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color.White)
                ) {
                    BasicTextField(
                        value = weight,
                        onValueChange = {
                            weight = it
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .align(Alignment.Center)
                            .padding(5.dp),
                        textStyle = TextStyle(fontSize = 16.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }

            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
                    .height(40.dp)
                    .clip(RoundedCornerShape(6.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    text = "Weekly weight loss (in kg)",
                    color =  MaterialTheme.colors.secondary,
                    fontWeight = FontWeight.Bold
                )

                Box(
                    modifier = Modifier
                        .width(50.dp)
                        .clip(RoundedCornerShape(6.dp))
                        .background(color = Color.White)
                ) {
                    BasicTextField(
                        value = target,
                        onValueChange = {
                            target = it
                        },
                        modifier = Modifier
                            .width(50.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(6.dp))
                            .align(Alignment.Center)
                            .padding(5.dp),
                        textStyle = TextStyle(fontSize = 16.sp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun GenderRadioButton(
    gender: MutableState<String>
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp, top = 5.dp, bottom = 5.dp)
            .height(40.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        var selectedGender by remember { mutableStateOf("") }
        Text(
            text = "Gender",
            fontWeight = FontWeight.Bold,
            color =  MaterialTheme.colors.secondary)
        Spacer(modifier = Modifier.size(16.dp))
        RadioButton(
            selected = selectedGender == Gender.male,
            onClick = {
                selectedGender = Gender.male
                gender.value = selectedGender
        },
            colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(Gender.male)
        Spacer(modifier = Modifier.size(16.dp))
        RadioButton(
            selected = selectedGender == Gender.female,
            onClick = {
                selectedGender = Gender.female
                gender.value = selectedGender
        },
            colors = RadioButtonDefaults.colors(MaterialTheme.colors.primary)
        )
        Spacer(modifier = Modifier.size(8.dp))
        Text(Gender.female)
    }

}
object Gender {
    const val male = "Male"
    const val female = "Female"
}

@Composable
fun KeyValueRow(property: String, value: String, weight: Float){
    Row(
        modifier = Modifier.padding(horizontal = 6.dp)
    ){
        Text(
            text = property,
            style = MaterialTheme.typography.body2,
            fontSize = 18.sp,
            modifier = Modifier.weight(weight)
        )
        Text(
            text = value,
            style = MaterialTheme.typography.body2,
            fontSize = 18.sp,
            modifier = Modifier.weight(weight)
        )
    }
}

@ExperimentalMaterialApi
@Composable
@Preview
fun ProfileScreenPreview() {
        ProfileScreen(profileViewModel = ProfileViewModel())
}