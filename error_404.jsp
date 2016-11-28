<%--
October 1, 2014
Copyright © 2014 Alex Simoneaux, Chad Ryder
Elon University, Elon, NC, 27244
--%>

<%--
Custom error page displayed any time an improper url is entered
--%>

<%@page contentType="text/html" pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>CSC330 Homework 2</title>
        <link rel="stylesheet" href="styles/main.css" type="text/css"/>
    </head>
    <body>
        <h2>404 Error</h2>
        <div id="error_p">
            <p>The server was not able to find the file you requested.</p>
            <p>To continue, click the Back button.</p>
        </div>
        <%@include file="/includes/footer.jsp" %>
    </body>
</html>
