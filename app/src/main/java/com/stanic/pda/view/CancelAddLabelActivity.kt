package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_add_label.*
import kotlinx.android.synthetic.main.activity_cancel_add_lable.*
import kotlinx.android.synthetic.main.activity_cancel_add_lable.et_box_code

class CancelAddLabelActivity : BaseActivity() , MvpView ,View.OnClickListener {

    private lateinit var mReceiver: BroadcastReceiver
    private var mvpPresenter : MvpPresenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cancel_add_lable)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        initView()
        initReceiver()
        setClickListener()

    }

    private fun initView(){
        rv_cancel_add_label_result.layoutManager = LinearLayoutManager(this)
    }

    private fun setClickListener(){
        btn_cancel_add_label.setOnClickListener(this)
    }

    override fun showData(data: Any) {

    }

    private fun initReceiver(){
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                    cancelAddLabel(string)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }

    override fun onClick(p0: View?) {
        when(p0?.id){
            R.id.rv_cancel_add_label_result -> {
                val boxCode = et_box_code.text.toString()
                cancelAddLabel(boxCode)
            }
        }
    }

    private fun cancelAddLabel(code: String){
        if (TextUtils.isEmpty(code)){
            Toast.makeText(this,"盒码不能为空", Toast.LENGTH_SHORT).show()
            return
        }
        val url = "${BASE_URL}pdaout/cancellabel"
        val map = HashMap<Any,Any>()
        map["project"] = StanicManager.stanicManager.projectCode!!
        map["barcode"] = code
        map["userid"] = StanicManager.stanicManager.userId!!
        mvpPresenter?.getData(map,url)
    }
}