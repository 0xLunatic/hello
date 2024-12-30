// Explicit Intent - Open SecondActivity with Extra and Flag
val explicitIntent = Intent(this, SecondActivity::class.java).apply {
    putExtra("KEY_NAME", "John Doe")  // Adding extra data
    flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK  // Adding flags
}
startActivity(explicitIntent)



// Implicit Intent - Open a URL with Extra and Flag
val implicitIntent = Intent(Intent.ACTION_VIEW).apply {
    data = Uri.parse("https://www.google.com")  // URL to open
    putExtra(Intent.EXTRA_TEXT, "Hello from my app!")  // Adding extra data
    flags = Intent.FLAG_ACTIVITY_NEW_TASK  // Adding flags
}
startActivity(implicitIntent)


// Inside SecondActivity
val name = intent.getStringExtra("KEY_NAME")  // Retrieve the extra data using the key
val extraText = intent.getStringExtra(Intent.EXTRA_TEXT)  // Example for a generic text extra

// You can also use getIntExtra() for integers, e.g.:
// val number = intent.getIntExtra("KEY_NUMBER", 0)  // Second parameter is default value if extra not found

// Use the data as needed
println("Name: $name")
println("Extra Text: $extraText")
