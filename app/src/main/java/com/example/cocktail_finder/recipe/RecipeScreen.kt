package com.example.cocktail_finder.recipe

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.request.ImageRequest
import com.example.cocktail_finder.dataModels.IngredientModel
import kotlinx.coroutines.NonDisposableHandle.parent
import kotlinx.coroutines.flow.StateFlow

//class RecipeScreen : ComponentActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//
//        setContent {
//            IngredientCard()
//        }
//    }
//}
@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//class RecipeScreen (
//    model: StateFlow<RecipeFragment.State>,
//    onBack: () -> Unit = {}
//) {
//    Box() {}
//}
//
//fun RecipeContent(model: RecipeModel) {
//    Column(
//        modifier = Modifier
//            .padding(start = 1.dp, end = 1.dp)
//    ) {
//        Box(
//            modifier = Modifier
//                .fillMaxWidth()
//            contentAligment = Aligment.CenterEnd
//        ) {
//            AsyncImage(
//            model = ImageRequest.Builder(LocalContext.current)
//                .data(data,imageUrl)
//            )
//        }
//    }
//    LargeText(
//
//    )
//}
@Composable
public fun IngredientCard(item: IngredientModel?) {
    Row(
        modifier = Modifier
            .height(12.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val (measure, ingredient) = createRefs()
            Text(
                text = item!!.ingredient,
                modifier = Modifier.constrainAs(ingredient) {
                    start.linkTo(parent.start)
                }
            )
            Text(
                text = item.measure,
                modifier = Modifier.constrainAs(measure) {
                    end.linkTo(parent.end)
                }
            )
        }
    }
}

@Composable
fun IngredientsList(list: List<IngredientModel>) {
    Column {
        list.forEach {
            IngredientCard(item = it)
        }
    }
}

@Composable
fun Instruction(str: String) {
    Row(){
        Text(
            text = str
        )
    }
}