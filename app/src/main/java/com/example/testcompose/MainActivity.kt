package com.example.testcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.testcompose.ui.theme.TestComposeTheme
import com.example.testcompose.utils.MainPageCategory
import com.example.testcompose.utils.TodoData
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TestComposeTheme{Main()}
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Main(viewModel: MainViewModel = viewModel()) {
    val context = LocalContext.current

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {Text("test app")},
                actions = {
                    IconButton(
                        onClick = {Toast.makeText(context, "button test", Toast.LENGTH_SHORT).show()},
                        content = {Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = "test button"
                        )}
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        content = {innerPadding ->
            Column (modifier = Modifier.padding(innerPadding)) {
                when(viewModel.currentPage.value) {
                    MainPageCategory.Main -> MainBody(viewModel)
                    MainPageCategory.Calender -> CalendarBody()
                    MainPageCategory.Category -> CategoryBody()
                }
            }
        },
        bottomBar = {
            NavigationBar {
                MainPageCategory.entries.forEach {
                    NavigationBarItem(
                        icon = { Icon(it.icon, it.title) },
                        selected = (it == viewModel.currentPage.value),
                        onClick = {viewModel.setPage(it)}
                    )
                }
            }
        }
    )
}

@Composable
fun MainBody(viewModel: MainViewModel) {
    LazyColumn {
        items(viewModel.todoList) {TodoItem(todo = it, onToggle = {viewModel.toggleTodo(it.id)})}
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CalendarBody() {
    DatePicker(
        state = rememberDatePickerState(),
        modifier = Modifier.padding(8.dp)
    )
}

@Composable
fun CategoryBody() {}

@Preview(showBackground = true)
@Composable
fun Preview() {
    TestComposeTheme {Main()}
}

@Composable
fun TodoItem(todo: TodoData, onToggle: () -> Unit, modifier: Modifier = Modifier) {
    Row (
        modifier = modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Checkbox(
            checked = todo.isDone,
            onCheckedChange = {onToggle()}
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(todo.title, style = MaterialTheme.typography.bodyLarge)
            val formatter = DateTimeFormatter.ofPattern("dd일 HH시 mm분")
            Text(
                "기한: " + todo.time.format(formatter),
                style = MaterialTheme.typography.bodySmall,
                color = Color.Gray
            )
        }
    }
}