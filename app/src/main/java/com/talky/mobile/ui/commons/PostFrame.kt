package com.talky.mobile.ui.commons

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.talky.mobile.ui.theme.ComposeSampleTheme
import java.util.*

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale.Companion.Crop
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

import coil.request.ImageRequest
import com.talky.mobile.R
import com.talky.mobile.ui.theme.TestSecondary

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
    val author: User,
    val assets: List<Asset>
)

@Composable
fun PostFrame(
    post: Post
) {
    Card(
        shape = RoundedCornerShape(20.dp),
        backgroundColor = TestSecondary
    ) {
        Column {
            Column(
                Modifier.padding(16.dp)
            ) {
                PostHeader(post.author)

                Text(text = post.content)
            }
            AssetGrid(post.assets)
        }
    }
}

// --- Assets --- //

@Composable
fun AssetGrid(assets: List<Asset>) {
//    val list = (1..5).map { it.toString() }.chunked(3)

    val assetsRows = assets.chunked(3)

    LazyColumn(
        Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(2.dp)

    ) {
        itemsIndexed(assetsRows) { _, assetsRow ->
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
                        Asset(it)
                    }
                }
            }
        }
    }
}

@Composable
fun Asset(asset: Asset) {
    AsyncImage(
        model = asset.url,
        contentScale = Crop,
        contentDescription = null,
    )
}

// --- Post parts --- //
@Composable
fun PostHeader(author: User) {
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

        Text(
            text = author.displayedName,
            fontSize = 20.sp,
        )
    }
}

// --- Preview --- //

@Preview(showBackground = true, backgroundColor = 0x67AAF9FF)
@Composable
fun PostDefaultPreview() {
    ComposeSampleTheme {
        PostFrame(
            Post(
                UUID.randomUUID(),
                "Chupa chups shortbread I love chocolate cake cookie macaroon. I love jelly-o croissant liquorice tart I love. Cookie marzipan I love cake soufflé candy jujubes marzipan powder. I love ice cream pudding I love tiramisu powder.",
                User(
                    UUID.randomUUID(),
                    "John Doe",
                    "https://images.unsplash.com/photo-1535713875002-d1d0cf377fde?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=880&q=80"
                ),
                assets = listOf(
                    Asset("IMAGE", "https://images.unsplash.com/photo-1649649853880-05d01c3dc093?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"),
                    Asset("IMAGE", "https://images.unsplash.com/photo-1649615644692-b26f39484243?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"),
                    Asset("IMAGE", "https://images.unsplash.com/photo-1649452814788-7b88e0b4d16c?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
                    Asset("IMAGE", "https://images.unsplash.com/photo-1649452814987-ece76376762a?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1470&q=80"),
                    Asset("IMAGE", "https://images.unsplash.com/photo-1576078766985-396ea7fb41b5?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"),
                )
            )
        )
    }
}