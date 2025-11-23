package com.example.testcompose.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Home
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime

data class TodoData(
    val id: String,
    val title: String,
    val isDone: Boolean = false,
    val time: LocalDateTime = LocalDateTime.now()
)

enum class MainPageCategory(val icon: ImageVector, val title: String) {
    Main(Icons.Default.Home, "메인페이지"), Calender(Icons.Default.DateRange, "캘린더"),
    Category(Icons.Default.Category, "카테고리")
}