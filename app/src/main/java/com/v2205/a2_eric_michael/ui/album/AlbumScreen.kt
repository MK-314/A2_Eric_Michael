package com.v2205.a2_eric_michael.ui.album

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.rememberImagePainter
import coil.transform.CircleCropTransformation
import com.v2205.a2_eric_michael.util.CustomColors
import com.v2205.a2_eric_michael.util.CustomColors.darkBackground
import com.v2205.a2_eric_michael.util.CustomColors.lightBlue
import com.v2205.model.response.StudentResponse

@Composable
fun AlbumScreen(gotoPost: () -> Unit, navigationCallback: (String) -> Unit) {
    val viewModel: AlbumViewModel = viewModel()
    val students = viewModel.studentsState.value
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
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 30.dp)
        ) {
            Text(
                text = "CSTP_2205",
                color = lightBlue,
                fontSize = 40.sp,
                fontStyle = FontStyle.Italic,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                letterSpacing = 1.8.sp,
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier.fillMaxWidth()
        ) {
            TextButton(
                onClick = { gotoPost() },
                Modifier
                    .border(4.dp, color = lightBlue, shape = RoundedCornerShape(17))
                    .background(color = darkBackground)

            ) {
                Text(
                    text = "Add New +",
                    Modifier
                        .padding(horizontal = 40.dp, vertical = 11.dp),
                    color = CustomColors.lightBlue,
                    fontSize = 17.sp,
                    fontStyle = FontStyle.Italic
                )
            }
        }
        LazyColumn(
            contentPadding = PaddingValues(16.dp)
        ) {
            items(students) { student ->            // use items with List inside
                StudentCard(student, navigationCallback)
            }
        }
    }
}

@Composable
fun StudentCard(student: StudentResponse, navigationCallback: (String) -> Unit) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxWidth()
            .background(
                color = CustomColors.background, RoundedCornerShape(10.dp)
            )
            .padding(20.dp)
            .clickable { navigationCallback(student.id) }

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
                .size(140.dp)
                .weight(4f)
        )
        Text(
            text = student.name,
            color = Color.Black,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            letterSpacing = 1.8.sp,

            modifier = Modifier
                .weight(5f)
                .padding(horizontal = 30.dp)
        )
    }
    Spacer(modifier = Modifier.height(20.dp))
}














