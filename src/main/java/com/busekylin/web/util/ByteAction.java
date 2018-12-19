package com.busekylin.web.util;

public class ByteAction {
    /**
     * 从一个数组中找出另一个数组的位置
     *
     * @param source
     * @param start
     * @param end
     * @param search
     * @return
     */
    public static int byteIndexOf(byte[] source, int start, int end, byte[] search) {


        for (int i = start; i < end; i++) {
            int j = 0;
            int index = i;
            for (; j < search.length && index < end; j++) {
                if (source[index] != search[j])
                    break;

                index++;
            }

            if (j == search.length)
                return i;
        }

        return -1;
    }

    /**
     * 从一个数组中提取另一个数组
     *
     * @param source
     * @param from
     * @param end
     * @return
     */
    public static byte[] subBytes(byte[] source, int from, int end) {
        byte[] result = new byte[end - from];
        System.arraycopy(source, from, result, 0, result.length);

        return result;
    }
}
