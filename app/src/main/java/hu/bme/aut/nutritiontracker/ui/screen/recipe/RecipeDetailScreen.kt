package hu.bme.aut.nutritiontracker.ui.screen.recipe

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight.Companion.Bold
import androidx.compose.ui.text.font.FontWeight.Companion.Medium
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.text.HtmlCompat
import androidx.core.text.HtmlCompat.FROM_HTML_MODE_COMPACT
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImagePainter
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import hu.bme.aut.nutritiontracker.R
import hu.bme.aut.nutritiontracker.data.RecipeDetailResult
import hu.bme.aut.nutritiontracker.ui.theme.Shapes

@ExperimentalCoilApi
@Composable
fun RecipeDetailScreen(recipeViewModel: RecipeViewModel){
    val recipe: RecipeDetailResult? by recipeViewModel.recipeDetail.observeAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Details about the recipe"
                    )
                },
            modifier = Modifier.padding(0.dp,0.dp,0.dp,8.dp))
        }
    ){
        recipe?.let{ recipe ->
            Content(recipe = recipe)
        }

    }

}


@ExperimentalCoilApi
@Composable
fun Content(recipe: RecipeDetailResult){
    Column {
        val painter = rememberImagePainter(data = recipe.image)

        Image(
            painter = painter,
            contentDescription = "Recipe Image",
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .clip(Shapes.medium),
            contentScale = ContentScale.Crop,
        )
        if(painter.state is AsyncImagePainter.State.Loading){
            CircularProgressIndicator()
        }

        Column(
            Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            recipe.title.let { title ->
                Text(
                    text = title!!,
                    fontSize = 26.sp,
                    fontWeight = Bold,
                    modifier = Modifier
                        .padding(horizontal = (16.dp))
                )
            }
        }

        LazyColumn(){
            item{
                BasicInfo(recipe = recipe)
                Description(recipe = recipe)
            }
        }
    }


}

@Composable
fun BasicInfo(recipe: RecipeDetailResult) {
    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp)
    ) {
        InfoColumn(R.drawable.ic_baseline_clock_24, (recipe.readyInMinutes ?: "?").toString() +"min")
        InfoColumn(R.drawable.ic_outline_star_outline_24, (recipe.spoonacularScore ?: "?").toString())
        InfoColumn(R.drawable.ic_baseline_serving_24, (recipe.servings ?: "?").toString())
    }
}

@Composable
fun InfoColumn(@DrawableRes iconResouce: Int, text: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            painter = painterResource(id = iconResouce),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.height(24.dp)
        )
        Text(text = text, fontWeight = Bold)
    }
}

@Composable
fun Description(recipe: RecipeDetailResult) {
    var instructions = "Instructions are missing."
    if(recipe.instructions != null)
        instructions = HtmlCompat.fromHtml(recipe.instructions, FROM_HTML_MODE_COMPACT).toString()
    Text(
        text = instructions,
        fontWeight = Medium,
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
    )
}