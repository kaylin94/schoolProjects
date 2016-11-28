/*
October 1, 2014
Copyright © 2014 Alex Simoneaux, Kaylin Hunter
Elon University, Elon, NC, 27244
*/

/*
User object that stores variables entered by the user in
the index.jsp page:

-investmentAmount
-yearlyInterestRate
-numberOfYears

It uses these values to calculate the future value. This
value is accessed in the calculated.jsp page and displayed
to the screen.
*/

package edu.elon.business;

import java.io.Serializable;
import java.text.DecimalFormat;

public class User implements Serializable {
    
    private String investmentAmount;
    private String yearlyInterestRate;
    private String numberOfYears;
    private String futureValue;
    
    public User() {
        investmentAmount = "";
        yearlyInterestRate = "";
        numberOfYears = "";
    }
    
    public User(String investmentAmount, String yearlyInterestRate, String numberOfYears) {
        this.investmentAmount = investmentAmount;
        this.yearlyInterestRate = yearlyInterestRate;
        this.numberOfYears = numberOfYears;
        this.futureValue = calculate();
    }
    
    public String getInvestmentAmount() {
        return "$" + addCommas(Double.parseDouble(investmentAmount));
    }
    
    public void setInvestmentAmount(String investmentAmount) {
        this.investmentAmount = investmentAmount;
    }
    
    public String getYearlyInterestRate() {
        return yearlyInterestRate;
    }
    
    public void setYearlyInterestRate(String yearlyInterestRate) {
        this.yearlyInterestRate = yearlyInterestRate;
    }
    
    public String getNumberOfYears(){
        return numberOfYears;
    }
    
    public void setNumberOfYears(String numberOfYears) {
        this.numberOfYears = numberOfYears;
    }
    
    public String calculate(){
        double iA = Double.parseDouble(this.investmentAmount);
        double yIR =  Double.parseDouble(this.yearlyInterestRate)/100;
        double nOY =    Double.parseDouble(this.numberOfYears);
        
        double fV = roundTwoDecimals(iA*Math.pow((1+yIR),nOY));
        String futureV = "" + fV;
        return futureV;
    }
    
    public String getFutureValue(){
        return "$" + addCommas(Double.parseDouble(futureValue));
    }
    
    public double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }
    
    public String addCommas(double num){
        DecimalFormat df = new DecimalFormat("#,###.00");
        return df.format(num);
    }
}
