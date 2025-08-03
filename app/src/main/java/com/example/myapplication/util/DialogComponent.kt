package com.example.myapplication.util

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWithButtonComponent (
    modifier: Modifier,
    title: String? = null,
    subTitle: String? = null,
    body: String? = null,
    onDismiss: () -> Unit,
    onDelete: (() -> Unit)? = null,
    onUpdate: (() -> Unit)? = null,
) {
    BasicAlertDialog(
        modifier = modifier
            .padding(horizontal = 15.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        Column (
            modifier = Modifier
                .background(
                    color = colorResource(R.color.white),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = colorResource(R.color.black),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            if (subTitle != null) {
                Text(
                    text = subTitle,
                    color = colorResource(R.color.black),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            if (body != null) {
                Text(
                    text = body,
                    color = colorResource(R.color.gray),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            Row (
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp),
                horizontalArrangement = Arrangement.spacedBy(15.dp)
            ) {
                ButtonComponent(
                    modifier = Modifier.weight(1f),
                    buttonText = stringResource(R.string.cancel),
                    buttonColor = colorResource(R.color.gray),
                    onClick = {
                        onDismiss.invoke()
                    }
                )
                if (onDelete != null) {
                    ButtonComponent(
                        modifier = Modifier.weight(1f),
                        buttonText = stringResource(R.string.delete),
                        buttonColor = colorResource(R.color.red),
                        onClick = {
                            onDelete.invoke()
                        }
                    )

                }
                if (onUpdate != null) {
                    ButtonComponent(
                        modifier = Modifier.weight(1f),
                        buttonText = stringResource(R.string.update),
                        buttonColor = colorResource(R.color.orange),
                        onClick = {
                            onUpdate.invoke()
                        }
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogComponent (
    modifier: Modifier,
    title: String? = null,
    subTitle: String? = null,
    body: String? = null,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        Column (
            modifier = Modifier
                .background(
                    color = colorResource(R.color.green),
                    shape = RoundedCornerShape(10.dp)
                )
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (title != null) {
                Text(
                    text = title,
                    color = colorResource(R.color.white),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.W500,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            if (subTitle != null) {
                Text(
                    text = subTitle,
                    color = colorResource(R.color.white),
                    fontSize = 18.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
            if (body != null) {
                Text(
                    text = body,
                    color = colorResource(R.color.white),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.W400,
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}