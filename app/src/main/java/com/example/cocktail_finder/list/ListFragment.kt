package com.example.cocktail_finder.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import com.example.cocktail_finder.recipe.DetailsFragment
import com.example.cocktail_finder.ListAdapter
import com.example.cocktail_finder.R
import com.example.cocktail_finder.databinding.ListFragmentBinding

class ListFragment : Fragment() {

    private var binding: ListFragmentBinding? = null

    private var ingSearch: Boolean = false

    private val adapter = ListAdapter {item ->
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .replace(R.id.container, DetailsFragment.openFragment(item))
            .addToBackStack(DetailsFragment::class.java.canonicalName)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        binding?.let {

            it.switchBox.setOnCheckedChangeListener { buttonView, isChecked ->
                ingSearch = isChecked
            }
            it.cocktailList.addItemDecoration(
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL).apply {
                    setDrawable(
                        ResourcesCompat.getDrawable(resources,
                            R.drawable.item_separator, requireContext().theme)!!)
                }
            )
            it.cocktailList.adapter = adapter
            it.searchField.setOnEditorActionListener { v, actionId, event ->
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    if (ingSearch == false)
                    {
                        adapter.search(it.searchField.text.toString())
                    }
                    else
                    {
                        adapter.searchByIng(it.searchField.text.toString())
                    }
                }
                true
            }
            it.searchButton.setOnClickListener { _ ->
                if (it.searchField.text.toString() != "") {
                    if (ingSearch == false) {
                        adapter.search(it.searchField.text.toString())
                    } else {
                        adapter.searchByIng(it.searchField.text.toString())
                    }
                }
            }
        }
    return binding?.root
    }
}
