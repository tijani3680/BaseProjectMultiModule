package com.base.baseproject.activities.main

import core.views.base.State

sealed interface MainState : State {
    object Initial : MainState
}
