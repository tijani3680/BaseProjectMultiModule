package com.base.baseproject.activities.main

import core.views.base.BaseViewModel
import core.views.base.Coordinator
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import javax.inject.Inject

class MainViewModel @Inject constructor(
) : BaseViewModel<MainState, MainAction, MainMutation, MainEffect, Coordinator>() {
    override fun initialState(): MainState = MainState.Initial

    override fun handle(action: MainAction): Flow<MainMutation> = emptyFlow()
}
