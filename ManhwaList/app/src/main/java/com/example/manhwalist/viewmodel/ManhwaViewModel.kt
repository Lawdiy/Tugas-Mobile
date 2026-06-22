package com.example.manhwalist.viewmodel

import androidx.lifecycle.*
import com.example.manhwalist.data.*
import com.example.manhwalist.repository.ManhwaRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class ManhwaViewModel(private val repository: ManhwaRepository) : ViewModel() {

    val allStatuses: StateFlow<List<ReadStatus>> = repository.allStatuses.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    private val _selectedStatusId = MutableStateFlow<Int?>(null)
    val selectedStatusId: StateFlow<Int?> = _selectedStatusId

    val manhwaList: StateFlow<List<ManhwaWithStatus>> = _selectedStatusId.flatMapLatest { statusId ->
        if (statusId == null) {
            repository.allManhwa
        } else {
            repository.getManhwaByStatus(statusId)
        }
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000),
        initialValue = emptyList()
    )

    fun selectStatus(statusId: Int?) {
        _selectedStatusId.value = statusId
    }

    fun getManhwaById(id: Int): Flow<Manhwa?> {
        return repository.getManhwaById(id)
    }

    fun insertManhwa(manhwa: Manhwa) = viewModelScope.launch {
        repository.insertManhwa(manhwa)
    }

    fun updateManhwa(manhwa: Manhwa) = viewModelScope.launch {
        repository.updateManhwa(manhwa)
    }

    fun deleteManhwa(manhwa: Manhwa) = viewModelScope.launch {
        repository.deleteManhwa(manhwa)
    }

    class Factory(private val repository: ManhwaRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ManhwaViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ManhwaViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
