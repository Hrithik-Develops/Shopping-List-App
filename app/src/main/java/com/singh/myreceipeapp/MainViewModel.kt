package com.singh.myreceipeapp

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val _categoryState = mutableStateOf(RecipeState())
    val categoriesState: State<RecipeState> = _categoryState

    init{
        fetchCategories()
    }

    private fun fetchCategories() {
        viewModelScope.launch {
            try {
                val response = reciepeService.getCategories()
                _categoryState.value = _categoryState.value.copy(
                    isLoading = false,
                    recipes = response.categories,
                    error = null
                )


            } catch (e: Exception) {
                _categoryState.value = _categoryState.value.copy(
                    isLoading = false,
                    error = "Error fetching categories: ${e.message}"

                )

            }

        }
    }

    data class RecipeState(
        val isLoading: Boolean = false,
        val recipes: List<Category> = emptyList(),
        val error: String? = null
    )

}
