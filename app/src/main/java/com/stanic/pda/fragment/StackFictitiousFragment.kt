package com.stanic.pda.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import com.alibaba.fastjson.JSONObject
import com.android.volley.VolleyError
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.adapter.CaseRelationAdapter
import com.stanic.pda.bean.CaseRelationBean
import com.stanic.pda.dialog.TipDialog
import com.stanic.pda.view.RelationStackActivity
import com.stanic.sjty_pda.util.VolleyUtil
import kotlinx.android.synthetic.main.fragment_relation_stack_fictitious.*

class StackFictitiousFragment : BaseFragment(), View.OnClickListener, TipDialog.SelectListener {

    private var stackCode = ""
    private lateinit var tipDialog: TipDialog
    private lateinit var currentOldCode: String
    private val relationBeanList = ArrayList<CaseRelationBean>()
    private lateinit var caseRelationAdapter: CaseRelationAdapter
    private lateinit var rvRelateRes: RecyclerView
    private lateinit var btnRelation: Button
    private lateinit var btnClean: Button

    companion object {
        private const val BE_RELATED = 11 //该盒已与其他箱码关联
        private const val SUCCESS = 1 //关联成功
        private const val SAME_CASE_CODE_RELATED = 10 //箱码已与本盒关联
        val instance: StackFictitiousFragment by lazy(mode = LazyThreadSafetyMode.SYNCHRONIZED) {
            StackFictitiousFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = View.inflate(context, R.layout.fragment_relation_stack_fictitious, null)
        rvRelateRes = view.findViewById(R.id.rv_relation_result)
        rvRelateRes.layoutManager = LinearLayoutManager(activity)
        btnRelation = view.findViewById(R.id.btn_relation_case)
        btnClean = view.findViewById(R.id.btn_clean)
        btnClean.setOnClickListener(this)
        btnRelation.setOnClickListener(this)
        stackCode = System.currentTimeMillis().toString()
        return view
    }


    /**
     * 清空箱码，盒码数据
     */
    fun cleanEdit() {
        et_case_code?.setText("")
        stackCode = System.currentTimeMillis().toString()
    }

    /**
     * 关联
     */
    fun relationCase(caseCode: String = "") {
        val url = "${BASE_URL}pdaout/relationstore"
        val userId = StanicManager.stanicManager.userId
        val projectCode = StanicManager.stanicManager.projectCode!!
        VolleyUtil.get(url, object : VolleyUtil.OnResponse<String> {
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = projectCode
                map["storecode"] = stackCode
                map["casecode"] = caseCode
                map["userid"] = userId!!
            }

            override fun onSuccess(response: String) {
                Log.d("httpResponse = ", response)
                val caseRelationBean = JSONObject.parseObject(response, CaseRelationBean::class.java)
                when (caseRelationBean.code) {
                    BE_RELATED -> {
                        val oldCode = caseRelationBean?.data.toString()
                        if (!TextUtils.isEmpty(oldCode)) {
                            currentOldCode = oldCode
                            showTipDialog(oldCode)
                        }
                    }
                    SAME_CASE_CODE_RELATED -> {
                        Toast.makeText(activity, caseRelationBean.msg, Toast.LENGTH_SHORT).show()
                    }
                    SUCCESS -> {
                        caseRelationBean.stackCode = stackCode
                        caseRelationBean.caseCode = et_case_code.text.toString().trim()
                        et_case_code.setText("")
                        relationBeanList.add(0, caseRelationBean)
                        setAdapter()
                    }
                    else -> Toast.makeText(activity,caseRelationBean.msg,Toast.LENGTH_SHORT).show()
                }
                (activity as RelationStackActivity).scanControl = true
            }

            override fun onError(error: VolleyError) {
                (activity as RelationStackActivity).scanControl = true
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


    /**
     * 覆盖关联
     * @param oldCode
     */
    private fun reRelationCase(oldCode: String) {
        val url = "${BASE_URL}pdaout/relationstore"
        val projectCode = StanicManager.stanicManager.projectCode
        val userId = StanicManager.stanicManager.userId
        val caseCode = et_case_code.text.toString().trim()
        VolleyUtil.get(url, object : VolleyUtil.OnResponse<String> {
            override fun onMap(map: HashMap<String, String>) {
                map["project"] = projectCode!!
                map["storecode"] = stackCode
                map["casecode"] = caseCode
                map["userid"] = userId!!
                map["oldcasecode"] = oldCode
            }

            override fun onSuccess(response: String) {
                Log.d("httpResponse = ", response)
                val caseRelationBean =
                    JSONObject.parseObject(response, CaseRelationBean::class.java)
                if (tipDialog.isShowing) {
                    tipDialog.hide()
                }
                if (caseRelationBean.code == SUCCESS) {
                    caseRelationBean.stackCode = stackCode
                    caseRelationBean.caseCode = et_case_code.text.toString().trim()
                    et_case_code.setText("")
                    relationBeanList.add(0, caseRelationBean)
                    setAdapter()
                }else{
                    Toast.makeText(activity,caseRelationBean.msg,Toast.LENGTH_SHORT).show()
                }
                (activity as RelationStackActivity).scanControl = true
            }

            override fun onError(error: VolleyError) {
                (activity as RelationStackActivity).scanControl = true
                Toast.makeText(activity, "未知错误", Toast.LENGTH_SHORT).show()
                if (tipDialog.isShowing) {
                    tipDialog.hide()
                }
            }

        })
    }

    private fun setAdapter() {
        caseRelationAdapter = CaseRelationAdapter(context!!, relationBeanList)
        rvRelateRes.adapter = caseRelationAdapter
    }

    override fun onSelect(position: Int) {
        if (position == 0) {
            tipDialog.hide()
        } else {
            reRelationCase(currentOldCode)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        stackCode = System.currentTimeMillis().toString()
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.btn_clean -> cleanEdit()

            R.id.btn_relation_case -> {
                val caseCode = et_case_code.text.toString()
                if (TextUtils.isEmpty(caseCode)) {
                    Toast.makeText(activity, "请扫描或输入箱码", Toast.LENGTH_SHORT).show()
                    return
                }
                relationCase(caseCode)
            }
        }
    }
}