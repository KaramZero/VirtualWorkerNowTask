package com.example.vwntask.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.vwntask.model.pojo.Product

@Database(entities = [Product::class], version = 1)
@TypeConverters(Converters::class)
abstract class RoomDb : RoomDatabase() {
    abstract fun roomDAO(): RoomDAO

    companion object {
        private var INSTANCE: RoomDb? = null

        //one thread at a time to access this method
        @Synchronized
        fun getInstance(context: Context): RoomDb {
            return INSTANCE ?: Room.databaseBuilder(
                context.applicationContext,
                RoomDb::class.java,
                "ProductsAppData"
            ).fallbackToDestructiveMigration().build()
        }
    }
}