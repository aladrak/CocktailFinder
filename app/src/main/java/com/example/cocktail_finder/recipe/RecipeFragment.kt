package com.example.cocktail_finder.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.example.cocktail_finder.utils.CocktailRepository
import com.example.cocktail_finder.dataModels.DetailsModel
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.dataModels.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeFragment : Fragment() {

    private val repository = CocktailRepository()
    private val scope = CoroutineScope(Dispatchers.IO)

    data class State(
        val model: DetailsModel = DetailsModel("", "", listOf(IngredientModel("", "")), ""),
        val load: Boolean = true
    )
    private val state = MutableStateFlow(State())

    @Preview
    @Composable
    fun RecipeScreenPreview() {
        RecipeScreen(model = state)
    }
    private val _id: String?
        get() = arguments?.getString(ID_KEY)

    override fun onCreateView (
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        )
        setContent {
            RecipeScreen(
                model = state
            )
        }
        scope.launch {
            val result = repository.searchCocktailById(_id)
            result?.let {
                withContext(Dispatchers.Main) {
                    state.emit(
                        State(
                            model = result,
                            load = true
                        )
                    )
                }
            }
        }
    }

    companion object {
        const val ID_KEY = "ID_KEY"

        fun openFragment(item: ListViewModel): Fragment {
            val fragment = RecipeFragment()
            val bundle = bundleOf(
                ID_KEY to item.id)
            fragment.arguments = bundle
            return fragment
        }
    }
}