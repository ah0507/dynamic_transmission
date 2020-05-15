package net.chensee.util;

public class ConvertUtil {

    public static String convertDirection(String direction) {
        String dir = null;
        switch (direction) {
            case "1":
                dir = "下行";
                break;
            case "0":
                dir = "上行";
                break;
            default:
                break;
        }
        return dir;
    }
}
