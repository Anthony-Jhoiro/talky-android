package com.talky.mobile.ui.commons

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.talky.mobile.api.models.MessageDto
import com.talky.mobile.ui.theme.TestSecondary
import com.talky.mobile.ui.theme.VioletFonce
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle

@Composable
fun PrivateMessageSelf(
    messageDto: MessageDto
){
    Row() {
        Spacer(
            modifier = Modifier.weight(1F)
        )
        Card(
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(4F),
            backgroundColor = VioletFonce,
        ) {
            Column() {
                Row(
                    modifier = Modifier.padding(top = 10.dp, end = 10.dp)
                ) {
                    Text(
                        //text = messageDto.createdAt,
                        text = messageDto.createdAt!!.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        fontSize = 15.sp,
                        textAlign = TextAlign.End
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 8.dp, end = 15.dp, bottom = 15.dp)
                ) {
                    Text(
                        color = Color.White,
                        //text = messageDto.content,
                        text = messageDto.content!!)

                }
            }
        }
    }
}

@Composable
fun PrivateMessageFriend(
    messageDto: MessageDto
){
    Row(
        horizontalArrangement = Arrangement.Start
    ) {

        Card(
            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomEnd = 15.dp),
            modifier = Modifier
                .padding(8.dp)
                .fillMaxWidth()
                .weight(4F),
            backgroundColor = TestSecondary,
        ) {
            Column() {
                Row(
                    modifier = Modifier.padding(top = 10.dp,start = 10.dp)
                ) {
                    Text(
                        //text = messageDto.createdAt
                        text = messageDto.createdAt!!.format(DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT)),
                        modifier = Modifier.fillMaxWidth(),
                        color = Color.LightGray,
                        fontSize = 15.sp,
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(start = 15.dp, top = 8.dp, end = 15.dp, bottom = 15.dp)
                ) {
                    Text(
                        color = Color.Black,
                        //text = messageDto.content
                        text = messageDto.content!!)

                }

            }

        }
        Spacer(
            modifier = Modifier.weight(1F)
        )
    }
}