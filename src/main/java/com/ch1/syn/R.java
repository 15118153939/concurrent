package com.ch1.syn;

/**
 * @author sxylml
 * @Date : 2019/5/13 10:12
 * @Description:
 */
public class R {
    public static void main(String[] args) {
        String str = "<aa><ab>123</ab><ac>456</ac></aa>";
        String regex = "<ac>(.*?)</ac>";
        System.out.println(str.replaceAll(regex, ""));
       String  str2 = "<aa><ab>456</ab><ac>456</ac></aa><aa><ab>789</ab><ac>110</ac></aa>";
        System.out.println(str2);
        System.out.println(str2.replaceAll(regex, ""));

    }
}
