package com.example.listaperros.data.datasource.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.listaperros.data.datasource.database.dao.DogDao
import com.example.listaperros.data.datasource.database.entities.DogEntity

@Database(entities = [DogEntity::class], version = 1)

abstract class DatabaseDogs : RoomDatabase(){
    abstract fun dogDao(): DogDao
}