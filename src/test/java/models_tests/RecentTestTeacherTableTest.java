package models_tests;

import com.example.eksamens_vm.models.RecentTestTeacherTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecentTestTeacherTableTest {
    private RecentTestTeacherTable tableRow;

    @BeforeEach
    void setUp() {
        tableRow = new RecentTestTeacherTable("Quiz 1", "Group A");
    }

    @Test
    void constructorAndGetters_shouldInitializeFields() {
        assertEquals("Quiz 1", tableRow.getName(),  "getName should match constructor");
        assertEquals("Group A", tableRow.getGroup(), "getGroup should match constructor");

        StringProperty nameProp  = tableRow.getNameProperty();
        StringProperty groupProp = tableRow.getGroupProperty();

        assertNotNull(nameProp,  "Name property must not be null");
        assertNotNull(groupProp, "Group property must not be null");
        assertEquals("Quiz 1",    nameProp.get(),  "Name property value should match");
        assertEquals("Group A",   groupProp.get(), "Group property value should match");
    }

    @Test
    void setters_shouldUpdateValueAndProperty() {
        tableRow.setName("Midterm");
        tableRow.setGroup("Group B");

        assertEquals("Midterm", tableRow.getName(),  "getName should reflect setter");
        assertEquals("Group B", tableRow.getGroup(), "getGroup should reflect setter");

        assertEquals("Midterm", tableRow.getNameProperty().get(),  "Name property should update");
        assertEquals("Group B", tableRow.getGroupProperty().get(), "Group property should update");
    }

    @Test
    void propertyBinding_shouldNotifyListeners() {
        StringProperty nameProp  = tableRow.getNameProperty();
        StringProperty groupProp = tableRow.getGroupProperty();

        final boolean[] nameChanged  = {false};
        final boolean[] groupChanged = {false};

        nameProp.addListener((obs, oldV, newV) -> nameChanged[0]  = true);
        groupProp.addListener((obs, oldV, newV) -> groupChanged[0] = true);

        nameProp.set("Final Exam");
        groupProp.set("Group C");

        assertTrue(nameChanged[0],  "Name listener should fire on change");
        assertTrue(groupChanged[0], "Group listener should fire on change");

        assertEquals("Final Exam", tableRow.getName());
        assertEquals("Group C",    tableRow.getGroup());
    }
}
