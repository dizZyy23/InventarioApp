package com.duoc.inventario.ui.producto

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.duoc.inventario.data.local.ProductoEntity
import com.duoc.inventario.repository.ProductoRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ProductoViewModel(private val repository: ProductoRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(ProductoUiState())
    val uiState: StateFlow<ProductoUiState> = _uiState.asStateFlow()

    val listaProductos = repository.todosLosProductos

    fun cargarProducto(id: Int) {
        if (id != 0) {
            viewModelScope.launch {
                val producto = repository.obtenerProducto(id)
                producto?.let { p ->
                    _uiState.update { 
                        it.copy(
                            id = p.id,
                            codigo = p.codigo,
                            nombre = p.nombre,
                            categoria = p.categoria,
                            cantidad = p.cantidad.toString(),
                            stockMinimo = p.stockMinimo.toString(),
                            codigoQR = p.codigoQR ?: ""
                        )
                    }
                }
            }
        } else {
            resetState()
        }
    }

    fun onFieldChange(field: String, value: String) {
        _uiState.update { currentState ->
            when (field) {
                "codigo" -> currentState.copy(codigo = value, codigoError = null)
                "nombre" -> currentState.copy(nombre = value, nombreError = null)
                "categoria" -> currentState.copy(categoria = value, categoriaError = null)
                "cantidad" -> currentState.copy(cantidad = value, cantidadError = null)
                "stockMinimo" -> currentState.copy(stockMinimo = value, stockMinimoError = null)
                "codigoQR" -> currentState.copy(codigoQR = value)
                else -> currentState
            }
        }
    }

    fun guardarProducto() {
        val currentState = _uiState.value

        val codigoErr = if (currentState.codigo.isBlank()) "Obligatorio" else null
        val nombreErr = if (currentState.nombre.isBlank()) "Obligatorio" else null
        val catErr = if (currentState.categoria.isBlank()) "Obligatorio" else null
        
        val cant = currentState.cantidad.toIntOrNull()
        val cantErr = if (cant == null || cant < 0) "Debe ser número >= 0" else null
        
        val stock = currentState.stockMinimo.toIntOrNull()
        val stockErr = if (stock == null || stock < 0) "Debe ser número >= 0" else null

        if (codigoErr != null || nombreErr != null || catErr != null || cantErr != null || stockErr != null) {
            _uiState.update { 
                it.copy(
                    codigoError = codigoErr, 
                    nombreError = nombreErr, 
                    categoriaError = catErr, 
                    cantidadError = cantErr, 
                    stockMinimoError = stockErr
                ) 
            }
            return
        }

        viewModelScope.launch {
            _uiState.update { it.copy(guardando = true) }
            val producto = ProductoEntity(
                id = currentState.id,
                codigo = currentState.codigo,
                nombre = currentState.nombre,
                categoria = currentState.categoria,
                cantidad = cant ?: 0,
                stockMinimo = stock ?: 0,
                codigoQR = currentState.codigoQR.ifBlank { null }
            )
            repository.guardarProducto(producto)
            _uiState.update { it.copy(guardando = false, guardadoExitosamente = true) }
        }
    }
    
    fun resetGuardado() {
        _uiState.update { it.copy(guardadoExitosamente = false) }
    }

    private fun resetState() {
        _uiState.value = ProductoUiState()
    }
}

class ProductoViewModelFactory(private val repository: ProductoRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductoViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductoViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
