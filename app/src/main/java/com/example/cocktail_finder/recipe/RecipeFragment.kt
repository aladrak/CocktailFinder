package com.example.cocktail_finder.recipe

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import coil.ImageLoader
import coil.decode.ImageDecoderDecoder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.cocktail_finder.CocktailRepository
import com.example.cocktail_finder.R
import com.example.cocktail_finder.dataModels.IngredientModel
import com.example.cocktail_finder.databinding.CocktailFragmentBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailsFragment : Fragment() {

    private val repository = CocktailRepository()

    private val scope = CoroutineScope(Dispatchers.IO)

    private var binding : CocktailFragmentBinding? = null

    private val title: String?
        get() = arguments?.getString(TITLE_KEY)

    private val img: String?
        get() = arguments?.getString(IMG_KEY)

    private val ing: String?
        get() = arguments?.getString(INGREDIENTS_KEY)

    private val mea: String?
        get() = arguments?.getString(MEASURE_KEY)

    private val ins: String?
        get() = arguments?.getString(INSTRUCTION_KEY)

    private val id: String?
        get() = arguments?.getString(ID_KEY)

    override fun onViewCreated (
        view: View,
        savedInstanceState: Bundle?
    ) {

    }

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

        Glide.with(it.img)
            .load(img)
            .override(400, 400)
            .placeholder(R.drawable.__loading)
            .skipMemoryCache(true)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .into(it.img)
        var result: IngredientModel?
        scope.launch {
            result = repository.searchCocktailById(id)
//            result?.let {
//                binding!!.name.setText(result.title ?: "")
//                binding!!.ingredients.setText(result.ingredients ?: "")
////                binding!!.measure.setText(result.measure ?: "")
//                binding!!.instruction.setText(result.instruction)
//            }
        }
        it.name.setText(title ?: "")
//        it.ingredients.setText(ing ?: "")
//        it.measure.setText(mea ?: "")
        it.instruction.setText(ins)

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

    companion object {

        const val TITLE_KEY = "TITLE_KEY"
        const val IMG_KEY = "IMG_KEY"
        const val INGREDIENTS_KEY = "INGREDIENTS_KEY"
        const val MEASURE_KEY = "MEASURE_KEY"
        const val INSTRUCTION_KEY = "INSTRUCTION_KEY"
        const val ID_KEY = "ID_KEY"

        fun openFragment(id: String, title: String, img: String, ingred: String, mea: String, instr: String): Fragment {
            val fragment = DetailsFragment()
            val bundle = bundleOf(
                ID_KEY to id,
                TITLE_KEY to title,
                IMG_KEY to img,
                INGREDIENTS_KEY to ingred,
                MEASURE_KEY to mea,
                INSTRUCTION_KEY to instr)
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