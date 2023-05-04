package com.example.ecommerce;

import javax.xml.transform.Result;
import java.sql.ResultSet;

public class Login {
    public  Customer customerLogin(String username, String password){
        String loginQuery = "SELECT * FROM customer WHERE email= '"+username+"'AND password= '"+password+"'";
        dbconnection conn = new dbconnection();
        ResultSet rs= conn.getQueryTable(loginQuery);
        try{
            if (rs.next()){
                return new Customer(rs.getInt("id"), rs.getString("name"), rs.getString("email"),rs.getString("mobile"));
            }



        }catch (Exception e){
            e.printStackTrace();
        }
        return null;

    }

    public static void main(String[] args) {
        Login login = new  Login();
        Customer customer= login.customerLogin("robil.cs.coder@gmail.com", "abc@123");
        System.out.println("Welcome :"+customer.getName());
    }
}
