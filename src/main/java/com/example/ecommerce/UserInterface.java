package com.example.ecommerce;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;



public class UserInterface {
    GridPane loginPage;
    HBox headerBar;
    HBox footerBar;
    VBox body;
    Button signInButton;
    Label welcomeLabel;


    Customer loggedInCustomer;
    ProductList productList= new ProductList();
    VBox productPage;
    Button placeOrderButton= new Button("place order");

    ObservableList<Product> itemsInCart= FXCollections.observableArrayList();


    public UserInterface(){
        createLoginPage();
        createHeaderBar();
        createFooterBar();
    }
    private void createLoginPage(){
        Text userNameText= new Text("User Name");
        Text passwordText= new Text("Password");

        TextField userName= new TextField();
        userName.setPromptText("Type your user name here");

        PasswordField password= new PasswordField();
        password.setPromptText("Type your password here");

        Button loginButton= new Button("login");
        Label messagelabel= new Label("Hi");


        loginPage= new GridPane();
        loginPage.setStyle("-fx-background-color: grey;");
        loginPage.setAlignment(Pos.CENTER);
        loginPage.setHgap(10);
        loginPage.setVgap(10);
        loginPage.add(userNameText,0,0 );   //	add(Node child, int columnIndex, int rowIndex)
        loginPage.add(userName,1,0);
        loginPage.add(passwordText,0,1);
        loginPage.add(password,1,1);
        loginPage.add(messagelabel,0,2);

        loginPage.add(loginButton, 1,2);

        loginButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String name= userName.getText();
                String pass= password.getText();
                Login login= new Login();
                loggedInCustomer= login.customerLogin(name,pass);

                if(loggedInCustomer!=null){
                    messagelabel.setText("Welcome"+loggedInCustomer.getName());
                    welcomeLabel.setText("Welcome"+loggedInCustomer.getName());

                    headerBar.getChildren().add(welcomeLabel);
                    body.getChildren().clear();
                    body.getChildren().add(productPage);
                }else{
                    messagelabel.setText("LogIn Failed !! please give correct user name and password.");
                }


            }
        });


    }
    public BorderPane createContent(){
        BorderPane root= new BorderPane();
        root.setPrefSize(800,600);
        // root.getChildren().add(loginPage);// adding loginpage to pane
        // root.setCenter(loginPage);
        root.setTop(headerBar);
        body = new VBox();

        body.setPadding(new Insets(10));
        body.setAlignment(Pos.CENTER);
        productPage= productList.getAllProducts();
        root.setCenter(body);
        body.getChildren().add(productPage);

        root.setBottom(footerBar);
        return root;
    }

    private void createHeaderBar(){
         Button homeButton= new Button();
         Image image= new Image("C:\\Users\\robil\\IdeaProjects\\Ecommerce\\src\\IMG_20190101_150513.jpg");
         ImageView imageView= new ImageView();
         imageView.setImage(image);
         imageView.setFitWidth(80);
         imageView.setFitHeight(20);

         homeButton.setGraphic(imageView);
         headerBar= new HBox();

         TextField searchBar = new TextField();
         searchBar.setPromptText("Search here");
         searchBar.setPrefWidth(280);

         Button searchButton = new Button("Search");

          signInButton= new Button("SignIn");
          welcomeLabel= new Label();
          Button cartButton= new Button("Cart");
          Button orderButton= new Button("Orders");




         headerBar.setPadding(new Insets(10));
         headerBar.setSpacing(10);
         headerBar.setAlignment(Pos.CENTER);
         headerBar.getChildren().addAll(homeButton,searchBar,searchButton,signInButton,cartButton,orderButton);
         signInButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();// remove everything
                 body.getChildren().add(loginPage);// put login page

                 headerBar.getChildren().remove(signInButton);

             }
         });
         cartButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 VBox prodpage= productList.getProductsInCart(itemsInCart);
                 prodpage.setSpacing(10);
                 prodpage.setAlignment(Pos.CENTER);
                 prodpage.getChildren().add(placeOrderButton);
                 body.getChildren().add(prodpage);
                 footerBar.setVisible(false);// all cases need to be handled for this
             }
         });

         placeOrderButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 // here need list of product and a customer.


                 if(itemsInCart==null){
                     showDialog("please add some products in the cart");
                     return;
                 }

                 if(loggedInCustomer==null){
                     showDialog("please login first to place order");
                     return;
                 }
                 int  count= Order.placeMultipleOrder(loggedInCustomer,itemsInCart);

                 if(count!=0){
                     showDialog("Order for"+count+" products placed successfully!!");
                 }else{
                     showDialog("Order failed");
                 }
             }
         });

         homeButton.setOnAction(new EventHandler<ActionEvent>() {
             @Override
             public void handle(ActionEvent actionEvent) {
                 body.getChildren().clear();
                 body.getChildren().add(productPage);
                 footerBar.setVisible(true);

                 if(loggedInCustomer==null){
                     //System.out.println(headerBar.getChildren().indexOf(signInButton));
                     if(headerBar.getChildren().indexOf(signInButton)== -1){
                         headerBar.getChildren().add(signInButton);
                     }

                 }
             }
         });
    }


    private void createFooterBar(){
        footerBar= new HBox();



        Button buyNowButton = new Button("BuyNow");
        Button addToCartButton= new Button("Add to Cart");

        footerBar.setPadding(new Insets(10));
        footerBar.setSpacing(10);
        footerBar.setAlignment(Pos.CENTER);
        footerBar.getChildren().addAll(buyNowButton, addToCartButton);


            buyNowButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {

                   Product  product = productList.getSelectedProduct();

                   if(product==null){
                       showDialog("please select a product first to  place order!");
                       return;
                   }

                   if(loggedInCustomer==null){
                       showDialog("please login first to place order");
                       return;
                   }
                   boolean status= Order.placeOrder(loggedInCustomer,product);

                   if(status==true){
                       showDialog("Order placed successfully!!");
                   }else{
                       showDialog("Order failed");
                   }
                }
            });

            addToCartButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    Product  product = productList.getSelectedProduct();

                    if(product==null){
                        showDialog("please select a product first to add it to Cart!");
                        return;
                    }
                    itemsInCart.add(product);
                    showDialog("Selected item has been added to the cart successfully");
                }
            });
    }

    private void showDialog(String message){
         Alert alert = new Alert(Alert.AlertType.INFORMATION);
         alert.setHeaderText(null);
         alert.setContentText(message);
         alert.setTitle("Message");
         alert.showAndWait();
    }
}
