package com.example.cocktail_finder.recipe

import android.os.Bundle
import android.provider.Contacts
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_finder.CocktailRepository
import com.example.cocktail_finder.R
import com.example.cocktail_finder.dataModels.DetailsModel
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.dataModels.ListViewModel
import com.example.cocktail_finder.databinding.CocktailFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val repository = CocktailRepository()

    private val scope = CoroutineScope(Dispatchers.IO)

    private var binding : CocktailFragmentBinding? = null

//    private val item: ListViewModel?
//        get() = arguments.
    private val id: String?
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
    ): View = CocktailFragmentBinding.inflate(inflater, container, false).also {
        binding = it

        val imageLoader = ImageLoader.Builder(requireContext())
            .components {
                add(ImageDecoderDecoder.Factory())
            }
            .build()
        var result: DetailsModel? = null
        scope.launch {
            result = repository.searchCocktailById(id)
            result?.let {
                binding!!.name.text = result!!.title ?: ""
    //                binding!!.ingredients.setText(result.ingredients ?: "")
    //                binding!!.measure.setText(result.measure ?: "")
    //                binding!!.instruction.setText(result.instruction)
                binding!!.recipeComposeView.setContent {
                    IngredientsList(list = result!!.ingredients)
                }
            }
        }
//        it.recipeComposeView.setContent {
//            getData()?.let { it1 -> IngredientsList(it1.ingredients) }
//        }

//        Glide.with(result.img)
//            .load(img)
//            .override(400, 400)
//            .placeholder(R.drawable.__loading)
//            .skipMemoryCache(true)
//            .diskCacheStrategy(DiskCacheStrategy.NONE)
//            .into(it.img)


//        it.name.setText(title ?: "")
//        it.ingredients.setText(ing ?: "")
//        it.measure.setText(mea ?: "")
//        it.instruction.setText(ins)

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

    }.root

    fun getData(): DetailsModel? {
        var result: DetailsModel? = null
        scope.launch {
            result = repository.searchCocktailById(id)
            result?.let {
                binding!!.name.text = result!!.title ?: ""
//                binding!!.ingredients.setText(result.ingredients ?: "")
//                binding!!.measure.setText(result.measure ?: "")
//                binding!!.instruction.setText(result.instruction)
            }
        }
        Thread.sleep(1800L)
        return result
    }

    companion object {

        const val ITEM_KEY = "ITEM_KEY"
        const val TITLE_KEY = "TITLE_KEY"
        const val IMG_KEY = "IMG_KEY"
        const val INGREDIENTS_KEY = "INGREDIENTS_KEY"
        const val MEASURE_KEY = "MEASURE_KEY"
        const val INSTRUCTION_KEY = "INSTRUCTION_KEY"
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