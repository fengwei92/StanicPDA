package com.stanic.pda.view

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import com.stanic.pda.R


/**
 * @author fengw
 */
class MainActivity : BaseActivity(), MvpView {

    private val WHAT_MESSAGE = 0
    private val DELAY_MILLIS = 3000L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        handler.postDelayed({ handler.sendEmptyMessage(WHAT_MESSAGE) },DELAY_MILLIS)
    }

    override fun showData(data: Any) {

    }

    private val handler = @SuppressLint("HandlerLeak")
    object : Handler(){
        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)
            val intent = Intent(this@MainActivity,LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}
