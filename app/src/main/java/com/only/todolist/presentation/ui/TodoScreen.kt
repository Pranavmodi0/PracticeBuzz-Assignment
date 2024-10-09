package com.only.todolist.presentation.ui

import android.app.Activity
import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.only.todolist.data.entity.TodoEntity
import com.only.todolist.presentation.viewmodel.TodoViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun TodoScreen(modifier: Modifier) {
    val viewModel = hiltViewModel<TodoViewModel>()
    Content(todoViewModel = viewModel, modifier = modifier)
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun Content(todoViewModel: TodoViewModel, modifier: Modifier) {

    LaunchedEffect(key1 = true, block = {
        todoViewModel.getTodoDetails()
    })

    Column(
        modifier
            .fillMaxSize()
            .background(Color.Black),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier
                .fillMaxWidth()
                .fillMaxHeight(0.7f),
            contentAlignment = Alignment.TopCenter
        ) {
            TopContent(todoViewModel = todoViewModel, modifier = modifier)
        }

        Text(
            text = "Todo List",
            modifier = modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            style = TextStyle(
                brush = Brush.linearGradient(
                    colors = listOf(Color.Black, Color.White)
                )
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.size(5.dp))

        BottomContent(todoViewModel = todoViewModel, modifier = modifier)
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopContent(todoViewModel: TodoViewModel, modifier: Modifier) {

    val submissionStatus by todoViewModel.submissionStatus.collectAsState()

    val context = LocalContext.current

    submissionStatus?.let { isSuccess ->
        LaunchedEffect(isSuccess) {
            if (isSuccess) {
                Toast.makeText(context, "Submission Successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, "Submission Failed", Toast.LENGTH_SHORT).show()
            }
            todoViewModel.submissionStatus
        }
    }

    val title by todoViewModel.title.collectAsStateWithLifecycle()
    val description by todoViewModel.description.collectAsStateWithLifecycle()

    val onTitleSet: (value: String) -> Unit = remember {
        return@remember todoViewModel::setTitle
    }

    val onDescriptionSet: (value: String) -> Unit = remember {
        return@remember todoViewModel::setDescription
    }

    val onSubmit: (value: TodoEntity) -> Unit = remember {
        return@remember todoViewModel::insertTodo
    }

    val isButtonEnabled = title.isNotEmpty() && description.isNotEmpty()

    Scaffold(
        topBar = {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        Color.Black,
                        RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp)
                    )
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = "Todo",
                            color = Color.Gray,
                            fontSize = 25.sp,
                            style = TextStyle(
                                brush = Brush.linearGradient(
                                    colors = listOf(Color.Black, Color.White)
                                )
                            )
                        )
                    },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Black
                    )
                )

                Spacer(Modifier.size(5.dp))
            }
        },
        content = { padding ->
            Column(
                modifier
                    .fillMaxSize()
                    .padding(padding)
                    .background(Color.Yellow),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .height(45.dp),
                    value = title,
                    maxLines = 1,
                    onValueChange = { onTitleSet(it) },
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier
                                .background(Color.White, RoundedCornerShape(size = 15.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(size = 15.dp)
                                )
                                .padding(horizontal = 17.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (title.isEmpty()) {
                                Text(
                                    "Title",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            innerTextField()
                        }
                    },
                )

                Spacer(Modifier.size(16.dp))


                Spacer(Modifier.size(16.dp))

                BasicTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 15.dp)
                        .height(45.dp),
                    value = description,
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(
                        imeAction = ImeAction.Done
                    ),
                    onValueChange = { onDescriptionSet(it) },
                    decorationBox = { innerTextField ->
                        Box(
                            Modifier
                                .background(Color.White, RoundedCornerShape(size = 15.dp))
                                .border(
                                    width = 1.dp,
                                    color = Color.Gray,
                                    shape = RoundedCornerShape(size = 15.dp)
                                )
                                .padding(horizontal = 15.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            if (description.isEmpty()) {
                                Text(
                                    "Description",
                                    color = Color.Gray,
                                    textAlign = TextAlign.Start,
                                    modifier = Modifier.fillMaxWidth()
                                )
                            }
                            innerTextField()
                        }
                    },
                )

                Spacer(Modifier.size(25.dp))

                TextButton(
                    modifier = modifier
                        .padding(horizontal = 30.dp)
                        .background(
                            if (isButtonEnabled) Color.Black else Color.LightGray,
                            RoundedCornerShape(size = 15.dp)
                        )
                        .fillMaxWidth(),
                    enabled = isButtonEnabled,
                    onClick = {
                        onSubmit(
                            TodoEntity(
                                title = title,
                                description = description
                            )
                        )
                    }) {
                    Text(
                        text = "Submit",
                        color = Color.White,
                    )
                }
            }
        }
    )
}

@Composable
fun BottomContent(
    todoViewModel: TodoViewModel,
    modifier: Modifier
){
    val contents by todoViewModel.todoDetailList.collectAsStateWithLifecycle()

    val modify = modifier
        .padding(15.dp)
        .fillMaxWidth()
        .height(100.dp)

    LazyColumn(
        content = {
            items(contents) {
                val item = ImmutableUser(it)
                TodoCard(wrapper = item, modifier = modify)
            }
        }
    )
}

@Immutable
data class ImmutableUser(val todoEntity: TodoEntity)

@Composable
fun TodoCard(
    wrapper: ImmutableUser,
    modifier: Modifier
){
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column(
                modifier = Modifier
                    .padding(start = 10.dp)
                    .weight(3f)
                    .fillMaxHeight(),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = wrapper.todoEntity.title,
                    maxLines = 1,
                    fontWeight = FontWeight.Bold,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(5.dp))
                Text(
                    text = wrapper.todoEntity.description,
                    maxLines = 1,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

fun Context.requireActivity(): Activity {
    var context = this
    while (context is ContextWrapper) {
        if (context is Activity) return context
        context = context.baseContext
    }
    throw IllegalStateException("No activity was present but it is required.")
}