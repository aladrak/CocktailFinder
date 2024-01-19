package com.example.cocktail_finder

import android.util.Log
import com.example.cocktail_finder.dataModels.DetailsModel
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.dataModels.ListViewModel
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONArray
import org.json.JSONObject

class CocktailRepository {
    private val client = OkHttpClient()

    suspend fun searchCocktail(searchString: String): List<ListViewModel>? {
        val request = Request.Builder()
            .url(SEARCH_BY_NAME + searchString)
            .build()
        try {
            val response = client.newCall(request).execute()
            val json = JSONObject(response.body!!.string())
            val list = json.get("drinks")
            val resultList = mutableListOf<ListViewModel>()
            if (list is JSONArray) {
                (0 until list.length()).forEach {
                    val item = list.getJSONObject(it)
                    resultList.add(
                        ListViewModel(
                            item.getString("idDrink"),
                            item.getString("strDrink")
                        )
                    )
                }
            } else {
                resultList.add(
                    ListViewModel(
                        "",
                        "No results!"
                    )
                )
            }
            return resultList
        } catch (ex: Exception) {
            Log.e("OK_HTTP", ex.message ?: "")
            return null
        }
        return null
    }

    suspend fun searchCocktailByIng(searchString: String): List<ListViewModel>? {
        val request = Request.Builder()
            .url(SEARCH_BY_ING + searchString)
            .build()
        try {
            val response = client.newCall(request).execute()
            val json = JSONObject(response.body!!.string())
            val list = json.get("drinks")
            val resultList = mutableListOf<ListViewModel>()
            if (list is JSONArray) {
                (0 until list.length()).forEach {
                    val item = list.getJSONObject(it)
                    resultList.add(
                        ListViewModel(
                            item.getString("idDrink"),
                            item.getString("strDrink")
                        )
                    )
                }
            } else {
                resultList.add(
                    ListViewModel(
                        "",
                        "No results!"
                    )
                )
            }
            return resultList
        } catch (ex: Exception) {
            Log.e("OK_HTTP", ex.message ?: "")
            return null
        }
        return null
    }

    fun searchCocktailById(idDrink: String?): DetailsModel? {
        val request = Request.Builder()
//            .url("https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i=11728")
            .url(SEARCH_BY_ID + idDrink)
            .build()
        try {
            val response = client.newCall(request).execute()
            val json = JSONObject(response.body!!.string())
            val list = json.get("drinks")
            if (list is JSONArray) {
                val item = list.getJSONObject(0)
                return DetailsModel (
                    title = item.getString("strDrink"),
                    img = item.getString("strDrinkThumb"),
                    ingredients = createIngredientList(item),
                    instruction = item.getString("strInstructions")
                )
            }
        } catch (ex: Exception) {
            Log.e("OK_HTTP", ex.message ?: "")
            return null
        }
        return null
    }

    private fun createIngredientList(item: JSONObject): List<IngredientModel> {
        var list = mutableListOf<IngredientModel>()
        for (i in 1 .. 15) {
            if (getIngredient(item, i) != "" && getMeasure(item, i) != "") {
                list.add(
                    IngredientModel(
                        ingredient = getIngredient(item, i),
                        measure = getMeasure(item, i)
                    )
                )
            } else {break}
        }
        return list
    }
    private fun getMeasure(item: JSONObject, i: Int): String {
        var el = item.getString("strMeasure$i")
        if (el == "null" || el == "") {
            return ""
        }
        else if (el.contains("oz", false))
        {
            var regres1 = Regex("""(\d+)""").find(el, 0)?.value
            var regres2 = Regex("""(\d)""").find(el, 2)?.value
            var regres3 = Regex("""(\d)""").find(el, 4)?.value
            if (regres2 != null && regres3 != null && regres1 != null) {
                el = (regres1.toInt() * 30 + (regres2.toInt() * 30 / regres3.toInt())).toString() + " gram"
            } else if (regres1 != null) {
                val regresEx = Regex("""(\d)""").find(el, 2)?.value
                if (regresEx != null) {
                    el = (regres1.toInt() * 30 / regresEx.toInt()).toString() + " gram"
                } else {
                    el = (regres1.toInt() * 30).toString() + " gram"
                }
            }
//                el = el.replace("oz", "gram")
        }
        else if (el.contains("cl", false))
        {
            el = el.replace("cl", "ml")
        }
        return el
    }

    private fun getIngredient(item: JSONObject, i: Int): String {
        if (item.getString("strIngredient$i") == "null") {
            return ""
        }
        return "$i. " + item.getString("strIngredient$i")
    }

//    private fun JSONObject.toMap(): Map<String, *> = keys().asSequence().associateWith {
//        when (val value = this[it])
//        {
//            is JSONArray ->
//            {
//                val map = (0 until value.length()).associate { Pair(it.toString(), value[it]) }
//                JSONObject(map).toMap().values.toList()
//            }
//            is JSONObject -> value.toMap()
//            JSONObject.NULL -> null
//            else            -> value
//        }
//    }

    companion object {
        private const val SEARCH_BY_NAME = "https://www.thecocktaildb.com/api/json/v1/1/search.php?s="
        private const val SEARCH_BY_ING = "https://www.thecocktaildb.com/api/json/v1/1/filter.php?i="
        private const val SEARCH_BY_ID = "https://www.thecocktaildb.com/api/json/v1/1/lookup.php?i="
    }
}