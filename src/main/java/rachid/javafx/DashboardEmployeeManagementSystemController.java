package rachid.javafx;

import java.beans.Statement;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.AreaChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class DashboardEmployeeManagementSystemController implements Initializable {

    @FXML
    private Button addEmployee_addBtn, addEmployee_btn, addEmployee_clearBtn, addEmployee_deleteBtn,
            addEmployee_importBtn, addEmployee_updateBtn, close, home_btn, logout, minimize, salary_btn,
            salary_clearBtn, salary_updateBtn;

    @FXML
    private TableView<EmployeeData> addEmployee_tableView;

    @FXML
    private TableColumn<EmployeeData, Date> addEmployee_col_date;
    @FXML
    private TableColumn<EmployeeData, Integer> addEmployee_col_employeeID;
    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_genre, addEmployee_col_nom,
            addEmployee_col_phoneNum, addEmployee_col_position,
            addEmployee_col_prenom;

    @FXML
    private TextField addEmployee_employeeID, addEmployee_nom, addEmployee_phoneNum,
            addEmployee_prenom, addEmployee_search, salary_employeeID, salary_salary;

    @FXML
    private ComboBox<String> addEmployee_genre, addEmployee_position;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private AreaChart<?, ?> home_chart;

    @FXML
    private AnchorPane addEmployee_form, home_form, main_form, salary_form;

    @FXML
    private Label home_totalEmployees, home_totalInactiveEm, home_totalPresents, salary_nom,
            salary_position, salary_prenom, username;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    public void addEmpoyeeInsertImage() {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            GetData.path = file.getAbsolutePath();

            Image image = new Image(file.toURI().toString(), 101, 150, false, true);
            addEmployee_image.setImage(image);
        }
    }

    // public void getData()
    // {
    // EmployeeData employeeData =
    // addEmployee_tableView.getSelectionModel().getSelectedItem();
    // int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

    // if ((num -1) < -1) {return;}

    // addEmployee_employeeID.setText(String.valueOf(employeeData.getEmployee_id()));
    // addEmployee_prenom.setText(employeeData.getPrenom());
    // addEmployee_nom.setText(employeeData.getNom());
    // addEmployee_phoneNum.setText(employeeData.getPhoneNum());

    // String uri = "file:" + employeeData.getImage();

    // image = new Image(uri, 101, 150, false, true);
    // addEmployee_image.setImage(image);

    // }

    public void getData() {
        EmployeeData employeeData = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if (num < 0) {
            return;
        }

        addEmployee_employeeID.setText(String.valueOf(employeeData.getEmployee_id()));
        addEmployee_prenom.setText(employeeData.getPrenom());
        addEmployee_nom.setText(employeeData.getNom());
        addEmployee_phoneNum.setText(employeeData.getPhoneNum());

        String imagePath = employeeData.getImage(); // Assurez-vous que getImage() renvoie le chemin correct

        if (imagePath != null && !imagePath.isEmpty()) {
            String uri = "file:" + imagePath; // Construction du chemin d'accès à l'image

            Image image = new Image(uri, 101, 150, false, true);
            addEmployee_image.setImage(image); // Affiche l'image

        } else {
            System.out.println("Chemin de l'image non spécifié.");
        }
    }

    // public void displayUsername()
    // {
    // username.setText(GetData.username);
    // }

    @SuppressWarnings("exports")
    public void switchForm(ActionEvent event) {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color: transparent");
            salary_btn.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color: transparent");
            salary_btn.setStyle("-fx-background-color: transparent");
        } else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color: transparent");
            addEmployee_btn.setStyle("-fx-background-color: transparent");
        }
    }

    @FXML
    void createEmployee(ActionEvent event) {
        if (validateField()) {
            Date date = new Date(0);
            Date sqlDate = new Date(date.getTime());
            String insert = "INSERT INTO employee(employee_id, prenom, nom, genre, phoneNum, position, image, membreDate)VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

            try {
                prepare = connect.prepareStatement(insert);
                prepare.setString(1, addEmployee_employeeID.getText());
                prepare.setString(2, addEmployee_prenom.getText());
                prepare.setString(3, addEmployee_nom.getText());
                prepare.setString(4, (String) addEmployee_genre.getSelectionModel().getSelectedItem());
                prepare.setString(5, addEmployee_phoneNum.getText());
                prepare.setString(6, (String) addEmployee_position.getSelectionModel().getSelectedItem());

                String path = GetData.path;
                // System.out.println("Chemin retourné par GetData.path : " + path); permet de
                // voir le chemin de l'image
                prepare.setString(7, path);
                prepare.setString(8, String.valueOf(sqlDate));
                prepare.executeUpdate();

                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Message D'information");
                alert.setHeaderText(path);
                alert.setContentText("Employee creer avec succes!");
                alert.showAndWait();

                showEmpoyee();
                addEmpoyeeReset();

            } catch (Exception e) {
                showError("Error lors de l'insertion", e.getMessage());
            }
        }
        // else{
        // String check = "SELECT employee_id FROM employee WHERE employee_id = ' "
        // + addEmployee_employeeID.getText() +" ' ";

        // statement = connect.createStatement();
        // result = statement.executeQuery(check);
        // }

    }

    public void addEmpoyeeReset() {
        addEmployee_employeeID.clear();
        addEmployee_prenom.clear();
        addEmployee_nom.clear();
        addEmployee_genre.getSelectionModel().clearSelection();
        addEmployee_phoneNum.clear();
        addEmployee_position.getSelectionModel().clearSelection();
        addEmployee_image.setImage(null);
        GetData.path = "";
    }

    @FXML
    public void logout() {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Êtes vous sûr de vouloir vous déconnecter ?");
        Optional<ButtonType> option = alert.showAndWait();

        try {
            if (option.get().equals(ButtonType.OK)) {
                logout.getScene().getWindow().hide();

                Parent root = FXMLLoader
                        .load(getClass().getResource("/rachid/javafx/FXMLEmployeeManagementSystem.fxml"));
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
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void close() {
        System.exit(0);
    }

    @FXML
    public void minimize() {
        Stage stage = (Stage) main_form.getScene().getWindow();
        stage.setIconified(true);
    }

    public boolean validateField() {
        if (addEmployee_employeeID.getText().isEmpty()
                || addEmployee_prenom.getText().isEmpty()
                || addEmployee_nom.getText().isEmpty()
                || addEmployee_genre.getSelectionModel().getSelectedItem() == null
                || addEmployee_phoneNum.getText().isEmpty()
                || addEmployee_position.getSelectionModel().getSelectedItem() == null
                || GetData.path == null || GetData.path == "") {

            showError("Champs manquant", "Tous les champs sont obligatoire");
            return false;
        }
        return true;
    }

    public void showError(String title, String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public ObservableList<EmployeeData> getEmployeeData() {
        ObservableList<EmployeeData> employeeDatas = FXCollections.observableArrayList();

        String sql = "select * from employee";

        try {
            connect = DatabaseConnection.connection();
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            while (result.next()) {
                EmployeeData employeeData = new EmployeeData();
                employeeData.setEmployee_id(result.getInt("employee_id"));
                employeeData.setPrenom(result.getString("prenom"));
                employeeData.setNom(result.getString("nom"));
                employeeData.setGenre(result.getString("genre"));
                employeeData.setPhoneNum(result.getString("phoneNum"));
                employeeData.setPosition(result.getString("position"));
                employeeData.setMembreDate(result.getDate("membreDate"));

                employeeDatas.add(employeeData);
            }
        } catch (Exception e) {
            showError("Error lors de la recuperation de information", e.getMessage());
        }
        return employeeDatas;
    }

    public void showEmpoyee() {
        ObservableList<EmployeeData> list = getEmployeeData();
        addEmployee_tableView.setItems(list);
        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<EmployeeData, Integer>("employee_id"));
        addEmployee_col_prenom.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("prenom"));
        addEmployee_col_nom.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("nom"));
        addEmployee_col_genre.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("genre"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<EmployeeData, String>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<EmployeeData, Date>("membreDate"));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addEmployee_position.setItems(FXCollections.observableArrayList("Developpement web", "Ciber securite", "Comptabilite", "Administration"));
        addEmployee_genre.setItems(FXCollections.observableArrayList("Male", "Femele"));
        showEmpoyee();
    }
}
