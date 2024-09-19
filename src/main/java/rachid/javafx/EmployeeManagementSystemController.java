package rachid.javafx;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class EmployeeManagementSystemController implements Initializable {
    @FXML
    private Button close;

    @FXML
    private Button close1;

    @FXML
    private AnchorPane connect_form;

    @FXML
    private TextField email_inscription;

    @FXML
    private AnchorPane inscrir_form;

    @FXML
    private Button loginBtn;

    @FXML
    private Button loginBtn1;

    @FXML
    private AnchorPane main;

    @FXML
    private PasswordField password_connection;

    @FXML
    private PasswordField password_inscription;

    @FXML
    private Button singin_btn;

    @FXML
    private TextField username_connection;

    @FXML
    private TextField username_inscription;

    public void close() {
        System.exit(0);
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private double x = 0;
    private double y = 0;

    public void adminLogin() {

        String sql = "select * from admin where username = ? and password = ?";
        connect = DatabaseConnection.connection();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, username_connection.getText());
            prepare.setString(2, password_connection.getText());
            result = prepare.executeQuery();

            Alert alert;

            if (username_connection.getText().isEmpty() || password_connection.getText().isEmpty()) {
                alert = new Alert(AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
            } else {
                if (result.next()) {

                    // GetData.username = username.getText();

                    alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Message d'inforamtion");
                    alert.setHeaderText(null);
                    alert.setContentText("Connection reussi avec succes");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("/rachid/javafx/FXMLDashboard.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();

                } else {
                    alert = new Alert(AlertType.ERROR);
                    alert.setTitle("Message d'erreur");
                    alert.setHeaderText(null);
                    alert.setContentText("Username ou Mot de passe incorect");
                    alert.showAndWait();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Méthode pour gérer l'inscription d'un administrateur
    @FXML
    void adminSigin() {
        String query = "INSERT INTO admin (username, email, password) VALUES(?, ?, ?)";
        connect = DatabaseConnection.connection();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, username_inscription.getText());
            prepare.setString(2, email_inscription.getText());
            prepare.setString(3, password_inscription.getText());

            // Vérifier si les champs sont vides
            if (username_inscription.getText().isEmpty() || email_inscription.getText().isEmpty() || password_inscription.getText().isEmpty()) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Message d'erreur");
                alert.setHeaderText(null);
                alert.setContentText("Veuillez remplir tous les champs");
                alert.showAndWait();
            } else {
                // Effectuer l'insertion
                int affectedRows = prepare.executeUpdate();
                if (affectedRows > 0) {
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Message d'information");
                    alert.setHeaderText(null);
                    alert.setContentText("Inscription réussie avec succès");
                    alert.showAndWait();

                    Parent root = FXMLLoader.load(getClass().getResource("/rachid/javafx/FXMLEmployeeManagementSystem.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    // Gestion du déplacement de la fenêtre
                    root.setOnMousePressed((MouseEvent event) -> {
                        x = event.getSceneX();
                        y = event.getSceneY();
                    });

                    root.setOnMouseDragged((MouseEvent event) -> {
                        stage.setX(event.getScreenX() - x);
                        stage.setY(event.getScreenY() - y);
                    });

                    stage.initStyle(StageStyle.TRANSPARENT);
                    stage.setScene(scene);
                    stage.show();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            // Fermer les ressources
            try {
                if (prepare != null)
                    prepare.close();
                if (connect != null)
                    connect.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @SuppressWarnings("exports")
    public void switchForm(ActionEvent event) {
        if (event.getSource() == loginBtn1) {
            inscrir_form.setVisible(true);
            connect_form.setVisible(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }
}