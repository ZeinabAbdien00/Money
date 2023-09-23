package com.iti.moneyapp.data.local.home_db

import androidx.room.*
import com.iti.moneyapp.model.db.HomeModel

@Dao
interface HomeDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNote(model: List<HomeModel>)

    @Query("select * from HomeModel")
    fun viewNotes(): List<HomeModel>

//    @Delete
//    suspend fun deleteNote(model: HomeModel)
//
//    @Update
//    fun updateNote(model: HomeModel)


}