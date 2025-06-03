package com.example.dcotoyproject.service

import com.example.dcotoyproject.domain.AdCreativeDetails
import com.example.dcotoyproject.repository.AdCreativeDetailsRepository
import org.springframework.stereotype.Service
import java.util.*

@Service
class AdDataService(private val repository: AdCreativeDetailsRepository) {

    fun saveCreativeDetails(details: AdCreativeDetails): AdCreativeDetails {
        return repository.save(details)
    }

    fun findCreativeDetailsById(id: Long): AdCreativeDetails? {
        return repository.findById(id).orElse(null)
    }
}
