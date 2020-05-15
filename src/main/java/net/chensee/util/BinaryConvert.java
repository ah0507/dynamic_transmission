package net.chensee.util;

public class BinaryConvert {

    /**
     * 将int转为指定位数的二进制字符串
     * @param num
     * @return
     */
    public static String toBinary(int num) {
        int digits = 6;
        int value = 1 << digits | num;
        //0x20 | 这个是为了保证这个string长度是6位数
        String bs = Integer.toBinaryString(value);
        return  bs.substring(1);
    }
}
