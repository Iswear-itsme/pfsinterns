package employee;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class main extends Application {

    private ObservableList<Employee> employees = FXCollections.observableArrayList();

    @Override
    public void start(Stage primaryStage) {
        BorderPane root = new BorderPane();
        VBox mainLayout = new VBox(10);
        root.setCenter(mainLayout);

        TableView<Employee> employeesTable = new TableView<>();
        employeesTable.setPlaceholder(new Label("No employees found."));
        employeesTable.setItems(employees);

        TableColumn<Employee, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Employee, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Employee, Double> salaryColumn = new TableColumn<>("Salary");
        salaryColumn.setCellValueFactory(new PropertyValueFactory<>("salary"));

        employeesTable.getColumns().addAll(idColumn, nameColumn, salaryColumn);

        Button addEmployeeButton = new Button("Add Employee");
        addEmployeeButton.setOnAction(event -> {
            Stage addEmployeeStage = new Stage();
            addEmployeeStage.setTitle("Add Employee");
            addEmployeeStage.setResizable(false);

            Label nameLabel = new Label("Name:");
            TextField nameField = new TextField();

            Label salaryLabel = new Label("Salary:");
            TextField salaryField = new TextField();

            Button addButton = new Button("Add");
            addButton.setOnAction(event2 -> {
                String name = nameField.getText();
                double salary = Double.parseDouble(salaryField.getText());
                salary = applyTax(salary);
                Employee newEmployee = new Employee(employees.size() + 1, name, salary);
                employees.add(newEmployee);
                addEmployeeStage.close();
            });

            VBox dialogLayout = new VBox(10);
            dialogLayout.getChildren().addAll(nameLabel, nameField, salaryLabel, salaryField, addButton);
            dialogLayout.setSpacing(10);

            Scene dialogScene = new Scene(dialogLayout, 300, 200);
            addEmployeeStage.setScene(dialogScene);
            addEmployeeStage.show();
        });

        Button editEmployeeButton = new Button("Edit Employee");
        editEmployeeButton.setOnAction(event -> {
            Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                Stage editEmployeeStage = new Stage();
                editEmployeeStage.setTitle("Edit Employee");
                editEmployeeStage.setResizable(false);

                Label nameLabel = new Label("Name:");
                TextField nameField = new TextField(selectedEmployee.getName());

                Label salaryLabel = new Label("Salary:");
                TextField salaryField = new TextField(String.valueOf(selectedEmployee.getSalary()));

                Button editButton = new Button("Edit");
                editButton.setOnAction(event2 -> {
                    String name = nameField.getText();
                    double salary = Double.parseDouble(salaryField.getText());
                    salary =applyTax(salary);
                    int index = employees.indexOf(selectedEmployee);
                    Employee newEmployee = new Employee(index + 1, name, salary);
                    employees.set(index, newEmployee);
                    editEmployeeStage.close();
                });

                VBox dialogLayout = new VBox(10);
                dialogLayout.getChildren().addAll(nameLabel, nameField, salaryLabel, salaryField, editButton);
                dialogLayout.setSpacing(10);

                Scene dialogScene = new Scene(dialogLayout, 300, 200);
                editEmployeeStage.setScene(dialogScene);
                editEmployeeStage.show();
            }
        });

        Button removeEmployeeButton = new Button("Remove Employee");
        removeEmployeeButton.setOnAction(event -> {
            Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                employees.remove(selectedEmployee);
            }
        });

        Button addDeductionButton = new Button("Add Deduction");
        addDeductionButton.setOnAction(event -> {
            Employee selectedEmployee = employeesTable.getSelectionModel().getSelectedItem();
            if (selectedEmployee != null) {
                Stage addDeductionStage = new Stage();
                addDeductionStage.setTitle("Add Deduction");
                addDeductionStage.setResizable(false);

                Label deductionLabel = new Label("Deduction:");
                TextField deductionField = new TextField();

                Button addButton = new Button("Add");
                addButton.setOnAction(event2 -> {
                    double deduction = Double.parseDouble(deductionField.getText());
                    selectedEmployee.setSalary(selectedEmployee.getSalary() - deduction);
                    int index = employees.indexOf(selectedEmployee);
                    Employee newEmployee = new Employee(index + 1, selectedEmployee.getName(), selectedEmployee.getSalary());
                    employees.set(index, newEmployee);
                    addDeductionStage.close();
                });

                VBox dialogLayout = new VBox(10);
                dialogLayout.getChildren().addAll(deductionLabel, deductionField, addButton);
                dialogLayout.setSpacing(10);

                Scene dialogScene = new Scene(dialogLayout, 300, 200);
                addDeductionStage.setScene(dialogScene);
                addDeductionStage.show();
            }
        });

      

        mainLayout.getChildren().addAll(employeesTable, addEmployeeButton, editEmployeeButton, removeEmployeeButton, addDeductionButton);

        primaryStage.setTitle("Employee Payroll System");
        primaryStage.setScene(new Scene(root, 800, 600));
        primaryStage.show();
    }

    public static double applyTax(double salary) {
        if (salary < 1000)
            return salary;
        else if (salary < 2000)
            salary = (salary - 1000) * 0.95 + 1000;
        else if (salary < 3000)
            salary = (salary - 2000) * 0.9 + 1000 + 1000 * 0.95;
        else if (salary < 4000)
            salary = (salary - 3000) * 0.85 + 1000 + 1000 * 0.95 + 1000 * 0.9;
        return salary;
    }

  
    public static void main(String[] args) {
        launch(args);
    }
}