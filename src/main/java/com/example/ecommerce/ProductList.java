package com.example.ecommerce;

import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

public class ProductList {
    private TableView<Product> productTable;

    public VBox createTable(ObservableList<Product> data){
        // columns
        TableColumn id= new TableColumn("ID");
        id.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn name= new TableColumn("NAME");
        name.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn price= new TableColumn("price");
        price.setCellValueFactory(new PropertyValueFactory<>("price"));


        //  data- dummy data


        productTable= new TableView<>();

        productTable.getColumns().addAll(id, name, price);
        productTable.setItems(data);
        productTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);//it will remove extra column which not in used

        VBox vBox= new VBox();
        vBox.setPadding(new Insets(10));
        vBox.getChildren().addAll(productTable);
        return vBox;

    }

//    public   VBox getDummyTable(){
//        ObservableList<product> data= FXCollections.observableArrayList();
//        data.add(new product(2, "Iphone", 44546));
//        data.add(new product(5, "Laptop", 34353));
       //return createTable(data);
//    }

    public  VBox getAllProducts(){
        ObservableList<Product> data= Product.getAllProducts();

        return createTable(data);

    }

    public Product getSelectedProduct(){
        return productTable.getSelectionModel().getSelectedItem();// here we are getting selected item
        //  fetching data  from tableview(product Table)  of selected item;
    }

    public VBox getProductsInCart(ObservableList<Product> customer){
        return createTable(customer);
    }
}
