package example.com.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.thymeleaf.*
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver

fun Application.configureTemplating() {
    install(Thymeleaf) {
        setTemplateResolver(ClassLoaderTemplateResolver().apply {
            prefix = "templates/thymeleaf/"
            suffix = ".html"
            characterEncoding = "utf-8"
        })
    }
    val mongoDatabase = connectToMongoDB()
    val carService = CarService(mongoDatabase)
    routing {
        get("/") {
            val cars = carService.getAll()
            call.respond(ThymeleafContent("index", mapOf("cars" to cars)))
        }
    }
}