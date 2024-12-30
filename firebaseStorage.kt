buildscript {
    dependencies {
        // Add this classpath in the dependencies section
        classpath 'com.google.gms:google-services:4.3.15'
    }
}

dependencies {
    // Add Firebase Storage dependency
    implementation 'com.google.firebase:firebase-storage:20.2.0'
}

// Apply the plugin at the bottom
apply plugin: 'com.google.gms.google-services'

service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var storageReference: StorageReference
    private lateinit var imageView: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize Firebase Storage
        storageReference = FirebaseStorage.getInstance().reference

        imageView = findViewById(R.id.imageView)

        // Button to upload image
        val uploadButton: Button = findViewById(R.id.uploadButton)
        uploadButton.setOnClickListener {
            chooseImage()
        }

        // Button to download image
        val downloadButton: Button = findViewById(R.id.downloadButton)
        downloadButton.setOnClickListener {
            downloadImage()
        }

        // Button to delete image
        val deleteButton: Button = findViewById(R.id.deleteButton)
        deleteButton.setOnClickListener {
            deleteImage()
        }
    }

    // Function to choose an image from the gallery
    private fun chooseImage() {
        val intent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, 101)
    }

    // Handle the image chosen from gallery
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            val selectedImageUri: Uri? = data.data
            if (selectedImageUri != null) {
                uploadImage(selectedImageUri)
            }
        }
    }

    // Upload image to Firebase Storage
    private fun uploadImage(uri: Uri) {
        val fileReference = storageReference.child("images/${UUID.randomUUID()}")
        val uploadTask: UploadTask = fileReference.putFile(uri)

        uploadTask.addOnSuccessListener {
            Toast.makeText(this, "Image uploaded successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Upload failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Download image from Firebase Storage
    private fun downloadImage() {
        val fileReference = storageReference.child("images/sample_image.jpg")
        val localFile = File.createTempFile("tempImage", "jpg")

        fileReference.getFile(localFile).addOnSuccessListener {
            Toast.makeText(this, "Image downloaded successfully", Toast.LENGTH_SHORT).show()
            // You can display the image in the ImageView
            imageView.setImageURI(Uri.fromFile(localFile))
        }.addOnFailureListener {
            Toast.makeText(this, "Download failed", Toast.LENGTH_SHORT).show()
        }
    }

    // Delete image from Firebase Storage
    private fun deleteImage() {
        val fileReference = storageReference.child("images/sample_image.jpg")
        fileReference.delete().addOnSuccessListener {
            Toast.makeText(this, "Image deleted successfully", Toast.LENGTH_SHORT).show()
        }.addOnFailureListener {
            Toast.makeText(this, "Delete failed", Toast.LENGTH_SHORT).show()
        }
    }
}


<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />


<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:orientation="vertical"
    android:padding="16dp">

    <ImageView
        android:id="@+id/imageView"
        android:layout_width="200dp"
        android:layout_height="200dp"
        android:layout_gravity="center"
        android:contentDescription="Image" />

    <Button
        android:id="@+id/uploadButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Upload Image" />

    <Button
        android:id="@+id/downloadButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Download Image" />

    <Button
        android:id="@+id/deleteButton"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:text="Delete Image" />

</LinearLayout>
