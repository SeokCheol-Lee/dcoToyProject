package com.example.dcotoyproject.repository

import com.example.dcotoyproject.domain.AdCreativeDetails
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AdCreativeDetailsRepository : JpaRepository<AdCreativeDetails, Long>
