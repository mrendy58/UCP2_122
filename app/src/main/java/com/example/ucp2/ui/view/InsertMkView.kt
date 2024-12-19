package com.example.ucp2.ui.view

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.ucp2.ui.customwidget.TopAppBar
import com.example.ucp2.ui.viewmodel.DosenViewModel
import com.example.ucp2.ui.viewmodel.FormErrorStateMK
import com.example.ucp2.ui.viewmodel.InsertMkViewModel
import com.example.ucp2.ui.viewmodel.MataKuliahEvent
import com.example.ucp2.ui.viewmodel.MkUiState
import kotlinx.coroutines.launch

@Composable
fun InsertMkView(
    onBack: () -> Unit,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
    insertMkViewModel: InsertMkViewModel = viewModel(),
    dosenViewModel: DosenViewModel = viewModel() // ViewModel untuk mengambil daftar dosen
){
    val uiState = insertMkViewModel.uiState
    val dosenUiState = dosenViewModel.uiState
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutinScope = rememberCoroutineScope()

    // Observe Snackbar message changes
    LaunchedEffect(uiState.snackBarMessage) {
        uiState.snackBarMessage?.let { message ->
            coroutinScope.launch {
                snackbarHostState.showSnackbar(message)
                insertMkViewModel.resetSnackBarMessage() // Reset Snackbar message after showing it
            }
        }
    }

    Scaffold(
        modifier = modifier,
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // TopAppBar untuk halaman Insert Mata Kuliah
            TopAppBar(
                onBack = onBack,
                showBackButton = true,
                judul = "Tambah Mata Kuliah"
            )

            // Form untuk Insert Mata Kuliah
            InsertBodyMk(
                uiState = uiState,
                dosenList = dosenUiState.dosenList,
                onValueChange = { updatedEvent ->
                    insertMkViewModel.updateState(updatedEvent) // Update form data di ViewModel
                },
                onClick = {
                    insertMkViewModel.saveData() // Simpan data
                    onNavigate()
                }
            )
        }
    }
}

// membuat insert body
@Composable
fun InsertBodyMk(
    modifier: Modifier = Modifier,
    uiState: MkUiState,
    dosenList: List<String>,
    onValueChange: (MataKuliahEvent) -> Unit,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        FormMataKuliah(
            mataKuliahEvent = uiState.mataKuliahEvent,
            onValueChange = onValueChange,
            errorState = uiState.isEntryValid,
            dosenList = dosenList,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            onClick = onClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Simpan")
        }
    }
}

//Form Mata Kuliah
@Composable
fun FormMataKuliah(
    mataKuliahEvent: MataKuliahEvent,
    onValueChange: (MataKuliahEvent) -> Unit,
    errorState: FormErrorStateMK,
    dosenList: List<String>,
    modifier: Modifier = Modifier
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedDosen by remember { mutableStateOf(mataKuliahEvent.DosenPengampu) }

    Column(
        modifier = modifier.fillMaxWidth().padding(20.dp)
    ) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.kode,
            onValueChange = { onValueChange(mataKuliahEvent.copy(kode = it)) },
            label = { Text("Kode Mata Kuliah") },
            isError = errorState.kode != null,
            placeholder = { Text("Masukkan Kode Mata Kuliah") }
        )
        Text(
            text = errorState.kode ?: "",
            color = Color.Red
        )

        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = mataKuliahEvent.namaMk,
            onValueChange = { onValueChange(mataKuliahEvent.copy(namaMk = it)) },
            label = { Text("Nama Mata Kuliah") },
            isError = errorState.namaMk != null,
            placeholder = { Text("Masukkan Nama Mata Kuliah") }
        )
        Text(
            text = errorState.namaMk ?: "",
            color = Color.Red
        )


    }
}