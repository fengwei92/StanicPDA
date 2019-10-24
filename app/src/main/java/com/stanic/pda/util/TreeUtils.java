package com.stanic.pda.util;

import android.util.Log;


import com.stanic.pda.bean.TreePoint;

import java.util.HashMap;

/**
 * Created by xulc on 2018/7/27.
 */

public class TreeUtils {
    //第一级别为0
    public static int getLevel(TreePoint treePoint, HashMap<String,TreePoint> map){
        if("root".equals(treePoint.getPARENTID())){
            return 0;
        }else{
            return 1+getLevel(getTreePoint(treePoint.getPARENTID(),map),map);
        }
    }



    public static TreePoint getTreePoint(String ID, HashMap<String,TreePoint> map){
        if(map.containsKey(ID)){
            return map.get(ID);
        }
        Log.e("xlc","ID:" + ID);
        return null;
    }
}
