package com.example.instagramclone.main

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.R

enum class BottomNavigationItem(val icon: Int, val navDestination: DestinationActivity){
    Feed(R.drawable.baseline_home_filled_24, DestinationActivity.Feed),
    SinglePost(R.drawable.baseline_portrait_24, DestinationActivity.SinglePost),
    Search(R.drawable.baseline_search_24, DestinationActivity.Search),
    MyPost(R.drawable.baseline_post_add_24, DestinationActivity.MyPost)
}

@Composable
fun BottomNavigationMenu(selectedItem: BottomNavigationItem, navController: NavController){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 4.dp, bottom = 20.dp)
            .background(Color.White)
    ){
        for(item in BottomNavigationItem.values()){
            Image(
                painter = painterResource(id = item.icon),
                contentDescription = null,
                modifier = Modifier
                    .size(45.dp)
                    .padding(5.dp)
                    .weight(1f)
                    .clickable {
                        navigateTo(navController, item.navDestination)
                    },
                colorFilter =
                if (item == selectedItem)
                    ColorFilter.tint(Color.Magenta)
                else
                    ColorFilter.tint(Color.Blue)
            )
        }
    }
}