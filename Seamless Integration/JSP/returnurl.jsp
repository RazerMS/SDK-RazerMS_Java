<%--
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
--%>
<%@page import="molpay.molpay"%>
<%@page import="java.util.Enumeration"%>
<%@page import="java.math.BigInteger"%>
<%@page import="java.security.MessageDigest"%>
<%@page import="java.security.NoSuchAlgorithmException"%>
<%@page import="java.io.InputStreamReader"%>
<%@page import="java.io.BufferedReader"%>
<%@page import="java.io.DataOutputStream"%>
<%@page import="java.net.URL"%>
<%@page import="javax.net.ssl.HttpsURLConnection"%>
<%@page import="javax.net.ssl.SSLContext"%>
<%@page import="javax.net.ssl.X509TrustManager"%>
<%@page import="javax.net.ssl.TrustManager"%>

<%!
    /***********************************************************
    * This is the response page
    ************************************************************/ 
%>
<html>
   <head>
      <title>Transaction Status</title>
   </head>
   <body>
    <%
        
    /***********************************************************
    * Print all of the received values
    ************************************************************/

    Enumeration in = request.getParameterNames();
    while(in.hasMoreElements()) {
     String paramName = in.nextElement().toString();
     out.println(paramName + " = " + request.getParameter(paramName)+"<br>");
    }
    
    %>
      
     <% 
        /***********************************************************
        * Set the values received from MOLPay's payment page
        ************************************************************/
        molpay data = new molpay();
        String skey = request.getParameter("skey");
        String tranID = request.getParameter("tranID");
        String domain = request.getParameter("domain");
        String status = request.getParameter("status");
        String amount = request.getParameter("amount");
        String currency = request.getParameter("currency");
        String paydate = request.getParameter("paydate");
        String orderid = request.getParameter("orderid");
        String appcode = request.getParameter("appcode");
        String error_code = request.getParameter("error_code");
        String error_desc = request.getParameter("error_desc");
        String channel = request.getParameter("channel");
        out.println(data.getDemo_type()); // Print out demo type
     %>

     <%
        /***********************************************************
        * IPN Snippet code is the enhancement required
        * by merchant to add into their return script in order to
        * implement backend acknowledge method for IPN
        ************************************************************/
        out.println("<br>" + "\nMerchant have sent IPN");
        String urlP = data.IPN_Return(data.getDemo_type(), skey, tranID, domain, status, amount, currency, paydate, orderid, appcode, error_code, error_desc, channel);
        out.println(urlP);
     %> 

      <%
        /***********************************************************
        * To verify the data integrity sending by MOLPay
        ************************************************************/
        
        String key1 = data.generateKeys(tranID, orderid, status, domain, amount, currency, paydate, appcode, data.getSecretkey() );
        out.println("status : " + status + "<br>");
        out.println("key1 : " + key1 + "<br>");
        out.println("skey : " + skey + "<br>");
      %>    
      
      <% if (!key1.equals( skey )) status = "-1"; /* Invalid transaction */ %>
      

      <% 
        /***********************************************************
        * Included acknowledge method for IPN
        ************************************************************/

        if(status.equals("00") || status.equals("22")){
           out.println(status + "<br>" + "Transaction was successfully made");
           // write your script here .....
           // if ( check_cart_amt($orderid, $amount) ) { }
        }
        else {
           // failure action
            out.println("<br>" + "Ops something just went wrong. Your transaction have been detected as fraud ");
            
        } 
      %>  
   </body>
</html>