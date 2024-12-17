package com.example.ucp2.repository

import com.example.ucp2.data.dao.DosenDao
import com.example.ucp2.data.entity.Dosen
import kotlinx.coroutines.flow.Flow

class LocalRepositoryDsn(
    private val dosenDao: DosenDao
) : RepositoryDsn {
    override suspend fun insertDosen(dosen: Dosen){
        dosenDao.insertDosen(dosen)
    }

    override fun getALLDosen(): Flow<List<Dosen>>{
        return dosenDao.getALLDosen()
    }

    override fun getDosen(nidn : String) : Flow<Dosen>{
        return dosenDao.getDosen(nidn)
    }
}