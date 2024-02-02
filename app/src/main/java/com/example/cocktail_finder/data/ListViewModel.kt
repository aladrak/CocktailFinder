package com.example.cocktail_finder.data

data class ListViewModel (
    val id: String,
    val title: String,
    var onClickAction: () -> Unit
)