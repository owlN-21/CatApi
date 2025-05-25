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
import com.example.myapplication.data.repository.CatRepositoryGet
import kotlinx.coroutines.launch


class CatViewModel : ViewModel() {

    private val repository = CatRepositoryGet()

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
                val tempList = mutableListOf<Pair<CatInformation, CatImage>>()

                for (breedId in breeds) {
                    try {
                        val info = repository.getCatInformation(breedId)
                        val image =  repository.getCatImage(info.reference_image_id)
                        tempList.add(info to image)
                    } catch (e: Exception) {
                        Log.e("CatViewModel", "Ошибка для $breedId: ${e.message}")
                    }
                }
                catItems.clear()
                catItems.addAll (tempList)
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
