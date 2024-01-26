package com.example.cocktail_finder.list

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.paddingFrom
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.Start
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.cocktail_finder.dataModels.ListViewModel
import com.example.cocktail_finder.utils.Line
import com.example.cocktail_finder.utils.MediumText
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListScreen (
    model: StateFlow<ListFragment.ListState>,
    onSearch: (String) -> Unit = {}
) {
    val state by model.collectAsState()
    val inputText = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .padding(5.dp)
    ) {
        Column {
            OutlinedTextField(
                label = { Text("Search") },
                value = inputText.value,
                onValueChange = {
                    inputText.value = it
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                singleLine = true,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Search),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onSearch(inputText.value.text)
                    }
                )
            )
        }
        CocktailList(state.list)
    }
}
@Composable
fun CocktailList (list: List<ListViewModel>) {
    Column (
        modifier = Modifier
            .wrapContentHeight()
            .verticalScroll(rememberScrollState())
    ) {
        list.forEachIndexed { index, item ->
            ListItem(item, index == list.lastIndex)
        }
    }
}
@Composable
fun ListItem (item: ListViewModel, b: Boolean) {
    Column(
        modifier = Modifier
            .clickable {
                item.onClickAction()
            }
            .height(55.dp)
            .fillMaxWidth(),
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box (
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier
                .height(54.dp)
                .padding(5.dp, 0.dp)
        ) {
            MediumText(
                text = item.title,
                modifier = Modifier,
            )
        }
        if (!b) {
            Line(1.dp)
        }
    }
}

//private fun Recipe
//        setContent {
//            MaterialTheme(
//                Id (
//                    args = smth
//                )
//            )
//        }