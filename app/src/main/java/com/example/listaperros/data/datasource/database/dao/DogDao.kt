package com.example.listaperros.data.datasource.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.listaperros.data.datasource.database.entities.DogEntity

@Dao
interface DogDao {

    // Listado de todos los Dogs (de tipo DogEntity)
    @Query("SELECT * FROM dogentity")
    suspend fun getAll(): List<DogEntity> // Aquí devolvemos DogEntity, no Dogs

    // Listado de todos los Dogs dada la raza
    @Query("SELECT * FROM dogentity WHERE breed = :breed")
    suspend fun getDogsByBreed(breed: String): List<DogEntity> // También cambiamos aquí

    // Insertamos uno o varios Dogs (DogEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDog(vararg dogs: DogEntity)

    // Insertamos una lista de Dogs (DogEntity)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllDog(dogs: List<DogEntity>)

    // Eliminamos todos los Dogs
    @Query("DELETE FROM dogentity")
    suspend fun deleteAll()

    @Query("DELETE FROM dogentity WHERE breed = :breed")
    suspend fun deleteDog(breed: String)
}
