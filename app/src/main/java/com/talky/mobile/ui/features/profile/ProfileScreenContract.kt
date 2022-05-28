package com.talky.mobile.ui.features.profile

import com.talky.mobile.api.models.UserDto

class ProfileScreenContract {

    data class State(
        val profile: UserDto?,
    )
}