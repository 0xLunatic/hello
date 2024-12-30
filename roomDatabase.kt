// Entity class a.k.a Initiator 
@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "age") val age: Int
)

// Dao Class, GETTER AND SETTER.

@Dao
interface UserDao {
    @Insert
    suspend fun insert(user: User)

    @Update
    suspend fun update(user: User)

    @Delete
    suspend fun delete(user: User)

    @Query("SELECT * FROM user_table")
    suspend fun getAllUsers(): List<User>
}


/// Create Room DB
@Database(entities = [User::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}


// Activity Code

class MainActivity : AppCompatActivity() {

    private lateinit var db: AppDatabase
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Room database
        db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "user-database"
        ).build()

        userDao = db.userDao()

        // Insert data in background thread (since Room requires background operations for DB)
        GlobalScope.launch(Dispatchers.IO) {
            // Inserting a new user
            val user = User(name = "John Doe", age = 30)
            userDao.insert(user)

            // Querying data
            val users = userDao.getAllUsers()
            withContext(Dispatchers.Main) {
                // Here you can update the UI with the fetched data
                println("Users: $users")
            }
        }
    }
}


// Dependencies
dependencies {
    implementation "androidx.room:room-runtime:2.6.0"
    kapt "androidx.room:room-compiler:2.6.0"  // Use annotation processor
}

