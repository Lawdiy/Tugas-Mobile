package com.example.apirest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import com.example.apirest.ui.screen.MainScreen
import com.example.apirest.ui.theme.APIRestTheme
import com.example.apirest.ui.viewmodel.ApiViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: ApiViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            APIRestTheme {
                MainScreen(viewModel)
            }
        }
    }
}
