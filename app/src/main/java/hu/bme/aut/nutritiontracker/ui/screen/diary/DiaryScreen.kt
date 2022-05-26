package hu.bme.aut.nutritiontracker.ui.screen


import android.util.Log
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import hu.bme.aut.nutritiontracker.R
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import hu.bme.aut.nutritiontracker.data.ConsumedFood
import hu.bme.aut.nutritiontracker.data.MacroNutrition
import hu.bme.aut.nutritiontracker.ui.screen.diary.ConsumedFoodItem
import hu.bme.aut.nutritiontracker.ui.screen.diary.DiaryViewModel
import hu.bme.aut.nutritiontracker.ui.theme.Shapes


@ExperimentalFoundationApi
@ExperimentalMaterialApi
@Composable
fun DiaryScreen(diaryViewModel: DiaryViewModel, navController: NavController) {
    val consumedFoodList by diaryViewModel.consumedFoodList.observeAsState()
    //diaryViewModel.getDayFlow()
    val day by diaryViewModel.day.observeAsState()
    val deltaWater: Double = 0.25
    Scaffold(
        topBar ={TopAppBar(
            title = {
                Text(
                    text = "Diary"
                )
            }
        )},
    ){
        LazyColumn(
            contentPadding = PaddingValues(all = 12.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            day?.let { day ->
                item{
                    MacroDetailCard(
                        macroTotal = day.macroTotal,
                        kcalLimit = day.kcalLimit,
                        consumed = 1500
                    )
                    AddFoodConsumptionCard(
                        onClick = {
                            navController.navigate(route = "food_search_screen")
                        }
                    )
                    AddWaterConsumptionCard(
                        currentWater = day.water!!,
                    onMinusClick = {
                        var new = day.water!! - deltaWater
                        if(new < 0.0) new = 0.0
                        diaryViewModel.updateWaterConsumption(new)
                    },
                    onPlusClick = {
                        val new = day.water!! + deltaWater
                        diaryViewModel.updateWaterConsumption(new)
                    })
                }
            }


            consumedFoodList?.let { foodList ->
                setConsumedFoodsList(foodList = foodList, diaryViewModel = diaryViewModel)
            }
        }
    }
}

@ExperimentalMaterialApi
fun LazyListScope.setConsumedFoodsList(foodList: List<ConsumedFood>, diaryViewModel: DiaryViewModel){
        items(items = foodList){ consumedFood ->
            ConsumedFoodItem(food = consumedFood)
        }

}


@ExperimentalMaterialApi
@Composable
fun MacroDetailCard(macroTotal: MacroNutrition, kcalLimit: Int, consumed: Int, macroCurrent: MacroNutrition = MacroNutrition()){
    Card(
        elevation = 8.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(10.dp, shape = Shapes.medium)
            .clip(Shapes.medium),
        onClick = {}

    ){
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, bottom = 4.dp)
            ) {
                spaceEvenlyTextRow(
                    string1 = "Consumed",
                    string2 = "Remaining",
                    string3 = "Total"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                spaceEvenlyTextRow(
                    string1 = consumed.toString(),
                    string2 = (kcalLimit - consumed).toString(),
                    string3 = kcalLimit.toString()
                )
            }

            Spacer(modifier = Modifier.padding(15.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
            ) {
                spaceEvenlyTextRow(
                    string1 = "Carbs",
                    string2 = "Protein",
                    string3 = "Fat"
                )
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
            ) {
                spaceEvenlyTextRow(
                    string1 = "${macroCurrent.carb}/${macroTotal.carb}",
                    string2 = "${macroCurrent.protein}/${macroTotal.protein}",
                    string3 = "${macroCurrent.fat}/${macroTotal.fat}"
                )
            }
        }
    }

}

@Composable
fun RowScope.spaceEvenlyTextRow(string1: String, string2: String, string3 : String){
    Text(text = string1,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier.weight(1f))
    Text(text = string2,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier.weight(1f))
    Text(text = string3,
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier.weight(1f))
}

@ExperimentalMaterialApi
@Composable
fun AddFoodConsumptionCard(onClick: ()->Unit){
    Card(
        elevation = 8.dp,
        backgroundColor = Color.LightGray,
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = Shapes.medium)
            .clip(Shapes.medium)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Text(text = "Consumed food",
                fontSize = 22.sp,
                modifier = Modifier.padding(bottom = 4.dp))

            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                contentDescription = "Add consumed food icon",
                modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 8.dp)
            )
        }

    }
}

@Composable
fun AddWaterConsumptionCard(
    currentWater: Double,
    onPlusClick: () -> Unit,
    onMinusClick: () -> Unit
){
    Card(
        elevation = 8.dp,
        backgroundColor = colorResource(R.color.waterBlue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .shadow(8.dp, shape = Shapes.medium)
            .clip(Shapes.medium)
    ){
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Row{
                Text(text = "Water",
                    textAlign = TextAlign.Center,
                    fontSize = 22.sp,
                    modifier = Modifier.padding(bottom = 4.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                IconButton(
                    modifier = Modifier
                        .padding(bottom = 8.dp)
                        .weight(1f),
                    onClick = onMinusClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_remove_circle_outline_24),
                        contentDescription = "Decrease water icon",
                        modifier = Modifier.size(50.dp)
                    )
                }


                Text(text = currentWater.toString() +"L",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                IconButton(modifier = Modifier
                    .size(50.dp)
                    .padding(bottom = 8.dp)
                    .weight(1f) ,
                    onClick = onPlusClick) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                        contentDescription = "Add water icon",
                        modifier = Modifier.size(50.dp)
                    )
                }

            }

            Row(
                modifier = Modifier.padding(bottom = 8.dp)
            ){
                spaceEvenlyTextRow(
                    string1 = "-0.25L",
                    string2 = "",
                    string3 = "+0.25L"
                )
            }

        }

    }
}


@ExperimentalMaterialApi
@Composable
@Preview
fun DiaryScreenPreview() {
    //DiaryScreen(diaryViewModel = DiaryViewModel(), navController = NavController())
}
