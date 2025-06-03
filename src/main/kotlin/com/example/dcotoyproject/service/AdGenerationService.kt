package com.example.dcotoyproject.service

import com.example.dcotoyproject.domain.AdCreativeDetails
import org.springframework.ai.chat.model.ChatModel
import org.springframework.ai.chat.prompt.Prompt
import org.springframework.stereotype.Service

@Service
class AdGenerationService(
    private val chatModel: ChatModel,
    private val adDataService: AdDataService
) {

    fun generateAdHtml(promptText: String): String {
        var creativeDetails = AdCreativeDetails(promptText = promptText)
        // Save initial prompt
        creativeDetails = adDataService.saveCreativeDetails(creativeDetails)

        val llmPromptContent = """
            You are an expert advertising copywriter.
            Create a compelling HTML snippet for an advertisement based on the following concept:
            '${creativeDetails.promptText}'.
            The HTML should be a single div, suitable for embedding directly onto a webpage.
            Provide only the HTML content, without any surrounding text or explanations.
        """.trimIndent()

        val fullPrompt = Prompt(llmPromptContent)
        val chatResponse = chatModel.call(fullPrompt)
        val generatedHtmlContent = chatResponse.result.output.content

        creativeDetails.generatedHtml = generatedHtmlContent
        adDataService.saveCreativeDetails(creativeDetails)

        return generatedHtmlContent ?: "<p>Error generating ad content.</p>"
    }
}
