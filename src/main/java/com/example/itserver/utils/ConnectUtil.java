package com.example.itserver.utils;

import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class ConnectUtil {

    public static HttpURLConnection getPostHttpConn(String url) throws Exception {
        return getHttpConn(url, "POST");
    }

    public static HttpURLConnection getGetHttpConn(String url) throws Exception {
        return getHttpConn(url, "GET");
    }

    private static HttpURLConnection getHttpConn(String url, String method) throws Exception {
        URL object = new URL(url);
        HttpURLConnection conn;
        conn = (HttpURLConnection) object.openConnection();
        conn.setDoOutput(true);
        conn.setDoInput(true);
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestMethod(method);
        return conn;
    }

    public static List<Map<String, String>> getDeviceList(String url) throws Exception {
        HttpURLConnection conn = getPostHttpConn(url);
        OutputStreamWriter wr = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
        wr.write("{}");
        wr.flush();

        // 显示 POST 请求返回的内容
        StringBuilder sb = new StringBuilder();
        int httpRspCode = conn.getResponseCode();
        if (httpRspCode == HttpURLConnection.HTTP_OK) {
            BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
            String line = null;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
            JSONObject jsonInfo= JSONObject.fromObject(sb);
        } else {

        }

        return null;
    }
}
