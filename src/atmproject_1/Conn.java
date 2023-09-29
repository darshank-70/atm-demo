
import java.sql.*;
public class Conn {
    Connection con;
    Statement stmt;
    ResultSet rs;
     int customerID;
    private int balance;
    public Conn(){
        String url="jdbc:mysql://localhost:3306/atmdb",//jdbc:mysql://localhost:3306/mysql
               userID="root",
               password="root123";
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            con=DriverManager.getConnection(url, userID, password);
            if(con!=null)
                System.out.println("Successfully Connected");
            
            /*  Statement sss=con.createStatement();
            ResultSet r=sss.executeQuery("Select * from customer");
            r.next();
            System.out.println(r.getString("CustomerName"));*/
        }
        catch(ClassNotFoundException | SQLException e){
            System.out.println(e);
        }
        
    }
    public boolean login(String name,String password ) throws Exception{
        String loginQuery="SELECT * FROM customer WHERE CustomerName='"+name+ "' and CustomerPassword='"+password+"'";
        stmt=con.createStatement();
        rs=stmt.executeQuery(loginQuery);
        if (rs.next()){
            customerID=rs.getInt("CustomerID");
            return true;
        }
        else
            return false;
            
    }
    
    public int getBalance(int id) throws SQLException{
        String balanceQuery="SELECT BalanceAmount from ACCOUNT where CustomerID="+id;
        rs=stmt.executeQuery(balanceQuery);
        if(rs.next()){
            balance=rs.getInt("BalanceAmount");
            return balance;
        }
        else{
            return 0;
        }
        
         
    }
    public boolean withdrawMoney(int id,int wAmount) throws SQLException{
        String withdrawQuery;
        if(wAmount>balance){
            return false;
        }
        else{
            int newBalance=balance-wAmount;
            withdrawQuery="UPDATE ACCOUNT set BalanceAmount="+newBalance+" where CustomerID="+id+"";
            stmt.executeUpdate(withdrawQuery);
            return true;
        }
    }
    public boolean depositMoney(int id,int dAmount) throws SQLException{
        int newBalance=balance+dAmount;
        String depositQuery="UPDATE ACCOUNT SET BalanceAmount="+newBalance+" where CustomerID="+id+"";
        int rowsAffected=stmt.executeUpdate(depositQuery);
        if(rowsAffected==1){
            return true;
        }
        else{
            return false;
        }
    }
    public static void main(String args[]){
        new Conn();
    }
}
