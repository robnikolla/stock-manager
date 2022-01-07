package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.PasswordField;

import java.sql.Connection;
import java.sql.Statement;

public class RegisterController {
	
	@FXML
	private Button CloseButton;
	@FXML
	private Label InformationLabel;
	@FXML
	private Button SignUpButton;
	@FXML
	private PasswordField PasswordField;
	@FXML
	private PasswordField ConfirmPasswordField;
	@FXML
	private TextField FirstnameTextField;
	@FXML
	private TextField LastnameTextField;
	@FXML
	private TextField UsernameTextField;
	@FXML
	private Label SignInLabel;
	@FXML
	private Label XLabel;
	
	Connection conn = null;
	
	public void CloseButtonAction(ActionEvent event) {
		Stage stage = (Stage) CloseButton.getScene().getWindow();
		stage.close();
		System.out.println("Closed");
	}
	
	@FXML
	public void XLabelClick(MouseEvent event) {
		Stage stage = (Stage) XLabel.getScene().getWindow();
		stage.close();
	}
	
	public void SignUpButtonAction(ActionEvent event) {
		
		if(FirstnameTextField.getText().isBlank() == false && LastnameTextField.getText().isBlank() == false && UsernameTextField.getText().isBlank() == false
				&& PasswordField.getText().isBlank() == false && ConfirmPasswordField.getText().isBlank() == false
				) {
			
			if(PasswordField.getText().equals(ConfirmPasswordField.getText())) {
				
				conn = DatabaseConnection.ConnectDB();
				
				String sql = "INSERT INTO user_account (firstname, lastname, username, password) VALUES ('" + FirstnameTextField.getText() + "','" + LastnameTextField.getText() + "','" + UsernameTextField.getText() + "','" + PasswordField.getText() + "')";
				
				try {
					
					Statement statement = conn.createStatement();
					statement.executeUpdate(sql);
					
					System.out.println("The user is register");
					InformationLabel.setTextFill(Color.web("#000679"));
					InformationLabel.setText("The user has been registered successfully!");
					FirstnameTextField.setText("");
					LastnameTextField.setText("");
					UsernameTextField.setText("");
					PasswordField.setText("");
					ConfirmPasswordField.setText("");
					
				}catch(Exception e) {
					System.out.println("Exception in LogIn Controller " + e);
				}
				
			}else {
				InformationLabel.setTextFill(Color.web("#ff0000"));
				InformationLabel.setText("Passwords does not match!");
			}
			
		}else {
			InformationLabel.setTextFill(Color.web("#ff0000"));
			InformationLabel.setText("Please fill all the blanks");
		}
		
		
	}
	
	
	public void OpenLogInForm(MouseEvent event) {
		try {
			Stage stage = (Stage) SignInLabel.getScene().getWindow();
			stage.close();
			Parent root = FXMLLoader.load(getClass().getResource("LogIn.fxml"));
			Stage LogInScene = new Stage();
			LogInScene.initStyle(StageStyle.UNDECORATED);
			LogInScene.setScene(new Scene(root, 523, 422));
			LogInScene.show();
			LogInScene.setResizable(false);
		} catch(Exception e) {
			e.printStackTrace();
			e.getCause();
		}
	}
	


}
