package com.example.cocktail_finder.data

data class DetailsModel (
    val title: String,
    val img: String,
    val ingredients: List<IngredientModel>,
    val instruction: String

//    val onClickAction: () -> Unit
)

data class IngredientModel (
    val ingredient: String,
    val measure: String
)