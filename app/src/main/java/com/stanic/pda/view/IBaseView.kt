package com.stanic.pda.view

import android.content.Context

/**
 * @author fengw
 */
interface IBaseView {


    /**
     * 获取上下文
     * @return 上下文
     */
    val context: Context

    /**
     * 显示正再加载view
     */
    fun showLoading()

    /**
     * 关闭正在加载view
     */
    fun hideLoading()

    /**
     * 显示提示
     * @param msg
     */
    fun showToast(msg: String)


    /**
     * 显示请求错误提示
     */
    fun showErr(msg: String)

}
