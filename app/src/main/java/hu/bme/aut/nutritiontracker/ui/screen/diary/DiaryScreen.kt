package hu.bme.aut.nutritiontracker.ui.screen


import hu.bme.aut.nutritiontracker.R
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.nutritiontracker.data.MacroNutrition
import hu.bme.aut.nutritiontracker.ui.screen.diary.DiaryViewModel
import hu.bme.aut.nutritiontracker.ui.theme.Shapes


@ExperimentalMaterialApi
@Composable
fun DiaryScreen(diaryViewModel: DiaryViewModel) {
    Scaffold(
        topBar ={TopAppBar(
            title = {
                Text(
                    text = "Diary"
                )
            }
        )},
    ){
        Column {
            MacroDetailCard(
                macroTotal = MacroNutrition(150, 150, 50),
                kcalLimit = 2300,
                consumed = 1500
            )
            AddFoodConsumptionCard()
            AddWaterConsumptionCard(2.0)
        }
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
            .padding(8.dp)
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

@Composable
fun AddFoodConsumptionCard(){
    Card(
        elevation = 8.dp,
        backgroundColor = Color.LightGray,
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
fun AddWaterConsumptionCard(currentWater: Double){
    Card(
        elevation = 8.dp,
        backgroundColor = colorResource(R.color.waterBlue),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
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
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_remove_circle_outline_24),
                    contentDescription = "Decrease water icon",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(bottom = 8.dp)
                        .weight(1f)
                )

                Text(text = currentWater.toString() +"L",
                    textAlign = TextAlign.Center,
                    fontSize = 20.sp,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_add_circle_24),
                    contentDescription = "Add water icon",
                    modifier = Modifier
                        .size(50.dp)
                        .padding(bottom = 8.dp)
                        .weight(1f)
                )
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
    DiaryScreen(diaryViewModel = DiaryViewModel())
}
