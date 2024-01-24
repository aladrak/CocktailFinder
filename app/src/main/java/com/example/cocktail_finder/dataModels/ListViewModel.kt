package com.example.cocktail_finder.dataModels

data class ListViewModel (
    val id: String,
    val title: String,
    var onClickAction: () -> Unit
)