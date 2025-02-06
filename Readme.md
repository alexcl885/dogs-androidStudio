# Programación Multimedia y Dispositivos Móviles

## Práctica Evaluable:

### Descargar el repositorio de Dogs en la rama `testRoom` y realizar cambios

**Repositorio:** [Dogs en GitHub](https://github.com/alexcl885/dogs-androidStudio/tree/testRoom)

He clonado el repositorio, pero me ha dado problemas, por lo que he tenido que montarlo desde cero, introduciendo todas las dependencias y asegurando que todo funcione correctamente. Después de realizar pruebas, he comprobado que todo funciona bien.

---

## Explicación de la implementación de la función Eliminar

### 1. Añadir el método de eliminar en `domain`

El primer paso es crear un nuevo caso de uso. Para ello, agregamos el método de eliminar un perro en **domain**, para luego implementarlo en **data** dentro del repositorio.

### 2. Implementación en el repositorio de `data`

Para realizar la eliminación, ha sido necesario modificar `DogDao`, agregando una **query** que elimine un perro según su raza. Se ha creado la siguiente consulta en `DogDao`:

```kotlin
@Query("DELETE FROM dogs WHERE breed = :breed")
fun deleteDogByBreed(breed: String)
```

### 3. Creación del caso de uso

Después de agregar la query en `DogDao`, se ha creado el **caso de uso** que invoca dicha consulta para eliminar un perro según su raza.

### 4. Implementación en el `ViewModel`

En el `ViewModel` se han realizado los siguientes pasos:

- Instanciar en el constructor el caso de uso (gracias a **Hilt**, se inicializa automáticamente).
- Crear una función que invoque el caso de uso y permita eliminar un perro.

```kotlin
class DogViewModel @Inject constructor(
    private val deleteDogUseCase: DeleteDogUseCase
) : ViewModel() {

    fun deleteDog(breed: String) {
        viewModelScope.launch {
            deleteDogUseCase(breed)
        }
    }
}
```

### 5. Implementación en `MainActivity`

En la `MainActivity`, se observa si hay cambios y se muestra un mensaje al usuario cuando un perro es eliminado. Finalmente, se recarga la lista para reflejar la eliminación.

Se ha creado la siguiente función para pasarla al adapter y permitir eliminar un perro según su posición:

```kotlin
fun deleteDog(breed: String) {
    viewModel.deleteDog(breed)
    Toast.makeText(this, "Perro eliminado", Toast.LENGTH_SHORT).show()
    reloadDogList()
}
```

### 6. Implementación en la parte `View`

Se ha añadido un botón en cada ítem del perro para que el usuario pueda eliminarlo al pulsarlo.

**Ejemplo en XML:**

```xml
<Button
    android:id="@+id/btnDeleteDog"
    android:layout_width="wrap_content"
    android:layout_height="wrap_content"
    android:text="Eliminar"
    android:onClick="deleteDog" />
```

---

## Posibles mejoras futuras

En el futuro, se podrían agregar nuevos **casos de uso** para mejorar la funcionalidad de la aplicación. Algunas ideas incluyen:

- Implementar una pestaña de **favoritos**.
- Integrar una **API externa** para obtener información adicional sobre las razas de los perros.
- Mejoras en la **arquitectura**, aunque actualmente se encuentra bien estructurada.

Por ahora, la implementación es funcional y estable, pero se podrían realizar cambios en función de futuras necesidades o mejoras en la aplicación.
