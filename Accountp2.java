/* Project 2 ATM
 * Nancy C Fain
 * June 16, 2018
 * This project was writing a program that implements and ATM Machine.
 * Specific elements included Withdraw. Savings, Transfer, and Balance Buttons
 * Radio buttons to select between checking and savings accounts.
 * Service fee for w/d transactions greater than 4 regardless if they were in one account or split.
 * Must be in Increments of 20
 * Insufficient Funds exceptions
 */

public class Account {

    private double balance;

    /*set account balance for testing to 500
    * Set to zero and use deposit function to put money in both checking and savings*/
    Account() {
        balance=0;
    }

    /*Account Methods for Balances after Transactions*/
    public void withdraw(double amount) {
        balance -= amount;
    }

    public void deposit(double amount) {
        balance += amount;
    }

    public void transfer(double amount) {
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }

    /*Getter for Service Charge*/
    public double getServiceCharge() {
        double serviceCharge = 1.5;
        return serviceCharge;

    }/*end main*/
}/*end class*/