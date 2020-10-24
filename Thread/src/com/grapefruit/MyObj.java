package com.grapefruit;

/**
 * @author Grapefruit
 * @version 1.0
 * @ModifyTime 2020/9/29 21:00:36
 */
public class MyObj {
    public static void main(String[] args) {
        Object o = new Object();

        //show how JVM do   --> javap -c MyObj.class
        /**
         *  0: new           #2                  // class java/lang/Object
         *  3: dup
         *  4: invokespecial #1                  // Method java/lang/Object."<init>":()V
         *  7: astore_1
         *   8: return
         */

        //the useful command (命令4或7容易造成指令重排序,也就是说可能引用赋值了,但对象的属性可能还没初始化)
        /**
         *  0: new           半初始化（开辟/申请内存空间）
         *  4: invokespecial 调用init方法（构造方法/初始化方法）
         *  7: astore_1      引用赋值（建立关联）
         */
    }
}
