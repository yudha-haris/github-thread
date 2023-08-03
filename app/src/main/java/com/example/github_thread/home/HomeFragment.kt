package com.example.github_thread.home

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.component.CircularLoading
import com.example.component.EmptySearchPlaceholder
import com.example.component.SearchBar
import com.example.component.UserItemCard
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
                        onFavoriteTap = {
                            onFavoriteTap()
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

    private fun onFavoriteTap(){
        val uri = Uri.parse("github-thread://favorite")
        startActivity(Intent(Intent.ACTION_VIEW, uri))
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
        onFavoriteTap: () -> Unit,
        onValueChange: (String) -> Unit,
        onKeyboardDone: (String) -> Unit,
        ) {
    MaterialTheme {
        Scaffold(
            backgroundColor = Color.Black,
            topBar = {
                Column {
                    Spacer(modifier = Modifier.height(24.dp))
                    Row (
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ){
                        Text(
                            "Github User",
                            fontSize = 32.sp,
                            color = Color.White,
                            fontWeight = FontWeight(800),
                            modifier = Modifier.padding(horizontal = 8.dp)
                        )
                        Icon(
                            imageVector = Icons.Default.Favorite,
                            contentDescription = "Favorite Button",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(end = 8.dp, top = 8.dp)
                                .clickable {
                                    onFavoriteTap()
                                }
                        )
                    }
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
    if(githubUsers.isEmpty()){
        EmptySearchPlaceholder()
    } else {
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

