package models_tests;

import com.example.eksamens_vm.enums.TestStatus;
import com.example.eksamens_vm.models.TestModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestTest {

    private TestModel testModel;

    @BeforeEach
    void setUp() {
        testModel = new TestModel(1, 10, 20, 30, "Midterm", TestStatus.INCOMPLETE);
    }

    @Test
    void constructorAndGettersReturnCorrectValues() {
        assertEquals(1, testModel.getId());
        assertEquals(10, testModel.getTeacherId());
        assertEquals(20, testModel.getRoomId());
        assertEquals(30, testModel.getGroupId());
        assertEquals("Midterm", testModel.getName());
        assertEquals(TestStatus.INCOMPLETE, testModel.getTestStatus());
    }

    @Test
    void settersUpdateAllFields() {
        testModel.setId(2);
        testModel.setTeacherId(11);
        testModel.setRoomId(21);
        testModel.setGroupId(31);
        testModel.setName("Final");
        testModel.setTestStatus(TestStatus.COMPLETE);

        assertEquals(2, testModel.getId());
        assertEquals(11, testModel.getTeacherId());
        assertEquals(21, testModel.getRoomId());
        assertEquals(31, testModel.getGroupId());
        assertEquals("Final", testModel.getName());
        assertEquals(TestStatus.COMPLETE, testModel.getTestStatus());
    }

    @Test
    void independentInstancesMaintainState() {
        TestModel other = new TestModel(3, 12, 22, 32, "Quiz", TestStatus.INCOMPLETE);
        testModel.setName("Updated");
        testModel.setTestStatus(TestStatus.COMPLETE);
        assertEquals("Updated", testModel.getName());
        assertEquals(TestStatus.COMPLETE, testModel.getTestStatus());
        assertEquals("Quiz", other.getName());
        assertEquals(TestStatus.INCOMPLETE, other.getTestStatus());
    }
}
