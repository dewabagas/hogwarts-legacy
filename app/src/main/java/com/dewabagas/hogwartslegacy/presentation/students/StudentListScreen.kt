package com.dewabagas.hogwartslegacy.presentation.students

import androidx.compose.foundation.layout.* // Untuk pengaturan layout
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.* // Untuk state dan remember
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
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StudentListScreen(viewModel: StudentListViewModel = hiltViewModel()) {
    var searchQuery by remember { mutableStateOf("") }

    // Ambil state dari ViewModel
    val studentsState by viewModel.students.collectAsState()
    val isRefreshing by viewModel.isRefreshing.collectAsState()

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
        Column(modifier = Modifier.padding(padding)) {
            // Search Bar
            OutlinedTextField(
                value = searchQuery,
                onValueChange = {
                    searchQuery = it
                    viewModel.searchStudents(searchQuery) // Panggil fungsi search di ViewModel
                },
                label = { Text("Search Students") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            )
            SwipeRefresh(
                state = SwipeRefreshState(isRefreshing),
                onRefresh = { viewModel.refreshStudents() }, // Panggil fungsi refresh di ViewModel
                modifier = Modifier.fillMaxSize()
            ) {
                when (studentsState) {
                    is DataState.Loading -> {
                        Box(
                            modifier = Modifier
                                .fillMaxSize(),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                    is DataState.Success -> {
                        val students = (studentsState as DataState.Success<List<Student>>).data
                        StudentGrid(
                            students = students,
                            modifier = Modifier.padding(top = 16.dp)
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
