package com.stanic.pda.view

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.OutAdapter
import com.stanic.pda.bean.*
import com.stanic.pda.dialog.ChildOrderDialog
import com.stanic.pda.dialog.OrderDialog
import com.stanic.pda.presenter.MvpPresenter
import com.stanic.pda.util.CodeToName
import com.stanic.pda.util.UrlList
import kotlinx.android.synthetic.main.activity_out_by_order.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OutputByOrderActivity : BaseActivity(), MvpView, View.OnClickListener,
    OrderDialog.DialogSelectListener, ChildOrderDialog.DialogSelectListener {
    private lateinit var mReceiver: BroadcastReceiver
    private var orderDialog: OrderDialog? = null
    private var childOrderDialog: ChildOrderDialog? = null
    private var mChildOrderBean: ChildOrderBean.DataBean? = null
    private var mOrderBean: OrderBean.DataBean? = null
    private var outstatus = 0 //1 按垛出库，2 按箱出库，3 按盒出库
    private var mvpPresenter: MvpPresenter? = null
    private val outBeanList = ArrayList<OutBean>()
    private var outAdapter: OutAdapter? = null
    private var currentCode: String = ""

    companion object {
        private const val ADMIN_ID = "e4bf44cd329f43f18f2c48d57e03e3db"
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.tv_parts -> {
                setSelectedColor(0)
                outstatus = 3
            }
            R.id.tv_case -> {
                setSelectedColor(1)
                outstatus = 2
            }
            R.id.tv_support -> {
                setSelectedColor(2)
                outstatus = 1
            }
            R.id.btn_submit -> {
                val code = et_out_code.text.toString().trim()
                out(code)
            }
            R.id.tv_choose_order -> {
                showOrderDialog()
                mChildOrderBean = null
            }
            R.id.tv_choose_child_order -> {
                showChildOrderDialog(mOrderBean)

            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_out_by_order)
        mvpPresenter = MvpPresenter()
        mvpPresenter?.attachView(this)
        scanControl = true
        outBeanList.clear()
        initView()
        initReceiver()
    }

    /**
     * 初始化view
     */
    private fun initView() {
        val angecyId = StanicManager.stanicManager.userAgencyId
        iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
        tv_parts.setTextColor(resources.getColor(R.color.mian_color))
        outstatus = 3
        rv_out.layoutManager = LinearLayoutManager(this)
        tv_parts.setOnClickListener(this)
        btn_submit.setOnClickListener(this)
        tv_case.setOnClickListener(this)
        tv_support.setOnClickListener(this)
        tv_choose_order.setOnClickListener(this)
        tv_choose_child_order.setOnClickListener(this)
        val outPut = intent.getSerializableExtra("outPutStr") as? MenuBean
        val outPutStr = outPut?.nameList
        if (outPutStr!!.contains(CodeToName.partsOutNum.toString())) {
            tv_parts.visibility = View.VISIBLE
            iv_1.visibility = View.VISIBLE
        } else {
            tv_parts.visibility = View.GONE
            iv_1.visibility = View.GONE
        }
        if (outPutStr.contains(CodeToName.boxOutNum.toString())) {
            tv_case.visibility = View.VISIBLE
            iv_2.visibility = View.VISIBLE
        } else {
            tv_case.visibility = View.GONE
            iv_2.visibility = View.GONE
        }
        if (outPutStr.contains(CodeToName.groupOutNum.toString())) {
            tv_support.visibility = View.VISIBLE
            iv_3.visibility = View.VISIBLE
        } else {
            tv_support.visibility = View.GONE
            iv_3.visibility = View.GONE
        }
    }

    /**
     * 获得广播实例
     */
    private fun initReceiver() {
        mReceiver = object : BroadcastReceiver() {
            override fun onReceive(p0: Context?, p1: Intent?) {
                val string = String(p1?.extras?.getByteArray(UrlList.KEY)!!)
                if (scanControl) {
                    scanControl = false
                    et_out_code.setText(string)
                    out(string)
                }
            }
        }
        val filter = IntentFilter()
        filter.addAction(UrlList.FILTER_STR)
        this.registerReceiver(mReceiver, filter)
    }

    /**
     * 根据position 设置下划线颜色
     * @param position
     */
    private fun setSelectedColor(position: Int) {
        cleanAllColor()
        when (position) {
            0 -> {
                iv_1.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_parts.setTextColor(resources.getColor(R.color.mian_color))
            }
            1 -> {
                iv_2.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_case.setTextColor(resources.getColor(R.color.mian_color))
            }
            2 -> {
                iv_3.setBackgroundColor(resources.getColor(R.color.mian_color))
                tv_support.setTextColor(resources.getColor(R.color.mian_color))
            }
        }

    }

    override fun showData(data: Any) {
        val result = data.toString()
        Log.d("httpResponse",result)
        val outBean = JSONObject.parseObject(result, OutBean::class.java)
        if (outBean.code == 1) {
            et_out_code.setText("")
            outBean.pdtName = mChildOrderBean?.pdtname
            outBean.setsCode(currentCode)
            outBeanList.add(0, outBean)
            setAdapter()
        } else {
            Toast.makeText(this, outBean.msg, Toast.LENGTH_SHORT).show()
        }
    }

    /**
     * 出库
     * @param code
     */
    private fun out(code: String) {
        currentCode = code
        if (null == mOrderBean || mOrderBean?.id == null) {
            Toast.makeText(this, "请选择订单", Toast.LENGTH_SHORT).show()
            return
        }
        if (null == mChildOrderBean || mChildOrderBean?.jourid == null) {
            Toast.makeText(this, "请选择子订单", Toast.LENGTH_SHORT).show()
            return
        }

        val url = "${BASE_URL}pdaout/pdaoutbyorder"
        val userId = StanicManager.stanicManager.userId
        val map = HashMap<Any, Any>()

        val projectCode = StanicManager.stanicManager.projectCode!!
        when (outstatus) {
            0 -> {
                Toast.makeText(this, "请选择出库类别", Toast.LENGTH_SHORT).show()
                return
            }
            1 -> map["storecode"] = code
            2 -> map["casecode"] = code
            3 -> map["barcode"] = code
        }
        map["project"] = projectCode
        map["suborderid"] = mChildOrderBean?.jourid!!
        map["userid"] = userId!!
        map["outstatus"] = outstatus
        mvpPresenter?.getData(map, url)
    }

    /**
     * 清除所有颜色
     */
    private fun cleanAllColor() {
        iv_1.setBackgroundColor(resources.getColor(R.color.white))
        iv_2.setBackgroundColor(resources.getColor(R.color.white))
        iv_3.setBackgroundColor(resources.getColor(R.color.white))
        tv_parts.setTextColor(resources.getColor(R.color.text_color))
        tv_case.setTextColor(resources.getColor(R.color.text_color))
        tv_support.setTextColor(resources.getColor(R.color.text_color))
    }

    /**
     * 显示订单列表
     */
    private fun showOrderDialog() {
        orderDialog = OrderDialog(this)
        orderDialog?.setSelectListener(this)
        orderDialog?.show()
    }

    /**
     * 显示子订单列表
     */
    private fun showChildOrderDialog(orderBean: OrderBean.DataBean?) {
        if (orderBean == null || TextUtils.isEmpty(orderBean.id)){
            Toast.makeText(this,"请先选择订单",Toast.LENGTH_SHORT).show()
            return
        }
        childOrderDialog = ChildOrderDialog(this,orderBean)
        childOrderDialog?.setSelectListener(this)
        childOrderDialog?.show()
    }

    /**
     * 设置adapter展示出库结果列表
     */
    private fun setAdapter() {
        outAdapter = OutAdapter(this, outBeanList)
        rv_out.adapter = outAdapter
    }


    /**
     * 子订单回调
     */
    override fun setDailogSelect(childOrderBean: ChildOrderBean.DataBean) {
        this.mChildOrderBean = childOrderBean
        tv_choose_child_order.text = childOrderBean.pdtname
        childOrderDialog?.dismiss()
    }

    /**
     * 订单回调
     */
    override fun onDailogSelect(orderBean: OrderBean.DataBean) {
        this.mOrderBean = orderBean
        tv_choose_order.text = orderBean.ordernum
        tv_choose_child_order.text = "请选择子订单"
        orderDialog?.dismiss()
    }



    override fun onDestroy() {
        super.onDestroy()

    }

}