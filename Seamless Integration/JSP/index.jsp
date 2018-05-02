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

<%@page import="molpay.molpay" %>
<!doctype html>
<!--[if lt IE 7]> 

<html class="no-js ie6 oldie" lang="en">
<![endif]-->
<!--[if IE 7]>    
<html class="no-js ie7 oldie" lang="en">
<![endif]-->
<!--[if IE 8]>    
<html class="no-js ie8 oldie" lang="en">
<![endif]-->
<!--[if gt IE 8]><!--> 
<html class="no-js" lang="en">
   <!--<![endif]-->
   <head>
      <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
      <title>MOLPay Seamless Version 3.17 : Demo</title>
      <link rel="stylesheet" href="css/bootstrap.min.css">
      <link rel="stylesheet" type="text/css" href="css/style.css">
      <!-- jQuery (necessary for MOLPay Seamless JavaScript plugins) -->
      <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
      <script src="<%= molpay.url(molpay.demo_type) %>" </script>
      <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
      <script>
         $(document).ready( function(){		
         $(".thb").hide();
         $(".vnd").hide();
         
         $('#selector').change(function(){
         var cur = $(this).val();
         $(".cur_span").text(cur);
         $("#currency").val(cur);
         $("input[name='total_amount']").val("61.01");
         $("span.price_span").text(" 61.01");
         
         if(cur == "MYR") {
         $(".myr").show();
         $(".thb").hide();
         $(".vnd").hide();
         } 
         else if(cur == "VND") {
                         $(".myr").hide();
                         $(".thb").hide();
         $(".vnd").show();
         $("input[name='total_amount']").val("25000");
         $("span.price_span").text(" 25000");
         }
         else {
         $(".thb").show();
         $(".myr").hide();
         $(".vnd").hide();
         }
         
         });
         
         $('input[name=payment_options]').on('click', function(){
         var $myForm = $(this).closest('form');
         if ($myForm[0].checkValidity()) {
         $myForm.trigger("submit");
         }
         else
         {
         alert("Please fill in required field.");
         $(":input[required]").each(function () {
         if($(this).val().length == 0)
         {
         $(this).focus();
         return false;
         }
         });
         }
         });
         });
      </script>
   </head>
   <body>
      <nav class="navbar ">
         <div class="container">
            <div class="navbar-header">
               <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
               <span class="sr-only">Toggle navigation</span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
               <span class="icon-bar"></span>
               </button>
               <img src="images/logo.jpg"  />
            </div>
            <div id="navbar" class="navbar-collapse collapse">
               <ul class="nav navbar-nav pull-right">
                  <li class="active"><a href="#">Home</a></li>
                  <li><a href="#">About</a></li>
                  <li><a href="#">Contact</a></li>
                  <li>
                     <select class="form-control" id="selector" autocomplete="off" style="margin-top:6px">
                        <option value="MYR" selected>MYR</option>
                        <option value="THB">THB</option>
                        <option value="VND">VND</option>
                     </select>
                  </li>
               </ul>
            </div>
            <!--/.nav-collapse -->
         </div>
      </nav>
      <body>
         <form method="POST" action="controller" role="molpayseamless" >
            <div class="jumbotron">
               <div class="container">
                  <h1>Order Details</h1>
                  <table class="table">
                     <tr class="ordertable-head">
                        <td>Product</td>
                        <td>Price</td>
                        <td>Quantity</td>
                        <td>Total</td>
                     </tr>
                     <tr>
                        <td>
                           <div class="row">
                              <div class="col-md-3"><img src="images/order-shoes.jpg" /></div>
                              <div class="col-md-6">Nike Roshe Run Sneakers<br />Size: 39</div>
                           </div>
                        </td>
                        <td><span class="cur_span">MYR</span><span class="price_span"> 61.01</span></td>
                        <td>1</td>
                        <td><span class="cur_span">MYR</span><span class="price_span"> 61.01</span></td>
                     </tr>
                  </table>
                  <table class="table">
                     <tr>
                        <td colspan="4" bgcolor="#eaeaea" style="padding: 15px 10px;">
                           <div class="row">
                              <div class="col-md-4">  <input placeholder="Enter your coupon code here" class="form-control"/></div>
                              <div class="col-md-1">
                                 <div class="bttn-couponapply"><a href="">Apply</a></div>
                              </div>
                           </div>
                        </td>
                     </tr>
                  </table>
                  <div class="row">
                     <div class="col-md-offset-8">
                        <div class="row">
                           <div class="col-md-6 text-right">   Shipping Fee</div>
                           <div class="col-md-2">   Free</div>
                        </div>
                     </div>
                     <div class="col-md-offset-8">
                        <div class="row">
                           <div class="col-md-6 text-right">   Sub Total</div>
                           <div class="col-md-4"><span class="cur_span">MYR</span><span class="price_span"> 61.01</span></div>
                        </div>
                     </div>
                     <div class="col-md-offset-8">
                        <div class="row" style="padding: 10px 0;">
                           <div class="col-md-6 text-right ordertotal">  <strong>Total</strong></div>
                           <div class="col-md-5 ordertotal">   <strong><span class="cur_span">MYR</span><span class="price_span"> 61.01</span></strong></div>
                        </div>
                     </div>
                  </div>
               </div>
            </div>
            <div class="container">
               <h1 class="margintop">Billing Information</h1>
               <div class="row">
                  <div class="col-md-4 marginbttm">First Name <span class="mandatory">*</span>  <input type="123" class="form-control" name="billingFirstName" id="billingFirstName" value="MOLPay" required></div>
                  <div class="col-md-4 marginbttm">Last Name <span class="mandatory">*</span> <input type="lname" class="form-control" name="billingLastName" id="billingLastName" value="Demo" required></div>
                  <div class="col-md-4 marginbttm">Phone <span class="mandatory">*</span> <input type="mobile" class="form-control" name="billingMobile" id="billingMobile" value="55218438" required></div>
                  <div class="col-md-4 marginbttm">Email <span class="mandatory">*</span> <input type="email" class="form-control" name="billingEmail" id="billingEmail" value="demo@molpay.com" required></div>
                  <div class="col-md-8 marginbttm">Address <span class="mandatory">*</span> <input type="address" class="form-control" name="billingAddress" id="billingAddress" value="B-13-3A, Jalan Multimedia 7/AH, CityPark, i-City" required></div>
                  <div class="col-md-4 marginbttm">City <span class="mandatory">*</span> <input type="city" class="form-control" name="billingCity" id="billingCity" value="Selangor" required></div>
                  <div class="col-md-4 marginbttm">State <span class="mandatory">*</span> <input type="state" class="form-control" name="billingState" id="billingState" value="Shah Alam" required></div>
                  <div class="col-md-4 marginbttm">Postcode/ZIP <span class="mandatory">*</span> <input type="poscode" class="form-control" name="billingPostcode" id="billingPostcode" value="40000" required></div>
               </div>
               <div class="smalltxt">
                  <input type="checkbox" name="chk_deliver_same_addr" aria-label="Deliver to the same address" checked required> Deliver to the same address<br />
                  <input type="checkbox" name="chk_tnc" aria-label="Deliver to the same address" checked required> I?ve read and accept the terms & conditions.
               </div>
               <h1 class="margintop" style="margin-top:70px">Payment Method</h1>
               <p class="smalltxt">
                  <strong>Secure Online Payment by MOLPay</strong>
                  <br />
                  Please select a payment type from below to proceed for payment.
               </p>
               <div class="row">
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentcredit_alb-paymex"><img src="images/payment-credit.jpg" title="ALB-Paymex" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentcredit_alb-paymex" value="credit" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentm2u"><img src="images/payment-m2u.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentm2u" value="maybank2u" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentcimb"><img src="images/payment-cimb.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentcimb" value="cimbclicks" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="payment7e"><img src="images/payment-7e.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="payment7e" value="cash-711" required/>
                  </div>
                  <% if(molpay.demo_type.equals("production")) { %>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentfpx"><img src="images/payment-fpx.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentfpx" value="fpx" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymenthlb"><img src="images/payment-hlb.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymenthlb" value="hlb" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentcimbva"><img src="images/payment-cimb-va.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentcimbva" value="cimb-va" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentrhb"><img src="images/payment-rhb.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentrhb" value="rhb" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentaffin"><img src="images/payment-affin.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentaffin" value="affinonline" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentam"><img src="images/payment-amonline.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentam" value="amb" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentislam"><img src="images/payment-bank-islam.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentislam" value="bankislam" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="paymentpbe"><img src="images/payment-pbe.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paymentpbe"  value="pbb" required/>
                  </div>
                  <!-- <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="esapay"><img src="images/payment-esapay.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="esapay" value="cash-esapay" required/>
                     </div> -->
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="jompay"><img src="images/payment-jompay.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="jompay" value="jompay" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center thb">
                     <label class="hand" for="scb"><img src="images/payment-scb.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="scb" value="TH_PB_SCBPN" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center thb">
                     <label class="hand" for="krungthai"><img src="images/payment-krungthai.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="krungthai" value="TH_PB_KTBPN" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center thb">
                     <label class="hand" for="krungsri"><img src="images/payment-krungsri.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="krungsri" value="TH_PB_BAYPN" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center thb">
                     <label class="hand" for="bangkokbank"><img src="images/payment-bangkokbank.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="bangkokbank" value="TH_PB_BBLPN" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center thb">
                     <label class="hand" for="paysbuy"><img src="images/payment-paysbuy.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="paysbuy" value="TH_PB_CASH" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="FPX_OCBC"><img src="images/payment-ocbc.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="FPX_OCBC" value="FPX_OCBC" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center myr">
                     <label class="hand" for="FPX_SCB"><img src="images/payment-standard-chartered.jpg" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="FPX_SCB" value="FPX_SCB" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center vnd">
                     <label class="hand" for="VTCPay_Ewallet"><img src="images/vtcpay/vtcpay.png" alt="VTCPay_Ewallet" /></label>
                     <br />  
                     <input type="radio" name="payment_options" id="VTCPay_Ewallet" value="vtcpay-ewallet" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center vnd">
                     <label class="hand" for="VTCPay_Vietcombank"><img src="images/vtcpay/vietcombank.png" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="VTCPay_Vietcombank" value="vtcpay-vietcombank" required/>
                  </div>
                  <div class="col-md-2 col-xs-6 marginbttm text-center vnd">
                     <label class="hand" for="VTCPay_Techcombank"><img src="images/vtcpay/techcom-bank.png" /></label>
                     <br />
                     <input type="radio" name="payment_options" id="VTCPay_Techcombank" value="vtcpay-techcombank" required/>
                  </div>
                  <% } %>
               </div>
               <br />
            </div>
            </div>
            <input type="hidden" name="currency" id="currency" value="MYR" />
            <input type="hidden" name="total_amount" value="61.01" />
            <input type="hidden" name="molpaytimer" value="61" />
         </form>
   </body>
</html>