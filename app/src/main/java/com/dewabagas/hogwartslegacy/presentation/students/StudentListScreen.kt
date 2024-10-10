package com.dewabagas.hogwartslegacy.presentation.students

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.dewabagas.hogwartslegacy.data.core.network.DataState
import com.dewabagas.hogwartslegacy.domain.entities.Student
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage
import com.skydoves.landscapist.components.rememberImageComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(viewModel: StudentListViewModel = hiltViewModel()) {
    val studentsState by viewModel.students.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hogwarts Students") },
                actions = {
                    IconButton(onClick = { viewModel.getAllStudents() }) {
                        Icon(Icons.Default.Refresh, contentDescription = "Refresh")
                    }
                }
            )
        }
    ) { padding ->
        when (studentsState) {
            is DataState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is DataState.Success -> {
                val students = (studentsState as DataState.Success<List<Student>>).data
                StudentGrid(students = students,
                    modifier = Modifier.padding(top = 56.dp)
                    )
            }
            is DataState.Error -> {
                val error = (studentsState as DataState.Error).exception.message
                Text(
                    text = "Error: $error",
                    color = MaterialTheme.colorScheme.error,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun StudentGrid(students: List<Student>, modifier: Modifier = Modifier) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxSize(), // Gunakan modifier dengan padding tambahan
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(students) { student ->
            StudentItem(student)
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp)
        ) {
            CoilImage(
                modifier = Modifier
                    .height(200.dp)
                    .fillMaxWidth(),
                imageModel = { student.image },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = "Student image"
                ),
                component = rememberImageComponent {
                    +com.skydoves.landscapist.animation.circular.CircularRevealPlugin(duration = 800)
                    +com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin()
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "${student.name}",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "House: ${student.house}",
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = "Actor: ${student.actor}",
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}
