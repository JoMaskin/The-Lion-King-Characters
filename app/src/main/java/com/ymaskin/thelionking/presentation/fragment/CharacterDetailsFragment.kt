package com.ymaskin.thelionking.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.ymaskin.thelionking.R
import com.ymaskin.thelionking.databinding.FragmentCharacterDetailsBinding
import com.ymaskin.thelionking.util.setImage

class CharacterDetailsFragment : Fragment() {
    private lateinit var binding: FragmentCharacterDetailsBinding
    private val args: CharacterDetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val placeholder = ContextCompat.getDrawable(
            requireContext(),
            R.drawable.ic_image_placeholder
        )
        binding.apply {
            characterDetailsImage.setImage(
                context = requireContext(),
                url = args.character.imageUrl ?: "",
                width = 800,
                height = 1000,
                placeholder = placeholder
            )

            characterDetailsTitle.text = args.character.name
            characterDetailsDescription.text = args.character.description
        }
    }
}