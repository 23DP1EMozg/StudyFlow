package models_tests;

import com.example.eksamens_vm.models.TestAttemptTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAttemptTableTest {

    private TestAttemptTable tableRow;

    @BeforeEach
    void setUp() {
        tableRow = new TestAttemptTable("Alice", 90, 0.9);
    }

    @Test
    void constructorAndGettersInitializeFields() {
        assertEquals("Alice", tableRow.getStudent());
        assertEquals(90, tableRow.getGrade());
        assertEquals(0.9, tableRow.getPercentage(), 1e-6);

        StringProperty studentProp    = tableRow.getStudentProperty();
        StringProperty gradeProp      = tableRow.getGradeProperty();
        StringProperty percentageProp = tableRow.getPercentageProperty();

        assertNotNull(studentProp);
        assertNotNull(gradeProp);
        assertNotNull(percentageProp);

        assertEquals("Alice", studentProp.get());
        assertEquals("90",    gradeProp.get());
        assertEquals("0.9",   percentageProp.get());
    }

    @Test
    void settersUpdateValueAndProperty() {
        tableRow.setStudent("Bob");
        tableRow.setGrade(75);
        tableRow.setPercentage(0.75);

        assertEquals("Bob", tableRow.getStudent());
        assertEquals(75,    tableRow.getGrade());
        assertEquals(0.75,  tableRow.getPercentage(), 1e-6);

        assertEquals("Bob",  tableRow.getStudentProperty().get());
        assertEquals("75",   tableRow.getGradeProperty().get());
        assertEquals("0.75", tableRow.getPercentageProperty().get());
    }

    @Test
    void propertyListenersFireOnChange() {
        StringProperty studentProp    = tableRow.getStudentProperty();
        StringProperty gradeProp      = tableRow.getGradeProperty();
        StringProperty percentageProp = tableRow.getPercentageProperty();

        final boolean[] studentChanged    = {false};
        final boolean[] gradeChanged      = {false};
        final boolean[] percentageChanged = {false};

        studentProp.addListener((obs, o, n) -> studentChanged[0] = true);
        gradeProp.addListener((obs, o, n) -> gradeChanged[0] = true);
        percentageProp.addListener((obs, o, n) -> percentageChanged[0] = true);

        studentProp.set("Charlie");
        gradeProp.set("60");
        percentageProp.set("0.6");

        assertTrue(studentChanged[0]);
        assertTrue(gradeChanged[0]);
        assertTrue(percentageChanged[0]);

        assertEquals("Charlie", tableRow.getStudent());
        assertEquals(60,        tableRow.getGrade());
        assertEquals(0.6,       tableRow.getPercentage(), 1e-6);
    }
}
