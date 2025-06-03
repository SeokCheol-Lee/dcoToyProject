package com.example.dcotoyproject.controller

import com.example.dcotoyproject.service.AdGenerationService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/ads")
class AdController(private val adGenerationService: AdGenerationService) {

    @PostMapping("/generate")
    fun generateAd(@RequestBody prompt: String): String {
        return adGenerationService.generateAdHtml(prompt)
    }
}
