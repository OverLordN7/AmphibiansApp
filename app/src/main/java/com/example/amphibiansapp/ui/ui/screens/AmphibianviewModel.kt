package com.example.amphibiansapp.ui.ui.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import coil.network.HttpException
import com.example.amphibiansapp.AmphibianApplication
import com.example.amphibiansapp.data.AmphibianRepository
import com.example.amphibiansapp.model.Amphibian
import kotlinx.coroutines.launch
import java.io.IOException

sealed interface AmphibianUIState{
    data class Success(val amphibians: List<Amphibian>): AmphibianUIState
    object Error: AmphibianUIState
    object Loading: AmphibianUIState
}

class AmphibianViewModel(private val amphibianRepository: AmphibianRepository): ViewModel(){
    var amphibianUIState: AmphibianUIState by mutableStateOf(AmphibianUIState.Loading)
        private set

    init{
        getAmphibians()
    }

    fun getAmphibians(){
        viewModelScope.launch {
            amphibianUIState = AmphibianUIState.Loading
            amphibianUIState = try{
                AmphibianUIState.Success(amphibianRepository.getAmphibians())
            }catch (e: IOException){
                AmphibianUIState.Error
            }catch (e: HttpException){
                AmphibianUIState.Error
            }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = (this[APPLICATION_KEY] as AmphibianApplication)
                val amphibianRepository = application.container.amphibianRepository
                AmphibianViewModel(amphibianRepository = amphibianRepository)
            }
        }
    }
}