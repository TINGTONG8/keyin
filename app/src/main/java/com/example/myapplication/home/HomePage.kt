package com.example.myapplication.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.myapplication.R
import com.example.myapplication.SystemUiController
import com.example.myapplication.UserViewModel
import com.example.myapplication.util.ButtonComponent
import com.example.myapplication.util.GuideOverlayComponent
import com.example.myapplication.util.TextFieldComponent
import com.example.myapplication.util.safeNavigate

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomePage(
    navController: NavController,
    userViewModel: UserViewModel
) {
    val focusManager = LocalFocusManager.current

    val userList = userViewModel.users.collectAsState()

    val firstName = userViewModel.firstName
    val lastName = userViewModel.lastName
    val eMail = userViewModel.eMail

    val showGuide = userViewModel.showFirstSaveGuide.value
    val menuButtonRect = remember { mutableStateOf<Rect?>(null) }

    val isSaveEnabled = firstName.value.isNotBlank()
            && lastName.value.isNotBlank() && eMail.value.isNotBlank()

    SystemUiController(useDarkIcons = true)

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                            if (layoutCoordinates.isAttached) {
                                val position = layoutCoordinates.positionInRoot()
                                val size = layoutCoordinates.size
                                menuButtonRect.value = Rect(
                                    left = position.x,
                                    top = position.y,
                                    right = position.x + size.width,
                                    bottom = position.y + size.height
                                )
                            }
                        },
                        enabled = userList.value.isNotEmpty(),
                        onClick = {
                            navController.safeNavigate("user_page_route")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = if (userList.value.isNotEmpty()) colorResource(R.color.black) else colorResource(R.color.gray)
                        )
                    }
                },
                title = {
                    Text(
                        stringResource(R.string.input_user),
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
                .padding(horizontal = 15.dp)
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {
                        focusManager.clearFocus()
                    }
                ),
        ) {
            TextFieldComponent(
                modifier = Modifier
                    .padding(top = 15.dp),
                label = stringResource(R.string.first_name),
                value = firstName.value,
                onValueChange = { newValue ->
                    userViewModel.firstName.value = newValue
                },
                onImeAction = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
            TextFieldComponent(
                modifier = Modifier.padding(top = 15.dp),
                label = stringResource(R.string.last_name),
                value = lastName.value,
                onValueChange = { newValue ->
                    userViewModel.lastName.value = newValue
                },
                onImeAction = {
                    focusManager.moveFocus(FocusDirection.Down)
                }
            )
            TextFieldComponent(
                modifier = Modifier.padding(top = 15.dp),
                label = stringResource(R.string.email),
                value = eMail.value,
                onValueChange = { newValue ->
                    userViewModel.eMail.value = newValue
                },
                imeAction = ImeAction.Done,
                onImeAction = {
                    focusManager.clearFocus()
                    if (eMail.value.isNotEmpty()) {
                        userViewModel.addUser()
                    }
                }
            )
            ButtonComponent(
                modifier = Modifier.padding(top = 20.dp),
                buttonText = stringResource(R.string.save),
                buttonColor = if (isSaveEnabled) {
                    colorResource(R.color.green)
                } else {
                    colorResource(R.color.gray)
                },
                onClick = {
                    focusManager.clearFocus()
                    userViewModel.addUser()
                },
                isEnabled = isSaveEnabled
            )
        }
    }

    /**
     * Guild Line
     */
    when {
        showGuide -> {
            GuideOverlayComponent(
                title = stringResource(R.string.click_to_see_all_the_saved_records),
                buttonText = stringResource(R.string.ok),
                highlightRect = menuButtonRect.value,
                gifResourceId = R.drawable.guild_click,
                offsetCenterX = 2,
                enableCountdown = false,
                onButtonClick = {
                    userViewModel.dismissFirstSaveGuide()
                }
            )
        }
    }
}