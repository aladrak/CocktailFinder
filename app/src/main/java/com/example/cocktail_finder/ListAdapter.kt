package com.example.cocktail_finder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cocktail_finder.dataModels.ListViewModel
import com.example.cocktail_finder.databinding.ListItemBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListAdapter (
    private val onButtonClick : (id: String, title: String, img: String, ing: String, mea: String, ins: String) -> Unit
) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private val list = mutableListOf<ListViewModel>()

    private val scope = CoroutineScope(Dispatchers.IO)

    private val repository = CocktailRepository()

    inner class ListViewHolder (
        private val binding: ListItemBinding
    ): RecyclerView.ViewHolder(binding.root) {

        private val cocktailItem: AppCompatTextView = binding.cocktailItem

        fun initViewModel(viewModel: ListViewModel) {
            binding.viewModel = viewModel
        }

        init {
            cocktailItem.setOnClickListener {
                val item = list[adapterPosition];
                onButtonClick(item.id, item.title, item.img, item.ingredients, item.measure, item.instructions)
                Toast.makeText(
                    cocktailItem.context,
                    cocktailItem.text.toString(),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ListItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ListViewHolder(binding)
    }

    override fun getItemCount(): Int = list.size

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.initViewModel(list[position])
    }

     fun search(searchString: String) {
         scope.launch {
             val result = repository.searchCocktail(searchString)
             result?.let {
                 list.clear()
                 list.addAll(it)
             }
             withContext(Dispatchers.Main) {
                 notifyDataSetChanged()
             }
         }
    }

    fun searchByIng(searchString: String) {
        scope.launch {
            val result = repository.searchCocktailByIng(searchString)
            result?.let {
                list.clear()
                list.addAll(it)
            }
            withContext(Dispatchers.Main) {
                notifyDataSetChanged()
            }
        }
    }
}