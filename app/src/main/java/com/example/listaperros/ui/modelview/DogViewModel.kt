package com.example.listaperros.ui.modelview

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.domain.usercase.DeleteDogUseCase
import com.example.listaperros.domain.usercase.DeleteDogsFromDataBaseUseCase
import com.example.listaperros.domain.usercase.GetDogsBreedUseCase
import com.example.listaperros.domain.usercase.GetDogsUseCase
import com.example.listaperros.domain.usercase.PostDogEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class DogViewModel @Inject constructor(
    private val useCaseList : GetDogsUseCase,
    private val getDogsBreedUseCase: GetDogsBreedUseCase,
    private val userCaseDeleteDatabase : DeleteDogsFromDataBaseUseCase,
    private val insertDog : PostDogEntity,
    private val deleteDogUseCase : DeleteDogUseCase
): ViewModel() {


    var dogListLiveData = MutableLiveData<List<Dog>>() //repositorio observable
    var progressBarLiveData = MutableLiveData<Boolean> () //progressbar observable
    var breed = MutableLiveData<String>() //para el campo search con la raza.
    var insertLiveData = MutableLiveData<Boolean>()
    var deleteDogLiveData = MutableLiveData<Boolean>()
    fun list() {
        viewModelScope.launch {
            progressBarLiveData.value = true //notifico
            delay(500)
            // useCaseList = GetDogsUseCase()  //Ya no me hace falta, porque se crea por Hilt.
            var data : List<Dog> ?
            withContext(Dispatchers.IO){
                data  = useCaseList()  //aquí se invoca y se obtienen los datos.
            }
            // var data : List<Dog> ? = useCaseList()  //aquí se invoca y se obtienen los datos.
            data.let {
                dogListLiveData.value = it  //notifico
                progressBarLiveData.value = false  //notifico
            }
        }
    }
    fun insertDogFun(dog:Dog) {
        viewModelScope.launch {
            progressBarLiveData.value = true
            var res = false;
            withContext(Dispatchers.IO){
                insertDog.dog = dog
                res = insertDog(dog)
            }
            progressBarLiveData.value = false;
            if (res){
                insertLiveData.value = true
            }


        }

    }



    fun listForBreed(breed: String) {
        viewModelScope.launch {
            progressBarLiveData.value = true //notifico
            delay(500)
            var data : List<Dog> ?

            withContext(Dispatchers.IO){
                getDogsBreedUseCase.setBreed(breed) //primero tenemos que setear, antes de llamar al caso de uso
                data  = getDogsBreedUseCase()  //aquí se invoca y se obtienen los datos.
            }
            data.let {
                dogListLiveData.value = it  //notifico
                progressBarLiveData.value = false  //notifico
            }
        }
    }

    fun searchByBreed(breed: String){
        //Log.i("TAG-DOGS", "La raza elegida es $breed")
        this.breed.value = breed  //notificamos cambio
    }

    /*
    Este caso de uso, es para eliminar los datos de la base de datos.
     */
    fun delete() {
        viewModelScope.launch {
            withContext(Dispatchers.IO){
                userCaseDeleteDatabase() //si invocamos para borrar la base de datos.
            }
            list() //Vuelvo a cargar los datos desde Dogs.
        }
    }
    fun deleteDogFunction(dog: Dog) {
        viewModelScope.launch {
            progressBarLiveData.value = true
            var res = false
            withContext(Dispatchers.IO) {
                deleteDogUseCase.dog = dog
                res = deleteDogUseCase(dog)
            }
            progressBarLiveData.value = false
            deleteDogLiveData.value = res

            if (res) {
                list()
            }
        }
    }


}
