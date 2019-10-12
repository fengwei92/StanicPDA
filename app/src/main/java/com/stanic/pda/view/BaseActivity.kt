package com.stanic.pda.view

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.stanic.pda.dialog.LoadingDialog

open class BaseActivity : AppCompatActivity(), IBaseView {
    val builder = LoadingDialog.Builder(this)
        .setMessage("加载中...")
        .setCancelable(false)
        .setCancelOutside(false)
    lateinit var dialog : LoadingDialog
    var scanControl = true

    val TEST_IP = "http://192.168.1.222:8083/"//scb
//    val TEST_IP = "http://192.168.1.176:8081/"//xyh
//    val TEST_IP = "http://192.168.1.110:8081/"//cj

    val TEST_PROJECT_CODE = "pdaout/"
    val TEST_BASE_URL = TEST_IP + TEST_PROJECT_CODE

    val IP = "https://www.stanidc.com/zzpt/"
    val PROJECT_CODE = ""
    val BASE_URL = IP

    override val context: Context
        get() = this

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dialog = builder.create()
    }

    override fun showLoading() {
        dialog.show()
    }

    override fun hideLoading() {
        scanControl = true
        dialog.hide()
    }

    override fun showToast(msg: String) {
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun showErr(msg: String) {
        dialog.hide()
        scanControl = true
    }
}
