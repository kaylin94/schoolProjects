<%--
October 1, 2014
Copyright © 2014 Alex Simoneaux, Chad Ryder
Elon University, Elon, NC, 27244
--%>

<%--
Page that is accessed by entering values in the index.jsp page and forwarded by
the FutureValueCalculatorServlet.

Uses jsp:useBean and jsp:getProperty tags to access the user object created
in the servlet to present those values on this webpage.
--%>

<jsp:useBean id="user" scope="request" class="edu.elon.business.User"/>
<!DOCTYPE html>
<html>
    <head>
        <title>CSC330 Homework 2</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h1>Future Value Calculator</h1>
        <table>
            <tr>
                <td>Investment Amount:</td>
                <td id="col2"><jsp:getProperty name="user" property="investmentAmount"/></td>
            </tr>
            <tr>
                <td>Yearly Interest Rate:</td>
                <td id="col2"><jsp:getProperty name="user" property="yearlyInterestRate"/></td>               
            </tr>
            <tr>
                <td>Number of Years:</td>
                <td id="col2"><jsp:getProperty name="user" property="numberOfYears"/></td>               
            </tr>
            <tr>
                <td>Future Value:</td>
                <td id="col2"><jsp:getProperty name="user" property="futureValue"/></td>                
            </tr>
        </table>
        <%@include file="/includes/footer.jsp" %>
    </body>
</html>


