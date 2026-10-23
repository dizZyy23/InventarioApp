package com.duoc.inventario.ui.producto

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.QrCodeScanner
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductoFormScreen(
    viewModel: ProductoViewModel,
    productoId: Int,
    onNavigateBack: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(productoId) {
        viewModel.cargarProducto(productoId)
    }

    LaunchedEffect(uiState.guardadoExitosamente) {
        if (uiState.guardadoExitosamente) {
            viewModel.resetGuardado()
            onNavigateBack()
        }
    }
    
    val qrScannerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.TakePicturePreview()
    ) { bitmap ->
        if (bitmap != null) {
            viewModel.onFieldChange("codigoQR", "QR-FICTICIO-789")
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(if (productoId == 0) "Nuevo Producto" else "Editar Producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            OutlinedTextField(
                value = uiState.codigo,
                onValueChange = { viewModel.onFieldChange("codigo", it) },
                label = { Text("Código") },
                isError = uiState.codigoError != null,
                trailingIcon = {
                    if (uiState.codigoError != null) Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = {
                    AnimatedVisibility(visible = uiState.codigoError != null, enter = fadeIn(), exit = fadeOut()) {
                        Text(uiState.codigoError ?: "")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )
            
            OutlinedTextField(
                value = uiState.nombre,
                onValueChange = { viewModel.onFieldChange("nombre", it) },
                label = { Text("Nombre") },
                isError = uiState.nombreError != null,
                trailingIcon = {
                    if (uiState.nombreError != null) Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = {
                    AnimatedVisibility(visible = uiState.nombreError != null, enter = fadeIn(), exit = fadeOut()) {
                        Text(uiState.nombreError ?: "")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = uiState.categoria,
                onValueChange = { viewModel.onFieldChange("categoria", it) },
                label = { Text("Categoría") },
                isError = uiState.categoriaError != null,
                trailingIcon = {
                    if (uiState.categoriaError != null) Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                },
                supportingText = {
                    AnimatedVisibility(visible = uiState.categoriaError != null, enter = fadeIn(), exit = fadeOut()) {
                        Text(uiState.categoriaError ?: "")
                    }
                },
                modifier = Modifier.fillMaxWidth()
            )

            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = uiState.cantidad,
                    onValueChange = { viewModel.onFieldChange("cantidad", it) },
                    label = { Text("Cantidad") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.cantidadError != null,
                    trailingIcon = {
                        if (uiState.cantidadError != null) Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    },
                    supportingText = {
                        AnimatedVisibility(visible = uiState.cantidadError != null, enter = fadeIn(), exit = fadeOut()) {
                            Text(uiState.cantidadError ?: "")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
                
                OutlinedTextField(
                    value = uiState.stockMinimo,
                    onValueChange = { viewModel.onFieldChange("stockMinimo", it) },
                    label = { Text("Stock Mín.") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    isError = uiState.stockMinimoError != null,
                    trailingIcon = {
                        if (uiState.stockMinimoError != null) Icon(Icons.Filled.Error, "Error", tint = MaterialTheme.colorScheme.error)
                    },
                    supportingText = {
                        AnimatedVisibility(visible = uiState.stockMinimoError != null, enter = fadeIn(), exit = fadeOut()) {
                            Text(uiState.stockMinimoError ?: "")
                        }
                    },
                    modifier = Modifier.weight(1f)
                )
            }
            
            Spacer(modifier = Modifier.height(16.dp))
            
            Button(
                onClick = { qrScannerLauncher.launch(null) },
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
            ) {
                Icon(Icons.Filled.QrCodeScanner, contentDescription = null, modifier = Modifier.padding(end = 8.dp))
                Text("Escanear Código QR (Cámara)")
            }
            
            AnimatedVisibility(visible = uiState.codigoQR.isNotEmpty()) {
                Text(
                    text = "Código QR detectado: ${uiState.codigoQR}",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            Button(
                onClick = { viewModel.guardarProducto() },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.guardando
            ) {
                Text("Guardar Producto")
            }
        }
    }
}
