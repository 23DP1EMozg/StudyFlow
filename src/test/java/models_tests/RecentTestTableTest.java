package models_tests;

import com.example.eksamens_vm.models.RecentTestTable;
import javafx.beans.property.StringProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RecentTestTableTest {
    private RecentTestTable recent;

    @BeforeEach
    void setUp() {
        recent = new RecentTestTable("Midterm", 85);
    }

    @Test
    void constructorAndGetters_shouldInitializeFields() {
        assertEquals("Midterm", recent.getName(), "Name should match constructor");
        assertEquals(85, recent.getGrade(),      "Grade should match constructor");

        StringProperty nameProp  = recent.getNameProperty();
        StringProperty gradeProp = recent.getGradeProperty();

        assertNotNull(nameProp,  "Name property must not be null");
        assertNotNull(gradeProp, "Grade property must not be null");
        assertEquals("Midterm", nameProp.get(),  "Name property value should match");
        assertEquals("85",      gradeProp.get(), "Grade property (string) should match");
    }

    @Test
    void setters_shouldUpdateValueAndProperty() {
        recent.setName("Final");
        recent.setGrade(92);

        assertEquals("Final", recent.getName(), "getName should reflect setter");
        assertEquals(92,      recent.getGrade(), "getGrade should reflect setter");

        assertEquals("Final", recent.getNameProperty().get(),  "Name property should update");
        assertEquals("92",    recent.getGradeProperty().get(), "Grade property should update");
    }

    @Test
    void propertyBinding_shouldNotifyListeners() {
        StringProperty nameProp  = recent.getNameProperty();
        StringProperty gradeProp = recent.getGradeProperty();

        final boolean[] nameChanged  = {false};
        final boolean[] gradeChanged = {false};

        nameProp.addListener((obs, oldV, newV) -> nameChanged[0]  = true);
        gradeProp.addListener((obs, oldV, newV) -> gradeChanged[0] = true);

        nameProp.set("Quiz");
        gradeProp.set("78");

        assertTrue(nameChanged[0],  "Name listener should fire on change");
        assertTrue(gradeChanged[0], "Grade listener should fire on change");

        assertEquals("Quiz", recent.getName());
        assertEquals(78,     recent.getGrade());
    }
}
