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

   Document   : index
   Created on : Apr 11, 2018, 6:07:40 PM
   Author     : MOLPay Sdn Bhd
   --%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="molpay.controller"%>
<%@page import="molpay.molpay"%>
<%@page import="java.util.Random"%>
<%@page import="java.math.BigInteger"%>
<%@ page import="java.security.MessageDigest" %>
<%@ page import="java.security.NoSuchAlgorithmException" %>
<%@ page import="java.nio.ByteBuffer;" %>

<%-- request page need to use verify keys --%> 
<% 
    /***********************************************************
    * This is the configuration page
    ************************************************************/
   final String demo_type = "sandbox"; // **Change demo type to either sandbox or production demo.**
  
   // Amount is static
   
   controller data = new controller();
   data.setDemo_type(demo_type);
   
   final String requestURL = request.getRequestURL().toString();
   final String requestURI = request.getRequestURI();
   final String contextPath =  request.getContextPath();
   
   int orderid = molpay.getRandomOrderId(); 

   String getBaseURL = molpay.getBaseURL(requestURL, requestURI, contextPath);
%>

<!DOCTYPE html>
<html>
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
      <title>JSP page</title>
      <!-- Latest compiled and minified CSS -->
      <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
      <!-- jQuery library -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
      <!-- Latest compiled JavaScript -->
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
      <style>
         .footer {
         position: fixed;
         left: 0;
         bottom: 0;
         width: 100%;
         color: white;
         text-align: center;
         }
      </style>
   </head>
   <body>
      <div class="std">
         <div class="row">
            <div class="col-md-offset-3 col-md-6">
               <div class="page-title">
                  <h1>Payment Page</h1>
               </div>
               <form action="<% out.println(molpay.url(data.getDemo_type(), data.getMerchantid())); %>/" method="POST">
                  <input type = "hidden" name = "vcode" value = "<%= molpay.getMD5Hex("2.02" + data.getMerchantid() + orderid + data.getVkey() ) %>" />
                  <div class="form-group">
                     <label for="bill_desc">Description:</label>

                     <input class="form-control" id="bill_desc" type = "text"  name = "bill_desc" value="Order <%= orderid %> - kucing x 2" readonly="readonly"/>
                  </div>
                  <div class="form-group">
                     <label for="amount">Amount:</label>
                     <input class="form-control" id="amount" type = "text" name = "amount" value="2.02" readonly="readonly"/>
                  </div>
                  <div class="form-group">
                     <label for="bill_name">Name: </label>
                     <input class="form-control" id="bill_name" type = "text" name = "bill_name" value="John Smith" placeholder="Full name"/>
                  </div>
                  <div class="form-group">
                     <label for="bill_email">Email: </label>
                     <input class="form-control" id="bill_email" type = "text" name = "bill_email" value="syid98@gmail.com" placeholder="Email"/>
                  </div>
                  <div class="form-group">
                     <label for="bill_mobile">Phone: </label>
                     <input class="form-control" id="bill_mobile" type = "text" name = "bill_mobile" value="0171231234" placeholder="Phone number"/>
                  </div>
                  <div class="form-group">
                     <label for="country">Country:</label>
                     <select class="form-control" id="country" type = "text" name = "country" />
                         <option value="MY">Malaysia</option> >
                     <select/>
                  </div>
                  <div class="form-group">
                     <label for="cur">Currency: </label>
                     <select class="form-control" id="country" type = "text" name = "cur" />
                         <option value="MYR">MYR</option>
                     <select/>  
                  </div>
                  
                  <input type = "hidden" name = "returnurl" value = "<% out.println(getBaseURL); %>returnurl.jsp" />
                  <input type = "hidden" name = "callbackurl" value = "<% out.println(getBaseURL); %>callbackurl.jsp" />       
                  <input type = "hidden" name = "orderid" value = "<%= orderid %>" /> 
                  <div class="col-md-offset-4">
                     <button type="submit" class="btn btn-default btn-lg" style="width: 220px;">Submit</button>
                  </div>
               </form>
            </div>
         </div>
      </div>
      <div class="footer">
         <img src="img/simple-logo-800x50.png" class="img-rounded" alt="footer">
      </div>
   </body>
</html>