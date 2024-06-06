package com.example.instagramclone.main

//noinspection UsingMaterialAndMaterial3Libraries
import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.data.PostData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun FeedActivity(navController: NavController, vm: IgViewModel) {
    val userDataLoading = vm.inProgress.value
    val userData = vm.userData.value
    val personalizedFeed = vm.postsFeed.value
    val personalizedFeedLoading = vm.postsFeedProgress.value
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    )
    {
        Spacer(modifier = Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .background(Color.White)
        )
        {
            UserImageCard(
                userImage = userData?.imageUrl
            )
            Spacer(modifier = Modifier.width(560.dp))
            Text(
                text = "Back",
                modifier = Modifier
                    .clickable { navController.popBackStack() },
                fontSize = 20.sp,
                textAlign = TextAlign.Right,
                color = Color.Blue,
                fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(20.dp))
            PostList(
                posts = personalizedFeed,
                modifier = Modifier.weight(1f),
                loading = personalizedFeedLoading or userDataLoading,
                navController = navController,
                vm = vm,
                currentUserId = userData?.userId ?: ""
            )
            BottomNavigationMenu(
                selectedItem = BottomNavigationItem.Feed,
                navController = navController
            )
    }
}

@Composable
fun PostList(
    posts: List<PostData>,
    modifier: Modifier,
    loading: Boolean,
    navController: NavController,
    vm: IgViewModel,
    currentUserId: String
){
    Box(modifier = Modifier) {
        LazyColumn{
            items(items = posts){
                Post(
                    post = it,
                    currentUserId = currentUserId,
                    vm = vm) {
                    navigateTo(
                        navController,
                        DestinationActivity.SinglePost
                    )
                }
            }
        }
        if(loading){
            CustomProgressSpinner()
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun Post(post: PostData, currentUserId: String, vm: IgViewModel, onPostClick: () -> Unit) {
    val likeAnimation = remember { mutableStateOf(false) }
    val disLikeAnimation = remember { mutableStateOf(true) }
    Card(
        shape = RoundedCornerShape(corner = CornerSize(10.dp)),
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(top = 4.dp, bottom = 4.dp)
    ) {
        Column {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card(
                    shape = CircleShape,
                    modifier = Modifier
                        .padding(4.dp)
                        .size(32.dp)
                ) {
                    CommonImage(data = post.userImage, contentScale = ContentScale.Crop)
                }
                Text(
                    text = post.username ?: "",
                    modifier = Modifier.padding(4.dp)
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.Center
            ) {
                val modifier = Modifier
                    .fillMaxWidth()
                    .defaultMinSize(minHeight = 150.dp)
                    .pointerInput(Unit) {
                        detectTapGestures (
                            onDoubleTap = {
                                if(post.likes?.contains(currentUserId)==true){
                                    disLikeAnimation.value = true
                                }else{
                                    likeAnimation.value = true
                                }
                                vm.onLikePost(post)
                            },
                            onTap = {
                                onPostClick.invoke()
                            }
                        )
                    }
                CommonImage(
                    data = post.postImage,
                    modifier = modifier,
                    contentScale = ContentScale.FillWidth
                )
                if(likeAnimation.value){
                    CoroutineScope(Dispatchers.Main).launch{
                        delay(1000L)
                        likeAnimation.value = false
                    }
                }
                if(disLikeAnimation.value){
                    CoroutineScope(Dispatchers.Main).launch{
                        delay(1000L)
                        disLikeAnimation.value = true
                    }
                    LikeAnimation(false)
                }
            }
        }
        Spacer(modifier = Modifier.height(20.dp))
    }
}