package com.stanic.pda.fragment

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.BoxRelationAdapter
import com.stanic.pda.bean.BoxRelationBean
import com.stanic.pda.dialog.TipDialog
import com.stanic.pda.util.UrlList
import com.stanic.pda.view.RelationCaseActivity
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.fragment_relation_fictitious.*
import java.lang.Exception

class FictitiousFragment : BaseFragment(), View.OnClickListener, TipDialog.SelectListener {

    private var caseCode = ""
    private lateinit var tipDialog: TipDialog
    private lateinit var currentBarCode: String
    private lateinit var currentOldCode: String
    private val relationBeanList = ArrayList<BoxRelationBean>()
    private lateinit var boxRelationAdapter: BoxRelationAdapter
    private lateinit var rvRelateRes: RecyclerView

    companion object {
        private const val BE_RELATED = 11 //该盒已与其他箱码关联
        private const val SUCCESS = 1 //关联成功
        private const val SAME_CASE_CODE_RELATED = 10 //箱码已与本盒关联
        val instance: FictitiousFragment by lazy (mode = LazyThreadSafetyMode.SYNCHRONIZED){
            FictitiousFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        caseCode = System.currentTimeMillis().toString()
        initData()
        val view = View.inflate(context, R.layout.fragment_relation_fictitious, null)
        rvRelateRes = view.findViewById(R.id.rv_box_relate_res)
        rvRelateRes.layoutManager = LinearLayoutManager(context)
        return view
    }

    private fun initData() {
        relationBeanList.clear()
    }

    /**
     * 清空盒码数据
     */
    private fun cleanEdit() {
        et_box_code.setText("")
        caseCode = System.currentTimeMillis().toString()
    }

    /**
     * 关联
     */
    @Throws(Exception::class)
     fun relationCase(barCode: String) {
        currentBarCode = barCode
        val url = "${BASE_URL}pdaout/relationcase"
        val userId = StanicManager.stanicManager.userId
        val projectCode = StanicManager.stanicManager.projectCode
        VolleyUtil.get(url, object : VolleyUtil.OnResponse<String> {
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = projectCode!!
                map["barcode"] = barCode
                map["casecode"] = caseCode
                map["userid"] = userId!!
            }

            override fun onSuccess(response: String) {
                Log.d("httpResponse = ", response)
                val boxRelationBean = JSONObject.parseObject(response, BoxRelationBean::class.java)
                when (boxRelationBean.code) {
                    BE_RELATED -> {
                        val oldCode = boxRelationBean?.data.toString()
                        if (!TextUtils.isEmpty(oldCode)) {
                            currentOldCode = oldCode
                            showTipDialog(oldCode)
                        }
                    }
                    SAME_CASE_CODE_RELATED -> {
                        Toast.makeText(activity, boxRelationBean.msg, Toast.LENGTH_SHORT).show()
                    }
                    SUCCESS -> {
                        boxRelationBean.caseCode = caseCode
                        relationBeanList.add(0, boxRelationBean)
                        setAdapter()
                    }
                    else -> Toast.makeText(activity, boxRelationBean.msg, Toast.LENGTH_SHORT).show()
                }
                (activity as RelationCaseActivity).scanControl = true
            }

            override fun onError(error: VolleyError) {
                (activity as RelationCaseActivity).scanControl = true
                Toast.makeText(activity, error.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

    /**
     * 覆盖关联
     * @param oldCode
     */
    private fun reRelationCase(oldCode: String) {
        val url = "${BASE_URL}pdaout/relationcase"
        val projectCode = StanicManager.stanicManager.projectCode
        val userId = StanicManager.stanicManager.userId
        VolleyUtil.get(url, object : VolleyUtil.OnResponse<String> {
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = projectCode!!
                map["barcode"] = currentBarCode
                map["casecode"] = caseCode
                map["userid"] = userId!!
                map["oldcasecode"] = oldCode
            }

            override fun onSuccess(response: String) {
                Log.d("httpResponse = ", response)
                val boxRelationBean =
                    JSONObject.parseObject(response, BoxRelationBean::class.java)
                if (tipDialog.isShowing) {
                    tipDialog.hide()
                }
                if (boxRelationBean.code == SUCCESS) {
                    boxRelationBean.caseCode = caseCode
                    relationBeanList.add(0, boxRelationBean)
                    setAdapter()
                }else{
                    Toast.makeText(activity, boxRelationBean.msg, Toast.LENGTH_SHORT).show()
                }
                (activity as RelationCaseActivity).scanControl = true
            }

            override fun onError(error: VolleyError) {
                (activity as RelationCaseActivity).scanControl = true
                Toast.makeText(activity, "未知错误", Toast.LENGTH_SHORT).show()
                if (tipDialog.isShowing) {
                    tipDialog.hide()
                }
            }

        })
    }

    /**
     * 展示提示dialog
     * @param oldCode
     */
    private fun showTipDialog(oldCode: String) {
        val builder = TipDialog.Builder(context!!)
        builder.setMessage("该盒已和箱码[${oldCode}]关联过,是否重新关联？")
        builder.setSelectListener(this)
        tipDialog = builder.create()
        tipDialog.show()
    }




    private fun setAdapter() {
        boxRelationAdapter = BoxRelationAdapter(context!!, relationBeanList)
        rvRelateRes.adapter = boxRelationAdapter
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_clean -> cleanEdit()

            R.id.btn_relation_case -> {
                val barCode = et_box_code.text.toString()
                if (TextUtils.isEmpty(barCode)) {
                    Toast.makeText(activity, "请输入盒码", Toast.LENGTH_SHORT).show()
                    return
                }
                relationCase(barCode)
            }
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        caseCode = System.currentTimeMillis().toString()
    }

    override fun onSelect(position: Int) {
        if (position == 0) {
            tipDialog.hide()
        } else {
            reRelationCase(currentOldCode)
        }
    }

}