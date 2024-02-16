package com.jacky.cryptocrazy.service

import com.jacky.cryptocrazy.model.Crypto
import com.jacky.cryptocrazy.model.CryptoList
import com.jacky.cryptocrazy.util.Constants
import com.jacky.cryptocrazy.util.Constants.ALL_CRYPTO
import com.jacky.cryptocrazy.util.Constants.CRYPTO
import retrofit2.http.GET

interface CryptoAPI {


    // Crypto List
    @GET(ALL_CRYPTO)
    suspend fun getCryptoList(): CryptoList

    // Crypto Detail
    @GET(CRYPTO)
    suspend fun getCrypto(): Crypto
}