package com.ymaskin.thelionking.presentation.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.ymaskin.thelionking.R
import com.ymaskin.thelionking.databinding.RowCharacterBinding
import com.ymaskin.thelionking.domain.model.Character
import com.ymaskin.thelionking.util.setImage

class CharactersAdapter(
    private var dataSet: ArrayList<Character>,
    private val onCharacterClicked: (character: Character) -> Unit,
) : RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {
    private lateinit var binding: RowCharacterBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        binding = RowCharacterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CharacterViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(dataSet[holder.adapterPosition], onCharacterClicked)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateDataSet(newDataSet: List<Character>) {
        dataSet = ArrayList()
        dataSet.addAll(newDataSet)
        notifyDataSetChanged()
    }

    class CharacterViewHolder(private val binding: RowCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(
            character: Character,
            onCharacterClicked: (character: Character) -> Unit
        ) {
            binding.apply {
                characterName.text = character.name
                characterImage.apply {
                    val placeholder = ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_image_placeholder
                    )
                    setImage(
                        context = context,
                        url = character.imageUrl ?: "",
                        width = 300,
                        height = 300,
                        placeholder = placeholder,
                    )
                }
                root.setOnClickListener {
                    onCharacterClicked.invoke(character)
                }
            }
        }
    }
}