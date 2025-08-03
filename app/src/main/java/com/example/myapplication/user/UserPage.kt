package com.example.myapplication.user

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.SystemUiController
import com.example.myapplication.UserViewModel
import com.example.myapplication.util.BottomSheetComponent
import com.example.myapplication.util.DialogComponent
import com.example.myapplication.util.DialogWithButtonComponent
import com.example.myapplication.util.GuideOverlayComponent
import com.example.myapplication.util.ListUserComponent
import com.example.myapplication.util.User
import com.example.myapplication.util.safeNavigate
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter", "ResourceAsColor")
@Composable
fun UserPage(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val userList = userViewModel.users.collectAsState()

    val userToDelete = remember { mutableStateOf<User?>(null) }
    val userToUpdate = remember { mutableStateOf<User?>(null) }
    val originalUser = remember { mutableStateOf<User?>(null) }

    val showDeleteAllDialog = remember { mutableStateOf(false) }
    val showUpdateDialog = remember { mutableStateOf(false) }
    val showUpdateBottomSheet = remember { mutableStateOf(false) }
    val showUpdatedDialog = remember { mutableStateOf(false) }

    val showGuideClick = userViewModel.showGuideClick.value
    val showGuideLongPress = userViewModel.showGuideLongPress.value
    val showGuideClickClearAll = userViewModel.showGuideClickClearAll.value

    val listUserItemRect = remember { mutableStateOf<Rect?>(null) }
    val clearAllButtonRect = remember { mutableStateOf<Rect?>(null) }

    SystemUiController(useDarkIcons = true)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    navigationIcon = {
                        IconButton(
                            modifier = Modifier,
                            onClick = {
                                navController.safeNavigate("home_page_route")
                            }
                        ) {
                            Icon(
                                modifier = Modifier
                                    .size(50.dp),
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "Pop back"
                            )
                        }
                    },
                    actions = {
                        IconButton(
                            modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                                val position = layoutCoordinates.positionInRoot()
                                val size = layoutCoordinates.size
                                clearAllButtonRect.value = Rect(
                                    left = position.x,
                                    top = position.y,
                                    right = position.x + size.width,
                                    bottom = position.y + size.height
                                )
                            },
                            enabled = userList.value.isNotEmpty(),
                            onClick = {
                                showDeleteAllDialog.value = true
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete all users",
                                tint = if (userList.value.isNotEmpty()) colorResource(R.color.red) else colorResource(
                                    R.color.gray
                                )
                            )
                        }
                    },
                    title = {
                        Text(
                            stringResource(R.string.user_records),
                            fontSize = 20.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    },
                    colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                        containerColor = colorResource(R.color.white)
                    )
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colorResource(R.color.light_gray))
                    .padding(innerPadding)
            ) {
                if (userList.value.isEmpty()) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            stringResource(R.string.no_records),
                            color = colorResource(R.color.gray),
                            fontSize = 18.sp,
                            fontWeight = FontWeight(500),
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.padding(horizontal = 15.dp),
                        verticalArrangement = Arrangement.spacedBy(15.dp)
                    ) {
                        itemsIndexed(
                            items = userList.value,
                            key = { _, user -> user.id }
                        ) { index, user ->
                            val listUserModifier = if (index == 0) {
                                Modifier
                                    .padding(top = 15.dp)
                                    .onGloballyPositioned { layoutCoordinates ->
                                        val position = layoutCoordinates.positionInRoot()
                                        val size = layoutCoordinates.size
                                        listUserItemRect.value = Rect(
                                            left = position.x,
                                            top = position.y,
                                            right = position.x + size.width,
                                            bottom = position.y + size.height
                                        )
                                    }
                            } else {
                                Modifier.padding(top = if (index == 0) 15.dp else 0.dp)
                            }

                            ListUserComponent(
                                modifier = listUserModifier,
                                user = user,
                                onUpdate = {
                                    originalUser.value = user
                                    userToUpdate.value = user
                                    showUpdateDialog.value = true
                                },
                                onLongPressDelete = {
                                    userToDelete.value = user
                                }
                            )
                        }
                    }
                }
            }
        }

        /**
         * DeleteAll Users Dialog
         */
        if (showDeleteAllDialog.value) {
            DialogWithButtonComponent(
                modifier = Modifier,
                title = stringResource(R.string.important),
                subTitle = stringResource(R.string.do_you_want_to_delete_all_records),
                onDismiss = {
                    showDeleteAllDialog.value = false
                },
                onDelete = {
                    userViewModel.resetDatabase()
                    showDeleteAllDialog.value = false
                }
            )
        }

        /**
         * Update Dialog
         */
        if (showUpdateDialog.value) {
            DialogWithButtonComponent(
                modifier = Modifier,
                title = stringResource(R.string.update),
                subTitle = stringResource(R.string.do_you_want_to_update_the_records),
                body = "${stringResource(R.string.id_colon)} ${userToUpdate.value?.id}",
                onDismiss = {
                    showUpdateDialog.value = false
                },
                onUpdate = {
                    showUpdateDialog.value = false
                    showUpdateBottomSheet.value = true
                }
            )
        }

        /**
         * Update BottomSheet
         */
        if (showUpdateBottomSheet.value) {
            BottomSheetComponent(
                modifier = Modifier,
                originalUser = originalUser.value,
                firstName = userToUpdate.value?.firstName ?: "",
                lastName = userToUpdate.value?.lastName ?: "",
                eMail = userToUpdate.value?.eMail ?: "",
                onFirstNameChange = { newName -> userToUpdate.value = userToUpdate.value?.copy(firstName = newName) },
                onLastNameChange = { newName -> userToUpdate.value = userToUpdate.value?.copy(lastName = newName) },
                onEmailChange = { newEmail -> userToUpdate.value = userToUpdate.value?.copy(eMail = newEmail) },
                onDismiss = {
                    showUpdateBottomSheet.value = false
                },
                onUpdate = {
                    userToUpdate.value?.let { user ->
                        userViewModel.updateUser(user)
                        showUpdatedDialog.value = true
                    }
                    showUpdateBottomSheet.value = false
                }
            )
        }

        /**
         * Updated Dialog
         */
        if (showUpdatedDialog.value) {
            LaunchedEffect(Unit) {
                delay(5000)
                showUpdatedDialog.value = false
            }

            DialogComponent(
                modifier = Modifier,
                title = stringResource(R.string.successful),
                subTitle = stringResource(R.string.the_record_has_been_updated_successfully),
                body = "${stringResource(R.string.id_colon)} ${userToUpdate.value?.id}",
                onDismiss = {
                    showUpdatedDialog.value = false
                }
            )
        }

        /**
         * DeleteOnly User Dialog
         */
        userToDelete.value?.let { user ->
            DialogWithButtonComponent(
                modifier = Modifier,
                title = stringResource(R.string.important),
                subTitle = stringResource(R.string.do_you_want_to_delete_the_record),
                body = "${stringResource(R.string.id_colon)} ${userToDelete.value?.id}",
                onDismiss = {
                    userToDelete.value = null
                },
                onDelete = {
                    userViewModel.deleteUser(user)
                    userToDelete.value = null
                }
            )
        }

        /**
         * Guild Line
         */
        when {
            showGuideClick -> {
                GuideOverlayComponent(
                    title = stringResource(R.string.click_to_update_the_record),
                    buttonText = stringResource(R.string.next),
                    countDownText = stringResource(R.string.next),
                    highlightRect = listUserItemRect.value,
                    gifResourceId = R.drawable.guild_click,
                    onButtonClick = {
                        userViewModel.onGuideClickNext()
                    }
                )
            }
            showGuideLongPress -> {
                GuideOverlayComponent(
                    title = stringResource(R.string.long_press_to_delete_the_record),
                    buttonText = stringResource(R.string.next),
                    countDownText = stringResource(R.string.next),
                    highlightRect = listUserItemRect.value,
                    gifResourceId = R.drawable.guild_long_press,
                    gifSize = 10.dp,
                    onButtonClick = {
                        userViewModel.onGuideLongPressNext()
                    }
                )
            }
            showGuideClickClearAll -> {
                GuideOverlayComponent(
                    title = stringResource(R.string.click_to_clear_all_records),
                    buttonText = stringResource(R.string.ok),
                    countDownText = stringResource(R.string.finish),
                    highlightRect = clearAllButtonRect.value,
                    gifResourceId = R.drawable.guild_click,
                    gifSize = 90.dp,
                    offsetCenterX = 2,
                    onButtonClick = {
                        userViewModel.onGuideFinished()
                    }
                )
            }
        }
    }
}