package com.example.myapplication

import androidx.room.Dao
import androidx.room.Query

@Dao
interface ProductsDao {
    // Because this is marked suspend, Room will use it's own dispatcher
    //  to run this query in a main-safe way.
    @Query("select * from ProductListing ORDER BY dateStocked ASC")
    suspend fun loadProductsByDateStockedAscending(): List<ProductListing>

    // Because this is marked suspend, Room will use it's own dispatcher
    //  to run this query in a main-safe way.
    @Query("select * from ProductListing ORDER BY dateStocked DESC")
    suspend fun loadProductsByDateStockedDescending(): List<ProductListing>
}