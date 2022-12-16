package com.ymaskin.thelionking.presentation.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ymaskin.thelionking.databinding.FragmentCharactersBinding
import com.ymaskin.thelionking.presentation.adapter.CharactersAdapter
import com.ymaskin.thelionking.presentation.viewmodel.CharactersViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class CharactersFragment : Fragment() {
    private val viewModel: CharactersViewModel by viewModels()
    private lateinit var binding: FragmentCharactersBinding
    private lateinit var charactersAdapter: CharactersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.registerUsersListener()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCharactersRecyclerViewBehavior()
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.characters.collectLatest {
                    charactersAdapter.updateDataSet(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.unregisterListener()
    }

    private fun setCharactersRecyclerViewBehavior() {
        charactersAdapter = CharactersAdapter(
            dataSet = arrayListOf(),
            onCharacterClicked = { clickedCharacter ->
                val action = CharactersFragmentDirections
                    .actionCharactersFragmentToCharacterDetailsFragment(clickedCharacter)
                findNavController().navigate(action)
            }
        )

        binding.charactersRv.apply {
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(context)
            adapter = charactersAdapter
        }
    }
}