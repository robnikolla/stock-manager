package application;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;

import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


import javafx.event.ActionEvent;

import javafx.scene.control.TableView;

import javafx.scene.control.TableColumn;

public class DashboardController implements Initializable {
	
	@FXML
	private TextField nameInput;
	@FXML
	private TextField producerInput;
	@FXML
	private TextField barcodeInput;
	@FXML
	private TextField priceInput;
	@FXML
	private TextField stockInput;
	
	@FXML
	private Button insertButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button updateButton;
	
	@FXML
	private TableView<Products> ProductsTable;
	@FXML
	private TableColumn<Products,String> nameColumn;
	@FXML
	private TableColumn<Products,String> producerColumn;
	@FXML
	private TableColumn<Products,Integer> barcodeColumn;
	@FXML
	private TableColumn<Products,Double> priceColumn;
	@FXML
	private TableColumn<Products,Integer> stockColumn;
	
	Connection conn = null;
	@FXML
	public void insertButtonAction(ActionEvent event) {
		insertProduct();
		showProducts();
	}
	// Event Listener on Button[#updateButton].onAction
	@FXML
	public void updateButtonAction(ActionEvent event) {
		updateProduct();
		showProducts();
	}
	// Event Listener on Button[#deleteButton].onAction
	@FXML
	public void deleteButtonAction(ActionEvent event) {
		deleteProducts();
		showProducts();
	}
	
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		showProducts();
		
		ProductsTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
		    if (newSelection != null) {
		    	Products selectedProduct = ProductsTable.getSelectionModel().getSelectedItem();
		    	
		    	Integer barcodeSelected = selectedProduct.getBarcode();
		    	Double priceSelected = selectedProduct.getPrice();
		    	Integer stockSelected = selectedProduct.getStock();
		    	
		    	
		    	nameInput.setText(selectedProduct.getName());
		    	producerInput.setText(selectedProduct.getProducer());
		    	barcodeInput.setText(barcodeSelected.toString());
		    	priceInput.setText(priceSelected.toString());
		    	stockInput.setText(stockSelected.toString());
		    	
		    

		    }
		});	}
	
	private void insertProduct(){
		conn = DatabaseConnection.ConnectDB();
		String query = "INSERT INTO products (name, producer, barcode, price, stock) VALUES (?,?,?,?,?);";
		
		
		try {	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			int barcode = Integer. parseInt(barcodeInput.getText());
			int stock = Integer.parseInt(stockInput.getText());
			String name = nameInput.getText();
			String producer = producerInput.getText();
			Double price = Double.parseDouble(priceInput.getText());
			
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, producer);
			preparedStmt.setInt(3, barcode);
			preparedStmt.setDouble(4, price);
			preparedStmt.setInt(5, stock);
			
			preparedStmt.execute();
			
		
	
		}catch(Exception ex) {
		ex.printStackTrace();
		}
	}
	
	public ObservableList<Products> getProducts(){
		ObservableList<Products> listOfProducts = FXCollections.observableArrayList();
		conn = DatabaseConnection.ConnectDB();	
		String query = "SELECT * FROM products";
		Statement statement;
		ResultSet queryResult;
		
		try {
			statement = conn.createStatement();
			queryResult = statement.executeQuery(query);
			Products products;
			while(queryResult.next()) {
				products = new Products(queryResult.getInt("id"), queryResult.getString("name"),
						          queryResult.getString("producer"),queryResult.getInt("barcode"),
						          queryResult.getDouble("price"),queryResult.getInt("stock"));
					
				listOfProducts.add(products);
			}	
		}catch(Exception ex) {
		ex.printStackTrace();
		}
		
		return listOfProducts;
	}
	
	public void updateProduct() {
		conn = DatabaseConnection.ConnectDB();
		Products selectedProduct = ProductsTable.getSelectionModel().getSelectedItem();
		String query = "UPDATE products SET name = ?, producer = ?, barcode = ?, price = ?,stock = ? WHERE id = ?";
		
		try {
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			int barcode = Integer. parseInt(barcodeInput.getText());
			int stock = Integer. parseInt(stockInput.getText());
			String name = nameInput.getText();
			String producer = producerInput.getText();
			Double price = Double.parseDouble(priceInput.getText());
			
			preparedStmt.setString(1, name);
			preparedStmt.setString(2, producer);
			preparedStmt.setInt(3, barcode);
			preparedStmt.setDouble(4, price);
			preparedStmt.setInt(5,stock);
			preparedStmt.setInt(6, selectedProduct.getId());
			
			preparedStmt.executeUpdate();
		
		}catch(Exception ex) {
			ex.printStackTrace();
		}	
	}
	
	public void showProducts() {
		ObservableList<Products> products = getProducts();
		
		nameColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("name"));
		producerColumn.setCellValueFactory(new PropertyValueFactory<Products, String>("producer"));
		barcodeColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("barcode"));
		priceColumn.setCellValueFactory(new PropertyValueFactory<Products, Double>("price"));
		stockColumn.setCellValueFactory(new PropertyValueFactory<Products, Integer>("stock"));
		
		ProductsTable.setItems(products);

	}
	
	public void deleteProducts() {
		conn = DatabaseConnection.ConnectDB();
		Products selectedProduct = ProductsTable.getSelectionModel().getSelectedItem();
		String query = "DELETE FROM products where id = ?";
		
		try {	
			PreparedStatement preparedStmt = conn.prepareStatement(query);
			
			preparedStmt.setInt(1, selectedProduct.getId());
			
			preparedStmt.execute();
				
		}catch(Exception ex) {
			ex.printStackTrace();
		}
	}

}


