package com.example.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShimmerList() {
    val brush = Brush.linearGradient(
        listOf(
            Color.LightGray.copy(alpha = 0.9f),
            Color.LightGray.copy(alpha = 0.4f),
            Color.LightGray.copy(alpha = 0.9f)
        )
    )
    LazyColumn{
        items(4){
            Box{
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.Center) {
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(fraction = 0.7f)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(fraction = 0.9f)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Spacer(
                        modifier = Modifier
                            .height(16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(fraction = 0.9f)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Spacer(
                        modifier = Modifier
                            .height(12.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .fillMaxWidth(fraction = 0.5f)
                            .background(brush)
                    )
                    Spacer(modifier = Modifier.height(20.dp))
                }
            }
        }
    }
}