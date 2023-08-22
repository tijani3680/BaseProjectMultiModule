package com.base.baseproject.activities.main

import core.views.base.Action
import java.net.URI

sealed interface MainAction : Action<MainState> {
    data class ReactionToIncomingCall(
        val acceptCall: Boolean,
        val partnerId: Int,
        val callId: Long,
        val callerAvatar: URI?,
        val callerName: String,
        val mediaDomain: String
    ) : MainAction
}
