package app.medview.config

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("openai")
data class OpenAiConfig (val apiKey: String)