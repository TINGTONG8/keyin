package com.example.myapplication.util

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun TextFieldComponentPreview() {
    TextFieldComponent(
        modifier = Modifier,
        label = "ID",
        value = "12345",
        onValueChange = {}
    )
}

@Composable
fun TextFieldComponent (
    modifier: Modifier,
    label: String,
    value: String,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    onValueChange: (String) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif
        ),
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.transparent),
            unfocusedContainerColor = colorResource(R.color.transparent),
            disabledContainerColor = colorResource(R.color.transparent),
            cursorColor = colorResource(R.color.black),
            focusedIndicatorColor = colorResource(R.color.black),
            unfocusedIndicatorColor = colorResource(R.color.gray),
            focusedLabelColor =colorResource(R.color.black),
            unfocusedLabelColor = colorResource(R.color.gray),
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        enabled = enabled
    )
}

@Composable
fun TextFieldComponent (
    modifier: Modifier,
    label: String,
    value: TextFieldValue,
    imeAction: ImeAction = ImeAction.Next,
    onImeAction: () -> Unit = {},
    onValueChange: (TextFieldValue) -> Unit,
    keyboardType: KeyboardType = KeyboardType.Text,
    enabled: Boolean = true
) {
    OutlinedTextField(
        modifier = modifier
            .fillMaxWidth(),
        value = value,
        textStyle = TextStyle(
            fontSize = 18.sp,
            fontWeight = FontWeight(500),
            fontFamily = FontFamily.SansSerif
        ),
        onValueChange = onValueChange,
        label = {
            Text(
                label,
                fontSize = 18.sp,
                fontWeight = FontWeight(500),
                fontFamily = FontFamily.SansSerif,
                textAlign = TextAlign.Center
            )
        },
        singleLine = true,
        colors = TextFieldDefaults.colors(
            focusedContainerColor = colorResource(R.color.transparent),
            unfocusedContainerColor = colorResource(R.color.transparent),
            disabledContainerColor = colorResource(R.color.transparent),
            cursorColor = colorResource(R.color.black),
            focusedIndicatorColor = colorResource(R.color.black),
            unfocusedIndicatorColor = colorResource(R.color.gray),
            focusedLabelColor =colorResource(R.color.black),
            unfocusedLabelColor = colorResource(R.color.gray),
        ),
        shape = RoundedCornerShape(10.dp),
        keyboardOptions = KeyboardOptions(
            imeAction = imeAction,
            keyboardType = keyboardType
        ),
        keyboardActions = KeyboardActions(
            onNext = { onImeAction() },
            onDone = { onImeAction() }
        ),
        enabled = enabled
    )
}