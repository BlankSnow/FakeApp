package com.blank.fakeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.blank.fakeapp.ui.screens.MainScreen
import com.blank.fakeapp.ui.theme.FakeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FakeAppTheme {
                MainScreen()
            }
        }
    }
}
