package com.example.github_thread.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import coil.compose.AsyncImage
import com.example.core.data.Resource
import com.example.core.domain.model.GithubUser
import com.example.github_thread.detail.GithubUserDetailActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            if (activity != null) {
                val users = homeViewModel.githubUsers
                val searchedUsers = homeViewModel.searchResults
                setContent {
                    HomeApp(
                        githubUsers = users.collectAsLazyPagingItems(),
                        searchedUsers = searchedUsers.collectAsState(),
                        onItemTap = {
                            onItemTap(it)
                        },
                        onValueChange = {
                            onTextFieldChange(it)
                        },
                        onKeyboardDone = {
                            onKeyboardDone(it)
                        }

                    )
                }

            }
        }
    }

    private fun onItemTap(username: String){
        val intent = Intent(activity, GithubUserDetailActivity::class.java)
        intent.putExtra(GithubUserDetailActivity.EXTRA_USERNAME, username)
        startActivity(intent)
    }

    private fun onTextFieldChange(value: String){
        if(value.isEmpty()){
            homeViewModel.clearSearch()
        }
    }

    private fun onKeyboardDone(value: String){
        homeViewModel.searchUsers(value).observe(viewLifecycleOwner) { data ->
            when (data) {
                is Resource.Success -> {
                    homeViewModel.inputResult(data.data ?: listOf())
                }
                else -> {}
            }

        }
    }
}

@Composable
fun HomeApp(
        githubUsers: LazyPagingItems<GithubUser>,
        searchedUsers: State<Resource<List<GithubUser>>>,
        onItemTap: (String) -> Unit,
        onValueChange: (String) -> Unit,
        onKeyboardDone: (String) -> Unit,
        ) {
    MaterialTheme {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                Column {
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
                        SearchBar(
                            onValueChange,
                            onKeyboardDone,
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                }
            }
        ) { innerPadding ->
            searchedUsers.value.let {
                when(it){
                    is Resource.Success -> {
                        UsersSearchResult(
                            it.data ?: listOf(),
                            onItemTap,
                            modifier = Modifier.padding(innerPadding)
                        )
                    }
                    is Resource.Loading -> {
                        CircularLoading()
                    }
                    else -> {
                        UsersRecommendation(githubUsers, onItemTap, modifier = Modifier.padding(innerPadding))
                    }
                }
            }
        }
    }

}

@Composable
fun UsersSearchResult(githubUsers: List<GithubUser>, onItemTap: (String) -> Unit, modifier: Modifier) {
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(githubUsers.size) { idx ->
            UserItemCard(user = githubUsers[idx], onItemTap)
        }
    }
}

@Composable
fun UsersRecommendation(githubUsers: LazyPagingItems<GithubUser>, onItemTap: (String) -> Unit, modifier: Modifier) {
    when (githubUsers.loadState.refresh) {
        is LoadState.Loading -> {
            CircularLoading()
        }
        else -> {}
    }
    LazyColumn(
        modifier = modifier
    ) {
        item {
            Spacer(modifier = Modifier.height(16.dp))
        }
        items(githubUsers.itemCount) { idx ->
            githubUsers[idx]?.let { user -> UserItemCard(user,onItemTap) }
        }
        when (githubUsers.loadState.append) {
            is LoadState.Loading -> {
                item {
                    CircularLoading()
                }
            }
            else -> {}
        }
    }

}

@Composable
fun UserItemCard(
    user: GithubUser,
    onTap: (String) -> Unit,
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .clickable {
                onTap(user.login)
            }
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
fun SearchBar(
    onValueChange: (String) -> Unit,
    onKeyboardDone: (String) -> Unit,
) {
    var value by remember {
        mutableStateOf("")
    }
    BasicTextField(
        value = value,
        onValueChange = { newValue ->
            value = newValue
            onValueChange(value)
        },
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                onKeyboardDone(value)
            }
        ),
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
                Box {
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

@Composable
fun CircularLoading() {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.padding(vertical = 4.dp)
        )
    }
}

