package com.example.instagramclone.main

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.instagramclone.DestinationActivity
import com.example.instagramclone.IgViewModel


@Composable
fun SearchActivity(navController: NavController, vm: IgViewModel){
  val searchedPostsLoading = vm.searchPostsProgress.value
  val searchPosts = vm.searchPosts.value
  var searchTerm by rememberSaveable { mutableStateOf("")}
  Column(
      modifier = Modifier.padding(10.dp).fillMaxWidth()
  ){
      SearchBar(
          modifier = Modifier.padding(top = 20.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
          searchTerm = searchTerm,
          onSearchChange = {
              searchTerm = it },
          onSearch = {vm.searchPosts(searchTerm)}
      )
      PostList(
          isContextLoading = false,
          postsLoading = searchedPostsLoading,
          posts = searchPosts,
          modifier = Modifier
              .weight(1f)
              .fillMaxWidth()
              .padding(8.dp)
      ) {
          navigateTo(
              navController = navController,
              dest = DestinationActivity.SinglePost
          )
      }
      BottomNavigationMenu(selectedItem = BottomNavigationItem.Search, navController = navController)
  }
}
@Composable
fun SearchBar(
    searchTerm: String,
    onSearchChange: (String) -> Unit,
    onSearch: () -> Unit,
    modifier: Modifier
){
  val focusManager = LocalFocusManager.current
  TextField(
      value = searchTerm,
      onValueChange = onSearchChange,
      modifier = Modifier
          .padding(8.dp)
          .fillMaxWidth()
          .border(1.dp, Color.LightGray, CircleShape),
      shape = CircleShape,
      keyboardOptions = KeyboardOptions(
          keyboardType = KeyboardType.Text,
          imeAction = ImeAction.Search
      ),
      keyboardActions = KeyboardActions(
          onSearch = {
              onSearch()
              focusManager.clearFocus()
          }
      ),
      maxLines = 1,
      singleLine = true,
      colors = TextFieldDefaults.textFieldColors(
          backgroundColor = Color.Transparent,
          textColor = Color.Black,
          focusedIndicatorColor = Color.Transparent,
          unfocusedLabelColor = Color.Transparent,
          disabledIndicatorColor = Color.Transparent
      ),
      trailingIcon = {
          IconButton(onClick = {
              onSearch()
              focusManager.clearFocus()
          }) {
              Icon(imageVector = Icons.Filled.Search, contentDescription = "Search Icon")
          }
      }
  )
}