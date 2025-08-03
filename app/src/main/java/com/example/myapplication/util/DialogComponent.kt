package com.example.myapplication.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import com.example.myapplication.R

@Preview
@Composable
fun DialogComponentPreview() {
    DialogComponent(
        modifier = Modifier,
        icon = painterResource(R.drawable.icon_check),
        titlePrefix = stringResource(R.string.all_deleted),
        titleSuffix = stringResource(R.string.successful),
        subTitle = stringResource(R.string.all_records_has_been_deleted_successfully),
        onDismiss = {}
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DialogWithButtonComponent (
    modifier: Modifier,
    title: String? = null,
    subTitle: String? = null,
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
    icon: Painter = painterResource(R.drawable.icon_check),
    titlePrefix: String? = null,
    titleSuffix: String? = null,
    titlePrefixColor: Color = colorResource(R.color.red),
    subTitle: String? = null,
    onDismiss: () -> Unit
) {
    BasicAlertDialog(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 15.dp, end = 15.dp, bottom = 30.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false),
        onDismissRequest = {
            onDismiss.invoke()
        }
    ) {
        Box(
            contentAlignment = Alignment.TopCenter
        ) {
            Column (
                modifier = Modifier
                    .padding(top = 40.dp)
                    .background(
                        color = colorResource(R.color.white),
                        shape = RoundedCornerShape(10.dp)
                    )
                    .padding(top = 50.dp, bottom = 20.dp, start = 20.dp, end = 20.dp),
                verticalArrangement = Arrangement.spacedBy(15.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row (
                    modifier = Modifier,
                    horizontalArrangement = Arrangement.Center
                ) {
                    if (titlePrefix != null) {
                        Text(
                            text = titlePrefix,
                            color = titlePrefixColor,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    }
                    if (titleSuffix != null) {
                        Text(
                            modifier = Modifier.padding(start = 3.dp),
                            text = titleSuffix,
                            color = colorResource(R.color.black),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.W500,
                            fontFamily = FontFamily.SansSerif,
                            textAlign = TextAlign.Center
                        )
                    }
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
            }

            Box(
                modifier = Modifier
                    .background(
                        colorResource(R.color.white),
                        RoundedCornerShape(50.dp)
                    )
            ) {
                Image(
                    modifier = Modifier
                        .size(80.dp)
                        .padding(5.dp)
                        .clip(CircleShape),
                    painter = icon,
                    contentDescription = "check",
                    contentScale = ContentScale.FillBounds
                )
            }
        }
    }
}