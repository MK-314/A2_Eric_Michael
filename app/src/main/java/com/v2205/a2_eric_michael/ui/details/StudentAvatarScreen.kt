package com.v2205.a2_eric_michael.ui.details

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

import com.v2205.a2_eric_michael.fireStorage.FireStorageRepo
import com.v2205.a2_eric_michael.util.CustomColors
import com.v2205.a2_eric_michael.util.CustomColors.darkBackground
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun StudentAvatarScreen(toTheMainPage: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel: StudentAvatarViewModel = viewModel()
    val student = viewModel.studentState.value

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = darkBackground
            )
            .padding(top = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Image(
                painter = rememberImagePainter(
                    data = student.imageUrl,
                    builder = {
                        transformations(
                            CircleCropTransformation()
                        )
                    }

                ), contentDescription = null,
                modifier = Modifier
                    .size(200.dp)
                    .weight(4f)
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp)
        ) {
            Text(
                text = student.name,
                color = CustomColors.lightBlue,
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.8.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp)
        ) {
            Text(
                text = student.description,
                color = CustomColors.lightBlue,
                fontSize = 20.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.8.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 50.dp)
        ) {
            TextButton(
                onClick = {
                    scope.launch {
                        viewModel.deleteStudent(student.id)
                        Firebase.storage.reference.child("images/${student.techName}").delete()
                            .await()
                    }.invokeOnCompletion {
                        toTheMainPage()
                    }
                },
                Modifier
                    .border(4.dp, color = CustomColors.lightBlue, shape = RoundedCornerShape(20))
                    .background(color = darkBackground)
            ) {
                Text(
                    text = "Delete",
                    Modifier
                        .padding(horizontal = 7.dp, vertical = 2.dp),
                    color = CustomColors.lightBlue,
                    fontSize = 17.sp,
                    fontStyle = FontStyle.Italic
                )
            }
            TextButton(
                onClick = { toTheMainPage() },
                Modifier
                    .border(4.dp, color = CustomColors.lightBlue, shape = RoundedCornerShape(20))
                    .background(color = darkBackground)
            ) {
                Text(
                    text = "Go back",
                    Modifier
                        .padding(horizontal = 7.dp, vertical = 2.dp),
                    color = CustomColors.lightBlue,
                    fontSize = 17.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }

}





















