package com.dewabagas.hogwartslegacy.presentation.school

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.coil.CoilImage

// Data class untuk menyimpan informasi mengenai setiap rumah
data class SchoolHouse(
    val name: String,
    val color: Color,
    val imageUrl: String
)

@Composable
fun SchoolScreen() {
    // Daftar rumah Hogwarts beserta warna dan URL gambar masing-masing
    val schoolHouses = listOf(
        SchoolHouse("Gryffindor", Color.Red, "https://static.wikia.nocookie.net/harrypotter/images/b/b1/Gryffindor_ClearBG.png/revision/latest?cb=20190222162949"),
        SchoolHouse("Slytherin", Color.Green, "https://static.wikia.nocookie.net/harrypotter/images/0/00/Slytherin_ClearBG.png/revision/latest?cb=20161020182557"),
        SchoolHouse("Hufflepuff", Color.Yellow, "https://static.wikia.nocookie.net/harrypotter/images/0/06/Hufflepuff_ClearBG.png/revision/latest?cb=20161020182518"),
        SchoolHouse("Ravenclaw", Color.Blue, "https://static.wikia.nocookie.net/harrypotter/images/7/71/Ravenclaw_ClearBG.png/revision/latest?cb=20161020182442")
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2), // Membuat layout dengan 2 item per baris
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp), // Padding luar grid
        verticalArrangement = Arrangement.spacedBy(16.dp), // Jarak antar baris
        horizontalArrangement = Arrangement.spacedBy(16.dp) // Jarak antar kolom
    ) {
        items(schoolHouses) { house ->
            SchoolCard(house)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SchoolCard(house: SchoolHouse) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .clickable { /* Handle navigation or action */ },
        shape = RoundedCornerShape(8.dp),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            CoilImage(
                imageModel = { house.imageUrl },
                modifier = Modifier
                    .size(100.dp)
                    .padding(8.dp),
                imageOptions = ImageOptions(
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = house.name,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = house.color
            )
        }
    }
}
