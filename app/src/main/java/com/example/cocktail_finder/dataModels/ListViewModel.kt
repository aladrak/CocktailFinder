package com.example.cocktail_finder.dataModels

data class ListViewModel (
    val id: String,
    val title: String,
    val img: String,
    val ingredients: String,
    val measure: String,
    val instructions : String
//    val onClickAction: () -> Unit
)