package demo;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Hashtable;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

class BankTest {


    String fileName = "CS 3331 - Bank Users 4.csv";
    BankHT bht = new BankHT(fileName);
    Hashtable<String, Customer> bankUsersByName = bht.getUsersByNameHT();

    /***
     //assertTrue
     //assertFalse  */

    @org.junit.jupiter.api.Test
    void transfer() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        int money = -500;
        String accType = "Checking";
        String toAcc = "Saving";
        Customer test1 = new Customer();
        test1.transfer(money,accType, toAcc,false);

        String expected = "Invalid number you can not transfer negative values\n";
        assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void transfer2() {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        int money = 100;
        String accType = "Checking";
        String toAcc = "Savings";
        String first = "Laura";
        String last = "Blanco";
        String dob = "November 22 1990";
        int identity = 1;
        String address = "5000 Schuster";
        String phone = "9159000";
        Checking check = new Checking(4,200.0,true);
        Savings save = new Savings(5,800.0,true);
        Credit credit = new Credit(6,-250.0,5000,true);
        Customer test1 = new Customer(first,last,dob,identity,address,phone,"hi","laura@mail.com",check,save,credit);
        test1.transfer(money,accType, toAcc,false);

        String expected = "Successfully transferred: 100.0\nNew account balance in Checking: 100.0\nNew account balance in Savings: 900.0\n";
        assertEquals(expected, outContent.toString());
    }


    @Test
    @DisplayName("Test make sure getters are right values.")
    void assertGet() {
        //Variables and objects
        Customer savedAcc = bankUsersByName.get("mickeymouse");

        assertEquals("Mickey", savedAcc.getFirstName());
        assertEquals("Mouse", savedAcc.getLastName());
        assertEquals("Mouse*Mickey!987", savedAcc.getPassword());
        assertEquals("(714) 781-4636", savedAcc.getPhoneNum());
        assertEquals(1, savedAcc.getIdentificationNum());
        assertEquals(2854.17, savedAcc.getCheckBegBal());
        assertEquals(3940.92, savedAcc.getSavBegBal());
        assertEquals(-1132.03, savedAcc.getCredBegBal());
        assertEquals("MickeyMouse@disney.com", savedAcc.getEmail());
        assertEquals("\"July 21, 1941\"", savedAcc.getDOB());
        assertEquals("\"1313 Disneyland Dr, Anaheim, CA 92802\"", savedAcc.getAddress());


        //Create savings object
        Savings userSavings = savedAcc.getSaving();
        assertEquals(2000, userSavings.getAccountNumber());

        Checking userCheckings = savedAcc.getCheck();
        assertEquals(1000, userCheckings.getAccountNumber());

        Credit userCredit = savedAcc.getCredit();
        assertEquals(3000, userCredit.getAccountNumber());
        assertEquals(5122, userCredit.getMaxBal());

    }



    @org.junit.jupiter.api.Test
    void payMon() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Customer Acc1 = bankUsersByName.get("mickeymouse");


        Acc1.paySomeone(100,Acc1,"checking","checking",false);

        String expected = "Cannot pay yourself\n";


        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void payMoreMoney() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Customer Acc1 = bankUsersByName.get("mickeymouse");
        Customer Acc2 = bankUsersByName.get("donaldduck");


        Acc1.paySomeone(1000000000,Acc2,"checking","checking",false);

        String expected = "Unable to pay due to insufficient funds\n";


        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void payNeg() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Customer Acc1 = bankUsersByName.get("mickeymouse");
        Customer Acc2 = bankUsersByName.get("donaldduck");
        Acc1.paySomeone(-10,Acc2,"checking","checking",false);

        String expected = "Invalid number you can not pay negative values\n";
        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void printInfo() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        Customer Acc1 = bankUsersByName.get("mickeymouse");
        BankManagerModel model = new BankManagerModel();

        model.printInquiredInfo(Acc1);

        String expected = "User: Mickey Mouse\n" +
                "\n" +
                "First Name: Mickey\n" +
                "Last Name: Mouse\n" +
                "Password: Mouse*Mickey!987\n" +
                "Date of Birth: July 21, 1941\n" +
                "Identification Number: 1\n" +
                "Address: 1313 Disneyland Dr, Anaheim, CA 92802\n" +
                "Phone Number: (714) 781-4636\n" +
                "Email: MickeyMouse@disney.com\n" +
                "Checking Account Number: 1000\n" +
                "Checking Account Balance: 2854.17\n" +
                "Savings Account Number: 2000\n" +
                "Savings Account Balance: 3940.92\n" +
                "Credit Account Number: 3000\n" +
                "Credit Account Balance: -1132.03\n" +
                "Credit Max: 5122.0\n";
        assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void newUserVerify() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BankManagerModel model = new BankManagerModel();

        model.verifyEnterName(bankUsersByName,"", "Blanco");

        String expected = "\ndemo.BankException: First name of last name is blank.\n";
        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void userExists() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BankManagerModel model = new BankManagerModel();

        model.verifyEnterName(bankUsersByName,"Mickey", "Mouse");

        String expected = "\n" +
                "demo.BankException: User/account already exist. Please, try again.\n";
        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void allowedNewUser() {
        BankManagerModel model = new BankManagerModel();

        assertTrue(model.verifyEnterName(bankUsersByName,"Mickey", "Mine"));
    }


    @org.junit.jupiter.api.Test
    void verifyDOB() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BankManagerModel model = new BankManagerModel();

       model.verifyEnterDOB("1","2", "99","3");
        String expected =
                "\ndemo.BankException: Date is not in the correct format (mm/dd/yyyy).\n";
        assertEquals(expected, outContent.toString());
    }


    @org.junit.jupiter.api.Test
    void verifyDOB2() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        BankManagerModel model = new BankManagerModel();

        model.verifyEnterDOB("12","08", "1999","MM/dd-yyyy");
        String expected =
                "\n" +
                        "ParseException: Date is not valid (mm/dd/yyyy)\n";
        assertEquals(expected, outContent.toString());
    }

    @org.junit.jupiter.api.Test
    void verifyDOB3() {

        BankManagerModel model = new BankManagerModel();

        assertTrue(model.verifyEnterDOB("12","08", "1999","MM-dd-yyyy"));

    }



    @org.junit.jupiter.api.Test
    void verifyKeyUser() {

        UserInterface interfaceUser = new UserInterface();

        assertTrue(interfaceUser.verifyKey(bankUsersByName, "mickeymouse"));
    }


    @org.junit.jupiter.api.Test
    void verifyKeyUser1() {

        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        UserInterface interfaceUser = new UserInterface();

        interfaceUser.verifyKey(bankUsersByName, "lalablanco");

        String expected =
                "\ndemo.BankException: User/account doesn't exist. Please, try again.\n";

        assertEquals(expected, outContent.toString());
    }

}

