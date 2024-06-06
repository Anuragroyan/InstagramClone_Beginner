package com.example.instagramclone.main

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.ButtonDefaults
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedButton
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.R
import com.example.instagramclone.data.PostData

data class PostRow(
    var post1: PostData? = null,
    var post2: PostData? = null,
    var post3: PostData? = null
){
    fun isFull() = post1 != null && post2 != null && post3 != null
    fun add(post: PostData){
        if(post1 == null){
            post1 = post
        }else if(post2 == null){
            post2 = post
        }else if(post3 == null){
            post3 = post
        }
    }
}


@Composable
fun MyPostActivity(navController: NavController, vm: IgViewModel){
    val userData = vm.userData.value
    val isLoading = vm.inProgress.value
    val postLoading = vm.refreshPostsProgress.value
    val posts = vm.posts.value
    val newPostImageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
         uri -> uri?.let {
             val encoded = Uri.encode(it.toString())
             val route = DestinationActivity.NewPost.createRoute(encoded)
             navController.navigate(route)
    }
    }
    Column {
        Column(
            modifier = Modifier.weight(1f)) {
            Row {
                ProfileImage(userData?.imageUrl){
                  newPostImageLauncher.launch("image/*")
                }
                Text(text = "${posts.size}\nposts",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(text = "54\nfollowers",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
                Text(text = "95\nfollowing",
                    modifier = Modifier
                        .weight(1f)
                        .align(Alignment.CenterVertically),
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp
                )
            }
            Column(
                modifier = Modifier.padding(16.dp)) {
                val usernameDisplay =
                    if (userData?.username == null) "" else "@ ${userData.username}"
                Text(text = userData?.name ?: "", fontWeight = FontWeight.Bold,  fontSize = 20.sp)
                Text(text = usernameDisplay,  fontSize = 20.sp)
                Text(text = userData?.bio ?: "",  fontSize = 20.sp)
            }
            OutlinedButton(onClick =
            {
              navigateTo(navController, DestinationActivity.Profile)
            },
                modifier = Modifier
                    .padding(16.dp)
                    .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 0.dp,
                    pressedElevation = 0.dp,
                    disabledElevation = 0.dp
                ),
                shape = RoundedCornerShape(10)
                ) {
                   Text(text = "Edit Profile", color = Color.Black,fontWeight = FontWeight.Bold,  fontSize = 20.sp)
            }
           PostList(
               isContextLoading = isLoading,
               postsLoading = postLoading,
               posts = posts,
               modifier = Modifier
                   .weight(1f)
                   .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 20.dp)
                   .fillMaxWidth()
                   .fillMaxHeight(0.95f))
           { post ->
               navigateTo(
                   navController =  navController,
                   DestinationActivity.SinglePost,
//                   NavParam("post", post)
               )
           }
        }
        BottomNavigationMenu(
            selectedItem = BottomNavigationItem.MyPost,
            navController = navController
        )
    }
    if(isLoading){
        CustomProgressSpinner()
    }
}

@Composable
fun ProfileImage(imageUrl: String?, onClick: () -> Unit) {
  Box(
      modifier = Modifier
          .padding(top = 26.dp)
          .clickable { onClick.invoke() }
  ) {
      UserImageCard(
          userImage = imageUrl,
          modifier = Modifier
              .padding(16.dp)
              .size(60.dp)
      )
      Card(
          shape = CircleShape,
          border = BorderStroke(width = 2.dp, color = Color.Black),
          modifier = Modifier
              .size(30.dp)
              .align(Alignment.BottomEnd)
              .padding(bottom = 8.dp, end = 8.dp)
      ) {
          Image(
              painter = painterResource(id = R.drawable.baseline_add_24),
              contentDescription = null,
              modifier = Modifier
                  .background(Color.Green)
          )
      }
  }
}


@Composable
fun PostList(
    isContextLoading: Boolean,
    postsLoading: Boolean,
    posts: List<PostData>,
    modifier: Modifier,
    onPostClick: (PostData) -> Unit){
    if(postsLoading){
        CustomProgressSpinner()
    }else if(posts.isEmpty()){
        Column(
            modifier = modifier,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            if(!isContextLoading) Text(text = "No posts found", fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }else {
        LazyColumn(modifier = modifier) {
            val rows = arrayListOf<PostRow>()
            var currentRow = PostRow()
            rows.add(currentRow)
            for(post in posts){
                if(currentRow.isFull()){
                    currentRow = PostRow()
                    rows.add(currentRow)
                }
                currentRow.add(post=post)
            }
            items(items = rows){row ->
                PostsRow(item = row, onPostClick = onPostClick)
            }
        }
    }
}
@Composable
fun PostsRow(item: PostRow, onPostClick: (PostData) -> Unit){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(150.dp)) {
        PostImage(imageUrl = item.post1?.postImage, modifier = Modifier
            .weight(1f)
            .padding(20.dp)
            .clickable { item.post1?.let { post -> onPostClick(post) } }
        )
        PostImage(imageUrl = item.post2?.postImage, modifier = Modifier
            .weight(1f)
            .padding(20.dp)
            .clickable { item.post2?.let { post -> onPostClick(post) } }
        )
        PostImage(imageUrl = item.post3?.postImage, modifier = Modifier
            .weight(1f)
            .padding(20.dp)
            .clickable { item.post3?.let { post -> onPostClick(post) } }
        )
    }
}

@Composable
fun PostImage(imageUrl: String?, modifier: Modifier){
    Box(
        modifier = Modifier) {
        var modifier = Modifier
            .padding(5.dp)
            .fillMaxSize()
        if(imageUrl == null){
            modifier = modifier.clickable(enabled = false) {}
        }
        CommonImage(data = imageUrl, modifier = Modifier, contentScale = ContentScale.Crop)
    }
}
