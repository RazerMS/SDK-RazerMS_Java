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

/**
 *
 * @author MOLPay Sdn Bhd.
 */
public class controller {
    /***********************************************************
    * This is the configuration for request/response page
    ************************************************************/ 

    final String vkey = "xxx"; // Verify Keys (Replace with your verify key)
    final String merchantid = "xxx"; // Merchant ID (Replace with your merchant id)
    final String secretkey = "xxx"; // Secret keys (Replace with your secret keys) 
    // Contact MOLPay support team to reset your keys whenever needed 
    private static String demo_type;
    private static String skey;
    private static String tranID;
    private static String domain;
    private static String status;
    private static String amount;
    private static String currency;
    private static String paydate;
    private static String orderid;
    private static String appcode;
    private static String error_code;
    private static String error_desc;
    private static String channel;
    private static String extraP;
    public static String nbcb;
    public static String treq;
     
    public controller(){
        // Set default value for constructor only for return url IPN
        this.treq = "1";
        
    }
    
    /**
     *
     * @return vkey
     */
    public String getVkey() {
        return vkey;
    }
    
    /**
     *
     * @return merchantid
     */
    public String getMerchantid() {
        return merchantid;
    }
    
    /**
     *
     * @return secretkey
     */
    public String getSecretkey() {
        return secretkey;
    }

    /**
     *
     * @return Demo type
     */
    public String getDemo_type() {
        return demo_type;
    }

    /**
     *
     * @param demo_type demo type to either sandbox or production demo. change in main page
     */
    public void setDemo_type(String demo_type) {
        this.demo_type = demo_type;
    }

    /**
     *
     * @return Nbcb
     */
    public String getNbcb() {
        return nbcb;
    }

    /**
     *
     * @param nbcb received value from MOLPay
     */
    public void setNbcb(String nbcb) {
        this.nbcb = nbcb;
    }

    /**
     *
     * @return Treq
     */
    public String getTreq() {
        return treq;
    }

    /**
     *
     * @param treq received value from MOLPay
     */
    public void setTreq(String treq) {
        this.treq = treq;
    }

    /**
     *
     * @return skey
     */
    public String getSkey() {
        return skey;
    }

    /**
     *
     * @param skey received value from MOLPay
     */
    public void setSkey(String skey) {
        this.skey = skey;
    }

    /**
     *
     * @return TranID
     */
    public String getTranID() {
        return tranID;
    }

    /**
     *
     * @param tranID received value from MOLPay
     */
    public void setTranID(String tranID) {
        this.tranID = tranID;
    }

    /**
     *
     * @return domain
     */
    public String getDomain() {
        return domain;
    }

    /**
     *
     * @param domain received value from MOLPay
     */
    public void setDomain(String domain) {
        this.domain = domain;
    }

    /**
     *
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     *
     * @param status received value from MOLPay
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     *
     * @return amount
     */
    public String getAmount() {
        return amount;
    }

    /**
     *
     * @param amount received value from MOLPay
     */
    public void setAmount(String amount) {
        this.amount = amount;
    }

    /**
     *
     * @return currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency received value from MOLPay
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return date
     */
    public String getPaydate() {
        return paydate;
    }

    /**
     *
     * @param paydate received value from MOLPay
     */
    public void setPaydate(String paydate) {
        this.paydate = paydate;
    }

    /**
     *
     * @return orderID
     */
    public String getOrderid() {
        return orderid;
    }

    /**
     *
     * @param orderid received value from MOLPay
     */
    public void setOrderid(String orderid) {
        this.orderid = orderid;
    }

    /**
     *
     * @return appcode
     */
    public String getAppcode() {
        return appcode;
    }

    /**
     *
     * @param appcode received value from MOLPay
     */
    public void setAppcode(String appcode) {
        this.appcode = appcode;
    }

    /**
     *
     * @return error code
     */
    public String getError_code() {
        return error_code;
    }

    /**
     *
     * @param error_code received value from MOLPay
     */
    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    /**
     *
     * @return error description
     */
    public String getError_desc() {
        return error_desc;
    }

    /**
     *
     * @param error_desc received value from MOLPay
     */
    public void setError_desc(String error_desc) {
        this.error_desc = error_desc;
    }

    /**
     *
     * @return channel
     */
    public String getChannel() {
        return channel;
    }

    /**
     *
     * @param channel received value from MOLPay
     */
    public void setChannel(String channel) {
        this.channel = channel;
    }

    /**
     *
     * @return json array
     */
    public String getExtraP() {
        return extraP;
    }

    /**
     *
     * @param extraP received value from MOLPay
     */
    public void setExtraP(String extraP) {
        this.extraP = extraP;
    }
   
}
