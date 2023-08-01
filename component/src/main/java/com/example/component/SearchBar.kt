package com.example.component
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

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