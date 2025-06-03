package com.example.dcotoyproject.service

import com.example.dcotoyproject.domain.AdCreativeDetails
import org.springframework.ai.chat.client.ChatClient
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service

@Service
class AdGenerationService(
    private val chatClient: ChatClient,
    private val adDataService: AdDataService
) {

    fun generateAdHtml(promptText: String): String {
        var creativeDetails = AdCreativeDetails(promptText = promptText)
        // Save initial prompt
        creativeDetails = adDataService.saveCreativeDetails(creativeDetails)

        val llmPrompt = """
            You are an expert advertising copywriter.
            Create a compelling HTML snippet for an advertisement based on the following concept:
            '$promptText'.
            The HTML should be a single div, suitable for embedding directly onto a webpage.
            Provide only the HTML content, without any surrounding text or explanations.
        """.trimIndent()

        val response = chatClient.prompt(Prompt(llmPrompt)).call().content()

        creativeDetails.generatedHtml = response
        adDataService.saveCreativeDetails(creativeDetails)

        return response ?: "<p>Error generating ad content.</p>"
    }
}
