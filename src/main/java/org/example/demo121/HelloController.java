package org.example.demo121;

import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

public class HelloController {
    @FXML
    private TextField nameInput;
    @FXML
    private ComboBox<String> typeInput;
    @FXML
    private TextField hoursInput;
    @FXML
    private TextField rateInput;
    @FXML
    private TableView<Employee> table;

    @FXML
    private TableColumn<Employee, String> nameColumn;
    @FXML
    private TableColumn<Employee, String> typeColumn;
    @FXML
    private TableColumn<Employee, Number> salaryColumn;

    private ObservableList<Employee> employeeList;

    @FXML
    public void initialize() {
        employeeList = FXCollections.observableArrayList();

        nameColumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getName()));
        typeColumn.setCellValueFactory(cellData -> new SimpleStringProperty(
                cellData.getValue() instanceof FullTimeEmployee ? "Full-time" :
                        (cellData.getValue() instanceof PartTimeEmployee ? "Part-time" : "Contractor")));
        salaryColumn.setCellValueFactory(cellData -> new SimpleDoubleProperty(cellData.getValue().calculateSalary()));

        table.setItems(employeeList);

        // Add context menu for removing employees
        ContextMenu contextMenu = new ContextMenu();
        MenuItem removeMenuItem = new MenuItem("Remove Employee");
        removeMenuItem.setOnAction(event -> handleRemoveEmployee());
        contextMenu.getItems().add(removeMenuItem);
        table.setContextMenu(contextMenu);
    }

    @FXML
    private void handleAddEmployee() {
        try {
            String name = nameInput.getText();
            String type = typeInput.getValue();

            // Ensure type is not null
            if (type == null) {
                showAlert("Invalid input", "Please select an employee type.");
                return;
            }

            double rate = Double.parseDouble(rateInput.getText());

            // Validate hours input
            int hours = 0;
            if (!hoursInput.getText().isEmpty()) {
                hours = Integer.parseInt(hoursInput.getText());
            }

            // Perform validation checks
            if (rate <= 0 || hours < 0) {
                showAlert("Invalid input", "Please enter a positive rate and non-negative hours.");
                return;
            }

            Employee employee = switch (type) {
                case "Full-time" -> new FullTimeEmployee(name, rate);
                case "Part-time" -> new PartTimeEmployee(name, rate, hours);
                case "Contractor" -> new Contructor(name, rate, hours);
                default -> throw new IllegalStateException("Unexpected value: " + type);
            };

            employeeList.add(employee);
            table.refresh();
        } catch (NumberFormatException e) {
            // Show alert if input is invalid
            showAlert("Invalid input", "Please enter valid numerical values for rate and hours.");
        }
    }

    @FXML
    private void handleRemoveEmployee() {
        Employee selectedEmployee = table.getSelectionModel().getSelectedItem();
        if (selectedEmployee != null) {
            employeeList.remove(selectedEmployee);
            table.refresh();
        } else {
            showAlert("No selection", "Please select an employee to remove.");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
