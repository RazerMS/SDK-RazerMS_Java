package molpay;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.net.URL;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.RequestDispatcher;
 
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
@WebServlet("/controller")
public class molpay extends HttpServlet {
    public static boolean status;
    public static String mpsmerchantid = "SB_supportmolpay";
    public static String vkey = "xxx";
    public static String secretkey = "xxx";
    public static String demo_type = "sandbox";
    public static String mpschannel;
    public static String mpsamount;
    public static String mpsorderid;
    public static String mpsbill_name;
    public static String mpsbill_email;
    public static String mpsbill_mobile;
    public static String mpsbill_desc;
    public static String mpscountry = "MY";
    public static String mpsvcode;
    public static String mpscurrency;
    public static String mpslangcode = "en";
    public static int mpstimer;
    public static String mpstimerbox = "#counter";
    public static String mpscancelurl = "http://localhost:8080/SeamlessIntegration/cancel_order.jsp";
    public static String mpsreturnurl = "http://localhost:8080/SeamlessIntegration/returnurl.jsp";
    public static String mpsapiversion = "3.17";
    
    public molpay(){
        // Silences is golden
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {     
            // read form fields
            status = true;
            mpschannel = request.getParameter("payment_options");
            mpsamount = request.getParameter("total_amount");
            
            mpsbill_name = request.getParameter("billingFirstName") + " " + request.getParameter("billingLastName");
            mpsbill_email = request.getParameter("billingEmail");
            mpsbill_mobile = request.getParameter("billingMobile");
            mpsbill_desc = request.getParameter("billingAddress");
            mpscurrency = request.getParameter("currency");
            mpstimer = Integer.parseInt(request.getParameter("molpaytimer"));
            
            String nextJSP = "/process_order.jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(nextJSP);
            dispatcher.forward(request,response);

    }

    public static int getRandomOrderId(){
        int result = (int)(Math.random()*10000);
        return result;
    }
    
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
    
    public static String hash(String input) {
        String str = input;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(str.getBytes());
            byte[] digest = md.digest();
            StringBuilder sb = new StringBuilder();
            for (byte b : digest) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
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
    for (int i = 0; i < byteData.length; i++
    ) {
        sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
    }
    
    return sb.toString();
    }
    
    
    
    /***********************************************************
     * Get the current redirect URL based on demo type
     * e.g. usage for form action=""
     * @param input 
     * @param merchantid 
     * @return  
     ************************************************************/
    
    public static String url(String input) {
    String url = "";
    if(input.equals("production")) {
        url = "https://www.onlinepayment.com.my/MOLPay/API/seamless/3.17/js/MOLPay_seamless.deco.js";
    } else if(input.equals("sandbox")){
        url = "https://sandbox.molpay.com/MOLPay/API/seamless/3.16/js/MOLPay_seamless.deco.js";
    }
    return url;
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
   
    public String IPN_Return(String type, String skey, String tranID, String domain, String status, String amount, String currency, String paydate, String orderid, String appcode, String error_code, String error_desc, String channel) throws Exception {
        TrustCert();
        // Check either demo type is production or sandbox
        String url = CheckDemo(type);
             
         // each received values from MOLPay
        String blocks[] = {
            skey, tranID, domain, status, amount, currency, paydate, orderid, appcode, error_code, error_desc, channel
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

    public String IPN_Notification(String type, String nbcb, String skey, String tranID, String domain, String status, String amount, String currency, String paydate, String orderid, String appcode, String error_code, String error_desc, String channel) throws Exception {
        TrustCert();
        // Check either demo type is production or sandbox
        String url = CheckDemo(type);
             
         // each received values from MOLPay
        String blocks[] = {
            nbcb, skey, tranID, domain, status, amount, currency, paydate, orderid, appcode, error_code, error_desc, channel
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
    
    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMpsmerchantid() {
        return mpsmerchantid;
    }

    public void setMpsmerchantid(String mpsmerchantid) {
        this.mpsmerchantid = mpsmerchantid;
    }

    public String getVkey() {
        return vkey;
    }

    public void setVkey(String vkey) {
        this.vkey = vkey;
    }

    public String getSecretkey() {
        return secretkey;
    }

    public void setSecretkey(String secretkey) {
        this.secretkey = secretkey;
    }

    public static String getDemo_type() {
        return demo_type;
    }

    public static void setDemo_type(String demo_type) {
        molpay.demo_type = demo_type;
    }  

    public String getMpschannel() {
        return mpschannel;
    }

    public void setMpschannel(String mpschannel) {
        this.mpschannel = mpschannel;
    }

    public String getMpsamount() {
        return mpsamount;
    }

    public void setMpsamount(String mpsamount) {
        this.mpsamount = mpsamount;
    }

    public String getMpsorderid() {
        return mpsorderid;
    }

    public void setMpsorderid(String mpsorderid) {
        this.mpsorderid = mpsorderid;
    }

    public String getMpsbill_name() {
        return mpsbill_name;
    }

    public void setMpsbill_name(String mpsbill_name) {
        this.mpsbill_name = mpsbill_name;
    }

    public String getMpsbill_email() {
        return mpsbill_email;
    }

    public void setMpsbill_email(String mpsbill_email) {
        this.mpsbill_email = mpsbill_email;
    }

    public String getMpsbill_mobile() {
        return mpsbill_mobile;
    }

    public void setMpsbill_mobile(String mpsbill_mobile) {
        this.mpsbill_mobile = mpsbill_mobile;
    }

    public String getMpsbill_desc() {
        return mpsbill_desc;
    }

    public void setMpsbill_desc(String mpsbill_desc) {
        this.mpsbill_desc = mpsbill_desc;
    }

    public String getMpscountry() {
        return mpscountry;
    }

    public void setMpscountry(String mpscountry) {
        this.mpscountry = mpscountry;
    }

    public String getMpsvcode() {
        return mpsvcode;
    }

    public void setMpsvcode(String mpsvcode) {
        this.mpsvcode = mpsvcode;
    }

    public String getMpscurrency() {
        return mpscurrency;
    }

    public void setMpscurrency(String mpscurrency) {
        this.mpscurrency = mpscurrency;
    }

    public String getMpslangcode() {
        return mpslangcode;
    }

    public void setMpslangcode(String mpslangcode) {
        this.mpslangcode = mpslangcode;
    }

    public int getMpstimer() {
        return mpstimer;
    }

    public void setMpstimer(int mpstimer) {
        this.mpstimer = mpstimer;
    }

    public String getMpstimerbox() {
        return mpstimerbox;
    }

    public void setMpstimerbox(String mpstimerbox) {
        this.mpstimerbox = mpstimerbox;
    }

    public String getMpscancelurl() {
        return mpscancelurl;
    }

    public void setMpscancelurl(String mpscancelurl) {
        this.mpscancelurl = mpscancelurl;
    }

    public String getMpsreturnurl() {
        return mpsreturnurl;
    }

    public void setMpsreturnurl(String mpsreturnurl) {
        this.mpsreturnurl = mpsreturnurl;
    }

    public String getMpsapiversion() {
        return mpsapiversion;
    }

    public void setMpsapiversion(String mpsapiversion) {
        this.mpsapiversion = mpsapiversion;
    }

    
    
}
 