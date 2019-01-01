package com.bawei.shaopenglai.network;

import android.text.TextUtils;

/**
 * @author Peng 判断是否为空
 */
public class NonNullUtils {
    private static NonNullUtils instence;
    public NonNullUtils(){

    }
    public static NonNullUtils getInstence(){
        if (instence==null){
            instence=new NonNullUtils();
        }
        return instence;
    }
    public boolean isNotNull(String name,String password){
        return !TextUtils.isEmpty(name)&&!TextUtils.isEmpty(password);
    }
}
