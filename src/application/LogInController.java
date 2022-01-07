package application;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.sql.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;



public class LogInController {
	
	@FXML
	private Button CancelButton;
	@FXML
	private Label loginMessageLabel;
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordTextField;
	@FXML
	private Label RegisterLabel;
	@FXML
	private Label XLabel;
	
	Connection conn = null;
	
	@FXML
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
	}
		
	@FXML
	public void cancelButtonAction(ActionEvent event) {
		Stage stage = (Stage) CancelButton.getScene().getWindow();
		stage.close();

	}
	
	@FXML
	public void OpenRegisterForm(MouseEvent event) {
		try {
			Stage stage = (Stage) RegisterLabel.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("Register.fxml"));
			Stage registerScene = new Stage();
			registerScene.initStyle(StageStyle.UNDECORATED);
			registerScene.setScene(new Scene(root, 600, 457));
			registerScene.show();
			registerScene.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	
	
	
	@FXML
	private void SignIn(ActionEvent event) throws Exception{
		
		if(usernameTextField.getText().isBlank() == false && passwordTextField.getText().isBlank() == false) {
			conn = DatabaseConnection.ConnectDB();
			
			String sql = "SELECT count(1) FROM user_account WHERE username = '" + usernameTextField.getText() + "' AND password = '" + passwordTextField.getText() + "'";
			
			try {
				
				Statement statement = conn.createStatement();
				ResultSet queryResult = statement.executeQuery(sql);
				
				while(queryResult.next()) {
					
					if(queryResult.getInt(1)==1) {
						loginMessageLabel.setTextFill(Color.web("#000679"));
						loginMessageLabel.setText("Connected!");
						System.out.println("You're connected");
						
						Stage stage = (Stage) RegisterLabel.getScene().getWindow();
						stage.close();
						Parent root = FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
						Stage registerScene = new Stage();
						registerScene.initStyle(StageStyle.UNDECORATED);
						registerScene.setScene(new Scene(root, 800, 608));
						registerScene.show();
						registerScene.setResizable(false);
						
					}else {
						loginMessageLabel.setTextFill(Color.web("#ff0000"));
						loginMessageLabel.setText("Invalid SignIn");
						System.out.println("Invalid Sign in");
					}
					
				}
				
			}catch(Exception e) {
				System.out.println("Exception in LogIn Controller " + e);
			}
		}
		else {
			loginMessageLabel.setTextFill(Color.web("#ff0000"));
			loginMessageLabel.setText("Please type your username and password.");
		}
	
	}
	
}
