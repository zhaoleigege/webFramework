package com.busekylin.web.util.fileupload;

import com.busekylin.web.server.http.request.Part;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.*;

public class MultiPartStream {
    private InputStream stream;
    private boolean parsed;
    private String delimiter;

    private Map<String, Collection<Part>> params;

    public MultiPartStream(String boundary, InputStream stream) {
        this.stream = stream;
        this.delimiter = "\r\n--" + boundary;
        this.parsed = false;
        this.params = new HashMap<>();
    }


    private void multiPartParse(InputStream stream, String boundary) throws IOException {
        if (parsed)
            return;

        List<Part> parts = new ArrayList<>();
        boolean next = false;
        int nextIndex = 0;

        byte[] buffer = new byte[2 * 1024];
        byte[] delimiterByte = delimiter.getBytes(Charset.forName("UTF-8"));

        int hasRead;
        int offset = 0;
        int length = buffer.length;
        int pointer = 0;

        while (true) {
            hasRead = stream.read(buffer, offset, length);
            if (hasRead == -1 && offset == 0)
                break;

            int index = delimiterIndex(buffer, pointer, offset, delimiterByte);

            if (index == -1) {
                System.out.println("数据过长，缓存不足需要存储到磁盘中");
            } else if (index < (hasRead + offset)) {

            } else {

            }
        }

    }

    public Part parseContent(InputStream stream) throws IOException {
        FilePart part = new FilePart();
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        String line = reader.readLine();
        int[] indexes = new int[2];
        String[] tokens = new String[]{
                "name=\"",
                "\"; filename=\""
        };

        for (int i = 0; i < indexes.length; i++) {
            indexes[i] = line.indexOf(tokens[i]);
        }

        if (indexes[1] != -1) {
            part.setName(line.substring(indexes[0], indexes[1]));
            line = reader.readLine();
            part.setFileName(line.substring(line.indexOf("Content-Type: ")));
            reader.readLine();

//            Input
        } else {
            part.setName(line.substring(indexes[0], line.length() - 1));
        }

        return null;
    }

    /**
     * 返回在source数组中匹配的search数组开始的索引
     * 如果source数组匹配到末尾但是search数组还未匹配完，则返回length加上search已匹配到的索引
     *
     * @param source
     * @param offset
     * @param length
     * @param search
     * @return
     */
    private int delimiterIndex(byte[] source, int offset, int length, byte[] search) {
        int index = -1;

        int j = -1;
        for (int i = offset; i < length; i++) {
            int sIndex = i;
            j = 0;
            for (; j < search.length && sIndex < length; j++) {
                if (source[i + j] != search[j])
                    break;
                else
                    sIndex++;
            }

            if (j == search.length)
                return sIndex;
        }

        if (j != 0)
            return length + j;

        return index;
    }
}
