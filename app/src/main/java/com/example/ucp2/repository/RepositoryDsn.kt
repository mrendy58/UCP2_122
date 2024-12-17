package com.example.ucp2.repository

import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

interface RepositoryDsn {
    suspend fun insertDosen(dosen: Dosen)

    fun getALLDosen(): Flow<List<Dosen>>

    fun getDosen(nidn : String): Flow<Dosen>
}