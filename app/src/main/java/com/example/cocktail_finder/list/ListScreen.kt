package com.example.cocktail_finder.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.cocktail_finder.R
import com.example.cocktail_finder.data.ListViewModel
import com.example.cocktail_finder.utils.Line
import com.example.cocktail_finder.utils.MediumText
import kotlinx.coroutines.flow.StateFlow

//@Composable
//@Preview
//fun PreviewListScreen(){
//    SearchField(mutableStateOf(TextFieldValue("Test")))
//}

@Composable
fun ListScreen(
    model: StateFlow<ListFragment.ListState>,
    onSearch: (String) -> Unit = {}
) {
    val state by model.collectAsState()
    val inputText = remember { mutableStateOf(TextFieldValue()) }
    Column(
        modifier = Modifier
            .padding(8.dp, 8.dp, 8.dp, 0.dp)
    ) {
        SearchField(inputText, onSearch)
        CocktailList(state.list)
    }
}

@Composable
fun SearchField(
    inputText: MutableState<TextFieldValue>,
    onSearch: (String) -> Unit = {},
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
    ) {
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
            ),
            trailingIcon = {
                IconButton(
                    modifier = Modifier
                        .size(50.dp),
                    onClick = { onSearch(inputText.value.text) },
                ) {
                    Icon(
                        painter = painterResource(R.drawable.baseline_search_24),
                        "Search btn",
                    )
                }
            }
        )
    }
}

@Composable
fun CocktailList(list: List<ListViewModel>) {
    Column(
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
fun ListItem(item: ListViewModel, end: Boolean) {
    Column(
        modifier = Modifier
            .clickable {
                item.onClickAction()
            }
            .height(55.dp)
            .fillMaxWidth(),
//        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        Box(
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
        if (!end) {
            Line(1.dp)
        }
    }
}