package com.stanic.pda.util

import java.lang.Exception
import kotlin.concurrent.thread

/**
 * @author fengw
 */
object CodeToName {
    //5001
    val groupBox = "组箱"
    val groupBoxNum = 5001
    //5002
    val groupSupport = "组托"
    val groupSupportNum = 5002
    //5003
    val outPut = "出库"
    val outPutNum = 5003
    //500301
    val partsOut = "散件出库"
    val partsOutNum = 500301
    //500302
    val boxOut = "箱出库"
    val boxOutNum = 500302
    //500303
    val groupOut = "托出库"
    val groupOutNum = 500303
    //5004
    val cancelOut = "取消出库"
    val cancelOutNum = 5004
    //500401
    val cancelPartsOut = "散件取消出库"
    val cancelPartsOutNum = 500401
    //500402
    val cancelBoxOut = "箱取消出库"
    val cancelBoxOutNum = 500402
    //500403
    val cancelSupportOut = "托取消出库"
    val cancelSupportOutNum = 500403
    //5005
    val returnOut = "退货入库"
    val returnOutNum = 5005
    //500501
    val returnPartsOut = "散件退货入库"
    val returnPartsOutNum = 500501
    //500502
    val returnBoxOut = "箱退货入库"
    val returnBoxOutNum = 500502
    //500503
    val returnSupportOut = "托退货入库"
    val returnSupportOutNum = 500503
    //5006
    val addMark = "补标"
    val addMarkNum = 5006
    //5007
    val pdtInquiry = "产品查询"
    val pdtInquiryNum = 5007
    //5008
    val dayOutStorage = "当日出库统计"
    val dayOutStorageNum = 5008
    //5009
    val cancelRelationCase = "取消组箱"
    val cancelRelationCaseNum = 5009
    //5010
    val cancelRelationStack = "取消组托"
    val cancelRelationStackNum = 5010

    /**
     * 编码对照表
     */
    fun getMenuName(code : Int) : String{
        when (code){
            groupBoxNum -> return groupBox
            groupSupportNum -> return groupSupport
            outPutNum -> return outPut
            partsOutNum -> return partsOut
            boxOutNum -> return boxOut
            groupOutNum -> return groupOut
            cancelOutNum -> return cancelOut
            cancelPartsOutNum -> return cancelPartsOut
            cancelBoxOutNum -> return cancelBoxOut
            cancelSupportOutNum -> return cancelSupportOut
            returnOutNum -> return returnOut
            returnPartsOutNum -> return returnPartsOut
            returnBoxOutNum -> return returnBoxOut
            returnSupportOutNum -> return returnSupportOut
            addMarkNum -> return addMark
            pdtInquiryNum -> return pdtInquiry
            dayOutStorageNum -> return dayOutStorage
            cancelRelationCaseNum -> return cancelRelationCase
            cancelRelationStackNum -> return cancelRelationStack
        }
        return ""
    }

    fun nameToCode(name : String):Int{
        when(name){
            groupBox -> return groupBoxNum
            groupSupport -> return groupSupportNum
            outPut -> return outPutNum
            partsOut -> return partsOutNum
            boxOut -> return boxOutNum
            groupOut -> return groupOutNum
            cancelOut -> return cancelOutNum
            cancelPartsOut -> return cancelPartsOutNum
            cancelBoxOut -> return cancelBoxOutNum
            cancelSupportOut -> return cancelSupportOutNum
            returnOut -> return returnOutNum
            returnPartsOut -> return returnPartsOutNum
            returnBoxOut -> return returnBoxOutNum
            returnSupportOut -> return returnSupportOutNum
            addMark -> return addMarkNum
            pdtInquiry -> return pdtInquiryNum
            dayOutStorage -> return dayOutStorageNum
            cancelRelationCase -> return cancelRelationCaseNum
            cancelRelationStack -> return cancelRelationStackNum
        }
        return 0
    }


    fun codeToStr(list:List<String>) : List<String>{
        val listMenu = ArrayList<String>()
        try {
            for (i in list.listIterator()){
                val str = getMenuName(i.toInt())
                listMenu.add(str)
            }
        }catch (e:Exception){

        }
        return listMenu
    }



}