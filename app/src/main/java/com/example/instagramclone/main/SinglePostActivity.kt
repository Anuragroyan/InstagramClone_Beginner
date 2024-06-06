package com.example.instagramclone.main

//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberAsyncImagePainter
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.R


@Composable
fun SinglePostActivity(navController: NavController, vm: IgViewModel){
//      PostData.post.userId?.let {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight()
                .padding(8.dp)) {
            Text(text = "Back", modifier = Modifier.clickable { navController.popBackStack() },
                color = Color.Blue, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            CommonDivider()
            SinglePostDisplay(navController = navController, vm=vm)
        }
    }

@Composable
fun SinglePostDisplay(navController: NavController, vm: IgViewModel) {
    val userData = vm.userData.value
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(40.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Card(
                shape = CircleShape, modifier = Modifier
                    .padding(8.dp)
                    .size(32.dp)
            ) {
                Image(
                    painter = rememberAsyncImagePainter(model = R.drawable.baseline_face_retouching_natural_24),
                    contentDescription = null
                )
            }
            Text(text = "Sheldon", fontWeight = FontWeight.Bold, fontSize = 20.sp)
            Text(text = ".", modifier = Modifier.padding(8.dp))
            if (userData?.userId == "") {
                // do nothing
            } else if(userData?.userId == "54"){
                Text(
                    text = "Follow",
                    color = Color.Green,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        userData.userId?.let { vm.onFollowClick(userId = it) }
                    })
            }else{
                Text(
                    text = "Following",
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.clickable {
                        userData?.userId?.let { vm.onFollowClick(userId = it) }
                    }
                )
            }
        }
    }
    Box {
        val modifier = Modifier
            .fillMaxWidth()
            .defaultMinSize(minHeight = 150.dp)
        Image(
                painter = painterResource(id = R.drawable.tokyo),
                contentDescription = "",
            modifier = modifier,
            contentScale = ContentScale.FillWidth
        )
    }
    Column {
        Row(
            modifier = Modifier.padding(8.dp), verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.baseline_favorite_24),
                contentDescription = null,
                modifier = Modifier.size(24.dp),
                colorFilter = ColorFilter.tint(Color.Red)
            )
            Text(text = "4 likes", modifier = Modifier.padding(start = 34.dp), fontSize = 20.sp)
        }
        Row(
            modifier = Modifier.padding(8.dp)
        )
        {
            Text(text = "Sheldon", fontWeight = FontWeight.Bold)
            Text(text = "Tokyo", modifier = Modifier.padding(start = 8.dp))
        }
        Row(modifier = Modifier.padding(8.dp)) {
            Text(text = "10 comments", overflow = TextOverflow.Ellipsis,fontSize = 20.sp,color = Color.DarkGray, modifier = Modifier
                .padding(start = 8.dp)
                .clickable {
                    navController.navigate(DestinationActivity.Comments.route)
                })
        }
    }
}