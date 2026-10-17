package com.diegoalves.studentregistration.ui.screens

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegoalves.studentregistration.ui.theme.StudentRegistrationTheme

@Composable
fun SearchScreen(onNavigateBack: () -> Unit){
    Scaffold() {innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = "Student Search",
                style = MaterialTheme.typography.headlineMedium
            )
            TextButton(onClick = onNavigateBack) {
                Text("Back to home")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview(){
    StudentRegistrationTheme() {
        SearchScreen(onNavigateBack = {})
    }
}