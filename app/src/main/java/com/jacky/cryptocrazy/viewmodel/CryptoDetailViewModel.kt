package com.jacky.cryptocrazy.viewmodel

import androidx.lifecycle.ViewModel
import com.jacky.cryptocrazy.model.Crypto
import com.jacky.cryptocrazy.repository.CryptoRepository
import com.jacky.cryptocrazy.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailViewModel @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(): Resource<Crypto> {
        return repository.getCrypto()
    }
}