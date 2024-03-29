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
import androidx.lifecycle.lifecycleScope
import com.example.cocktail_finder.data.CocktailRepository
import com.example.cocktail_finder.data.DetailsModel
import com.example.cocktail_finder.data.IngredientModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RecipeFragment : Fragment() {

    private val repository = CocktailRepository()

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
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
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

        fun openFragment(id: String): Fragment {
            val fragment = RecipeFragment()
            val bundle = bundleOf(
                ID_KEY to id)
            fragment.arguments = bundle
            return fragment
        }
    }
}