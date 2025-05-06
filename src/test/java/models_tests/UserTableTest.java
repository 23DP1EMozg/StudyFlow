package models_tests;

import com.example.eksamens_vm.enums.UserRole;
import com.example.eksamens_vm.models.UserTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserTableTest {

    private UserTable tableRow;

    @BeforeEach
    void setUp() {
        tableRow = new UserTable("Group1", UserRole.STUDENT, "jdoe");
    }

    @Test
    void constructorAndGettersInitializeFields() {
        assertEquals("jdoe", tableRow.getUsername());
        assertEquals("STUDENT", tableRow.getRole());
        assertEquals("Group1", tableRow.getGroup());

        StringProperty usernameProp = tableRow.getUsernameProperty();
        StringProperty roleProp     = tableRow.getRoleProperty();
        StringProperty groupProp    = tableRow.getGroupProperty();

        assertNotNull(usernameProp);
        assertNotNull(roleProp);
        assertNotNull(groupProp);

        assertEquals("jdoe",    usernameProp.get());
        assertEquals("STUDENT", roleProp.get());
        assertEquals("Group1",  groupProp.get());
    }

    @Test
    void settersUpdateValueAndProperty() {
        tableRow.setUsername("asmith");
        tableRow.setRole(UserRole.TEACHER);
        tableRow.setGroup("Group2");

        assertEquals("asmith",    tableRow.getUsername());
        assertEquals("TEACHER",   tableRow.getRole());
        assertEquals("Group2",    tableRow.getGroup());

        assertEquals("asmith",    tableRow.getUsernameProperty().get());
        assertEquals("TEACHER",   tableRow.getRoleProperty().get());
        assertEquals("Group2",    tableRow.getGroupProperty().get());
    }

    @Test
    void propertyListenersFireOnChange() {
        StringProperty usernameProp = tableRow.getUsernameProperty();
        StringProperty roleProp     = tableRow.getRoleProperty();
        StringProperty groupProp    = tableRow.getGroupProperty();

        boolean[] userChanged = {false};
        boolean[] roleChanged = {false};
        boolean[] groupChanged = {false};

        usernameProp.addListener((obs, o, n) -> userChanged[0] = true);
        roleProp.addListener((obs, o, n)     -> roleChanged[0] = true);
        groupProp.addListener((obs, o, n)    -> groupChanged[0] = true);

        usernameProp.set("newUser");
        roleProp.set("ADMIN");
        groupProp.set("Group3");

        assertTrue(userChanged[0]);
        assertTrue(roleChanged[0]);
        assertTrue(groupChanged[0]);

        assertEquals("newUser", tableRow.getUsername());
        assertEquals("ADMIN",   tableRow.getRole());
        assertEquals("Group3",  tableRow.getGroup());
    }
}
