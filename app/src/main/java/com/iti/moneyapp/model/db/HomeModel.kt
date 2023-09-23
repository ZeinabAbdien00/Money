package com.iti.moneyapp.model.db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class HomeModel(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    @ColumnInfo
    var itemName: String?,

    @ColumnInfo
    var itemValue: Int?
)