package com.example.myapplication

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProductsViewModel(val productsRepository: ProductsRepository) : ViewModel() {
    private val _sortedProducts = MutableLiveData<List<ProductListing>>()
    val sortedProducts: LiveData<List<ProductListing>> = _sortedProducts

    private val _sortButtonsEnabled = MutableLiveData<Boolean>()
    val sortButtonsEnabled: LiveData<Boolean> = _sortButtonsEnabled

    init {
        _sortButtonsEnabled.value = true
    }

    /**
     * Called by the UI when the user clicks the appropriate sort button
     */
    fun onSortAscending() = sortPricesBy(ascending = true)
    fun onSortDescending() = sortPricesBy(ascending = false)

    private fun sortPricesBy(ascending: Boolean) {
        viewModelScope.launch {
            // disable the sort buttons whenever a sort is running
            _sortButtonsEnabled.value = false
            try {
                _sortedProducts.value =
                    productsRepository.loadSortedProducts(ascending)
            } finally {
                // re-enable the sort buttons after the sort is complete
                _sortButtonsEnabled.value = true
            }
        }
    }
}