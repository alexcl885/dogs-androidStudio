package com.example.listaperros.ui.views

import android.os.Bundle
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.widget.SearchView

import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.listaperros.databinding.ActivityMainBinding
import com.example.listaperros.domain.models.Dog
import com.example.listaperros.ui.adapter.DogAdapter
import com.example.listaperros.ui.modelview.DogViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), SearchView.OnQueryTextListener {
    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: DogAdapter
    private val dogViewModel: DogViewModel by viewModels() // Tiene que ser constante.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inicialización del binding antes de setContentView()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar SearchView con el listener
        binding.mySearch.setOnQueryTextListener(this)

        // Inicializar componentes
        registerLiveData()
        loadDada()
        initRecyclerView()
        initEvent()

        //insertDogTest() prueba de la insercion de un perro
    }

    private fun initEvent() {
        binding.btnAdd.setOnClickListener {
            dogViewModel.delete()
        }
    }

    private fun loadDada() {
        dogViewModel.list()  // Simulamos un evento para iniciar la carga de datos desde el ViewModel
    }

    /*
    Quiero suscribir al activity, cuando los datos de dogListLiveData,
    cambien. En el momento que haya ese cambio, el ViewModel notificará
    al activity y ejecutará la lambda.
    */
    private fun registerLiveData() {
        dogViewModel.dogListLiveData.observe(this) { myList ->
            myList?.let {
                adapter.dogRepository = it  // Asegurar los datos
                binding.myRecyclerPpal.adapter = adapter  // Asignar el adapter
                adapter.notifyDataSetChanged()  // Refrescar los datos
            }
        }

        /*
        Cuando exista un cambio en el modelo, quiero que el Activity
        sea notificado para que actualice la UI.
        */
        dogViewModel.progressBarLiveData.observe(this) { visible ->
            binding.progressBar.isVisible = visible
            Log.i("TAG-DOGS", "ProgressBar está $visible")
        }

        /*
        Observamos un cambio en el campo de búsqueda.
        */
        dogViewModel.breed.observe(this) { breed ->
            dogViewModel.listForBreed(breed)  // Filtrar por raza
            hideKeyBoard()
        }
        dogViewModel.insertLiveData.observe(this){ ifinsert ->
            if (ifinsert){
                Toast.makeText(this, "SE HA CREADO", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "No se ha creado", Toast.LENGTH_LONG).show()
            }

        }

    }

    /*
    Inicializa el RecyclerView y el Adapter
    */
    private fun initRecyclerView() {
        adapter = DogAdapter() // Asegurar inicialización del adapter
        binding.myRecyclerPpal.layoutManager = LinearLayoutManager(this)
        binding.myRecyclerPpal.adapter = adapter
    }

    /*
    Se ejecuta cuando el usuario envía una búsqueda.
    */
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            dogViewModel.searchByBreed(it)
        }
        return true
    }

    /*
    Se ejecuta cuando cambia el texto en el campo de búsqueda.
    */
    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText.isNullOrEmpty()) {
            dogViewModel.list() // Cargar la lista completa
            hideKeyBoard()
        }
        return true
    }

    /*
    Método que cierra el teclado.
    */
    private fun hideKeyBoard() {
        val imn = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        imn.hideSoftInputFromWindow(binding.myLayoutPpal.windowToken, 0)
    }
    fun insertDogTest () {
        val dog : Dog = Dog("raza6", "https://images.dog.ceo/breeds/mastiff-english/3.jpg")

        dogViewModel.insertDogFun(dog)
    }
}
