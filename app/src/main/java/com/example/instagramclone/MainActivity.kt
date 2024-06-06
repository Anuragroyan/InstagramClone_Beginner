@file:Suppress("DEPRECATION")

package com.example.instagramclone

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.instagramclone.auth.LoginActivity
import com.example.instagramclone.auth.ProfileActivity
import com.example.instagramclone.auth.SignupActivity
import com.example.instagramclone.data.PostData
import com.example.instagramclone.main.CommentsActivity
import com.example.instagramclone.main.FeedActivity
import com.example.instagramclone.main.MyPostActivity
import com.example.instagramclone.main.NewPostActivity
import com.example.instagramclone.main.NotificationMessage
import com.example.instagramclone.main.SearchActivity
import com.example.instagramclone.main.SinglePostActivity
import com.example.instagramclone.ui.theme.InstagramCloneTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter",
        "UnusedMaterialScaffoldPaddingParameter"
    )
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            InstagramCloneTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    InstagramClone()
                }
            }
        }
    }
}

sealed class DestinationActivity(val route: String){
    object SignUp: DestinationActivity("SignUp")
    object Login: DestinationActivity("Login")
    object Feed: DestinationActivity("Feed")
    object Search: DestinationActivity("Search")
    object MyPost: DestinationActivity("MyPost")
    object Profile: DestinationActivity("Profile")
    object NewPost: DestinationActivity("NewPost/{imageUri}"){
        fun createRoute(uri: String) = "NewPost/$uri"
    }
    object SinglePost : DestinationActivity("singlepost")
    object Comments: DestinationActivity("Comments")
}

@Composable
fun InstagramClone() {
    val vm = hiltViewModel<IgViewModel>()
    val navController = rememberNavController()
    NotificationMessage(vm = vm)
    NavHost(navController = navController, startDestination = DestinationActivity.SignUp.route) {
        composable(DestinationActivity.SignUp.route){
            SignupActivity(navController = navController, vm = vm)
        }
        composable(DestinationActivity.Login.route){
            LoginActivity(navController = navController, vm = vm)
        }
        composable(DestinationActivity.Feed.route){
            FeedActivity(navController = navController, vm = vm)
        }
        composable(DestinationActivity.Search.route){
            SearchActivity(navController = navController, vm = vm)
        }
        composable(DestinationActivity.MyPost.route){
            MyPostActivity(navController = navController, vm =vm)
        }
        composable(DestinationActivity.Profile.route){
            ProfileActivity(navController = navController, vm =vm)
        }
        composable(DestinationActivity.NewPost.route){ navBackStackEntry ->
            val imageUri = navBackStackEntry.arguments?.getString("imageUri")
            imageUri?.let {
                NewPostActivity(navController = navController, vm = vm, encodedUri = it)
            }
        }
        composable(DestinationActivity.SinglePost.route) {
//            val postData = navController
//                .previousBackStackEntry
//                ?.arguments
//                ?.getParcelable<PostData>("post")
//            postData?.let {
            SinglePostActivity(
                navController = navController,
                vm = vm
//                    post = postData
            )
        }
        composable(DestinationActivity.Comments.route){
            CommentsActivity(navController = navController, vm = vm)
        }
    }
}

@Preview(showBackground = true)
@Composable
fun InstagramClonePreview() {
    InstagramClone()
}