package com.example.myapplication.ui.screen

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.myapplication.viewmodel.CatViewModel


@Composable
fun CatBreedsScreen(viewModel: CatViewModel, navController: NavController) {

    when {
        viewModel.error != null -> Text("Ошибка: ${viewModel.error}")

        viewModel.isLoading -> Text("Загрузка...")
        else -> LazyColumn {
            items(viewModel.catItems) { (info, image) ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Text(
                        text = "Название: ${info.name}",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier.clickable {

                            try {
                                navController.navigate("detail/${info.id}")
                            } catch (e: Exception) {
                                Log.e("Navigation failed", "Ошибка ${e}")
                            }
                        }
                    )
                    AsyncImage(
                        model = image.url,
                        contentDescription = null,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(200.dp)
                    )

                }
            }
        }
    }
}
