package com.example.cocktail_finder.list

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import com.example.cocktail_finder.dataModels.ListViewModel
import kotlinx.coroutines.flow.StateFlow

@Composable
fun ListScreen (
    model: StateFlow<ListFragment.ListState>,
    onSearch: (String) -> Unit = {}
) {
    val state by model.collectAsState()
    val inputText = remember { mutableStateOf(TextFieldValue()) }
    Column(

    ) {
        TextField(
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
        for (item in list) {
            ListItem(item)
        }
    }
}
@Composable
fun ListItem (item: ListViewModel) {
    Row (
        modifier = Modifier
            .wrapContentHeight()
            .fillMaxWidth(),
    ) {
        Text(
            text = item.title
        )
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