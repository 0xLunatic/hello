// PHP: db.php (Database connection)
<?php
$host = 'localhost';
$username = 'root'; // Replace with your database username
$password = ''; // Replace with your database password
$dbname = 'my_database'; // Replace with your database name

// Create a connection to the database
$conn = new mysqli($host, $username, $password, $dbname);

// Check if the connection was successful
if ($conn->connect_error) {
    die("Connection failed: " . $conn->connect_error);
}
?>

// PHP: insert.php (Insert Data)
<?php
include 'db.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $name = $_POST['name'];
    $age = $_POST['age'];

    $sql = "INSERT INTO users (name, age) VALUES ('$name', '$age')";
    if ($conn->query($sql) === TRUE) {
        echo "User added successfully";
    } else {
        echo "Error: " . $sql . "<br>" . $conn->error;
    }

    $conn->close();
}
?>

// PHP: get_all.php (Get All Users)
<?php
include 'db.php';

$sql = "SELECT * FROM users";
$result = $conn->query($sql);

if ($result->num_rows > 0) {
    $users = [];
    while($row = $result->fetch_assoc()) {
        $users[] = $row;
    }
    echo json_encode($users);
} else {
    echo "0 results";
}

$conn->close();
?>

// PHP: delete.php (Delete User)
<?php
include 'db.php';

if ($_SERVER['REQUEST_METHOD'] == 'POST') {
    $id = $_POST['id'];

    $sql = "DELETE FROM users WHERE id = $id";
    if ($conn->query($sql) === TRUE) {
        echo "User deleted successfully";
    } else {
        echo "Error: " . $conn->error;
    }

    $conn->close();
}
?>


// Android: MainActivity.kt (Client-side code)
import android.os.AsyncTask
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.OutputStream
import java.net.HttpURLConnection
import java.net.URL

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Insert a new user
        val name = "John Doe"
        val age = 30
        InsertUserTask().execute(name, age.toString())

        // Get all users
        GetUsersTask().execute()

        // Delete a user (example: id 1)
        val userIdToDelete = 1
        DeleteUserTask().execute(userIdToDelete.toString())
    }

    // AsyncTask for inserting a user
    inner class InsertUserTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val urlString = "http://yourserver.com/insert.php"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            val postData = "name=${params[0]}&age=${params[1]}"
            val outputStream: OutputStream = connection.outputStream
            outputStream.write(postData.toByteArray())
            outputStream.flush()
            outputStream.close()

            return connection.inputStream.bufferedReader().readText()
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
        }
    }

    // AsyncTask for getting all users
    inner class GetUsersTask : AsyncTask<Void, Void, String>() {
        override fun doInBackground(vararg params: Void?): String {
            val urlString = "http://yourserver.com/get_all.php"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            return connection.inputStream.bufferedReader().readText()
        }

        override fun onPostExecute(result: String) {
            // Display all users as a string (for demo purposes)
            Toast.makeText(applicationContext, result, Toast.LENGTH_LONG).show()
        }
    }

    // AsyncTask for deleting a user
    inner class DeleteUserTask : AsyncTask<String, Void, String>() {
        override fun doInBackground(vararg params: String?): String {
            val urlString = "http://yourserver.com/delete.php"
            val url = URL(urlString)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "POST"
            connection.doOutput = true
            val postData = "id=${params[0]}"
            val outputStream: OutputStream = connection.outputStream
            outputStream.write(postData.toByteArray())
            outputStream.flush()
            outputStream.close()

            return connection.inputStream.bufferedReader().readText()
        }

        override fun onPostExecute(result: String) {
            Toast.makeText(applicationContext, result, Toast.LENGTH_SHORT).show()
        }
    }
}
