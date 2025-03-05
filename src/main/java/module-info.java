module com.example.eksamens_vm {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;

    opens com.example.eksamens_vm to javafx.fxml;
    opens com.example.eksamens_vm.controllers to javafx.fxml;
    opens com.example.eksamens_vm.models to com.google.gson, javafx.fxml;
    opens com.example.eksamens_vm.services to com.google.gson;

    exports com.example.eksamens_vm;
    exports com.example.eksamens_vm.enums;
}
