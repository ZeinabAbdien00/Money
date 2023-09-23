package com.iti.moneyapp.data.local.home_db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iti.moneyapp.model.db.HomeModel

@Database(entities = [HomeModel::class], exportSchema = true, version = 1)
abstract class HomeDatabase : RoomDatabase() {
    abstract fun getDao(): HomeDao

    companion object {
        var db: HomeDatabase? = null
        fun buildHomeDb(context: Context): HomeDatabase? {
            db = Room.databaseBuilder(
                context.applicationContext,
                HomeDatabase::class.java,
                "HomeDatabase"
            )
                .build()
            return db!!

        }
    }
}