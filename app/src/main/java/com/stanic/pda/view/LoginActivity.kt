package com.stanic.pda.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.telecom.TelecomManager
import android.telephony.TelephonyManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.MenuBean
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.SpUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(), MvpView, View.OnClickListener,ActivityCompat.OnRequestPermissionsResultCallback {
    companion object{
        const val REQUEST_READ_PHONE_STATE = 1
    }
    private var projectCode: String? = null
    private var mvpPresenter: MvpPresenter? = null
    private var imei= ""

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_login -> login()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        initView()
    }

    override fun onResume() {
        super.onResume()
        getDeviceId()
    }

    private fun initView() {
        btn_login.setOnClickListener(this)
        val userName = SpUtil.getString(this, SpUtil.USER_NAME)
        if (!TextUtils.isEmpty(userName)) {
            et_username.setText(userName)
        }
        val password = SpUtil.getString(this, SpUtil.PASSWORD)
        if (!TextUtils.isEmpty(password)) {
            et_password.setText(password)
        }
        val projectCode = SpUtil.getString(this, SpUtil.PROJECT_CODE)
        if (!TextUtils.isEmpty(projectCode)) {
            et_pro_code.setText(projectCode)
        }
    }

    override fun showErr(msg: String) {
        super.showErr(msg)
        Toast.makeText(this, "登陆失败", Toast.LENGTH_SHORT).show()
    }

    override fun showData(data: Any) {
        Log.d("httpResponse",data.toString())
        SpUtil.put(this, SpUtil.USER_NAME, et_username.text.toString())
        SpUtil.put(this, SpUtil.PASSWORD, et_password.text.toString())
        SpUtil.put(this, SpUtil.PROJECT_CODE, et_pro_code.text.toString())
        val dataObj = JSONObject.parseObject(data.toString())
        val code = dataObj.getIntValue("code")
        if (code == 1) {
            val menuStr = mvpPresenter?.dealData(data)
            if (TextUtils.isEmpty(menuStr.toString())) {
                Toast.makeText(this, "暂无PDA权限", Toast.LENGTH_SHORT).show()
            } else {
                val menuBeanList = menuStr as ArrayList<MenuBean>
                val intent = Intent(this@LoginActivity, MenuActivity::class.java)
                intent.putExtra("menuBeanList", menuBeanList)
                startActivity(intent)
            }
        } else {
            Toast.makeText(this, dataObj.getString("msg"), Toast.LENGTH_SHORT).show()
        }
    }

    private fun login() {
        if (TextUtils.isEmpty(imei)){
            showToast("未检测到设备号，请检查授权信息")
            return
        }
        val url = "${BASE_URL}pda/login"
        val userName = et_username.text.toString()
        val password = et_password.text.toString()
        projectCode = et_pro_code.text.toString()
        if (TextUtils.isEmpty(userName)) {
            showToast(resources.getString(R.string.empty_user_name))
            return
        }
        if (TextUtils.isEmpty(password)) {
            showToast(resources.getString(R.string.empty_password))
            return
        }
        if (TextUtils.isEmpty(projectCode)) {
            showToast(resources.getString(R.string.empty_pro_code))
            return
        }
        val map = HashMap<Any, Any>()
        map["loginName"] = userName
        map["pwd"] = password
        map["tableprix"] = projectCode!!
        map["imei"] = imei
        mvpPresenter?.getData(map, url)
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            REQUEST_READ_PHONE_STATE -> {
                if ((grantResults.size > 0)&& (grantResults[0] == PackageManager.PERMISSION_GRANTED)){
                    getDeviceId()
                }else{
                    showToast("权限已被拒绝")
                }
            }
        }
    }

    private fun getDeviceId(): String{
        val tm = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        if (null != tm){
            if (ActivityCompat.checkSelfPermission(this,android.Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this,
                    arrayOf(Manifest.permission.READ_PHONE_STATE),
                    REQUEST_READ_PHONE_STATE)
            }else{
                if (tm.deviceId != null){
                    imei = tm.deviceId
                }else{
                    imei = ""
                }
            }
        }
        return imei
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpPresenter?.detachView()
    }

}