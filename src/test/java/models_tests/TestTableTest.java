package models_tests;

import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.models.TestTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTableTest {

    private TestTable tableRow;

    @BeforeEach
    void setUp() {
        tableRow = new TestTable("Quiz 1", TestStatus.INCOMPLETE, "Dr. Smith", "Group A");
    }

    @Test
    void constructorAndGettersInitializeFields() {
        assertEquals("Quiz 1", tableRow.getName());
        assertEquals("Group A", tableRow.getGroup());
        assertEquals("Dr. Smith", tableRow.getTeacher());
        assertEquals("INCOMPLETE", tableRow.getStatus());

        StringProperty nameProp    = tableRow.getNameProperty();
        StringProperty groupProp   = tableRow.getGroupProperty();
        StringProperty teacherProp = tableRow.getTeacherProperty();
        StringProperty statusProp  = tableRow.getStatusProperty();

        assertNotNull(nameProp);
        assertNotNull(groupProp);
        assertNotNull(teacherProp);
        assertNotNull(statusProp);

        assertEquals("Quiz 1",    nameProp.get());
        assertEquals("Group A",   groupProp.get());
        assertEquals("Dr. Smith", teacherProp.get());
        assertEquals("INCOMPLETE", statusProp.get());
    }

    @Test
    void settersUpdateValueAndProperty() {
        tableRow.setName("Midterm");
        tableRow.setGroup("Group B");
        tableRow.setTeacher("Prof. Jones");
        tableRow.setStatus("COMPLETE");

        assertEquals("Midterm",     tableRow.getName());
        assertEquals("Group B",     tableRow.getGroup());
        assertEquals("Prof. Jones", tableRow.getTeacher());
        assertEquals("COMPLETE",    tableRow.getStatus());

        assertEquals("Midterm",     tableRow.getNameProperty().get());
        assertEquals("Group B",     tableRow.getGroupProperty().get());
        assertEquals("Prof. Jones", tableRow.getTeacherProperty().get());
        assertEquals("COMPLETE",    tableRow.getStatusProperty().get());
    }

    @Test
    void propertyListenersFireOnChange() {
        StringProperty nameProp    = tableRow.getNameProperty();
        StringProperty statusProp  = tableRow.getStatusProperty();

        final boolean[] nameChanged   = {false};
        final boolean[] statusChanged = {false};

        nameProp.addListener((obs, oldV, newV) -> nameChanged[0] = true);
        statusProp.addListener((obs, oldV, newV) -> statusChanged[0] = true);

        nameProp.set("Final Exam");
        statusProp.set("IN_PROGRESS");

        assertTrue(nameChanged[0]);
        assertTrue(statusChanged[0]);

        assertEquals("Final Exam",     tableRow.getName());
        assertEquals("IN_PROGRESS",    tableRow.getStatus());
    }
}
