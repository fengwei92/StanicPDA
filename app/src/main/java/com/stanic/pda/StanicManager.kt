package com.stanic.pda

import com.alibaba.fastjson.JSONObject

/**
 * @author fengw
 */
class StanicManager {
    /*当前语言*/
    var currentLanguage: String? = null
    /*用户id*/
    var userId: String? = null
    /*菜单集合*/
    var menuList : List<String>? = null
    /*菜单内容*/
    var menuObject : JSONObject? = null
    /*用户信息*/
    var userAgencyId : String? = null
    /*项目名*/
    var projectCode : String? = null

    var order: Int? = null //1有订单 0无订单

    companion object {
        /*StanicManager实例*/
        private var instance: StanicManager? = null

        val stanicManager: StanicManager
            get() {
                if (instance == null) {
                    instance = StanicManager()
                }
                return instance!!
            }
    }
}
