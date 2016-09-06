package com.example.basepackagelib.tools;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolUtils {
  
   private static  ExecutorService executorService;
    
    static {  
        executorService = Executors.newCachedThreadPool();
    } 
  
    /** 
     * 从线程池中抽取线程，执行指定的Runnable对象 
     *  
     * @param runnable 
     */  
    public static void execute(Runnable runnable) {  
    	executorService.execute(runnable);  
    }  
}
