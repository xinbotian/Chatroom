package com.demo.chat5;

import java.io.Closeable;

public class ChatUtils {
    /**
     * release
     */
    public static void close(Closeable... targets){
        for(Closeable target:targets){
            try{
                if(null!=target){
                    target.close();
                }
            }catch (Exception e){
        }
    }
}
}
