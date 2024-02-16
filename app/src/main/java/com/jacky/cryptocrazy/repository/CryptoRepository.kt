package com.jacky.cryptocrazy.repository

import com.jacky.cryptocrazy.model.Crypto
import com.jacky.cryptocrazy.model.CryptoList
import com.jacky.cryptocrazy.service.CryptoAPI
import com.jacky.cryptocrazy.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(private val api : CryptoAPI) {
    suspend fun getCryptoList(): Resource<CryptoList> {
        val response = try {
            api.getCryptoList()
        } catch (e : Exception) {
            return Resource.Error("Error.")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto() : Resource<Crypto> {
        val response = try {
            api.getCrypto()
        } catch (e : Exception) {
            return  Resource.Error("Error.")
        }
        return Resource.Success(response)
    }
}