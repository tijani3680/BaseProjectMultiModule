package com.base.baseproject.activities.main

import androidx.navigation.NavController
import core.views.base.Navigator

interface MainNavigator : Navigator {
    fun getNavController(): NavController?

}
