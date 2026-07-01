package com.dantariun.morphview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.dantariun.morphview.ui.theme.MorphviewTheme
import com.dantariun.presentation.navigation.MorphViewNavGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MorphviewTheme {
                MorphViewNavGraph()
            }
        }
    }
}
