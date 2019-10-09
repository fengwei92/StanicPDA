package com.stanic.pda.presenter


import com.stanic.pda.view.IBaseView
import android.icu.lang.UCharacter.GraphemeClusterBreak.V



/**
 * @author fengw
 * @param <V>
</V> */
open class BasePresenter<V : IBaseView> {

    /**
     * 绑定的view
     */


    /**
     * 获取连接的view
     */
    var attachedView: V? = null
        private set


    /**
     * 是否与view建立连接
     * 每次调用业务请求要先调用该方法
     */
    val isViewAttached: Boolean
        get() = attachedView != null


    /**
     * 绑定view，初始化中调用该方法
     */

    fun attachView(mvpView: V) {
        this.attachedView = mvpView
    }


    /**
     * 断开view，一般在onDestroy中调用
     */
    fun detachView() {
        this.attachedView = null
    }



}
