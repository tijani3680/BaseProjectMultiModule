package com.base.baseproject.activities.main

import android.os.CountDownTimer

class RingingCountDownTimer(
    private val startMediaPlayer: () -> Unit,
    private val releaseMediaPlayer: () -> Unit,
    millisInFuture: Long,
    countDownInterval: Long
) :
    CountDownTimer(millisInFuture, countDownInterval) {
    override fun onTick(millisUntilFinished: Long) {
        startMediaPlayer.invoke()
    }

    override fun onFinish() {
        releaseMediaPlayer.invoke()
    }
}
