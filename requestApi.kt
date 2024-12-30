import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    // Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://yourserver.com/") // Replace with your PHP server URL
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val apiService = retrofit.create(ApiService::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Insert user
        val newUser = User(name = "John Doe", age = 30)
        insertUser(newUser)

        // Get all users
        getAllUsers()

        // Delete user (example: delete user with id 1)
        deleteUser(1)
    }

    // Function to insert user
    private fun insertUser(user: User) {
        val call = apiService.insertUser(user)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, response.body(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to insert user", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to get all users
    private fun getAllUsers() {
        val call = apiService.getAllUsers()
        call.enqueue(object : Callback<List<User>> {
            override fun onResponse(call: Call<List<User>>, response: Response<List<User>>) {
                if (response.isSuccessful) {
                    val users = response.body()
                    Toast.makeText(applicationContext, "Users: $users", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<List<User>>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to get users", Toast.LENGTH_SHORT).show()
            }
        })
    }

    // Function to delete user
    private fun deleteUser(id: Int) {
        val call = apiService.deleteUser(id)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, response.body(), Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Toast.makeText(applicationContext, "Failed to delete user", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
