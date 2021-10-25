package com.v2205.a2_eric_michael.ui.post

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.net.toUri
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import com.v2205.a2_eric_michael.R
import com.v2205.a2_eric_michael.camera.CameraCapture
import com.v2205.a2_eric_michael.fireStorage.FireStorageRepo
import com.v2205.a2_eric_michael.gallery.GallerySelect
import com.v2205.a2_eric_michael.util.Constants.EMPTY_IMAGE_URI
import com.v2205.a2_eric_michael.util.CustomColors
import com.v2205.model.response.StudentResponse
import kotlinx.coroutines.*
import kotlinx.coroutines.tasks.await


@ExperimentalPermissionsApi
@Composable
fun PostScreen(toTheMainPage: () -> Unit) {
    val scope = rememberCoroutineScope()
    val viewModel: PostViewModel = viewModel()
    //
    var imageUri by remember { mutableStateOf(EMPTY_IMAGE_URI) }
    var techName by remember { mutableStateOf("") }
    var clickOnDefaultCamera by remember { mutableStateOf(false) }
    val context = LocalContext.current
    val fireStorageRepo by remember { mutableStateOf(FireStorageRepo()) }
    //
    var name by remember { mutableStateOf("") }
    var desc by remember { mutableStateOf("") }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                color = CustomColors.darkBackground
            )
            .padding(top = 10.dp)
    ) {
        if (!clickOnDefaultCamera) {
            Row {
                Image(
                    painter = if (imageUri == EMPTY_IMAGE_URI) rememberImagePainter(R.drawable.camera)
                    else rememberImagePainter(
                       data= imageUri,
                        builder = {
                            transformations(
                                CircleCropTransformation()
                            )
                        }
                    ),
                    contentDescription = null,
                    modifier = Modifier
                        .size(250.dp)
                        .weight(4f)
                        .padding(vertical = 20.dp)
                        .clickable { clickOnDefaultCamera = true }
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Fist & Second name") },
                    colors = TextFieldDefaults
                        .outlinedTextFieldColors(
                            focusedLabelColor = CustomColors.lightBlue,
                            unfocusedLabelColor = Color.Gray,
                            disabledLabelColor = Color.Blue,
                            errorLabelColor = Color.Red,
                            placeholderColor = Color.Blue,
                            disabledPlaceholderColor = Color.Magenta,
                            cursorColor = Color.White,
                            disabledBorderColor = Color.White,
                            textColor = Color.White,
                            unfocusedBorderColor = Color.White
                        ),
                    placeholder = {
                        Text(
                            text = "Name",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp, vertical = 10.dp)
            ) {
                OutlinedTextField(
                    value = desc,
                    onValueChange = { desc = it },
                    label = { Text("Description") },
                    colors = TextFieldDefaults
                        .outlinedTextFieldColors(
                            focusedLabelColor = CustomColors.lightBlue,
                            unfocusedLabelColor = Color.Gray,
                            disabledLabelColor = Color.Blue,
                            errorLabelColor = Color.Red,
                            placeholderColor = Color.Blue,
                            disabledPlaceholderColor = Color.Magenta,
                            cursorColor = Color.White,
                            disabledBorderColor = Color.White,
                            textColor = Color.White,
                            unfocusedBorderColor = Color.White
                        ),
                    placeholder = {
                        Text(
                            text = "Name",
                            color = Color.LightGray
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(10.dp))
                )
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 15.dp)
            ) {
                TextButton(
                    onClick = {
                        viewModel.studentState.value = StudentResponse(
                            id = "",
                            name = name,
                            description = desc,
                            imageUrl = imageUri.toString(),
                            techName = techName
                        )
                        scope.launch {
                            viewModel.simplePost()
                        }.invokeOnCompletion {
                            toTheMainPage()
                        }
                    },
                    Modifier
                        .border(
                            4.dp,
                            color = CustomColors.lightBlue,
                            shape = RoundedCornerShape(20)
                        )
                        .background(color = CustomColors.darkBackground)
                ) {
                    Text(
                        text = "Post",
                        Modifier
                            .padding(horizontal = 37.dp, vertical = 2.dp),
                        color = CustomColors.lightBlue,
                        fontSize = 17.sp,
                        fontStyle = FontStyle.Italic
                    )
                }
            }
        } else {
            var showGallerySelect by remember { mutableStateOf(false) }

            if (showGallerySelect) {
                GallerySelect(
                    onImageUri = { uri ->
                        showGallerySelect = false
                        imageUri = uri
                        techName = imageUri.lastPathSegment!!
                        scope.launch {
                            fireStorageRepo.uploadImageToStorage(
                                context,
                                imageUri,
                                imageUri.lastPathSegment!!
                            ).join()
                            imageUri =
                                Firebase.storage.reference.child("images/${imageUri.lastPathSegment!!}").downloadUrl.await()
                        }
                        clickOnDefaultCamera = false
                    }
                )
            } else {
                Box() {
                    CameraCapture(
                        onImageFile = { file ->
                            imageUri = file.toUri()
                            techName = imageUri.lastPathSegment!!
                            scope.launch {
                                fireStorageRepo.uploadImageToStorage(
                                    context,
                                    imageUri,
                                    imageUri.lastPathSegment!!
                                ).join()
                                imageUri =
                                    Firebase.storage.reference.child("images/${imageUri.lastPathSegment!!}").downloadUrl.await()
                            }
                            clickOnDefaultCamera = false
                        }
                    )
                    Row(
                        modifier = Modifier.align(Alignment.TopCenter),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Button(
                            modifier = Modifier
                                .padding(4.dp),
                            onClick = {
                                showGallerySelect = true
                            }
                        ) {
                            Text("Pick From Gallery")
                        }
                        Button(
                            modifier = Modifier
                                .padding(4.dp),
                            onClick = {
                                clickOnDefaultCamera = !clickOnDefaultCamera
                            }
                        ) {
                            Text("Back")
                        }
                    }
                }
            }
        }
    }
}

