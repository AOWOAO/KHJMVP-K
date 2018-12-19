package com.xhkj.khjmvp.activity

import android.app.ActivityOptions
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.Window
import android.view.WindowManager
import com.qmuiteam.qmui.widget.roundwidget.QMUIRoundButton
import com.xhkj.khjmvp.R
import com.xhkj.khjmvp.mvp.login.LoginActivity
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /*隐藏状态栏*/
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN)

        setContentView(R.layout.activity_start)
        val cdt = MyCountDownTimer(4000, 1000, adJump)
        cdt.start()

        adJump.setOnClickListener {
            toNext(cdt)
        }

        adImg.setOnClickListener{
            toNext(cdt)
        }
    }

    private fun toNext(cdt:MyCountDownTimer) {
        cdt.cancel()
        val intent = Intent(this, LoginActivity::class.java)
        startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle())
        finishAfterTransition()
    }


    private inner class MyCountDownTimer(millisInFuture: Long, countDownInterval: Long, internal var bt: QMUIRoundButton) : CountDownTimer(millisInFuture, countDownInterval) {

        override fun onFinish() {
            val intent = Intent(this@StartActivity, LoginActivity::class.java)
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this@StartActivity).toBundle())
            finishAfterTransition()
        }
        override fun onTick(millisUntilFinished: Long) {
            bt.text = "  ${millisUntilFinished/1000}  "
        }
    }
}
