package com.kualagames.compose.ui.main

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.kualagames.shared.components.MainComponent

@Composable
fun MainScreen(mainComponent: MainComponent) = BoxWithConstraints {
    Greeting(modifier = Modifier.align(Alignment.Center),"")
}

@Composable
fun Greeting(modifier : Modifier = Modifier, name: String) {
    Text(modifier = modifier, text = "Hello $name!")
}
