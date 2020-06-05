package com.zl.ThreadSafe;

import java.util.Hashtable;

/**
 * @ClassName : SafeClass
 * @Description :
 * @Author : Zhang Lei
 * @Date: 2020-04-09 11:28
 */

public class SafeClass {

    public static void main(String[] args) {
        Hashtable table = new Hashtable();
        new Thread(()->{
            if( table.get("key") == null) {
                table.put("key", "v1");
            }
        }).start();

        new Thread(()->{
            if( table.get("key") == null) {
                table.put("key", "v2");
            }
        }).start();
    }
}
