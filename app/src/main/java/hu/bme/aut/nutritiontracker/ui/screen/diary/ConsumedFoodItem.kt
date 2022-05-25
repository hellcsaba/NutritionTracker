package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.SubcomposeAsyncImage
import hu.bme.aut.nutritiontracker.data.ConsumedFood
import hu.bme.aut.nutritiontracker.ui.theme.Shapes

@ExperimentalMaterialApi
@Composable
fun ConsumedFoodItem(food: ConsumedFood) {
    var expandedState by remember { mutableStateOf(false) }
    val rotationState by animateFloatAsState(
        targetValue = if (expandedState) 180f else 0f
    )

    Card(
        shape = Shapes.small,
        modifier =  Modifier
            .fillMaxWidth()
            .shadow(6.dp,shape = Shapes.small)
            .clip(Shapes.small)
            .animateContentSize(
                animationSpec = tween(
                    durationMillis = 300,
                    easing = LinearOutSlowInEasing
                )
            ),
        elevation = 8.dp,
        onClick = {
            expandedState = !expandedState
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                SubcomposeAsyncImage(
                    model = food.image,
                    loading = {
                        CircularProgressIndicator()
                    },
                    contentDescription = "Food Image",
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.CenterStart,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                        .weight(1f)
                )

                Text(
                    text = food.name,
                    modifier = Modifier
                        .align(Alignment.CenterVertically)
                        .padding(12.dp)
                        .weight(10f),
                    fontSize = 16.sp
                )


                IconButton(
                    modifier = Modifier
                        .alpha(ContentAlpha.medium)
                        .rotate(rotationState)
                        .weight(1f),
                    onClick = {
                        expandedState = !expandedState
                    }) {
                    Icon(
                        imageVector = Icons.Default.ArrowDropDown,
                        contentDescription = "Drop-Down Arrow"
                    )
                }


            }
            if (expandedState) {
                KeyValueRow(
                    property = "Amount: ",
                    value = food.amount.toString() +"g"
                )

                KeyValueRow(
                    property = "Kcal: ",
                    value = food.kcal.toString()
                )

                KeyValueRow(
                    property = "Protein: ",
                    value = food.protein.toString() +"g"
                )

                KeyValueRow(
                    property = "Carbs: ",
                    value = food.carb.toString() +"g"
                )

                KeyValueRow(
                    property = "Fat: ",
                    value = food.fat.toString() +"g"
                )
            }
        }
    }
}
