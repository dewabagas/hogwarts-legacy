package com.dewabagas.hogwartslegacy.presentation.students

import androidx.compose.foundation.layout.* // Untuk pengaturan layout
import androidx.compose.foundation.lazy.LazyColumn // Untuk menampilkan daftar
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.* // Menggunakan Material3
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
        // Cek DataState untuk menampilkan UI sesuai state saat ini
        when (studentsState) {
            is DataState.Loading -> {
                // Tampilkan loading indicator
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
                // Jika data berhasil diambil, tampilkan daftar siswa
                val students = (studentsState as DataState.Success<List<Student>>).data
                StudentList(students)
            }
            is DataState.Error -> {
                // Tampilkan pesan error jika terjadi kesalahan
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
fun StudentList(students: List<Student>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(students) { student ->
            StudentItem(student)
        }
    }
}

@Composable
fun StudentItem(student: Student) {
    Card(
        modifier = Modifier.fillMaxWidth(),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            // Tampilkan gambar menggunakan CoilImage dari library Landscapist
            CoilImage(
                modifier = Modifier
                    .padding(bottom = 8.dp)
                    .height(250.dp)
                    .width(190.dp),
                imageModel = { student.image },
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center,
                    contentDescription = "Student image"
                ),
                component = rememberImageComponent {
                    // Circular reveal animation
                    +com.skydoves.landscapist.animation.circular.CircularRevealPlugin(duration = 800)
                    // Placeholder shimmer effect
                    +com.skydoves.landscapist.placeholder.shimmer.ShimmerPlugin()
                }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
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
}
