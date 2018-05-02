/*
 * The MIT License
 *
 * Copyright 2018 MOLPay Sdn Bhd.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package molpay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.net.URL;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author MOLPay Sdn Bhd.
 */
public class molpay {
    /** required java.security.MessageDigest **/
    
    /***********************************************************
    * Either two of method could be used for verification
     * @param input String text
     * @return MD5 Hashing
     * @throws java.security.NoSuchAlgorithmException 
    ************************************************************/
    public static String md5(String input) throws NoSuchAlgorithmException {
    String result = input;
    if(input != null) {
        MessageDigest md = MessageDigest.getInstance("MD5"); //or "SHA-1"
        md.update(input.getBytes());
        BigInteger hash = new BigInteger(1, md.digest());
        result = hash.toString(16);
        while(result.length() < 32) { //40 for SHA-1
            result = "0" + result;
        }
    }
    return result;
    }
    
    /**
     * @param inputString
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static String getMD5Hex(final String inputString) throws NoSuchAlgorithmException {

    MessageDigest md = MessageDigest.getInstance("MD5");
    md.update(inputString.getBytes());

    byte[] digest = md.digest();

    return convertByteToHex(digest);
    }

    private static String convertByteToHex(byte[] byteData) {

    StringBuilder sb = new StringBuilder();
    for (int i = 0; i < byteData.length; i++) {
        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
    
    return sb.toString();
    }
    
    /***********************************************************
    * Generate random order ID
     * @return Get random between 0-10000
    ************************************************************/

    public static int getRandomOrderId(){
        int result = (int)(Math.random()*10000);
        return result;
    }
 
    /***********************************************************
     * Get the current redirect URL based on demo type
     * e.g. usage for form action=""
     * @param input 
     * @param merchantid 
     * @return  
     ************************************************************/
    
    public static String url(String input, String merchantid) {
    String url = "";
    if(input.equals("production")) {
        url = "https://www.onlinepayment.com.my/MOLPay/pay/" + merchantid;
    } else if(input.equals("sandbox")){
        url = "https://sandbox.molpay.com/MOLPay/pay/" + merchantid;
    }
    return url;
    }
    /***********************************************************
    * Get the BaseURL. The payment page could be obtain using method
    * e.g return values: https://localhost/root/folder/payment/
     * @param requestURL http://localhost:8080/test/uriTest.jsp
     * @param requestURI /test/uriTest.jsp
     * @param contextPath Request Context Path: /test
     * @return URL without filename
     ************************************************************/
    public static String getBaseURL(String requestURL, String requestURI, String contextPath){
        String url = requestURL.toString();
        String baseURL = url.substring(0, url.length() - requestURI.length()) + contextPath + "/";
        return baseURL;
    }
    
    /***********************************************************
     * Key1 : Hashstring generated on Merchant system
     * either $merchant or $domain could be one from POST
     * and one that predefined internally
     * by right both values should be identical
     * @return Hash String 
     * @throws java.security.NoSuchAlgorithmException 
     ************************************************************/

    public static String generateKeys(String tranID, String orderid, String status, String domain, String amount, String currency, String paydate, String appcode, String vkey ) throws NoSuchAlgorithmException {
        // on response payment page
        String key0 = getMD5Hex(tranID + orderid + status + domain + amount + currency);
        String key1 = getMD5Hex(paydate + domain + key0 + appcode + vkey);
        return key1;
    }
    
    /**
     *
     */
    public void TrustCert() {
        TrustManager[] trustAllCerts = new TrustManager[] {
            new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {}

                public void checkServerTrusted(
                    java.security.cert.X509Certificate[] certs, String authType) {}
            }
        };

        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (Exception e) {}
    }

     /**
     * Request parameters: 
     * @param type Demo type
     * @param skey This is the data integrity protection hash string
     * @param tranID Generated by MOLPay System. Integer, 10 digits. Unique transaction ID for tracking purpose
     * @param domain Merchant ID in MOLPay system.
     * @param status 2-digit numeric value, 00 for Successful payment; 11 for failure; 22 for pending.
     * @param amount 2 decimal points numeric value. The total amount paid or to be paid for CASH payment request.
     * @param currency 2 or 3 chars (ISO-4217) currency code. Default currency is RM (indicating MYR) for Malaysia channels
     * @param paydate Date/Time( YYYY-MM-DD HH:mm:ss). Date/Time of the transaction
     * @param orderid Alphanumeric, 32 characters. Invoice or order number from merchant system.
     * @param appcode Alphanumeric, 16 chars. Bank approval code. Mandatory for Credit Card. Certain channel returns empty value
     * @param error_code Error Codes section
     * @param error_desc Error message or description
     * @param channel Predefined string in MOLPay system. Channel references for merchant system
     * @param extraP JSON encoded string or array
     * @return Sent Parameters
     * @throws Exception
     */
   
    public String IPN_Return(String type, String skey, String tranID, String domain, String status, String amount, String currency, String paydate, String orderid, String appcode, String error_code, String error_desc, String channel, String extraP) throws Exception {
        TrustCert();
        // Check either demo type is production or sandbox
        String url = CheckDemo(type);
             
         // each received values from MOLPay
        String blocks[] = {
            skey, tranID, domain, status, amount, currency, paydate, orderid, appcode, error_code, error_desc, channel, extraP
        }; 
        
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        ArrayList<String> parameter = new ArrayList<String>();
	parameter.add("skey");
	parameter.add("tranID");
	parameter.add("domain");
        parameter.add("status");
        parameter.add("amount");
        parameter.add("currency");
        parameter.add("paydate");
        parameter.add("orderid");
        parameter.add("appcode");
        parameter.add("error_code");
        parameter.add("error_desc");
        parameter.add("channel");
        parameter.add("extraP");
        
        // Add request header
        String USER_AGENT = "Mozilla/5.0";
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        // Dont change default values    
        String urlParameters = "treq=1"; // Additional parameter for IPN. Value always set to 1.  
        // Generate urlString for each received values from MOLPay
        for (int i = 0; i < parameter.size() ; i++) {
            urlParameters = urlParameters.concat("&" + parameter.get(i) + "=" + blocks[i]);
        }
        
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in .readLine()) != null) {
            response.append(inputLine);
        } in .close();
        return urlParameters;
    }
    
    /**
     *
     * @param type get demo type to either sandbox or production demo
     * @return demo type
     */
    public String CheckDemo(String type) { 
        // Check either demo type is production or sandbox
        String url = "";
        if (type.equals("production")) {
            url = "https://onlinepayment.com.my/MOLPay/API/chkstat/returnipn.php";
        } else if (type.equals("sandbox")) {
            url = "https://sandbox.molpay.com/MOLPay/API/chkstat/returnipn.php";
        }
        return url;
    }

    /**
     * Request parameters: 
     * @param type Demo type
     * @param nbcb Always equal to 2​, which indicates this is a notification from MOLPay
     * @param skey This is the data integrity protection hash string
     * @param tranID Generated by MOLPay System. Integer, 10 digits. Unique transaction ID for tracking purpose
     * @param domain Merchant ID in MOLPay system.
     * @param status 2-digit numeric value, 00 for Successful payment; 11 for failure; 22 for pending.
     * @param amount 2 decimal points numeric value. The total amount paid or to be paid for CASH payment request.
     * @param currency 2 or 3 chars (ISO-4217) currency code. Default currency is RM (indicating MYR) for Malaysia channels
     * @param paydate Date/Time( YYYY-MM-DD HH:mm:ss). Date/Time of the transaction
     * @param orderid Alphanumeric, 32 characters. Invoice or order number from merchant system.
     * @param appcode Alphanumeric, 16 chars. Bank approval code. Mandatory for Credit Card. Certain channel returns empty value
     * @param error_code Error Codes section
     * @param error_desc Error message or description
     * @param channel Predefined string in MOLPay system. Channel references for merchant system
     * @param extraP JSON encoded string or array
     * @return Sent Parameters
     * @throws Exception
     */
    public String IPN_Notification(String type, String nbcb, String skey, String tranID, String domain, String status, String amount, String currency, String paydate, String orderid, String appcode, String error_code, String error_desc, String channel, String extraP) throws Exception {
        TrustCert();
        // Check either demo type is production or sandbox
        String url = CheckDemo(type);
             
         // each received values from MOLPay
        String blocks[] = {
            nbcb, skey, tranID, domain, status, amount, currency, paydate, orderid, appcode, error_code, error_desc, channel, extraP
        }; 
        
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();
        ArrayList<String> parameter = new ArrayList<String>();
        parameter.add("nbcb");
	parameter.add("skey");
	parameter.add("tranID");
	parameter.add("domain");
        parameter.add("status");
        parameter.add("amount");
        parameter.add("currency");
        parameter.add("paydate");
        parameter.add("orderid");
        parameter.add("appcode");
        parameter.add("error_code");
        parameter.add("error_desc");
        parameter.add("channel");
        parameter.add("extraP");
        
        // Add request header
        String USER_AGENT = "Mozilla/5.0";
        con.setRequestMethod("POST");
        con.setRequestProperty("User-Agent", USER_AGENT);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
        
        // Dont change default values
        String urlParameters = "treq=1"; // Additional parameter for IPN. Value always set to 1.  
        // Generate urlString for each received values from MOLPay
        for (int i = 0; i < parameter.size() ; i++) {
            urlParameters = urlParameters.concat("&" + parameter.get(i) + "=" + blocks[i]);
        }
        
        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
            new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in .readLine()) != null) {
            response.append(inputLine);
        } in .close();
        return urlParameters;
    }

}
