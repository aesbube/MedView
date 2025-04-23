import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Service
import javax.sql.DataSource
import java.sql.Connection
import java.sql.SQLException

@Service
class DatabaseTestService {

    @Autowired
    private lateinit var dataSource: DataSource

    @Autowired
    private lateinit var jdbcTemplate: JdbcTemplate  // Autowire JdbcTemplate

    fun testConnection(): String {
        return try {
            // Approach 1: Using DataSource directly (more basic)
            // try {
            //     val connection: Connection = dataSource.connection
            //     connection.close()  // Important: Close the connection when done
            //     println("Successfully connected to the database using DataSource!")
            //     "Successfully connected (DataSource)"
            // } catch (e: SQLException) {
            //     println("Failed to connect to the database using DataSource: ${e.message}")
            //     e.printStackTrace()
            //     throw RuntimeException("Database connection failed (DataSource)", e)
            // }
            // Approach 2: Using JdbcTemplate (more common and convenient)
            val result = jdbcTemplate.queryForObject("SELECT 1", String::class.java)

            println("Successfully connected to the database using JdbcTemplate! Result: $result")
            "Successfully connected (JdbcTemplate) - Result: $result"  // return a useful message

        } catch (e: Exception) {
            println("Failed to connect to the database: ${e.message}")
            e.printStackTrace()
            throw RuntimeException("Database connection failed", e)
        }

    }
}