package com.ymaskin.thelionking.presentation.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.ymaskin.thelionking.domain.model.Character
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class CharactersViewModel : ViewModel() {
    private val charactersReference = Firebase.database.reference.child("characters")
    private lateinit var listener: ValueEventListener

    private val _characters = MutableStateFlow<List<Character>>(emptyList())
    val characters: StateFlow<List<Character>> = _characters

    fun registerUsersListener() {
        listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val tempList = arrayListOf<Character>()
                snapshot.children.forEach {
                    val character = it.getValue(Character::class.java)
                    character?.let { characterToAdd -> tempList.add(characterToAdd) }
                }
                _characters.value = tempList
            }

            override fun onCancelled(error: DatabaseError) {
                Log.e("CharactersViewModel", "Something went wrong", error.toException())
            }
        }
        charactersReference.addValueEventListener(listener)
    }

    fun unregisterListener() {
        if (::listener.isInitialized) {
            charactersReference.addValueEventListener(listener)
        }
    }
}