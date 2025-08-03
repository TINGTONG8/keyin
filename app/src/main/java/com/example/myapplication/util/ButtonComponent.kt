package com.example.myapplication.util

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@Preview
@Composable
fun ButtonComponentPreview() {
    Column {
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.save),
            buttonColor = colorResource(R.color.green),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.delete),
            buttonColor = colorResource(R.color.red),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.update),
            buttonColor = colorResource(R.color.orange),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        ButtonComponent (
            modifier = Modifier,
            buttonText = stringResource(R.string.cancel),
            buttonColor = colorResource(R.color.gray),
            onClick = {}
        )
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            ButtonGuildComponent(
                modifier = Modifier,
                buttonText = stringResource(R.string.next),
                buttonBorderColor = colorResource(R.color.white),
                onClick = {}
            )
            Spacer(modifier = Modifier.width(20.dp))
            ButtonGuildComponent(
                modifier = Modifier,
                buttonText = stringResource(R.string.ok),
                buttonBorderColor = colorResource(R.color.white),
                onClick = {}
            )
        }
    }
}

@Composable
fun ButtonComponent (
    modifier: Modifier,
    isEnabled: Boolean? = true,
    buttonText: String,
    buttonColor: Color,
    onClick: () -> Unit,
) {
    Button(
        enabled = isEnabled == true,
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = buttonColor,
        ),
        contentPadding = PaddingValues(15.dp)
    ) {
        Text(
            text = buttonText,
            color = colorResource(R.color.white),
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif
        )
    }
}

@Composable
fun ButtonGuildComponent (
    modifier: Modifier,
    buttonText: String,
    buttonBorderColor: Color,
    onClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .wrapContentSize()
            .clip(RoundedCornerShape(25.dp))
            .clickable {
                onClick.invoke()
            }
            .border(
                2.dp,
                buttonBorderColor,
                shape = RoundedCornerShape(25.dp)
            )
            .padding(vertical = 15.dp, horizontal = 30.dp)
    ) {
        Text(
            text = buttonText,
            color = colorResource(R.color.white),
            fontSize = 16.sp,
            fontWeight = FontWeight.W400,
            fontFamily = FontFamily.SansSerif
        )
    }
}