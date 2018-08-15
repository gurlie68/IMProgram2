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



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class ATM extends JFrame implements ActionListener {
    private static String input;

    /*instance variables*/

    private JRadioButton checking,savings;
    private JTextField textField;
    private Account checkingAccount,savingsAccount;
    private static int withdrawalNum;

    private static String accountSelected = "";

    /*ATM  Class*/
    private ATM(){

        /*Title*/
        super("ATM ");

        /*Layout for panel*/
        setLayout(new FlowLayout(FlowLayout.CENTER,100,20));
        JPanel buttonPanel = new JPanel ( new GridLayout ( 2, 2, 5, 10 ) );
        JPanel radiobuttonPanel = new JPanel ( new GridLayout ( 1, 2 ) );
        JPanel txtPanel = new JPanel ( new GridLayout ( 2, 2 ) );
        
        /* Action buttons construct and add to button panel*/
        JButton withdraw = new JButton ( "Withdraw" );
        withdraw.addActionListener(this);
        buttonPanel.add( withdraw );

        JButton deposit = new JButton ( "Deposit" );
        deposit.addActionListener(this);
        buttonPanel.add( withdraw );

        JButton transfer = new JButton ( "Transfer To" );
        transfer.addActionListener(this);
        buttonPanel.add( withdraw );

        JButton balance = new JButton ( "Balance" );
        balance.addActionListener(this);
        buttonPanel.add( balance );

        buttonPanel.add( withdraw );
        buttonPanel.add( deposit );
        buttonPanel.add( transfer );
        buttonPanel.add( balance );

        /*Radio Buttons construct and add to radio button panel*/

        checking =new JRadioButton("Checking");
        savings=new JRadioButton("Savings");

        ButtonGroup buttonGroup = new ButtonGroup ();
        buttonGroup.add(checking);
        buttonGroup.add(savings);

        radiobuttonPanel.add(checking);
        radiobuttonPanel.add(savings);

       /*Text Input Field*/

        textField=new JTextField(20);
        txtPanel.add(textField);

        /*Put it all together*/
        add( buttonPanel );
        add( radiobuttonPanel );
        add( txtPanel );

        /*Checking and Savings Object to use with Radio Buttons*/

        checkingAccount=new Account();
        savingsAccount=new Account();

        /*Withdrawal set to 0 for Counter*/
        withdrawalNum=0;//to keep track the number of withdrawals

    } /*end GUI*/

    public static void main(String args[]) {
        
        /*Construct ATM*/

    ATM atm=new ATM();
        atm.setSize(350,250);
        atm.setVisible(true); /*if set to false, you can't see it*/
    }

    public void actionPerformed(ActionEvent ae) {

        switch (ae.getActionCommand ()) {
            /*use switch to move through each button and set methods*/
            
            case "Withdraw":
                try {
                    if (!isNumeric ( textField.getText () ))
                        JOptionPane.showMessageDialog ( this, "Please Enter a Numeric Amount", 
                                "Notice: User Input Error", JOptionPane.ERROR_MESSAGE );
                    else if (Double.parseDouble ( textField.getText () ) % 20 != 0)
                        JOptionPane.showMessageDialog ( this, "Amount should be increments of $20", 
                                "Notice:  User Input Error", JOptionPane.ERROR_MESSAGE );
                    else {
                        double amount = Double.parseDouble ( textField.getText () );

                        /*Service Charge Method*/

                        if (withdrawalNum == 4)  {
                            if (checking.isSelected ()) {
                                if (checkingAccount.getBalance () < amount + checkingAccount.getServiceCharge ())
                                    throw new InsufficientFunds ( "Insufficient Funds" );

                                else {
                                    checkingAccount.withdraw ( amount + checkingAccount.getServiceCharge () );
                                    JOptionPane.showMessageDialog ( this, "Your Withdrawal Has Been Processed" );
                                    withdrawalNum = 0;
                                }
                            } else {
                                if (savingsAccount.getBalance () < amount + savingsAccount.getServiceCharge ())
                                    throw new InsufficientFunds ( "Insufficient Funds" );

                                else {
                                    savingsAccount.withdraw ( amount + savingsAccount.getServiceCharge () );
                                    JOptionPane.showMessageDialog ( this, "Your Withdrawal Has Been Processed" );
                                    withdrawalNum = 0;
                                }
                            }
                        } else {
                            if (checking.isSelected ()) {
                                if (checkingAccount.getBalance () < amount)
                                    throw new InsufficientFunds ( "Insufficient Funds" );

                                else {
                                    checkingAccount.withdraw ( amount );
                                    JOptionPane.showMessageDialog ( this, "Your Withdrawal Has Been Processed " );
                                    withdrawalNum++;
                                }
                            } else {

                                if (savingsAccount.getBalance () < amount)
                                    throw new InsufficientFunds ( "Insufficient Funds" );

                                else {
                                    savingsAccount.withdraw ( amount );
                                    JOptionPane.showMessageDialog ( this, "Your Withdrawal Has Been Processed " );
                                    withdrawalNum++;
                                }
                            }
                        }
                    }
                } catch (InsufficientFunds e) {
                    JOptionPane.showMessageDialog ( null, e.getMessage () );
                }
                textField.setText ( "" );
                break;


            /*End Withdraw Code and Begin Deposit and Transfer*/
            case "Deposit":
                if (!isNumeric ( textField.getText () ))
                   JOptionPane.showMessageDialog ( this, "Please Enter a Numeric Amount", 
                           "Notice:  User Input Error", JOptionPane.ERROR_MESSAGE );
                else {
                    double amount = Double.parseDouble ( textField.getText () );
                    if (checking.isSelected ()) {
                        checkingAccount.deposit ( amount );
                        JOptionPane.showMessageDialog ( null, "Your Deposit Has Been Processed" );
                        } else {
                        savingsAccount.deposit ( amount );
                        JOptionPane.showMessageDialog ( null, "Your Deposit Has Been Processed" );
                    }
                }
                textField.setText ( "" );
                break;

            case "Transfer To":
                if (!isNumeric ( textField.getText () ))
                    JOptionPane.showMessageDialog ( this, "Please Enter a Numeric Amount", 
                            "Notice:  User Input Error", JOptionPane.ERROR_MESSAGE );
                else {
                    try { 
                        double amount = Double.parseDouble ( textField.getText () );
                        if (checking.isSelected ()) {
                            if (savingsAccount.getBalance () < amount)
                                throw new InsufficientFunds ( "Insufficient Funds" );
                            else {
                                savingsAccount.transfer ( amount );
                                checkingAccount.deposit ( amount );
                                JOptionPane.showMessageDialog ( null, "Your Transfer Has Been Processed" );
                            }
                        } else {
                            if (savingsAccount.getBalance () < amount)
                                throw new InsufficientFunds ( "Insufficient Funds" );
                            else {
                                checkingAccount.transfer ( amount );
                                savingsAccount.deposit ( amount );
                                JOptionPane.showMessageDialog ( null, "Your Transfer Has Been Processed" );
                            }
                        }

                    } catch (InsufficientFunds ex) {
                        JOptionPane.showMessageDialog ( null, ex.getMessage () );
                    }
                }

                textField.setText ( "" );
                break;

                /*Balance Method*/

            case "Balance":
                if (checking.isSelected ()) {
                    JOptionPane.showMessageDialog ( this, String.format ( "Checking Account Balance is: $%s", 
                            checkingAccount.getBalance () ) );

                } else if (savings.isSelected ()) {
                    JOptionPane.showMessageDialog ( this, String.format ( "Savings Account Balance is: $%s",
                            savingsAccount.getBalance () ) );
                }
                break;
        } /*end Switch*/
    } /*end Action Event (ae)*/

    /*Requirement for Numeric Input*/
    private static boolean isNumeric(String input) {
        ATM.input = input;
        try {
            double d = Double.parseDouble(input);
        } catch(NumberFormatException nfe) {
            return false;
        }
        return true;
    } /*end numeric*/
}/*end class*/

