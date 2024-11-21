package com.example.dessertclicker.viewmodel

import androidx.lifecycle.ViewModel
import com.example.dessertclicker.R
import com.example.dessertclicker.data.Datasource
import com.example.dessertclicker.model.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class DessertViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(
        UiState(
            revenue = 0,
            dessertsSold = 0,
            currentDessertImageId = R.drawable.cupcake, // Default to a valid drawable
            currentDessertPrice = 5
        )
    )
    val uiState: StateFlow<UiState> = _uiState


    private val desserts = Datasource.dessertList

    fun onDessertClicked() {
        val currentState = _uiState.value
        val revenue = currentState.revenue + currentState.currentDessertPrice
        val dessertsSold = currentState.dessertsSold + 1

        // Determine the next dessert
        val nextDessert = determineDessertToShow(dessertsSold)

        // Update the state
        _uiState.value = UiState(
            revenue = revenue,
            dessertsSold = dessertsSold,
            currentDessertImageId = nextDessert.imageId,
            currentDessertPrice = nextDessert.price
        )
    }

    private fun determineDessertToShow(dessertsSold: Int) =
        desserts.lastOrNull { it.startProductionAmount <= dessertsSold } ?: desserts.first()
}
