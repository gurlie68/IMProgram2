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


class InsufficientFunds extends Exception {

    InsufficientFunds(String message){
       /*super to handle exception msg*/
        super(message);
    }

}