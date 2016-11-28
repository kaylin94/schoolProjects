<%--
October 1, 2014
Copyright © 2014 Alex Simoneaux, Chad Ryder
Elon University, Elon, NC, 27244
--%>

<%--
Inital page shown when loading the application. Forwarded to this
page from the servlet as a default action. User will enter values
in the associated input fields, and, upon clicking "Calculate", will
be redirected to the calculated.jsp page through the servlet.
--%>

<!DOCTYPE html>
<html>
    <head>
        <title>CSC330 Homework 2</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h1>Future Value Calculator</h1>     
        <form action="fvcs" method="post">
            <input type="hidden" name="action" value="add">        
            <label>Investment Amount:</label>
            <input type="text" name="investmentAmount" value="" pattern="\d+(\.\d*)?" required><br>
            <label>Yearly Interest Rate:</label>
            <input type="text" name="yearlyInterestRate" value="" pattern="\d+(\.\d*)?" required><br>
            <label>Number of Years:</label>
            <input type="text" name="numberOfYears" value="" placeholder="Integer number of years" pattern="\d+(\.\d*)?" required><br>        
            <label>&nbsp;</label>
            <input id="button" type="submit" value="Calculate" class="margin_left">
        </form>
        <%@include file="/includes/footer.jsp" %>
    </body>
</html>


