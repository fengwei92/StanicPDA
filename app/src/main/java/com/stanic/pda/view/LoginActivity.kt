package com.stanic.pda.view

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.MenuBean
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.SpUtil
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : BaseActivity(),MvpView , View.OnClickListener{
    private var projectCode : String? = null
    private var mvpPresenter : MvpPresenter? = null

    override fun onClick(v: View?) {
        when(v?.id){
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

    private fun initView(){
        btn_login.setOnClickListener(this)
        val userName = SpUtil.getString(this,SpUtil.USER_NAME)
        if (!TextUtils.isEmpty(userName)){
            et_username.setText(userName)
        }
        val password = SpUtil.getString(this,SpUtil.PASSWORD)
        if (!TextUtils.isEmpty(password)){
            et_password.setText(password)
        }
        val projectCode = SpUtil.getString(this,SpUtil.PROJECT_CODE)
        if (!TextUtils.isEmpty(projectCode)){
            et_pro_code.setText(projectCode)
        }
    }

    override fun showErr(msg: String) {
        super.showErr(msg)
        Toast.makeText(this,"登陆失败",Toast.LENGTH_SHORT).show()
    }

    override fun showData(data: Any) {
        SpUtil.put(this,SpUtil.USER_NAME,et_username.text.toString())
        SpUtil.put(this,SpUtil.PASSWORD,et_password.text.toString())
        SpUtil.put(this,SpUtil.PROJECT_CODE,et_pro_code.text.toString())
        StanicManager.stanicManager.projectCode = projectCode
        val dataObj = JSONObject.parseObject(data.toString())
        val code = dataObj.getIntValue("code")
        if (code == 1){
            val menuBeanList = mvpPresenter?.dealData(data) as ArrayList<MenuBean>
            val intent = Intent(this@LoginActivity,MenuActivity::class.java)
            intent.putExtra("menuBeanList",menuBeanList)
            startActivity(intent)
        }else{
            Toast.makeText(this,dataObj.getString("msg"),Toast.LENGTH_SHORT).show()
        }
    }

    private fun login(){
        val url = "${BASE_URL}pda/login"
        val userName = et_username.text.toString()
        val password = et_password.text.toString()
        projectCode = et_pro_code.text.toString()
        if (TextUtils.isEmpty(userName)){
            showToast(resources.getString(R.string.empty_user_name))
            return
        }
        if (TextUtils.isEmpty(password)){
            showToast(resources.getString(R.string.empty_password))
            return
        }
        if (TextUtils.isEmpty(projectCode)){
            showToast(resources.getString(R.string.empty_pro_code))
            return
        }
        val map = HashMap<Any,Any>()
        map["loginName"] = userName
        map["pwd"] = password
        map["tableprix"] = projectCode!!
        mvpPresenter?.getData(map,url)
    }

    override fun onDestroy() {
        super.onDestroy()
        mvpPresenter?.detachView()
    }

}