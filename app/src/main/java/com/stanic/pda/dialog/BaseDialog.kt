package com.stanic.pda.dialog

import android.content.Context
import android.support.v7.app.AlertDialog

open class BaseDialog(context: Context) : AlertDialog(context) {

        val TEST_IP = "http://192.168.1.222:8083/"//scb
//    val TEST_IP = "http://192.168.1.110:8081/"//cj

    val TEST_PROJECT_CODE = "pdaout/"
    val TEST_BASE_URL = TEST_IP + TEST_PROJECT_CODE

    val IP = "https://www.stanidc.com/zzpt/"
    val PROJECT_CODE = ""
    val BASE_URL = IP
}