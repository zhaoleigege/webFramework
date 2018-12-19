package com.busekylin.web.util.memoryupload;

import com.busekylin.web.server.http.request.MultiPartParse;
import com.busekylin.web.server.http.request.Part;
import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.busekylin.web.util.ByteAction.byteIndexOf;

public class MemoryMultiPartParse implements MultiPartParse {
    private final String LINE_BREAK = "\r\n";

    private boolean parsed;
    private Map<String, Part> paramMap;

    private InputStream inputStream;
    private String delimiter;

    public MemoryMultiPartParse(InputStream inputStream, String boundary) {
        this.inputStream = inputStream;
        this.delimiter = "--" + boundary;
        this.parsed = false;
    }

    @Override
    public Map<String, Part> getParams() throws IOException {
        if (parsed)
            return paramMap;

        paramMap = new HashMap<>();

        byte[] delimiterBytes = delimiter.getBytes(Charset.forName("UTF-8"));
        byte[] buffer = inputStream2Array();

        int delimiterLength = delimiterBytes.length;
        List<Integer> indexes = new ArrayList<>();

        while (true) {
            int offset = indexes.size() > 0 ? indexes.get(indexes.size() - 1) + delimiterLength : 0;

            int index = byteIndexOf(buffer, offset, buffer.length, delimiterBytes);

            if (index == -1)
                break;

            indexes.add(index);
        }

        int lineBreakSize = LINE_BREAK.getBytes(Charset.forName("UTF-8")).length;

        for (int i = 0; i < indexes.size() - 1; i++) {
            Part part = parseContent(buffer, indexes.get(i) + delimiterLength + lineBreakSize, indexes.get(i + 1) - lineBreakSize);

            paramMap.put(part.getName(), part);
        }

        parsed = true;
        return paramMap;
    }

    private Part parseContent(byte[] buffer, int start, int end) {
        MemoryPart part = new MemoryPart();
        String noFileEnding = "\"" + LINE_BREAK + LINE_BREAK;
        String fileEnding = LINE_BREAK + LINE_BREAK;

        String[] tokens = new String[]{
                "; name=\"",
                noFileEnding,
                "\"; filename=\"",
                "Content-Type: ",
                fileEnding
        };

        int[] indexes = new int[5];

        for (int i = 0; i < indexes.length; i++)
            indexes[i] = byteIndexOf(buffer, start, end, tokens[i].getBytes(Charset.forName("UTF-8")));

        if (indexes[2] != -1) {
            int nameStartIndex = indexes[0] + tokens[0].getBytes(Charset.forName("UTF-8")).length;
            int vFileStartLength = "\"; filename=\"".getBytes(Charset.forName("UTF-8")).length;
            int fnStartIndex = indexes[2] + vFileStartLength;
            int ctStartIndex = indexes[3] + "Content-Type: ".getBytes(Charset.forName("UTF-8")).length;
            int fileStartIndex = indexes[4] + fileEnding.getBytes(Charset.forName("UTF-8")).length;

            part.setName(new String(buffer, nameStartIndex, indexes[2] - nameStartIndex));
            part.setFileName(new String(buffer, fnStartIndex, indexes[3] - 1 - LINE_BREAK.getBytes(Charset.forName("UTF-8")).length - fnStartIndex));
            part.setContentType(new String(buffer, ctStartIndex, indexes[4] - ctStartIndex));
            part.setFile(new ByteArrayInputStream(buffer, fileStartIndex, end - fileStartIndex));
        } else {
            int nameStartIndex = indexes[0] + tokens[0].getBytes(Charset.forName("UTF-8")).length;
            int vStartLength = noFileEnding.getBytes(Charset.forName("UTF-8")).length;

            part.setName(new String(buffer, nameStartIndex, indexes[1] - nameStartIndex));
            part.setValue(new String(buffer, indexes[1] + vStartLength, end - indexes[1] - vStartLength));
        }

        return part;
    }

    private byte[] inputStream2Array() throws IOException {
        ByteOutputStream byteOutputStream = new ByteOutputStream();

        int hasRead;
        byte[] readByte = new byte[1024];
        while ((hasRead = inputStream.read(readByte)) != -1)
            byteOutputStream.write(readByte, 0, hasRead);

        return byteOutputStream.getBytes();
    }
}
