package com.example.myapplication.util

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R
import kotlinx.coroutines.delay

@Preview
@Composable
fun BottomSheetComponentPreview() {
    BottomSheetComponent(
        modifier = Modifier,
        originalUser = null,
        firstName = "John",
        lastName = "Doe",
        eMail = "john.doe@example.com",
        onFirstNameChange = { },
        onLastNameChange = { },
        onEmailChange = { },
        onDismiss = { },
        onUpdate = {}
    )
}

@Composable
fun BottomSheetComponent(
    modifier: Modifier,
    originalUser: User?,
    firstName: String,
    lastName: String,
    eMail: String,
    onFirstNameChange: (String) -> Unit,
    onLastNameChange: (String) -> Unit,
    onEmailChange: (String) -> Unit,
    onDismiss: () -> Unit,
    onUpdate: () -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val focusRequester = remember { FocusRequester() }

    val firstNameTextFieldValue = remember {
        mutableStateOf(TextFieldValue(text = firstName, selection = TextRange(firstName.length)))
    }
    val lastNameTextFieldValue = remember {
        mutableStateOf(TextFieldValue(text = lastName, selection = TextRange(lastName.length)))
    }
    val emailTextFieldValue = remember {
        mutableStateOf(TextFieldValue(text = eMail, selection = TextRange(eMail.length)))
    }

    LaunchedEffect(Unit) {
        delay(100)
        focusRequester.requestFocus()
    }

    val isSaveEnabled = remember(originalUser, firstNameTextFieldValue.value.text, lastNameTextFieldValue.value.text, emailTextFieldValue.value.text) {
        val areFieldsValid = firstNameTextFieldValue.value.text.isNotBlank() && lastNameTextFieldValue.value.text.isNotBlank()
        val hasChanges = originalUser == null ||
                originalUser.firstName != firstNameTextFieldValue.value.text ||
                originalUser.lastName != lastNameTextFieldValue.value.text ||
                originalUser.eMail != emailTextFieldValue.value.text
        areFieldsValid && hasChanges
    }

    Box(
        modifier = modifier
            .imePadding()
            .fillMaxSize()
            .background(color = colorResource(R.color.black_50))
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onDismiss.invoke() }
            ),
        contentAlignment = Alignment.BottomCenter
    ) {
        Column(
            modifier = Modifier
                .navigationBarsPadding()
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
                )
                .clip(RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp))
                .clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = {}
                )
                .padding(horizontal = 15.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                stringResource(R.string.update),
                modifier = Modifier.padding(top = 20.dp),
                fontSize = 22.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
            if (originalUser != null) {
                TextFieldComponent(
                    modifier = Modifier.padding(top = 20.dp),
                    label = stringResource(R.string.id),
                    value = originalUser.id.toString(),
                    onValueChange = {},
                    enabled = false
                )
            }
            TextFieldComponent(
                modifier = Modifier
                    .padding(top = 15.dp)
                    .focusRequester(focusRequester),
                label = stringResource(R.string.first_name),
                value = firstNameTextFieldValue.value,
                onValueChange = {
                    firstNameTextFieldValue.value = it
                    onFirstNameChange(it.text)
                },
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )
            TextFieldComponent(
                modifier = Modifier.padding(top = 15.dp),
                label = stringResource(R.string.last_name),
                value = lastNameTextFieldValue.value,
                onValueChange = {
                    lastNameTextFieldValue.value = it
                    onLastNameChange(it.text)
                },
                onImeAction = { focusManager.moveFocus(FocusDirection.Down) }
            )
            TextFieldComponent(
                modifier = Modifier.padding(top = 15.dp),
                label = stringResource(R.string.email),
                value = emailTextFieldValue.value,
                onValueChange = {
                    emailTextFieldValue.value = it
                    onEmailChange(it.text)
                },
                imeAction = ImeAction.Done,
                onImeAction = {
                    if (isSaveEnabled) {
                        focusManager.clearFocus()
                        onUpdate.invoke()
                    } else {
                        onDismiss.invoke()
                    }
                }
            )
            ButtonComponent(
                modifier = Modifier.padding(vertical = 20.dp),
                buttonText = stringResource(R.string.update),
                buttonColor = if (isSaveEnabled) colorResource(R.color.orange) else colorResource(R.color.gray),
                isEnabled = isSaveEnabled,
                onClick = { onUpdate.invoke() }
            )
        }
    }
}