package rachid.javafx;

import java.beans.Statement;
import java.io.File;
import java.net.URL;
import java.sql.Connection;
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
    private Button addEmployee_addBtn;

    @FXML
    private Button addEmployee_btn;

    @FXML
    private Button addEmployee_clearBtn;

    @FXML
    private TableView<EmployeeData> addEmployee_tableView;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_date;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_employeeID;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_genre;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_nom;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_phoneNum;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_position;

    @FXML
    private TableColumn<EmployeeData, String> addEmployee_col_prenom;

    @FXML
    private Button addEmployee_deleteBtn;

    @FXML
    private TextField addEmployee_employeeID;

    @FXML
    private AnchorPane addEmployee_form;

    @FXML
    private ComboBox<?> addEmployee_genre;

    @FXML
    private ImageView addEmployee_image;

    @FXML
    private Button addEmployee_importBtn;

    @FXML
    private TextField addEmployee_nom;

    @FXML
    private TextField addEmployee_phoneNum;

    @FXML
    private ComboBox<?> addEmployee_position;

    @FXML
    private TextField addEmployee_prenom;

    @FXML
    private TextField addEmployee_search;

    @FXML
    private Button addEmployee_updateBtn;

    @FXML
    private Button close;

    @FXML
    private Button home_btn;

    @FXML
    private AreaChart<?, ?> home_chart;

    @FXML
    private AnchorPane home_form;

    @FXML
    private Label home_totalEmployees;

    @FXML
    private Label home_totalInactiveEm;

    @FXML
    private Label home_totalPresents;

    @FXML
    private Button logout;

    @FXML
    private AnchorPane main_form;

    @FXML
    private Button minimize;

    @FXML
    private Button salary_btn;

    @FXML
    private Button salary_clearBtn;

    @FXML
    private TableColumn<?, ?> salary_col_employeeID;

    @FXML
    private TableColumn<?, ?> salary_col_nom;

    @FXML
    private TableColumn<?, ?> salary_col_position;

    @FXML
    private TableColumn<?, ?> salary_col_prenom;

    @FXML
    private TableColumn<?, ?> salary_col_salary;

    @FXML
    private TextField salary_employeeID;

    @FXML
    private AnchorPane salary_form;

    @FXML
    private Label salary_nom;

    @FXML
    private Label salary_position;

    @FXML
    private Label salary_prenom;

    @FXML
    private TextField salary_salary;

    @FXML
    private TableView<?> salary_tableView;

    @FXML
    private Button salary_updateBtn;

    @FXML
    private Label username;

    private double x = 0;
    private double y = 0;

    private Connection connect;
    private Statement statement;
    private PreparedStatement prepare;
    private ResultSet result;

    private Image image;

    public void addEmpoyeeInsertImage()
    {
        FileChooser open = new FileChooser();
        File file = open.showOpenDialog(main_form.getScene().getWindow());

        if (file != null) {
            GetData.path = file.getAbsolutePath();
            
            image = new Image(file.toURI().toString(), 101, 150, false, true);
            addEmployee_image.setImage(image);
        }
    }

    public ObservableList<EmployeeData> addEmployeeListeData()
    {
        ObservableList<EmployeeData> listeData = FXCollections.observableArrayList();
        String sql = "SELECT * FROM employee";

        connect = DatabaseConnection.connection();

        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();
            EmployeeData employeeData;

            while (result.next()) {
                employeeData = new EmployeeData(result.getInt("employee_id"),
                                result.getString("prenom"),
                                result.getString("nom"),
                                result.getString("genre"),
                                result.getString("phoneNum"),
                                result.getString("position"),
                                result.getString("image"),
                                result.getDate("membreDate"));
                listeData.add(employeeData);
            }
        } catch (Exception e) {e.printStackTrace();}
        return listeData;
    }

    private ObservableList<EmployeeData> addEmployeeList;
    public void addEmployeeShowListData()
    {
        addEmployeeList = addEmployeeListeData();

        addEmployee_col_employeeID.setCellValueFactory(new PropertyValueFactory<>("employee_id"));
        addEmployee_col_prenom.setCellValueFactory(new PropertyValueFactory<>("prenom"));
        addEmployee_col_nom.setCellValueFactory(new PropertyValueFactory<>("nom"));
        addEmployee_col_genre.setCellValueFactory(new PropertyValueFactory<>("genre"));
        addEmployee_col_phoneNum.setCellValueFactory(new PropertyValueFactory<>("phoneNum"));
        addEmployee_col_position.setCellValueFactory(new PropertyValueFactory<>("position"));
        addEmployee_col_date.setCellValueFactory(new PropertyValueFactory<>("membreDate"));

        addEmployee_tableView.setItems(addEmployeeList);
    }

    public void addEmpoyeeSelect()
    {
        EmployeeData employeeData = addEmployee_tableView.getSelectionModel().getSelectedItem();
        int num = addEmployee_tableView.getSelectionModel().getSelectedIndex();

        if ((num -1) < -1) {return;}

        addEmployee_employeeID.setText(String.valueOf(employeeData.getEmployee_id()));
        addEmployee_prenom.setText(employeeData.getPrenom());
        addEmployee_nom.setText(employeeData.getNom());
        addEmployee_phoneNum.setText(employeeData.getPhoneNum());

        String uri = "file:" + employeeData.getImage();

        image = new Image(uri, 101, 150, false, true);
        addEmployee_image.setImage(image);

    }

    public void displayUsername()
    {
        username.setText(GetData.username);
    }

    @SuppressWarnings("exports")
    public void switchForm(ActionEvent event)
    {
        if (event.getSource() == home_btn) {
            home_form.setVisible(true);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(false);

            home_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            addEmployee_btn.setStyle("-fx-background-color: transparent");
            salary_btn.setStyle("-fx-background-color: transparent");
        }
        else if (event.getSource() == addEmployee_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(true);
            salary_form.setVisible(false);

            addEmployee_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color: transparent");
            salary_btn.setStyle("-fx-background-color: transparent");
        }
        else if (event.getSource() == salary_btn) {
            home_form.setVisible(false);
            addEmployee_form.setVisible(false);
            salary_form.setVisible(true);

            salary_btn.setStyle("-fx-background-color: linear-gradient(to bottom right, #3a4368, #28966c);");
            home_btn.setStyle("-fx-background-color: transparent");
            addEmployee_btn.setStyle("-fx-background-color: transparent");
        }
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

                Parent root = FXMLLoader.load(getClass().getResource("/rachid/javafx/FXMLEmployeeManagementSystem.fxml"));
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

    @Override
    public void initialize(URL location, ResourceBundle resources) 
    {
        addEmployeeShowListData();
    }
}
