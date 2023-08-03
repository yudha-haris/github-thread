package com.example.github_thread.detail

import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.component.ShimmerCard
import com.example.component.ShimmerList
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.core.domain.model.GithubUserRepo
import com.example.github_thread.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class GithubUserDetailActivity : AppCompatActivity() {

    private val viewModel: GithubUserDetailViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        val window: Window = window
        window.statusBarColor = getColor(R.color.black)

        val username = intent.getStringExtra(EXTRA_USERNAME)

        if (username != null) {
            setContent {
                val user = viewModel.githubUser(username)
                    .collectAsState(initial = Resource.Loading())
                val repos = viewModel.githubUserRepo(username)
                    .collectAsState(initial = Resource.Loading())
                DetailScreen(user, repos,
                    onBackButton = { finish() },
                    onFavorite = { it, isFavorite ->
                        viewModel.setFavoriteUser(it, isFavorite)
                    }
                )
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "extra_username"
    }
}

@Composable
fun DetailScreen(
    user: State<Resource<GithubUser>>,
    repos: State<Resource<List<GithubUserRepo>>>,
    onBackButton: () -> Unit,
    onFavorite: (GithubUser, Boolean) -> Unit,
) {
    MaterialTheme {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                Column(
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .clickable {
                                onBackButton()
                            }
                            .padding(horizontal = 8.dp, vertical = 16.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowLeft,
                            contentDescription = "Back Button",
                            tint = Color.White
                        )
                        Text("Back", color = Color.White)
                    }
                    user.value.let {
                        when (it) {
                            is Resource.Loading -> {
                                ShimmerCard()
                            }
                            is Resource.Success -> {
                                ProfileCard(user = it.data!!, onFavorite)
                            }
                            is Resource.Error -> {}
                        }
                    }
                }
            }
        ) { innerPadding ->
            repos.value.let {
                when (it) {
                    is Resource.Loading -> {
                        ShimmerList()
                    }
                    is Resource.Success -> {
                        UserRepos(
                            modifier = Modifier.padding(innerPadding),
                            repos = it.data ?: listOf()
                        )
                    }
                    is Resource.Error -> {}
                }
            }

        }
    }
}

@Composable
fun ProfileCard(user: GithubUser, onFavorite: (GithubUser, Boolean) -> Unit) {
    var isFavorite by remember { mutableStateOf(user.isFavorite) }
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    user.name,
                    color = Color.White,
                    fontSize = 24.sp,
                    fontWeight = FontWeight(600)
                )
                Text(user.login, color = Color.White, fontSize = 14.sp)
            }
            AsyncImage(
                model = user.avatarUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(64.dp)
                    .clip(CircleShape)
            )
        }
        Button(
            onClick = {
                onFavorite(user, !isFavorite)
                isFavorite = !isFavorite
            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = if (isFavorite) Color.Black else Color.White,
            ),
            border = BorderStroke(
                width = 0.5.dp,
                color = Color.White
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            if (isFavorite) {
                Text("Remove from Favorite", fontWeight = FontWeight(600), color = Color.White)
            } else {
                Text("Add to Favorite", fontWeight = FontWeight(600))
            }
        }
    }
}

@Composable
fun UserRepos(
    repos: List<GithubUserRepo>,
    modifier: Modifier
) {
    LazyColumn(
        modifier = modifier
    ) {
        items(repos.size) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, top = 8.dp, bottom = 8.dp)
            ) {
                Column {
                    Text(
                        repos[it].name,
                        fontWeight = FontWeight(600),
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        repos[it].description,
                        color = Color.White,
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(repos[it].language, color = Color.LightGray, fontSize = 14.sp)
                    Spacer(modifier = Modifier.height(8.dp))
                    Divider(
                        color = Color.Gray,
                        thickness = (0.6).dp,
                    )
                }
            }
        }
    }
}