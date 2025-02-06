package com.example.listaperros.data.datasource.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity()
data class DogEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val id: Int = 0,  // Por defecto ponemos el id a 0
    @ColumnInfo(name = "breed") val breed: String,
    @ColumnInfo(name = "image") val image: String
)
