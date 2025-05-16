package app.medview

import app.medview.config.OpenAiConfig
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(OpenAiConfig::class)
class MedViewApplication

fun main(args: Array<String>) {
    runApplication<MedViewApplication>(*args)
}
