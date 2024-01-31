package com.example.cocktail_finder.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.cocktail_finder.utils.CocktailRepository
import com.example.cocktail_finder.recipe.RecipeFragment
import com.example.cocktail_finder.R
import com.example.cocktail_finder.dataModels.ListViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListFragment : Fragment() {
    data class ListState(
        val list: List<ListViewModel> = listOf(ListViewModel("", "", {})),
    )
    private val state = MutableStateFlow(ListState())
    private val repository = CocktailRepository()

//    private var ingSearch: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = ComposeView(requireContext()).apply {
        setViewCompositionStrategy(
            ViewCompositionStrategy.DisposeOnLifecycleDestroyed(viewLifecycleOwner)
        )
        setContent {
            ListScreen (
                model = state,
                onSearch = {
                    viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                        val result = repository.searchCocktail(it)
                        result?.let {
                            if (result[0].id != "-1") {
                                result.forEach {
                                    it.onClickAction = {
                                        requireActivity()
                                            .supportFragmentManager
                                            .beginTransaction()
                                            .replace(
                                                R.id.container,
                                                RecipeFragment.openFragment(it)
                                            )
                                            .addToBackStack(RecipeFragment::class.java.canonicalName)
                                            .commit()
                                    }
                                }
                            }
                            withContext(Dispatchers.Main) {
                                state.emit(
                                    ListState(
                                        list = result
                                    )
                                )
                            }
                        }
                    }
                }
            )
        }
    }

//    override fun onCreateView(
//        inflater: LayoutInflater,
//        container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        binding = ListFragmentBinding.inflate(inflater, container, false)
//        binding?.let {
//
//            it.switchBox.setOnCheckedChangeListener { buttonView, isChecked ->
//                ingSearch = isChecked
//            }
//            it.cocktailList.addItemDecoration(
//                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
//                    setDrawable(
//                        ResourcesCompat.getDrawable(resources,
//                            R.drawable.item_separator, requireContext().theme)!!)
//                }
//            )
//            it.cocktailList.adapter = adapter
//            it.searchField.setOnEditorActionListener { v, actionId, event ->
//                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
//                    if (ingSearch == false)
//                    {
//                        adapter.search(it.searchField.text.toString())
//                    }
//                    else
//                    {
//                        adapter.searchByIng(it.searchField.text.toString())
//                    }
//                }
//                true
//            }
//            it.searchButton.setOnClickListener { _ ->
//                if (it.searchField.text.toString() != "") {
//                    if (ingSearch == false) {
//                        adapter.search(it.searchField.text.toString())
//                    } else {
//                        adapter.searchByIng(it.searchField.text.toString())
//                    }
//                }
//            }
//        }
//    return binding?.root
//    }
}
