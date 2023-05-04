package com.example.ecommerce;

import javafx.collections.ObservableList;

import java.sql.ResultSet;

public class Order {
    public  static boolean  placeOrder(Customer customer, Product product){
        String  groupOrderId= "SELECT max(group_order_id) +1 id FROM orders";
        dbconnection dbconnection= new dbconnection();
        try{
            ResultSet rs= dbconnection.getQueryTable(groupOrderId);
            if(rs.next()){
                String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES(1, "+customer.getId()+","+product.getId()+")";
                return dbconnection.updateDatabase(placeOrder)!=0;
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return false;

    }

    public  static int  placeMultipleOrder(Customer customer, ObservableList<Product> productList){
        String  groupOrderId= "SELECT max(group_order_id) +1 id FROM orders";
        dbconnection dbconnection= new dbconnection();
        try{
            ResultSet rs= dbconnection.getQueryTable(groupOrderId);
            int count=0;
            if(rs.next()){
                for(Product product: productList){
                    String placeOrder = "INSERT INTO orders(group_order_id, customer_id, product_id) VALUES(1, "+customer.getId()+","+product.getId()+")";
                    count+= dbconnection.updateDatabase(placeOrder);
                }

                return count;
            }
        }catch(Exception e){
            e.printStackTrace();

        }
        return 0;

    }

}
