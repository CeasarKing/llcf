package com.lin.llcf.common.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileUtils {

    public static String readFromInStream(InputStream inputStream) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
        StringBuilder lineBuilder = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            lineBuilder.append(line).append("\r\n");
        }
        return lineBuilder.toString();
    }

}
