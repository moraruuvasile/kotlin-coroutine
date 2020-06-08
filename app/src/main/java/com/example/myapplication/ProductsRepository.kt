package com.example.myapplication

class ProductsRepository(val productsDao: ProductsDao) {

    /**
     * This is a "regular" suspending function, which means the caller must
     * be in a coroutine. The repository is not responsible for starting or
     * stopping coroutines since it doesn't have a natural lifecycle to cancel
     * unnecessary work.
     *
     * This *may* be called from Dispatchers.Main and is main-safe because
     * Room will take care of main-safety for us.
     */
    suspend fun loadSortedProducts(ascending: Boolean): List<ProductListing> {
        return if (ascending) {
            productsDao.loadProductsByDateStockedAscending()
        } else {
            productsDao.loadProductsByDateStockedDescending()
        }
    }
}