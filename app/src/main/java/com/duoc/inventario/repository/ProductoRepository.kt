package com.duoc.inventario.repository

import com.duoc.inventario.data.local.ProductoDao
import com.duoc.inventario.data.local.ProductoEntity
import kotlinx.coroutines.flow.Flow

class ProductoRepository(private val productoDao: ProductoDao) {
    val todosLosProductos: Flow<List<ProductoEntity>> = productoDao.obtenerTodos()

    suspend fun obtenerProducto(id: Int): ProductoEntity? {
        return productoDao.obtenerPorId(id)
    }

    suspend fun guardarProducto(producto: ProductoEntity) {
        if (producto.id == 0) {
            productoDao.insertar(producto)
        } else {
            productoDao.actualizar(producto)
        }
    }
}
