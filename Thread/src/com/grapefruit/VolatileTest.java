package com.grapefruit;

/**
 *
 * 指令重排序测试(马士兵老师)
 *
 * @author Grapefruit
 * @version 1.0
 * @ModifyTime 2020/9/29 20:32:03
 */
public class VolatileTest {

    private static int x = 0,y = 0;
    private static int a = 0,b = 0;

    public static void main(String[] args) throws InterruptedException {

        int i = 0;
        for(;;){
            i++;
             x = 0; y = 0;
             a = 0; b = 0;
             Thread one = new Thread(()->{
                 a = 1;
                 x = b;
             });
            Thread other = new Thread(()->{
                b = 1;
                y = a;
            });
            one.start();other.start();
            one.join();other.join();
            String result = "第" + i + "次(" + x + "," + y + ")";
            if(x == 0 & y == 0 ){
                System.out.println(result);
                break;
            }else {
            }
        }
    }
}
