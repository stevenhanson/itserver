package com.example.itserver.utils;

import java.io.BufferedReader;
import java.io.FileReader;

public class FileUtil {

    public static String getLicense() {
        FileReader fr = null;
        BufferedReader br = null;
        String line = null;
        try {
            fr = new FileReader("c://1.txt");
            br = new BufferedReader(fr);
            line = br.readLine();
        } catch (Exception e) {
            return null;
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception e1) {
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (Exception e1) {
                }
            }
        }

        return line;
    }
}
