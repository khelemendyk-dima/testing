package com.my.testing.utils;

import com.my.testing.exceptions.CaptchaException;
import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLException;
import java.io.*;
import java.net.ProtocolException;
import java.net.URL;
import java.net.UnknownHostException;
import java.util.Properties;

public class CaptchaUtil {
    private static final Logger logger = LogManager.getLogger(CaptchaUtil.class);
    private final String method;
    private final String captchaUrl;
    private final String secret;
    private final String userAgent;
    private final String acceptLanguage;

    public CaptchaUtil(Properties properties) {
        method = properties.getProperty("captcha.method");
        captchaUrl = properties.getProperty("captcha.url");
        secret = properties.getProperty("captcha.secret");
        userAgent = properties.getProperty("user-agent");
        acceptLanguage = properties.getProperty("accept-language");
    }

    public void verify(String gRecaptchaResponse) throws CaptchaException {
        try {
            URL url = new URL(captchaUrl);
            HttpsURLConnection connection = (HttpsURLConnection) url.openConnection();
            setupConnection(connection);
            writeOutput(gRecaptchaResponse, connection);
            StringBuilder response = getResponse(connection);
            checkIfCaptchaPassed(response);
        } catch(IllegalStateException | UnknownHostException | SSLException e){
            logger.error("skipped captcha - couldn't connect to google");
        } catch(Exception e){
            logger.error(e.getMessage());
            throw new CaptchaException();
        }
    }

    private void checkIfCaptchaPassed(StringBuilder response) throws CaptchaException {
        try (JsonReader jsonReader = Json.createReader(new StringReader(response.toString()))) {
            JsonObject jsonObject = jsonReader.readObject();
            if (!jsonObject.getBoolean("success")) {
                throw new CaptchaException();}}
    }

    private StringBuilder getResponse(HttpsURLConnection connection) throws IOException {
        StringBuilder response = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                response.append(inputLine);
            }
        }
        return response;
    }

    private void writeOutput(String gRecaptchaResponse, HttpsURLConnection connection) throws IOException {
        String postParams = "secret=" + secret + "&response=" + gRecaptchaResponse;
        try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
            outputStream.writeBytes(postParams);
            outputStream.flush();
        }
    }

    private void setupConnection(HttpsURLConnection connection) throws ProtocolException {
        connection.setRequestMethod(method);
        connection.setRequestProperty("User-Agent", userAgent);
        connection.setRequestProperty("Accept-Language", acceptLanguage);
        connection.setDoOutput(true);
    }
}
