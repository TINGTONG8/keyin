package com.example.myapplication.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ListUserComponent(
    modifier: Modifier,
    onUpdate: () -> Unit,
    onLongPressDelete: () -> Unit,
    user: User,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                color = colorResource(R.color.white),
                shape = RoundedCornerShape(10.dp)
            )
            .clip(RoundedCornerShape(10.dp))
            .combinedClickable(
                onClick = {
                    onUpdate.invoke()
                },
                onLongClick = {
                    onLongPressDelete.invoke()
                }
            )
            .padding(horizontal = 15.dp, vertical = 10.dp)
    ) {
        Column(
            modifier = Modifier,
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.id)}:",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    user.id.toString(),
                    modifier = Modifier.weight(2f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.End
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.first_name)}:",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    user.firstName,
                    modifier = Modifier.weight(2f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.End
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.last_name)}:",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    user.lastName,
                    modifier = Modifier.weight(2f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.End
                )
            }
            Row(
                modifier = Modifier
                    .padding(top = 5.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "${stringResource(R.string.email)}:",
                    modifier = Modifier.weight(1f),
                    fontSize = 18.sp,
                    fontWeight = FontWeight(400),
                    fontFamily = FontFamily.SansSerif
                )
                Text(
                    user.eMail,
                    modifier = Modifier.weight(2f),
                    fontSize = 18.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    fontWeight = FontWeight(500),
                    fontFamily = FontFamily.SansSerif,
                    textAlign = TextAlign.End
                )
            }
        }
    }
}