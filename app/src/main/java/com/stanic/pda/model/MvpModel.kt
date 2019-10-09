package com.stanic.pda.model

import com.alibaba.fastjson.JSONObject
import com.android.volley.RequestQueue
import com.android.volley.Response
import com.stanic.csy.MyStringRequest
import com.stanic.pda.MyApplication
import com.stanic.pda.R
import com.stanic.pda.StanicManager
import com.stanic.pda.bean.MenuBean
import com.stanic.pda.callback.Callback
import com.stanic.pda.util.CodeToName
import com.stanic.pda.util.UrlList
import com.stanic.pda.util.VolleyTool
import java.io.IOException


class MvpModel {
    private var mRequestQueue: RequestQueue? = null
    private val RESPONSE_SUCCESS = 1

    /**
     * 获取网络接口数据
     * @param url 请求接口
     * @param params 请求参数
     * @param callback 数据回调接口
     */

    fun getNetData(url: String, params: Map<Any, Any>, callback: Callback) {
        var urlStr = url
        val param = prepareParam(params)
        if (param.trim().isNotEmpty()) {
            urlStr += "?$param"
        }

        mRequestQueue = VolleyTool.getInstance(MyApplication.getContext()).requestQueue
        val stringRequest = MyStringRequest(urlStr, Response.Listener { response ->
            callback.onComplete()
            callback.onSuccess(response)

        }, Response.ErrorListener {
            callback.onError(it.toString())

        })
        mRequestQueue?.add(stringRequest)

    }


    private fun prepareParam(map: Map<Any, Any>): String {
        val sb = StringBuilder()
        return if (map.isEmpty()) {
            ""
        } else {
            for (key in map.keys) {
                val value = map[key]
                if (sb.isEmpty()) {
                    sb.append(key).append("=").append(value)
                } else {
                    sb.append("&").append(key).append("=").append(value)
                }
            }
            sb.toString()
        }

    }

    /**
     * 解析json数据获取result值
     * @return 1：成功 2：项目编码错误 3：用户名或密码错误 4：后台错误
     */

    private fun getResult(response: String): Int {
        val jsonObject = JSONObject.parseObject(response)
        return jsonObject.getIntValue(UrlList.resultKey)
    }

    @Throws(IOException::class)
    fun dealData(a: Any): Any {
        val jsonObject = JSONObject.parseObject(a.toString())
        val userObject = jsonObject.getJSONObject("user")
        val userId = userObject?.getString("id")
        val userAgencyId = userObject?.getString("agencyid")
        StanicManager.stanicManager.userId = userId
        StanicManager.stanicManager.userAgencyId = userAgencyId
        val menu = jsonObject.getString("catalogids")
        val obj = JSONObject.parseObject(menu)
        val keyListStr = getAllKeys(obj).toString()
        val keyList = keyListStr.split(",").sorted()
        val menuBeanList = ArrayList<MenuBean>()
        for (i in keyList.iterator()) {
            val menuBean = MenuBean()
            menuBean.name = CodeToName.getMenuName(i.toInt())
            val childNameList = obj.getJSONArray(i).toString()
            menuBean.nameList = childNameList
            when (CodeToName.getMenuName(i.toInt())) {
                CodeToName.outPut -> menuBean.logo = R.mipmap.out_put
                CodeToName.groupBox -> menuBean.logo = R.mipmap.group_box
                CodeToName.addMark -> menuBean.logo = R.mipmap.add_tag
                CodeToName.cancelOut -> menuBean.logo = R.mipmap.cancel_out
                CodeToName.dayOutStorage -> menuBean.logo = R.mipmap.day_out
                CodeToName.returnOut -> menuBean.logo = R.mipmap.return_back
                CodeToName.pdtInquiry -> menuBean.logo = R.mipmap.pdt_req
                CodeToName.groupSupport -> menuBean.logo = R.mipmap.group_support
                CodeToName.cancelRelationCase -> menuBean.logo = R.mipmap.cancel_relation_case
                CodeToName.cancelRelationStack -> menuBean.logo = R.mipmap.cancel_relation_stack
            }
            menuBeanList.add(menuBean)
        }
        return menuBeanList

    }

    private fun getAllKeys(obj: JSONObject): StringBuffer {
        val stringBuffer = StringBuffer()
        val keys = obj.keys.iterator()
        while (keys.hasNext()) {
            val key = keys.next()
            stringBuffer.append(key.toString()).append(",")
        }
        try {
            stringBuffer.deleteCharAt(stringBuffer.length - 1)
        } catch (e: Exception) {

        }

        return stringBuffer
    }

}
