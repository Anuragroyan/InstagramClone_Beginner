package com.example.instagramclone.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Button
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.OutlinedTextField
//noinspection UsingMaterialAndMaterial3Libraries
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel
import com.example.instagramclone.R
import com.example.instagramclone.main.CheckSignedIn
import com.example.instagramclone.main.CustomProgressSpinner
import com.example.instagramclone.main.navigateTo

@Composable
fun SignupActivity(navController: NavController, vm: IgViewModel){
   CheckSignedIn(navController = navController, vm = vm)
   Box(modifier = Modifier.fillMaxSize()){
      Column(
         modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .verticalScroll(
               rememberScrollState()
            ),
         horizontalAlignment = Alignment.CenterHorizontally
      ) {
         val usernameState = remember { mutableStateOf(TextFieldValue()) }
         val passwordState = remember { mutableStateOf(TextFieldValue()) }
         val emailState = remember { mutableStateOf(TextFieldValue()) }
         Image(
            painter = painterResource(id = R.drawable.ig_logo),
            contentDescription = null,
            modifier = Modifier
               .width(250.dp)
               .padding(top = 16.dp)
               .padding(8.dp)
         )
         Text(
            text = "Sign Up",
            modifier = Modifier.padding(30.dp),
            fontSize = 30.sp,
            fontFamily = FontFamily.Monospace,
            fontWeight = FontWeight.Bold,
         )
         OutlinedTextField(
            value = usernameState.value,
            onValueChange = { usernameState.value = it},
            modifier = Modifier
               .padding(30.dp)
               .fillMaxWidth(),
            label = { Text(text = "Username",
               modifier = Modifier.padding(30.dp),
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp
            )}
         )
         OutlinedTextField(
            value = emailState.value,
            onValueChange = { emailState.value = it},
            modifier = Modifier
               .padding(30.dp)
               .fillMaxWidth(),
            label = { Text(text = "Email",
               modifier = Modifier.padding(30.dp),
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp
            )}
         )
         OutlinedTextField(
            value = passwordState.value,
            onValueChange = { passwordState.value = it},
            modifier = Modifier
               .padding(30.dp)
               .fillMaxWidth(),
            label = { Text(text = "Password",
               modifier = Modifier.padding(30.dp),
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp
            )}
         )
         Button(
            onClick = {
                      vm.onSignup(
                         usernameState.value.text,
                         emailState.value.text,
                         passwordState.value.text
                      )
            },
            modifier = Modifier.padding(30.dp)
         ){
            Text(text = "SIGN UP",
               color =  Color.Yellow,
               fontWeight = FontWeight.Bold,
               fontSize = 30.sp
            )
         }
         Text(text = "Already a user? Go to login ->",
              color =  Color.Blue,
            fontWeight = FontWeight.Bold,
            fontSize = 30.sp,
            modifier = Modifier
               .padding(20.dp)
               .clickable {
                  navigateTo(navController, DestinationActivity.Login)
               }
            )
      }
      val isLoading = vm.inProgress.value
      if(isLoading){
         CustomProgressSpinner()
      }
   }
}
