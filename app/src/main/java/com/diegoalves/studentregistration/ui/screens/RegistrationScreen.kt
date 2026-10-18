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
import androidx.compose.runtime.LaunchedEffect
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
import com.diegoalves.studentregistration.ui.theme.StudentRegistrationTheme
import com.diegoalves.studentregistration.ui.viewmodel.RegistrationResult
import com.diegoalves.studentregistration.ui.viewmodel.RegistrationViewModel
import com.diegoalves.studentregistration.ui.viewmodel.StudentViewModelFactory

@Composable
fun RegistrationScreen(onNavigateBack: () -> Unit) {
    val application = LocalContext.current.applicationContext as StudentRegistrationApplication
    val registrationViewModel: RegistrationViewModel = viewModel(
        factory = StudentViewModelFactory(application.repository)
    )
    val registrationResult by registrationViewModel.registrationResult.observeAsState()

    RegistrationScreenContent(
        registrationResult = registrationResult,
        onRegisterClick = { name, course, yearText ->
            registrationViewModel.registerStudent(name = name,
                course = course,
                yearText = yearText
            )
        },
        onNavigateBack = onNavigateBack
    )
}



@Composable
fun RegistrationScreenContent(
    registrationResult: RegistrationResult?,
    onRegisterClick: (String, String, String) -> Unit,
    onNavigateBack: () -> Unit
) {
    var studentName by rememberSaveable { mutableStateOf("") }
    var studentCourse by rememberSaveable { mutableStateOf("") }
    var studentYearText by rememberSaveable { mutableStateOf("") }

    LaunchedEffect(registrationResult) {
        if (registrationResult is RegistrationResult.Success) {
            studentName = ""
            studentCourse = ""
            studentYearText = ""
        }
    }

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(20.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(15.dp)
        ) {
            Text(
                text = "Student Registration",
                style = MaterialTheme.typography.headlineMedium
            )
            OutlinedTextField(
                value = studentName,
                onValueChange = { studentName = it },
                label = { Text("Name") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = studentCourse,
                onValueChange = { studentCourse = it },
                label = { Text("Course") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = studentYearText,
                onValueChange = { studentYearText = it },
                label = { Text("Year (1 to 4)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth()
            )
            Button(
                onClick = { onRegisterClick(studentName, studentCourse, studentYearText) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Register")
            }

            when (registrationResult) {
                is RegistrationResult.Success -> {
                    Text(text = "Student registered with ID ${registrationResult.studentId}")
                }
                is RegistrationResult.Failure -> {
                    Text(
                        text = registrationResult.message,
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
fun RegistrationScreenPreview() {
    StudentRegistrationTheme {
        RegistrationScreenContent(
            registrationResult = null,
            onRegisterClick = { _, _, _ -> },
            onNavigateBack = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun RegistrationScreenErrorPreview() {
    StudentRegistrationTheme {
        RegistrationScreenContent(
            registrationResult = RegistrationResult.Failure("Year must be between 1 and 4."),
            onRegisterClick = { _, _, _ -> },
            onNavigateBack = {}
        )
    }
}