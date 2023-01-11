package com.example.amphibiansapp.ui.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.amphibiansapp.ui.ui.screens.AmphibianViewModel
import com.example.amphibiansapp.ui.ui.screens.HomeScreen

@Composable
fun AmphibianApp(modifier: Modifier = Modifier){
    Scaffold(
        modifier = modifier.fillMaxSize(),
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            color = MaterialTheme.colors.background
        ) {
            val amphibianViewModel: AmphibianViewModel = viewModel(factory = AmphibianViewModel.Factory)

            HomeScreen(
                amphibianUIState = amphibianViewModel.amphibianUIState,
                retryAction = { amphibianViewModel::getAmphibians})
        }
    }
}