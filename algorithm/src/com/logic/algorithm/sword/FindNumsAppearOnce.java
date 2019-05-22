package com.logic.algorithm.sword;

/**
 * 一个整型数组里除了两个数字之外，其他的数字都出现了两次。请写程序找出这两个只出现一次的数字。
 *
 * @author logic
 * @date 2019/5/22 3:00 PM
 * @since 1.0
 */
public class FindNumsAppearOnce {

    public static void main(String[] args) {
        int[] array = new int[]{2,4,3,6,3,2,5,5};
        int[] num1 = new int[1];
        int[] num2 = new int[1];
        FindNumsAppearOnce(array, num1, num2);
        System.out.println(num1[0]);
        System.out.println(num2[0]);
    }
    //num1,num2分别为长度为1的数组。传出参数
    //将num1[0],num2[0]设置为返回结果
    public static void FindNumsAppearOnce(int[] array, int num1[], int num2[]) {
        int result = 0;
        //先求出来result=x^y
        for (int i = 0; i < array.length; i++) {
            result ^= array[i];
        }

        //找出第一个不相同的位置
        int split = 1;
        for (int i = 0; i < 32; i++) {
            split = split << i;
            if ((result & split) != 0) {
                break;
            }
        }
        //将原数组分成创建两个数组
        for (int k = 0; k < array.length; k++) {
            if ((split & array[k]) == 0) {
                num1[0] ^= array[k];
            } else {
                num2[0] ^= array[k];
            }
        }
        num1[0] ^= result;
        num2[0] ^= result;
    }
}
