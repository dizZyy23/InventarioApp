package com.duoc.inventario.ui.producto

data class ProductoUiState(
    val id: Int = 0,
    val codigo: String = "",
    val nombre: String = "",
    val categoria: String = "",
    val cantidad: String = "",
    val stockMinimo: String = "",
    val codigoQR: String = "",
    
    val codigoError: String? = null,
    val nombreError: String? = null,
    val categoriaError: String? = null,
    val cantidadError: String? = null,
    val stockMinimoError: String? = null,
    
    val guardando: Boolean = false,
    val guardadoExitosamente: Boolean = false
)
