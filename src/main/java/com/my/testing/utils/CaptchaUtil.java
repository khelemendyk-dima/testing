package com.my.testing.utils;

import com.my.testing.exceptions.CaptchaException;
import jakarta.json.*;
import org.apache.logging.log4j.*;

import javax.net.ssl.*;
import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * Validate Google ReCaptcha
 *
 * @author Khelemendyk Dmytro
 * @version 1.0
 */
public class CaptchaUtil {
    private static final Logger logger = LogManager.getLogger(CaptchaUtil.class);
    private final String method;
    private final String captchaUrl;
    private final String secret;
    private final String userAgent;
    private final String acceptLanguage;

    /**
     * @param properties should contain all required fields to properly configure
     */
    public CaptchaUtil(Properties properties) {
        method = properties.getProperty("captcha.method");
        captchaUrl = properties.getProperty("captcha.url");
        secret = properties.getProperty("captcha.secret");
        userAgent = properties.getProperty("user-agent");
        acceptLanguage = properties.getProperty("accept-language");
    }

    /**
     * Checks if user is human. Setups and connects to Google ReCaptcha services. Sends user's captcha result.
     * Gets response and checks if it matches. Disable captcha if Google is down.
     * @param gRecaptchaResponse get it from controller
     * @throws CaptchaException if captcha is not valid
     */
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
