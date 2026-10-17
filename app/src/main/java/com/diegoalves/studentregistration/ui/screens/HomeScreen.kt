package com.diegoalves.studentregistration.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diegoalves.studentregistration.ui.theme.StudentRegistrationTheme

@Composable
fun HomeScreen(
    onNavigateToRegistration: () -> Unit,
    onNavigateToSearch: () -> Unit
){
    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            Button(
                onClick = onNavigateToRegistration,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register Student")
            }
            Button(
                onClick = onNavigateToSearch,
                modifier = Modifier.fillMaxWidth()
            ){
                Text("Search Student")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview(){
    StudentRegistrationTheme() {
        HomeScreen(
            onNavigateToRegistration = {},
            onNavigateToSearch = {}
        )
    }
}