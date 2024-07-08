package com.robert.banyai.sample.ui.movies

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.robert.banyai.sample.ui.model.PMovie
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import com.robert.banyai.sample.ui.movies.MoviesContract.SideEffect as Effect
import com.robert.banyai.sample.ui.movies.MoviesContract.ViewAction as Action

@Composable
fun MoviesScreen(
    viewModel: MoviesViewModel,
    openDetail: (movieId: Int) -> Unit
) {
    val state = viewModel.state.collectAsState().value

    LaunchedEffect(key1 = viewModel) {
        viewModel.effect.onEach { effect ->
            when (effect) {
                is Effect.OpenDetail -> {
                    openDetail(effect.movieId)
                }
            }
        }.collect()
    }

    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(state.movies) { movie ->
            MovieItem(
                movie = movie,
                onClick = {
                    viewModel.sendAction(
                        Action.OpenDetail(movie.id)
                    )
                })
        }
    }
}

@Composable
fun MovieItem(movie: PMovie, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable(onClick = onClick)
    ) {
        Image(
            painter = rememberAsyncImagePainter(model = movie.posterUrl),
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .padding(end = 8.dp)
        )
        Column(
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.align(Alignment.CenterVertically)
        ) {
            Text(
                text = movie.title,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                maxLines = 1,
                text = movie.overview,
                fontSize = 12.sp
            )
        }
    }
}