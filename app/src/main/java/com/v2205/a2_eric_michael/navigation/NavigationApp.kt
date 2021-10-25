package com.v2205.a2_eric_michael.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.v2205.a2_eric_michael.ui.details.StudentAvatarScreen
import com.v2205.a2_eric_michael.ui.album.AlbumScreen
import com.v2205.a2_eric_michael.ui.post.PostScreen


@ExperimentalPermissionsApi
@Composable
fun NavigationApp() {
    val navController = rememberNavController()
    NavHost(
        navController = navController, startDestination = "student_album_list"
    ) {
        composable(route = "student_album_list") {
            AlbumScreen({
                navController.navigate(route = "post_screen")
            }, { student_id ->
                navController.navigate(route = "student_avatar/$student_id")
            })
        }
        composable(
            route = "student_avatar/{student_id}",
            arguments = listOf(navArgument("student_id") {
                type = NavType.StringType
            })
        ) {
            StudentAvatarScreen(){
                navController.navigate(route = "student_album_list")
            }
        }
        composable(route = "post_screen") {
            PostScreen() {
                navController.navigate(route = "student_album_list")
            }
        }
    }
}
