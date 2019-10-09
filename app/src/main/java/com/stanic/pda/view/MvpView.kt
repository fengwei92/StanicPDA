package com.stanic.pda.view

/**
 * @author fengw
 */
interface MvpView : IBaseView {

    /**
     * 请求成功后，调用此接口显示数据
     * @param data 数据源
     */
    fun showData(data: Any)

}
