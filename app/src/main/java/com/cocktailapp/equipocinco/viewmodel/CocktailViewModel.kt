package com.cocktailapp.equipocinco.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import com.cocktailapp.equipocinco.repository.CocktailRepository
import com.cocktailapp.equipocinco.model.Drink

class CocktailViewModel(application: Application) : AndroidViewModel(application) {
    val context = getApplication<Application>()
    private val cocktailRepository = CocktailRepository(context)

    private val _progressState = MutableLiveData(false)
    val progressState: LiveData<Boolean> = _progressState

    private val _cocktail = MutableLiveData<List<Drink> >()
    val cocktail: LiveData<List<Drink> > get() = _cocktail

    fun getCocktail(cocktail: String) {
        viewModelScope.launch {
            _progressState.value = true
            try {
                _cocktail.value = cocktailRepository.getCocktail(cocktail)
                _progressState.value = false
            } catch (e: Exception) {
                _progressState.value = false
            }
        }
    }
}