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
import com.example.cocktail_finder.CocktailRepository
import com.example.cocktail_finder.dataModels.DetailsModel
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.dataModels.ListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DetailsFragment : Fragment() {

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

//    override fun onViewCreated (
//        view: View,
//        savedInstanceState: Bundle?
//    ): View {
//        binding = CocktailFragmentBinding.inflate(inflater, container, false)
//        setContent {
//            IngredientCard()
//        }
//        return view
//    }
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

//        floatbtnCreate.setOnClickListener {
//            val item = ListItemViewModel(0, title.text.toString(), description.text.toString())
//            val serializedItem = Json.encodeToString(item)
//            if (itemPosition == null) {
//                requireActivity().supportFragmentManager.setFragmentResult(
//                    ADD_ITEM_REQUEST_KEY,
//                    bundleOf(ADD_ITEM_TITLE_KEY to serializedItem)
//                )
//            }
//            requireActivity().supportFragmentManager.popBackStack()
////            parentFragmentManager.popBackStack()
//        }

    }

    companion object {
        const val ID_KEY = "ID_KEY"

        fun openFragment(item: ListViewModel): Fragment {
            val fragment = DetailsFragment()
            val bundle = bundleOf(
                ID_KEY to item.id)
            fragment.arguments = bundle
            return fragment
        }
//        fun editItemInstance(position: Int, title: String, description: String): Fragment {
//            val fragment = AddItemFragment()
//            val bundle = bundleOf(POSITION_KEY to position, TITLE_KEY to title, DESCRIPTION_KEY to description)
//            fragment.arguments = bundle
//            return fragment
//        }
    }
}