package com.example.devk.presentation.state

sealed interface SaveNotesState <out T> {
    object Loading: SaveNotesState<Nothing>
    data class Success<out T>(val data: Boolean): SaveNotesState<T>
    data class Failure(val error: Boolean): SaveNotesState<Nothing>
}