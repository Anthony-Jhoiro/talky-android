package com.talky.mobile.ui.features.postCreation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.talky.mobile.api.models.PostDto

@Composable
fun PrivacySelector(
    selectedOption: Option<PostDto.Privacy>,
    onOptionSelected: (Option<PostDto.Privacy>) -> Unit
) {
    Box(
        modifier = Modifier
            .shadow(
                elevation = 8.dp,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .selectableGroup()
                .background(Color.White)
                .padding(8.dp)

        ) {
            Text(
                text = "Qui peut voir votre post ?",
                style = MaterialTheme.typography.caption.merge()
            )
            privacyOptions.forEach { option ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(38.dp)
                        .selectable(
                            selected = option.value == selectedOption.value,
                            onClick = { onOptionSelected(option) },
                            role = Role.RadioButton
                        )
                        .padding(horizontal = 16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = option.value == selectedOption.value,
                        onClick = null
                    )
                    Text(
                        text = option.title,
                        style = MaterialTheme.typography.body1.merge(),
                        modifier = Modifier.padding(start = 16.dp)
                    )
                }
            }
        }
    }
}