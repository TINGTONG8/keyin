package com.example.myapplication.util

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
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
            InfoRow(
                label = stringResource(R.string.id),
                value = user.id.toString()
            )
            InfoRow(
                modifier = Modifier.padding(top = 5.dp),
                label = stringResource(R.string.first_name),
                value = user.firstName
            )
            InfoRow(
                modifier = Modifier.padding(top = 5.dp),
                label = stringResource(R.string.last_name),
                value = user.lastName
            )
            InfoRow(
                modifier = Modifier.padding(top = 5.dp),
                label = stringResource(R.string.email),
                value = user.eMail
            )
        }
    }
}