
import java.awt.Font;
import java.sql.SQLException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ATMmod {

    Scanner sc;
    Conn cObj;
    Font fontt = new Font("Times New Roman", 22, Font.BOLD);
    boolean validCustomer;

    ////////////////////constructor
    public ATMmod() throws SQLException {
        cObj = new Conn();
    }

    public void loginNew() throws Exception {
        sc = new Scanner(System.in);
        System.out.println("Enter the Customer Name: ");
        String cName = sc.next();
        System.out.println("Enter the password");
        String cPass = sc.next();
        validCustomer = cObj.login(cName, cPass);
        if (validCustomer) {
            System.out.println("__LOGIN SUCCESSFUL__");
            System.out.println("ACCOUNT HOLDER:" + cName);
            System.out.println("Customer ID:" + cObj.customerID);
            displayMenu();
        } else {
            System.out.println("Login Failed!, Try Again....");
            loginNew();
        }
    }

    ////////////////////////constuctor end
    public void displayMenu() throws SQLException {
        int choice = 0;
        boolean validChoice = false;

        System.out.println("---------------------------------------MENU-------------------------------------");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("|\t1.BALANCE\t2.WITHDRAW\t3.DEPOSIT\t4.LOGOUT\t\t|");
        System.out.println("|\t\t\t\t\t\t\t\t\t\t|");
        System.out.println("--------------------------------------------------------------------------------");
        System.out.println("enter the number <1,2,3,4>");
        while (!validChoice) {
            try {
                choice = sc.nextInt();
                validChoice=true;
            } catch (Exception e) {
                System.out.println("invalid input! please enter only digits from 1 - 4");
                sc.nextLine();
            }
        }

        switch (choice) {
            case 1:
                checkBalance();
                break;
            case 2:
                withdrawMoney();
                break;
            case 3:
                depositMoney();
                break;
            case 4:
                logout();
                break;
            default:
                System.out.println("Invalid choice");
                break;
        }
    }

    public void checkBalance() throws SQLException {
        int balance = cObj.getBalance(cObj.customerID);
        System.out.println("Current Balance:" + balance);
        displayMenu();
    }

    public void withdrawMoney() throws SQLException {
        boolean validInput=false;
        boolean flag;
        System.out.println("Enter the withdrawl Amount");
        int wAm=0;
        while(!validInput){
            try{
                wAm = sc.nextInt();//here Exception may be generated
                if (wAm>0)
                    validInput=true;
                else
                    System.out.println("Enter only positive numbers(greater than 0)");
            }
            catch(Exception e){
                System.out.println("Please Enter Only Numbers for WithDraw Amount:");
                sc.nextLine();
            }
        }
        
        
        flag = cObj.withdrawMoney(cObj.customerID, wAm);
        if (flag) {
            System.out.println("Money Debited:" + wAm);
        } else {
            System.out.println("Not Enough money in the Account");
        }
        checkBalance();
        displayMenu();
    }

    public void depositMoney() throws SQLException {
        int dAm = 0;
        System.out.println("Enter the Amount to deposit");
        boolean validInput=false;
        while(!validInput){
            try{
                dAm = sc.nextInt();
                if (dAm>0)
                    validInput=true;
                else
                    System.out.println("invalid Input, enter the amount greater than 0:");
            }
            catch(Exception e){
                System.out.print("Invalid Input, Enter only numbers:");
                sc.nextLine();
            }
        }
        
        boolean flag = cObj.depositMoney(cObj.customerID, dAm);
        if (flag) {
            System.out.println("Money Credited:" + dAm);
        }
        checkBalance();
        displayMenu();
    }

    public void logout() {
        System.out.println("LOGGED OUT SUCCESSSFULLY");
        System.exit(0);
    }

    public static void main(String[] args) throws SQLException, Exception {
        ATMmod atm = new ATMmod();
        atm.loginNew();
    }
}
