package com.example.cocktail_finder.recipe

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.utils.LargeText
import com.example.cocktail_finder.utils.Line
import com.example.cocktail_finder.utils.SmallText
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeScreen(
    model: StateFlow<RecipeFragment.State>
) {
    val state by model.collectAsState()
    Column(
        modifier = Modifier
            .padding(start = 1.dp, end = 1.dp)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
        ) {
            val (image, title, instruction, ingredients) = createRefs()
            Box(
                modifier = Modifier
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                    },
            ) {
                Image(state.model.img)
            }
            Box(
                modifier = Modifier
                    .constrainAs(title) {
                        top.linkTo(image.bottom)
                    },
            ) {
                Title(state.model.title)
            }
            Box(
                modifier = Modifier
                    .constrainAs(instruction) {
                        top.linkTo(title.bottom)
                    },
            ) {
                Instruction(state.model.instruction)
            }
            Box(
                modifier = Modifier
                    .constrainAs(ingredients) {
                        top.linkTo(instruction.bottom)
                    },
            ) {
                IngredientsList(state.model.ingredients)
            }
        }
    }
}
@Composable
fun IngredientCard(item: IngredientModel?) {
    Row(
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        ConstraintLayout (
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            val (measure, ingredient) = createRefs()
            SmallText(
                text = item!!.ingredient,
                modifier = Modifier.constrainAs(ingredient) {
                    start.linkTo(parent.start)
                }
            )
            SmallText(
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
    Column (
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth()
    ){
        SmallText(
            text = str
        )
    }
}

@Composable
fun Title(str: String) {
    Column(
        modifier = Modifier
            .height(65.dp)
            .padding(0.dp, 0.dp)
            .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
//        verticalAlignment = Alignment.CenterVertically,
    ){
        LargeText(
            text = str,
            modifier = Modifier
                .padding(0.dp, 6.dp),
        )
        Line(1.dp)
    }
}

@Composable
fun Image(link: String){
    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .height(200.dp)
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterEnd
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(link)
                    .crossfade(true)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clip(RoundedCornerShape(3))
                    .size(200.dp)
            )
        }
    }
}