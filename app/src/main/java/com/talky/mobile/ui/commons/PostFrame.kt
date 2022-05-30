package com.talky.mobile.ui.commons

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.layout.ContentScale.Companion.Fit
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.talky.mobile.api.models.PostDto
import com.talky.mobile.api.models.UserDto
import com.talky.mobile.ui.theme.TestSecondary
import com.talky.mobile.ui.theme.VioletClair
import java.util.*

data class Asset(
    val type: String,
    val url: String
)


data class User(
    val id: UUID,
    val displayedName: String,
    val profilePicture: String
)

data class Post(
    val id: UUID,
    val content: String,
    val author: UserDto,
    val assets: List<String>
)

@Composable
fun PostFrame(
    post: PostDto,
    openAsset: (url: String) -> Unit
) {
    Card(
        shape = RoundedCornerShape(12.dp),
        backgroundColor = TestSecondary,
        modifier = Modifier.shadow(
            elevation = 8.dp,
            shape = RoundedCornerShape(12.dp)
        )
    ) {
        Column {
            Column(
                Modifier.padding(16.dp)
            ) {
                PostHeader(post.author!!)

                Text(text = post.content!!)
            }
            AssetGrid(post.assets!!, openAsset)
        }

    }
}

// --- Assets --- //

@Composable
fun AssetGrid(assets: List<String>, onAssetClick: (asset: String) -> Unit) {
    val assetsRows = assets.chunked(3)

    Column(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)

    ) {

        for (assetsRow in assetsRows) {
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(1.dp)
            ) {

                assetsRow.map {
                    Column(
                        modifier = Modifier
                            .background(Color.Gray)
                            .weight(1F)
                            .height(80.dp)
                    ) {
                        AssetComposable(it, onAssetClick)
                    }
                }
            }
        }
    }
}

@Composable
fun AssetComposable(asset: String, onClick: (asset: String) -> Unit, adaptContent: Boolean = true) {
    var contentScale = Fit

    if (adaptContent) {
        contentScale = Crop
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(VioletClair),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AsyncImage(
            model = asset,
            contentScale = contentScale,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .clickable {
                    onClick(asset)
                }
        )
    }


}

// --- Post parts --- //
@Composable
fun PostHeader(author: UserDto) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(8.dp, 8.dp, 16.dp, 8.dp),

        verticalAlignment = Alignment.CenterVertically,

        ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(author.profilePicture)
                .size(100)
                .build(),
            contentScale = Crop,
            contentDescription = null,
            modifier = Modifier.clip(CircleShape),
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = author.displayedName!!,
            fontSize = 20.sp,
        )
    }
}
