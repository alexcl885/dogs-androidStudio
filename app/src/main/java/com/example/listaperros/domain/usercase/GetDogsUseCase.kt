package com.example.listaperros.domain.usercase

import com.example.listaperros.data.datasource.database.entities.DogEntity
import com.example.listaperros.data.repository.DogRepository
import com.example.listaperros.domain.mapper.toDogEntity
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.models.Repository
import javax.inject.Inject

class GetDogsUseCase @Inject constructor(
    private val dogRepositoryDao: DogRepository
) {
    suspend operator fun invoke(): List<Dog>?{
        Repository.dogs = dogRepositoryDao.getDogsEntity()  //Aquí tengo los datos.

        /*
           1.- Si no tengo nada en la BBDD, lo que haremos es cargarlos de memoria
           e insertarlo directamente en la BBDD.
           2.- Para ello, tengo que insertarlos en la BBDD, pero no como DogModel, sino como DogEntity, por tanto
           utilizo otro mapper.
           3.- Con los datos mapeados a DogEntity, ahora lo insertaremos en la BBDD.
            */
        if (Repository.dogs.isEmpty()){

            Repository.dogs = dogRepositoryDao.getDogs() //Aquí tengo los datos de memoria.
            val dataDogEntity : List<DogEntity> = Repository.dogs.map { it.toDogEntity() }  //lo mapeamos a Entity.
            dogRepositoryDao.insertBreedEntitytoDatabase(dataDogEntity)
        }
        return Repository.dogs

    }
}