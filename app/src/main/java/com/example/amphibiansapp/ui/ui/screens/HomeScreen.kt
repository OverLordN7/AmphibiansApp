package com.example.amphibiansapp.ui.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.amphibiansapp.R
import com.example.amphibiansapp.model.Amphibian

@Composable
fun HomeScreen(
    amphibianUIState: AmphibianUIState,
    retryAction: ()-> Unit,
    modifier: Modifier = Modifier
) {
    when(amphibianUIState){
        is AmphibianUIState.Loading -> LoadingScreen(modifier)
        is AmphibianUIState.Success -> AmphibianList(amphibians = amphibianUIState.amphibians,modifier)
        is AmphibianUIState.Error -> ErrorScreen(retryAction = retryAction, modifier)
    }
}

@Composable
fun LoadingScreen(modifier: Modifier){
    Box(
        contentAlignment = Alignment.Center, 
        modifier = modifier.fillMaxSize()
    ) {

        Image(
            modifier = Modifier.size((200.dp)),
            painter = painterResource(id = R.drawable.loading_img),
            contentDescription = "Loading"
        )
    }
}

@Composable
fun ErrorScreen(retryAction: () ->Unit, modifier: Modifier = Modifier){
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Failed to load")
        Button(onClick = retryAction) {
            Text(text = "Retry")
        }
    }
}

@Composable
fun AmphibianCard(
    amphibian: Amphibian,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(8.dp)
            .fillMaxWidth()
            .aspectRatio(1f),
        elevation = 8.dp
    ) {
        Column(modifier = Modifier,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "${amphibian.name} (${amphibian.type})",
                modifier = Modifier, fontWeight = FontWeight.Bold
            )
            Text(
                text = amphibian.description,
                textAlign = TextAlign.Justify,
                modifier = Modifier.padding(16.dp)
            )
            AsyncImage(
                model = ImageRequest.Builder(context = LocalContext.current)
                    .data(amphibian.imageRes)
                    .crossfade(true)
                    .build(),
                error = painterResource(id = R.drawable.ic_broken_image),
                placeholder = painterResource(id = R.drawable.loading_img),
                contentDescription = "Amphibian Photo",
                modifier = Modifier.fillMaxWidth(),
                contentScale = ContentScale.FillBounds
            )
        }
    }
}

@Composable
fun AmphibianList(amphibians: List<Amphibian>, modifier: Modifier = Modifier){
    LazyColumn(){
        items(items = amphibians){ amphibian ->
            AmphibianCard(amphibian = amphibian)
        }
    }
}

