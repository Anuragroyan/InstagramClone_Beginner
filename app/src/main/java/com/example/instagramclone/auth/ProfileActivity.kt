package com.example.instagramclone.auth

import android.annotation.SuppressLint
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Card
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.data.UserData
import com.example.instagramclone.main.CommonDivider
import com.example.instagramclone.main.CommonImage
import com.example.instagramclone.main.CustomProgressSpinner
import com.example.instagramclone.main.ProfileImage
import com.example.instagramclone.main.navigateTo


@Composable
fun ProfileActivity(navController: NavController, vm: IgViewModel){
    val isLoading = vm.inProgress.value
    if(isLoading){
        CustomProgressSpinner()
    }else{
        val userData = vm.userData.value
        var name by rememberSaveable { mutableStateOf(userData?.name ?: "")}
        var username by rememberSaveable { mutableStateOf(userData?.username ?: "")}
        var bio by rememberSaveable { mutableStateOf(userData?.bio ?: "")}
        ProfileContent(vm = vm,
            name = name,
            username = username,
            bio = bio,
            onUsernameChange = { username = it},
            onNameChange = { name = it},
            onBioChange = { bio = it},
            onSave = { vm.updateProfileData(name, username, bio)},
            onBack = { navigateTo(navController = navController, DestinationActivity.MyPost)},
            onLogout = { vm.onLogout()
                navigateTo(navController, DestinationActivity.Login)
            }
            )
    }
}

@SuppressLint("SuspiciousIndentation")
@Composable
fun  ProfileContent(
    vm: IgViewModel,
    name: String,
    username: String,
    bio: String,
    onNameChange: (String) -> Unit,
    onUsernameChange: (String) -> Unit,
    onBioChange: (String) -> Unit,
    onSave: () -> Unit,
    onBack: () -> Unit,
    onLogout: () -> Unit
){
  val scrollState = rememberScrollState()
  val imageUrl = vm.userData.value?.imageUrl
    Column(
        modifier = Modifier
            .verticalScroll(scrollState)
            .padding(20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "Back", modifier = Modifier.clickable { onBack.invoke() },
                fontWeight = FontWeight.Bold, color = Color.Blue, fontSize = 20.sp )
            Text(text = "Save", modifier = Modifier.clickable { onSave.invoke() },
                fontWeight = FontWeight.Bold, color = Color.Red,  fontSize = 20.sp)
        }
        CommonDivider()
        ProfileImage(imageUrl = imageUrl, vm = vm)
        CommonDivider()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Name", modifier = Modifier.width(300.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            TextField(
                value = name,
                onValueChange = onNameChange,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Username", modifier = Modifier.width(300.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            TextField(
                value = username,
                onValueChange = onUsernameChange,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                )
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 17.dp, end = 17.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Bio", modifier = Modifier.width(300.dp), fontWeight = FontWeight.Bold, fontSize = 20.sp)
            TextField(
                value = bio,
                onValueChange = onBioChange,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    textColor = Color.Black
                ),
                singleLine = false,
                modifier = Modifier.height(150.dp)
            )
        }
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 17.dp, bottom = 17.dp),
            horizontalArrangement = Arrangement.Center
        ){
            Text(text = "Logout", modifier = Modifier.clickable { onLogout.invoke() }, fontSize = 20.sp, fontWeight = FontWeight.Bold)
        }
    }
}

@Composable
fun ProfileImage(imageUrl: String?, vm: IgViewModel){
    val launcher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()) {
        uri: Uri? ->
        uri?.let{
            vm.uploadProfileImage(uri)
        }
    }
 Box(
     modifier = Modifier.height(IntrinsicSize.Min)) {
     Column(
         modifier = Modifier
             .padding(8.dp)
             .fillMaxWidth()
             .clickable { launcher.launch("image/*") },
         horizontalAlignment = Alignment.CenterHorizontally
     ) {
         Card(
             shape = CircleShape,
             modifier = Modifier
                 .padding(8.dp)
                 .size(100.dp)
         ) {
             CommonImage(data = imageUrl)
         }
         Text(text = "Change profile picture" , fontWeight = FontWeight.Bold, fontSize = 20.sp)
     }
     val isLoading = vm.inProgress.value
     if(isLoading){
         CustomProgressSpinner()
     }
 }
}