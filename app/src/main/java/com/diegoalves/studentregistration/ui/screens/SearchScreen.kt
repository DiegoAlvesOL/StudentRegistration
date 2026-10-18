package com.diegoalves.studentregistration.ui.screens

import android.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diegoalves.studentregistration.StudentRegistrationApplication
import com.diegoalves.studentregistration.data.local.Student
import com.diegoalves.studentregistration.ui.theme.StudentRegistrationTheme
import com.diegoalves.studentregistration.ui.viewmodel.SearchResult
import com.diegoalves.studentregistration.ui.viewmodel.SearchViewModel
import com.diegoalves.studentregistration.ui.viewmodel.StudentViewModelFactory


@Composable
fun SearchScreen(onNavigateBack: () -> Unit) {
    val application = LocalContext.current.applicationContext as StudentRegistrationApplication
    val searchViewModel: SearchViewModel = viewModel(
        factory = StudentViewModelFactory(application.repository)
    )
    val searchResult by searchViewModel.searchResult.observeAsState()

    SearchScreenContent(
        searchResult = searchResult,
        onSearchClick = { studentIdText -> searchViewModel.searchStudent(studentIdText) },
        onNavigateBack = onNavigateBack
    )
}

@Composable
fun SearchScreenContent(
    searchResult: SearchResult?,
    onSearchClick: (String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var studentIdText by rememberSaveable { mutableStateOf("") }

    Scaffold { innerPadding ->
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
            OutlinedTextField(
                value = studentIdText,
                onValueChange = { studentIdText = it },
                label = { Text("Student ID") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onSearchClick(studentIdText) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Search")
            }

            when (searchResult) {
                is SearchResult.Found -> {
                    Text(text = "Name: ${searchResult.student.name}")
                    Text(text = "Course: ${searchResult.student.course}")
                }
                is SearchResult.NotFound -> {
                    Text(
                        text = "Student not found. Check the ID and try again.",
                        color = MaterialTheme.colorScheme.error
                    )
                }
                is SearchResult.Failure -> {
                    Text(
                        text = searchResult.message,
                        color = MaterialTheme.colorScheme.error
                    )
                }
                null -> {
                }
            }

            TextButton(onClick = onNavigateBack) {
                Text("Back to home")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun SearchScreenPreview() {
    StudentRegistrationTheme {
        SearchScreenContent(
            searchResult = SearchResult.Found(
                Student(studentId = 1, name = "Diego", course = "Computer Science", year = 2)
            ),
            onSearchClick = {},
            onNavigateBack = {}
        )
    }
}