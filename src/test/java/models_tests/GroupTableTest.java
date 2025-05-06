package models_tests;

import com.example.eksamens_vm.models.GroupTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GroupTableTest {
    private GroupTable tableRow;

    @BeforeEach
    void setUp() {
        tableRow = new GroupTable("Group X", "12");
    }

    @Test
    void constructorAndGetters_shouldInitializeProperties() {
        assertEquals("Group X", tableRow.getName(),       "Name getter should return constructor value");
        assertEquals("12",      tableRow.getUsersCount(), "UsersCount getter should return constructor value");

        StringProperty nameProp  = tableRow.getNameProperty();
        StringProperty countProp = tableRow.getUsersCountProperty();
        assertNotNull(nameProp,  "Name property must not be null");
        assertNotNull(countProp, "UsersCount property must not be null");
        assertEquals("Group X", nameProp.get(),  "Name property value should match");
        assertEquals("12",      countProp.get(), "UsersCount property value should match");
    }

    @Test
    void setters_shouldUpdateBothValueAndProperty() {
        tableRow.setName("Group Y");
        tableRow.setUsersCount("5");

        assertEquals("Group Y", tableRow.getName(),       "Name getter should update");
        assertEquals("5",       tableRow.getUsersCount(), "UsersCount getter should update");

        assertEquals("Group Y", tableRow.getNameProperty().get(),  "Name property should update");
        assertEquals("5",       tableRow.getUsersCountProperty().get(), "UsersCount property should update");
    }

    @Test
    void propertyBinding_shouldReflectExternalChanges() {
        StringProperty nameProp  = tableRow.getNameProperty();
        StringProperty countProp = tableRow.getUsersCountProperty();

        final boolean[] nameChanged = {false};
        final boolean[] countChanged = {false};
        nameProp.addListener((obs, oldVal, newVal) -> nameChanged[0] = true);
        countProp.addListener((obs, oldVal, newVal) -> countChanged[0] = true);

        nameProp.set("Group Z");
        countProp.set("7");

        assertTrue(nameChanged[0],  "Name listener should fire on property change");
        assertTrue(countChanged[0], "UsersCount listener should fire on property change");

        assertEquals("Group Z", tableRow.getName());
        assertEquals("7",       tableRow.getUsersCount());
    }
}
