package com.example.uris_learning

import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Modifier
import com.example.uris_learning.ui.theme.Uris_learningTheme
import java.io.File
import java.io.FileOutputStream

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //---------------------------------------------Resource URIS -------------------------------
        val uri = Uri.parse("android.resource://$packageName/drawable/cube_texture")
        val imageBytes = contentResolver.openInputStream(uri)?.use {
            it.readBytes()
        }
        Log.i("IMAGE SIZE", "${imageBytes?.size}")


        //---------------------------------------------File URIS -----------------------------------
        val file = File(filesDir,"cube_texture.png")
        FileOutputStream(file).use {
            it.write(imageBytes)
        }
        Log.i("IMAGE SIZE", "${file.toURI()}")


        setContent {
            Uris_learningTheme {
                // ---------------------------------- Content URIS ---------------------------------

                val pickImage = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.GetContent(),
                    onResult = {contentUri->
                        Log.i("IMAGE SIZE", "$contentUri")
                    }
                )

                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Button(onClick = {
                        pickImage.launch("image/*")
                    }) {
                        Text(text = "Pick image")
                    }
                }
            }
        }
    }
}