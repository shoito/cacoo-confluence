package com.github.shoito.confluence.integration.cacoo.util;

import java.net.HttpURLConnection;
import java.net.URL;

public class DiagramImageUtil {
    public static boolean validateUrl(String diagramImageUrl) {
        try {
            URL url = new URL(diagramImageUrl);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();
            connection.setInstanceFollowRedirects(false);
            return connection.getResponseCode() ==  HttpURLConnection.HTTP_OK;
        } catch (Exception e) {
            return false;
        }
    }
}
