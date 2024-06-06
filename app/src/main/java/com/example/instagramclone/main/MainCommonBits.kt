package com.example.instagramclone.main

import android.os.Parcelable
import android.widget.Toast
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Divider
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberImagePainter
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.R
import com.example.instagramclone.data.PostData

@Composable
fun NotificationMessage(vm: IgViewModel){
    val noticeState = vm.popupNotification.value
    val noticeMessage = noticeState?.getContentOrNull()
    if (noticeMessage!=null){
        Toast.makeText(LocalContext.current, noticeMessage, Toast.LENGTH_LONG).show()
    }
}

@Composable
fun CustomProgressSpinner(){
    Row(
        modifier = Modifier
            .alpha(0.9f)
            .background(Color.LightGray)
            .clickable(enabled = false) { }
            .fillMaxSize(),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ){
        CircularProgressIndicator()
    }
}

//data class NavParam(
//    val name: String,
//    val value: Parcelable
//)

fun navigateTo(navController: NavController, dest: DestinationActivity){
//               vararg params: NavParam){
//    for (param in params) {
//        navController
//            .currentBackStackEntry?.
//            arguments?.
//            putParcelable(param.name,
//                param.value)
//    }
    navController.navigate(dest.route){
        popUpTo(dest.route)
        launchSingleTop = true
    }
}

@Composable
fun CheckSignedIn(navController: NavController, vm: IgViewModel){
   val alreadyLoggedIn = remember { mutableStateOf(false) }
   val signedIn = vm.signedIn.value
   if(signedIn && !alreadyLoggedIn.value){
       alreadyLoggedIn.value = true
       navController.navigate(DestinationActivity.MyPost.route){
           popUpTo(0)
       }
   }
}

@Composable
fun CommonImage(
    data: String?,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Crop
){
    val painter = rememberImagePainter(data = data)
    Image(
        painter = painter,
        contentDescription = null,
        modifier = modifier,
        contentScale = contentScale
    )
    if(painter.state is AsyncImagePainter.State.Loading){
        CustomProgressSpinner()
    }
}

@Composable
fun UserImageCard(
    userImage: String?,
    modifier: Modifier = Modifier
){
    Card(
        shape = CircleShape,
        modifier = modifier){
        if(userImage.isNullOrEmpty()){
            Image(
                painter = painterResource(id = R.drawable.baseline_face_retouching_natural_24),
                contentDescription = null,
                colorFilter = ColorFilter.tint(Color.Gray)
            )
        }else{
            CommonImage(data = userImage)
        }
    }
}

@Composable
fun CommonDivider(){
   Divider(
       color = Color.LightGray,
       thickness = 2.dp,
       modifier = Modifier
           .alpha(0.4f)
           .padding(top = 8.dp, bottom = 8.dp)
   )
}

private enum class LikeIconSize {
  SMALL,
  LARGE
}
@Composable
fun LikeAnimation(like: Boolean = true){
    var sizeState by remember { mutableStateOf(LikeIconSize.SMALL) }
    val transition = updateTransition(targetState = sizeState, label = null)
    val size by transition.animateDp (
        label = "",
        transitionSpec = {
            spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow
            )
        }
    ){
        state ->
        when(state){
            LikeIconSize.SMALL -> 0.dp
            LikeIconSize.LARGE -> 150.dp
        }
    }
    Image(painter = painterResource(id =
    if(like) R.drawable.baseline_favorite_24
    else R.drawable.baseline_not_favorite_border_24),
        contentDescription = null,
        modifier = Modifier.size(size=size),
        colorFilter = ColorFilter.tint(
            if(like) Color.Red
            else Color.Gray
        )
        )
    sizeState = LikeIconSize.LARGE
}
