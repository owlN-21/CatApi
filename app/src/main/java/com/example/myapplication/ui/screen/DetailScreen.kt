package com.example.myapplication.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.CatViewModel

@Composable
fun DetailScreen(breedId: String, viewModel: CatViewModel) {
    val info = viewModel.getCatInfoBy(breedId)
    val image = viewModel.getCatImageById(info?.reference_image_id)


    if (info != null && image != null) {
        Column(Modifier.padding(16.dp)) {
            Text("Название: ${info.name}", style = MaterialTheme.typography.titleMedium)
            Text("Описание: ${info.description}", style = MaterialTheme.typography.bodySmall)
            AsyncImage(
                model = image.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        }
    } else {
        Text("Загрузка...", Modifier.padding(16.dp))
    }
}
