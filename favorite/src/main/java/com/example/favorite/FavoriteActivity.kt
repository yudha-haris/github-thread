package com.example.favorite

import android.os.Bundle
import android.view.Window
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.component.EmptyListPlaceholder
import com.example.component.UserItemCard
import com.example.core.domain.model.GithubUser
import com.example.github_thread.R
import com.example.github_thread.di.FavoriteModuleDependencies
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

class FavoriteActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: ViewModelFactory

    private val viewModel: FavoriteViewModel by viewModels { factory }

    override fun onCreate(savedInstanceState: Bundle?) {
        DaggerFavoriteComponent.builder()
            .context(this)
            .appDependencies(
                EntryPointAccessors.fromApplication(
                    applicationContext,
                    FavoriteModuleDependencies::class.java
                )
            )
            .build()
            .inject(this)
        super.onCreate(savedInstanceState)

        supportActionBar?.hide()
        val window: Window = window
        window.statusBarColor = getColor(R.color.black)

        setContent {
            val users = viewModel.favoriteUsers
            FavoriteScreen(
                users = users.collectAsState(initial = listOf()),
                onItemTap = {})
        }
    }
}

@Composable
fun FavoriteScreen(
    users: State<List<GithubUser>>,
    onItemTap: (String) -> Unit
){
    MaterialTheme {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Text(
                        "Favorite User",
                        fontSize = 32.sp,
                        color = Color.White,
                        fontWeight = FontWeight(800),
                        modifier = Modifier.padding(horizontal = 8.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        ) { innerPadding ->
            users.value.let {
                if(it.isEmpty()){
                    EmptyListPlaceholder()
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        item {
                            Spacer(modifier = Modifier.height(16.dp))
                        }
                        items(it.size) { idx ->
                            UserItemCard(it[idx], onTap = {username ->
                                onItemTap(username)
                            })
                        }
                    }
                }

            }

        }
    }
}