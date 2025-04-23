import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @Autowired
    lateinit var databaseTestService: DatabaseTestService

    @GetMapping("/testdb")
    fun testDatabaseConnection(): String {
        return databaseTestService.testConnection()
    }
}