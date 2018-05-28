## Integrating MOLPay with JAVA SDK
Version 1.0. (Initial Release)

### Pre-Requisite
------
1. Java IDE (e.g. Netbeans, Eclipse) or above and required JDK.
2. MOLPay Development or Production ID.
3. MOLPay General API.

### Installation
#### Via File Explorer & NetBeans IDE

##### Recommended IDE 
> Product Version: NetBeans IDE 8.2 (Build 201609300101)
> 
> Java: 1.8.0_161
> 
> Runtime: Java(TM) SE Runtime Environment 1.8.0_161-b12

1. Download `Java-SDK-MOLPay + Sample.zip` file.
2. Extract the zip file. 
3. Open Netbeans, Click File > New project... > Java Web > Web application.
4. Right click web pages on project, Import all files

or

1. Download `Netbeans.zip` file.
2. Open Netbeans, Click File > Import Project.. > From zip, Select the netbeans.zip

### Usage

> Change Development or Production ID 


```JAVA
final String vkey = "xxx"; // Verify Keys (Replace with your verify key)
final String merchantid = "SB_supportmolpay"; // Merchant ID (Replace with your merchant id)
final String secretkey = "xxx"; // Secret keys (Replace with your secret keys) 
```

> Import the SDK on JSP file


```JSP
<%@page import="molpay.controller"%>
<%@page import="molpay.molpay"%>
```


> Configuration page can set either sandbox or production on **index.jsp**

```JAVA
final String demo_type = "sandbox"; // or production
```


> Create data object in order to access the properties of Base class: 

```JAVA
controller data = new controller();
```
> Set the values received from MOLPay's payment page for Return URL
```JSP
data.setSkey(request.getParameter("skey"));
data.setTranID(request.getParameter("tranID"));
data.setDomain(request.getParameter("domain"));
data.setStatus(request.getParameter("status"));
data.setAmount(request.getParameter("amount"));
data.setCurrency(request.getParameter("currency"));
data.setPaydate(request.getParameter("paydate"));
data.setOrderid(request.getParameter("orderid"));
data.setAppcode(request.getParameter("appcode"));
data.setError_code(request.getParameter("error_code"));
data.setError_desc(request.getParameter("error_desc"));
data.setChannel(request.getParameter("channel"));
data.setExtraP(request.getParameter("extraP")); 
```
> Create securitycert object in order to sent IPN for return URL
```JSP
molpay securitycert = new molpay();
```
> IPN(Instant Payment Notification) for Return URL



```JSP
/***********************************************************
* IPN Snippet code is the enhancement required
* by merchant to add into their return script in order to
* implement backend acknowledge method for IPN
************************************************************/

String urlP = securitycert.IPN_Return(data.getDemo_type(), data.getSkey(), data.getTranID(),
data.getDomain(), data.getStatus(), data.getAmount(), data.getCurrency(), data.getPaydate(),
data.getOrderid(), data.getAppcode(), data.getError_code(), 
data.getError_desc(), data.getChannel(), data.getExtraP());
out.println("<br>" + "\nMerchant have sent IPN");
out.println(urlP);
```

> IPN(Instant Payment Notification) for Notification & Callback URL

```JSP
controller data = new controller();
```
> Set the values received from MOLPay's payment page for Notification & Callback URL
```JSP
data.setSkey(request.getParameter("nbcb")); 
data.setSkey(request.getParameter("skey"));
data.setTranID(request.getParameter("tranID"));
data.setDomain(request.getParameter("domain"));
data.setStatus(request.getParameter("status"));
data.setAmount(request.getParameter("amount"));
data.setCurrency(request.getParameter("currency"));
data.setPaydate(request.getParameter("paydate"));
data.setOrderid(request.getParameter("orderid"));
data.setAppcode(request.getParameter("appcode"));
data.setError_code(request.getParameter("error_code"));
data.setError_desc(request.getParameter("error_desc"));
data.setChannel(request.getParameter("channel"));
data.setExtraP(request.getParameter("extraP")); 
```

```JSP
/***********************************************************
* IPN Snippet code is the enhancement required
* by merchant to add into their return script in order to
* implement backend acknowledge method for IPN
************************************************************/

molpay securitycert = new molpay();
out.println("<br>" + "\nMerchant have sent IPN");
String urlP = securitycert.IPN_Notification(data.getDemo_type(), data.getNbcb(), data.getSkey(), data.getTranID(), data.getDomain(), data.getStatus(), data.getAmount(), data.getCurrency(), data.getPaydate(), data.getOrderid(), data.getAppcode(), data.getError_code(), data.getError_desc(), data.getChannel(), data.getExtraP());
        out.println(urlP);
```

> Additional object must be set when using IPN

```JAVA 
String urlParameters = "treq=1"; // Default. Additional parameter for IPN. Value always set to 1. 
```

> To generate key1, method generateKeys() need to invoked with parameters. Refer Class MOLPay method summary for details.
```JSP
/***********************************************************
* Generate key1. References class method
************************************************************/
        
String key1 = molpay.generateKeys(data.getTranID(), data.getOrderid(),data.getStatus(), data.getDomain(),
data.getAmount(), data.getCurrency(), data.getPaydate(), data.getAppcode(), data.getSecretkey() );
out.println("key1 : " + key1 + "<br>");
```

> Notification & Callback URL with IPN
```JAVA
if( data.getNbcb().equals("2")){
/***********************************************************
* IPN Snippet code is the enhancement required
* by merchant to add into their return script in order to
* implement backend acknowledge method for notification IPN
************************************************************/
molpay securitycert = new molpay();
out.println("<br>" + "\nMerchant have sent IPN");
String urlP = securitycert.IPN_Notification(data.getDemo_type(), data.getNbcb(), 
data.getSkey(), data.getTranID(), data.getDomain(), data.getStatus(), data.getAmount(), 
data.getCurrency(), data.getPaydate(), data.getOrderid(), data.getAppcode(), 
data.getError_code(), data.getError_desc(), data.getChannel(), data.getExtraP());
out.println(urlP);
}
```

#### Sample of all 3 endpoints

> E.G Return URL

```JSP
<% if (!key1.equals( data.getSkey() )) data.setStatus("-1"); /* Invalid transaction */ %>
      

<% 
/***********************************************************
* Included acknowledge method for IPN
************************************************************/
          
	if(data.getStatus().equals("00")){
		out.println(data.getStatus() + "<br>" + "Transaction was successfully made");
		// write your script here .....
		// if ( check_cart_amt($orderid, $amount) ) { }
	}
	else {
	// failure action
		out.println("<br>" + "Ops something just went wrong. Your transaction have been detected as fraud ");
} 
%>  
```

> E.G Callback & Notification URL

```JSP
<% if (!key1.equals( data.getSkey() )) data.setStatus("-1"); /* Invalid transaction */ %>

       <% if(data.getStatus().equals("00")){
           // Write your script here e.g. check_cart()    
        }
        else {
            out.println(data.getStatus() + "<br>" + "Ops something just went wrong. Your transaction have been detected as fraud ");
        } 

        if( data.getNbcb().equals("1")){
             out.println("CBTOKEN:MPSTATOK");
        }
        
        if( data.getNbcb().equals("2")){
        /***********************************************************
        * IPN Snippet code is the enhancement required
        * by merchant to add into their return script in order to
        * implement backend acknowledge method for notification IPN
        ************************************************************/
        molpay securitycert = new molpay();
        out.println("<br>" + "\nMerchant have sent IPN");
        String urlP = securitycert.IPN_Notification(data.getDemo_type(), data.getNbcb(), data.getSkey(), data.getTranID(), data.getDomain(), data.getStatus(), data.getAmount(), data.getCurrency(), data.getPaydate(), data.getOrderid(), data.getAppcode(), data.getError_code(), data.getError_desc(), data.getChannel(), data.getExtraP());
        out.println(urlP);
        }
%>           

```

### Class MOLPay
------
`public class molpay
extends java.lang.Object`
#### Constructor Summary
###### Constructors

`molpay() `

### Method Summary
------
##### All Methods
###### Static Methods
###### Instance Methods
###### Concrete Methods
------

| Modifier and Type | Method and Description |
| ------------- |:-------------:| 
| `java.lang.String CheckDemo(java.lang.String type)` | 	`CheckDemo(java.lang.String type) `
| `static java.lang.String` | 	`generateKeys(java.lang.String tranID, java.lang.String orderid, java.lang.String status, java.lang.String domain, java.lang.String amount, java.lang.String currency, java.lang.String paydate, java.lang.String appcode, java.lang.String vkey)` *Key1 : Hashstring generated on Merchant system either merchant or domain could be one from POST and one that predefined internally by right both values should be identical* |
`static java.lang.String` | `getBaseURL(java.lang.String requestURL, java.lang.String requestURI, java.lang.String contextPath)`*Get the BaseURL.* |
`static java.lang.String` | `getMD5Hex(java.lang.String inputString)` *This method could be used for hashing*
`static int` | 	`getRandomOrderId()` *Generate random order ID*
`java.lang.String` | `IPN_Notification(java.lang.String type, java.lang.String nbcb, java.lang.String skey, java.lang.String tranID, java.lang.String domain, java.lang.String status, java.lang.String amount, java.lang.String currency, java.lang.String paydate, java.lang.String orderid, java.lang.String appcode, java.lang.String error_code, java.lang.String error_desc, java.lang.String channel, java.lang.String extraP)` *Request parameters*
`java.lang.String` | `IPN_Return(java.lang.String type, java.lang.String skey, java.lang.String tranID, java.lang.String domain, java.lang.String status, java.lang.String amount, java.lang.String currency, java.lang.String paydate, java.lang.String orderid, java.lang.String appcode, java.lang.String error_code, java.lang.String error_desc, java.lang.String channel, java.lang.String extraP)` *Request parameters*
`static java.lang.String` | `md5(java.lang.String input)` *This method could be used for hashing*
`void` | `TrustCert() `
`static java.lang.String` | `url(java.lang.String input, java.lang.String merchantid)`*Get the current redirect URL based on demo type`*

### Constructor Detail
------
###### molpay
`public molpay()`

### Method Detail
------
##### md5
`public static java.lang.String md5(java.lang.String input) throws java.security.NoSuchAlgorithmException`

> ###### Parameters:
*input - String text*
> ###### Returns:
*MD5 Hashing*

> ###### Throws:
`java.security.NoSuchAlgorithmException`

------

##### getMD5Hex
`public static java.lang.String getMD5Hex(java.lang.String inputString) throws java.security.NoSuchAlgorithmException`

Either two of method could be used for verification

> ###### Parameters:
*input - String text*
> ###### Returns:
*MD5 Hashing*

> ###### Throws:
`java.security.NoSuchAlgorithmException`

------

##### getRandomOrderId
`public static int getRandomOrderId()`


> ###### Returns:
*Get random order ID between 0-10000*


------

##### url
`public static java.lang.String url(java.lang.String input,
                                   java.lang.String merchantid)`


> ###### Parameters:
*input - either sandbox or production*

*merchantid - Merchant ID*
> ###### Returns:
*Get the current redirect URL based on demo type e.g. usage for form action*

> ###### Throws:
`java.security.NoSuchAlgorithmException`

------

##### getBaseURL
`public static java.lang.String getBaseURL(java.lang.String requestURL,
                                          java.lang.String requestURI,
                                          java.lang.String contextPath))`


> ###### Parameters:
*requestURL - http://localhost:8080/test/uriTest.jsp*

*requestURI - /test/uriTest.jsp*

*contextPath - Request Context Path: /test*
> ###### Returns:
Get the BaseURL. The payment page could be obtain using method 
e.g return values: URL without filename https://localhost/root/folder/payment/

------

##### generateKeys
`public static java.lang.String generateKeys(java.lang.String tranID,
                                            java.lang.String orderid,
                                            java.lang.String status,
                                            java.lang.String domain,
                                            java.lang.String amount,
                                            java.lang.String currency,
                                            java.lang.String paydate,
                                            java.lang.String appcode,
                                            java.lang.String vkey)
                                     throws java.security.NoSuchAlgorithmException`


> ###### Parameters:
*input - either sandbox or production*

merchantid - Merchant ID
> ###### Returns:
*Hashed String - Key1 : Hashstring generated on Merchant system either merchant or domain could be one from POST and one that predefined internally by right both values should be identical*

> ###### Throws:
`java.security.NoSuchAlgorithmException`

------

##### TrustCert
`public void TrustCert()`


> ###### Issues:
*Need for Java to trust SSL certificate*

------

##### IPN_Return
`public java.lang.String IPN_Return(java.lang.String type,
                                   java.lang.String skey,
                                   java.lang.String tranID,
                                   java.lang.String domain,
                                   java.lang.String status,
                                   java.lang.String amount,
                                   java.lang.String currency,
                                   java.lang.String paydate,
                                   java.lang.String orderid,
                                   java.lang.String appcode,
                                   java.lang.String error_code,
                                   java.lang.String error_desc,
                                   java.lang.String channel,
                                   java.lang.String extraP)
                            throws java.lang.Exception`


> ###### Parameters:
*Request parameters*

**type** - *Demo type*

**skey** - *This is the data integrity protection hash string*

**tranID** - *Generated by MOLPay System. Integer, 10 digits. Unique transaction ID for tracking purpose*

**domain** - *Merchant ID in MOLPay system.*

**status** - *2-digit numeric value, 00 for Successful payment; 11 for failure; 22 for pending.*

**amount** - *2 decimal points numeric value. The total amount paid or to be paid for CASH payment request.*

**currency** - *2 or 3 chars (ISO-4217) currency code. Default currency is RM (indicating MYR) for Malaysia channels*

**paydate** - *Date/Time( YYYY-MM-DD HH:mm:ss). Date/Time of the transaction*

**orderid** - *Alphanumeric, 32 characters. Invoice or order number from merchant system.*

**appcode** - *Alphanumeric, 16 chars. Bank approval code. Mandatory for Credit Card. Certain channel returns empty value*

**error_code** - *Error Codes section*

**error_desc** - *Error message or description*

**channel** - *Predefined string in MOLPay system. Channel references for merchant system*

**extraP** - *JSON encoded string or array*

**merchantid** - *Merchant ID*

> ###### Returns:
URL String (Sent)

> ###### Throws:
`java.lang.Exception`

------

##### CheckDemo
`public java.lang.String CheckDemo(java.lang.String type)`


> ###### Parameters:
type - get demo type to either sandbox or production demo

merchantid - Merchant ID
> ###### Returns:
demo type

------

##### IPN_Notification
`public java.lang.String IPN_Return(java.lang.String type,
                                   java.lang.String skey,
                                   java.lang.String tranID,
                                   java.lang.String domain,
                                   java.lang.String status,
                                   java.lang.String amount,
                                   java.lang.String currency,
                                   java.lang.String paydate,
                                   java.lang.String orderid,
                                   java.lang.String appcode,
                                   java.lang.String error_code,
                                   java.lang.String error_desc,
                                   java.lang.String channel,
                                   java.lang.String extraP)
                            throws java.lang.Exception`


> ###### Parameters:
*Request parameters for Notification IPN*

**type** - *Demo type*

**nbcb** - *Always equal to 2​, which indicates this is a notification from MOLPay*

**skey** - *This is the data integrity protection hash string*

**tranID** - *Generated by MOLPay System. Integer, 10 digits. Unique transaction ID for tracking purpose*

**domain** - *Merchant ID in MOLPay system.*

**status** - *2-digit numeric value, 00 for Successful payment; 11 for failure; 22 for pending.*

**amount** - *2 decimal points numeric value. The total amount paid or to be paid for CASH payment request.*

**currency** - *2 or 3 chars (ISO-4217) currency code. Default currency is RM (indicating MYR) for Malaysia channels*

**paydate** - *Date/Time( YYYY-MM-DD HH:mm:ss). Date/Time of the transaction*

**orderid** - *Alphanumeric, 32 characters. Invoice or order number from merchant system.*

**appcode** - *Alphanumeric, 16 chars. Bank approval code. Mandatory for Credit Card. Certain channel returns empty value*

**error_code** - *Error Codes section*

**error_desc** - *Error message or description*

**channel** - *Predefined string in MOLPay system. Channel references for merchant system*

**extraP** - *JSON encoded string or array*

**merchantid** - *Merchant ID*

> ###### Returns:
URL String (Sent)

> ###### Throws:
`java.lang.Exception`

### Class controller
------
`public class controller
extends java.lang.Object`
#### Constructor Summary
###### Constructors

`controller() `

### 
------
##### All Methods
###### Static Methods
###### Instance Methods
###### Concrete Methods
------

| Modifier and Type | Method and Description |
| ------------- |:-------------:| 
| `java.lang.String` | 	`getAmount() `|
| `java.lang.String` | 	`getAppcode()  `|
| `java.lang.String` | 	`getChannel() `|
| `java.lang.String` | 	`getCurrency()`|
| `java.lang.String` | 	`getDemo_type()`|
| `java.lang.String` | 	`getDomain() `|
| `java.lang.String` | 	`getError_code()`|
| `java.lang.String` | 	`getError_desc() `|
| `java.lang.String` | 	`getExtraP() `|
| `java.lang.String` | 	`getMerchantid() `|
| `java.lang.String` | 	`getNbcb()  `|
| `java.lang.String` | 	`getOrderid() `|
| `java.lang.String` | 	`getPaydate() `|
| `java.lang.String` | 	`getSecretkey()  `|
| `java.lang.String` | 	`getSkey()  `|
| `java.lang.String` | 	`getStatus()  `|
| `java.lang.String` | 	`getTranID()  `|
| `java.lang.String` | 	`getTreq()  `|
| `java.lang.String` | 	`getVkey()   `|
| `void` | 	`setAmount(java.lang.String amount)  `|
| `void` | 	`setAppcode(java.lang.String appcode)  `|
| `void` | 	`setChannel(java.lang.String channel)  `|
| `void` | 	`setCurrency(java.lang.String currency)  `|
| `void` | 	`setDemo_type(java.lang.String demo_type)  `|
| `void` | 	`setDomain(java.lang.String domain) `|
| `void` | 	`setError_code(java.lang.String error_code)  `|
| `void` | 	`setError_desc(java.lang.String error_desc)  `|
| `void` | 	`setExtraP(java.lang.String extraP)  `|
| `void` | 	`setNbcb(java.lang.String nbcb) `|
| `void` | 	`setOrderid(java.lang.String orderid) `|
| `void` | 	`setPaydate(java.lang.String paydate)  `|
| `void` | 	`setSkey(java.lang.String skey)  `|
| `void` | 	`setStatus(java.lang.String status) `|
| `void` | 	`setTranID(java.lang.String tranID)  `|
| `void` | 	`setTreq(java.lang.String treq)  `|


###### Methods inherited from class java.lang.Object
*clone, equals, finalize, getClass, hashCode, notify, notifyAll, toString, wait, wait, wait*


Support
-------

Merchant Technical Support / Customer Care : support@molpay.com <br>
Marketing Campaign : marketing@molpay.com <br>
Channel/Partner Enquiry : channel@molpay.com <br>
Media Contact : media@molpay.com <br>
R&D and Tech-related Suggestion : technical@molpay.com <br>
Abuse Reporting : abuse@molpay.com

Disclaimer
----------
Any amendment by your end is at your own risk.

Changelog
----------
1. 2018-04-20 - v1.0.0 - Initial Release


