## Integrating MOLPay with Seamless JAVA SDK
Version 1.0.1 (Seamless Version)

> Library Dependecies

Java-SDK/Seamless Integration/gson-2.8.0.jar

https://github.com/MOLPay/Java-SDK/raw/master/Seamless%20Integration/gson-2.8.0.jar

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

1. Download `Java-SDK-MOLPay.zip` file.
2. Open Netbeans, Click File > Import Project.. > From zip, Select the netbeans.zip


### Usage

> Change Development or Production ID 

  In molpay.java

```JAVA
public static String mpsmerchantid = "SB_supportmolpay"; // Merchant ID (Replace with your merchant id)
public static String vkey = "xxx"; // Verify Keys (Replace with your verify key)
public static String secretkey = "xxx"; // Secret keys (Replace with your secret keys) 
```

> Import the SDK on JSP file


```JSP
<%@page import="molpay.molpay" %>
```


> Configuration page can set either sandbox or production on **index.jsp**

```JAVA
public static String demo_type = "sandbox"; // or production
```

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
1. 2018-05-02 - v1.0.0 - Initial Release Java-SDK Seamless Integration
2. 2018-05-04 - v1.0.1 - Added 5 Requery & Added Merchant website API for requesting MOLPay API & Modify SDK



