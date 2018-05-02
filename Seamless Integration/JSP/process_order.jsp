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
<%
    molpay data = new molpay(); 
    data.setMpsorderid(Integer.toString(data.getRandomOrderId())); // get random order id 
    data.setMpsvcode(data.hash(data.getMpsamount() + data.getMpsmerchantid() + data.getMpsorderid() + data.getVkey()));
    // controller data = new controller(mpsvcode, orderid);
    JsonObject innerObject = new JsonObject();
    innerObject.addProperty("status", data.isStatus());
    innerObject.addProperty("mpsmerchantid", data.getMpsmerchantid());
    innerObject.addProperty("mpschannel", data.getMpschannel());
    innerObject.addProperty("mpsamount", data.getMpsamount());
    innerObject.addProperty("mpsorderid", data.getMpsorderid());
    innerObject.addProperty("mpsbill_name", data.getMpsbill_name());
    innerObject.addProperty("mpsbill_email", data.getMpsbill_email());
    innerObject.addProperty("mpsbill_mobile", data.getMpsbill_mobile());
    innerObject.addProperty("mpsbill_desc", data.getMpsbill_desc());
    innerObject.addProperty("mpscountry", data.getMpscountry());
    innerObject.addProperty("mpsvcode", data.getMpsvcode());
    innerObject.addProperty("mpscurrency", data.getMpscurrency());
    innerObject.addProperty("mpslangcode", data.getMpslangcode());
    innerObject.addProperty("mpstimer", data.getMpstimer());
    innerObject.addProperty("mpstimerbox", data.getMpstimerbox());
    innerObject.addProperty("mpscancelurl", data.getMpscancelurl());
    innerObject.addProperty("mpsreturnurl", data.getMpsreturnurl());
    innerObject.addProperty("mpsapiversion", data.getMpsapiversion());
    out.println(innerObject);
%>
<%@page import="com.google.gson.JsonObject"%>
<%@page import="molpay.molpay" %>
<%@page import="com.google.gson.Gson"%>