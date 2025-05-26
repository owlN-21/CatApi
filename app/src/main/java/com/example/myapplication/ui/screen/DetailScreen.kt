package com.example.myapplication.ui.screen

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.CatViewModel

@Composable
fun DetailScreen(
    breedId: String,
    viewModel: CatViewModel,
    navController: NavController
) {
    val catItem = viewModel.catItems.find { it.first.id == breedId }

    val info = catItem?.first
    val image = catItem?.second

    Column(Modifier.padding(16.dp)) {
        IconButton(
            onClick = { navController.navigateUp() },
            modifier = Modifier.padding(8.dp)
        ) {
            Icon(Icons.Default.ArrowBack, contentDescription = "Назад")
        }

        if (info != null && image != null) {
            Text("Название: ${info.name}", style = MaterialTheme.typography.titleMedium)
            Text("Описание: ${info.description}", style = MaterialTheme.typography.bodySmall)
            AsyncImage(
                model = image.url,
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
            )
        } else {
            Text("Загрузка...", Modifier.padding(16.dp))
        }
    }
}
