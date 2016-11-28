/*
October 1, 2014
Copyright © 2014 Alex Simoneaux, Kaylin Hunter
Elon University, Elon, NC, 27244
*/

/*
Servlet that controls the flow of actions in the application.
Upon first calling the app, it defaults to the index.jsp page,
when the form is submitted, it then stores the values entered
into a User object, and redirects to the calculated.jsp page.
*/

package edu.elon.controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.util.GregorianCalendar;
import java.util.Calendar;
import edu.elon.business.User;

@WebServlet("/fvcs")
public class FutureValueCalculatorServlet extends HttpServlet {
    
    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        String url = "";
        
        GregorianCalendar currentDate = new GregorianCalendar();
        int currentYear = currentDate.get(Calendar.YEAR);
        request.setAttribute("currentYear", currentYear);
        
        // get current action
        String action = request.getParameter("action");
        if (action == null) {
            action = "start";  // default action
        }
        
        // perform action and set URL to appropriate page
        if (action.equals("start")) {
            url = "/index.jsp";
        }
        else if (action.equals("add")) {
            // get parameters from the request
            String investmentAmount = request.getParameter("investmentAmount");
            String yearlyInterestRate = request.getParameter("yearlyInterestRate");
            String numberOfYears = request.getParameter("numberOfYears");
            
            // store data in User object
            User user = new User(investmentAmount, yearlyInterestRate, numberOfYears);
            
            // validate the parameters
            if (investmentAmount == null || yearlyInterestRate == null || numberOfYears == null ||
                    investmentAmount.isEmpty() || yearlyInterestRate.isEmpty() || numberOfYears.isEmpty()) {
                url = "/index.jsp";
            }
            else {
                request.setAttribute("user", user);
                url = "/calculated.jsp";
            }
           
        }
         getServletContext()
                    .getRequestDispatcher(url)
                    .forward(request, response);
    }
    
    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }
}