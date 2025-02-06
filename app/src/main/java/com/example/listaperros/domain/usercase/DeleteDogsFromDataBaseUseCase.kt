package com.example.listaperros.domain.usercase

import com.example.listaperros.data.datasource.database.dao.DogDao
import javax.inject.Inject

class DeleteDogsFromDataBaseUseCase @Inject constructor(
    private val dogRepositoryDao : DogDao
){

    //Me cargo la base de datos y en memoria también.
    suspend operator fun invoke(){
        dogRepositoryDao.deleteAll()
    }
}