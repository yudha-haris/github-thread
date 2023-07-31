package com.example.github_thread.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.viewModels
import coil.compose.AsyncImage
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(activity != null){
            homeViewModel.getGithubUsers()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            if(activity != null){
                homeViewModel.githubUsers().observe(viewLifecycleOwner){githubUsers ->
                    setContent {
                        HomeApp(item = githubUsers)
                    }

                }
            }
        }
    }
}

@Composable
fun LoadingView(){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .background(
                color = Color.Black
            ),
        contentAlignment = Alignment.Center
    ){
        CircularProgressIndicator()
    }
}

@Composable
fun HomeApp(item: Resource<List<GithubUser>>) {
    Column(
        modifier = Modifier
            .background(
                color = Color.Black,
            )
            .fillMaxHeight()
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Text(
            "Github User",
            fontSize = 32.sp,
            color = Color.White,
            fontWeight = FontWeight(800),
            modifier = Modifier.padding(horizontal = 8.dp)
        )
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier.padding(
                horizontal = 8.dp,
            )
        ) {
            SearchBar()
        }
        Spacer(modifier = Modifier.height(4.dp))
        when(item){
            is Resource.Loading -> {
                LoadingView()
            }
            is Resource.Success -> {
                val users = item.data ?: listOf()
                LazyColumn(){
                    item{
                        Spacer(modifier = Modifier.height(16.dp))
                    }
                    items(users.size, key = {users[it].id}){
                        UserItemCard(users[it])
                    }
                }
            }
            is Resource.Error -> {

            }
        }

    }
}


@Composable
fun UserItemCard(user: GithubUser) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Row {
            Spacer(modifier = Modifier.width(16.dp))
            AsyncImage(
                model = user.avatarUrl,
                contentScale = ContentScale.Crop,
                contentDescription = "User Profile",
                modifier = Modifier
                    .size(48.dp)
                    .clip(CircleShape)
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column {
                Box(
                    modifier = Modifier.height(48.dp)
                ) {
                    Column {
                        Text(user.login, fontWeight = FontWeight(500), color = Color.White)
                        Text(user.type, color = Color.Gray)
                    }
                }
                Spacer(modifier = Modifier.height(4.dp))
                Text(user.htmlUrl, color = Color.White)
                Spacer(modifier = Modifier.height(8.dp))
                Divider(
                    color = Color.Gray,
                    thickness = (0.6).dp,
                )
            }
        }
    }
}

@Composable
fun SearchBar() {
    var value by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            value = newValue
        },
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        cursorBrush = Brush.verticalGradient(colors = listOf(Color.White, Color.White)),
        decorationBox = { innerTextField ->
            Row(
                modifier = Modifier
                    .background(Color.Gray.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                    .padding(vertical = 8.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = null,
                    tint = Color.White,
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
                Box{
                    if (value.isEmpty()) {
                        Text(
                            text = "Search",
                            color = Color.LightGray,
                        )
                    }
                    innerTextField()
                }

            }

        },
        modifier = Modifier
            .fillMaxWidth()
    )
}