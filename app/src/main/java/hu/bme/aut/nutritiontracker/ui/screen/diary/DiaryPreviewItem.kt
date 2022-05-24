package hu.bme.aut.nutritiontracker.ui.screen.diary

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Alignment.Companion.CenterVertically
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.*
import hu.bme.aut.nutritiontracker.data.Food
import hu.bme.aut.nutritiontracker.data.Recipe
import hu.bme.aut.nutritiontracker.ui.theme.Shapes

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalCoilApi
@Composable
fun DiaryPreviewItem(food: Food, onClick: () -> Unit){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = Shapes.small,
        elevation = 8.dp,
        onClick = onClick
    ){
        Row()
        {
            Log.d(TAG,food.image.toString())
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
                )

            food.label?.let{ title ->
                Log.d(TAG,title)
                Text(
                    text = title,
                    modifier = Modifier
                        .align(CenterVertically)
                        .padding(12.dp),
                    fontSize = 16.sp
                )
            }

        }
    }
}
