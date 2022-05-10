package hu.bme.aut.nutritiontracker.ui.screen.recipe

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import hu.bme.aut.nutritiontracker.data.Recipe

@ExperimentalCoilApi
@Composable
fun RecipePreviewItem(recipe: Recipe){
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(6.dp),
        elevation = 8.dp,
    ){
        Column()
        {
            val painter = rememberImagePainter(data = recipe.image)
            Image(
                painter = painter,
                contentDescription = "Recipe Image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp),
                contentScale = ContentScale.Crop,

            )
            if(painter.state is ImagePainter.State.Loading){
                CircularProgressIndicator()
            }

            recipe.title?.let{ title ->
                Log.d("Title", title)
                Text(
                    text = title,
                    modifier = Modifier
                        .align(Alignment.Start)
                        .padding(12.dp),
                    fontSize = 16.sp
                )
            }

        }
    }
}
