package com.example.myapplication.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.myapplication.data.model.CatImage
import com.example.myapplication.data.model.CatInformation
import com.example.myapplication.data.repository.CatRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CatViewModel @Inject constructor(
    private val repository: CatRepository
) : ViewModel() {


    val catItems = mutableStateListOf<Pair<CatInformation, CatImage>>()

    var error by mutableStateOf<String?>(null)
        private set
    var isLoading by mutableStateOf(true)
        private set

    private val breeds = listOf(
        "abys", "aege", "abob", "acur", "asho", "awir", "amau", "amis", "bali"
    )

    init {
        loadCats()
    }
    private fun loadCats() {
        viewModelScope.launch {
            try {
                repository.getCatBreeds().collect { entities ->
                    catItems.clear()
                    catItems.addAll(entities.map {
                        val info = CatInformation(it.id, it.name, it.description, "")
                        val image = CatImage(it.id, it.url)
                        info to image
                    })
                }
            } catch (e: Exception) {
                error = e.message
                Log.e("CatViewModel", "Ошибка загрузки списка: ${e.message}")
            } finally {
                isLoading = false
            }
        }
    }


    fun getCatInfoBy(id: String): CatInformation?{
        return catItems.find { it.first.id == id }?.first
    }

    fun getCatImageById(imageId: String?): CatImage? {
        return catItems.find { it.second.id == imageId }?.second
    }
}
