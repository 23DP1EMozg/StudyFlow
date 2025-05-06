package models_tests;

import com.example.eksamens_vm.models.TestAttempt;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class TestAttemptTest {

    private TestAttempt attempt;

    @BeforeEach
    void setUp() {
        attempt = new TestAttempt(5, 85, 10, 0.85, 3);
    }

    @Test
    void constructorAndGettersReturnCorrectValues() {
        assertEquals(5, attempt.getUserId());
        assertEquals(85, attempt.getGrade());
        assertEquals(10, attempt.getTestId());
        assertEquals(0.85, attempt.getPercent(), 1e-6);
        assertEquals(3, attempt.getRoomId());
    }

    @Test
    void settersUpdateAllFields() {
        attempt.setUserId(6);
        attempt.setGrade(90);
        attempt.setTestId(11);
        attempt.setPercent(0.90);
        attempt.setRoomId(4);

        assertEquals(6, attempt.getUserId());
        assertEquals(90, attempt.getGrade());
        assertEquals(11, attempt.getTestId());
        assertEquals(0.90, attempt.getPercent(), 1e-6);
        assertEquals(4, attempt.getRoomId());
    }

    @Test
    void independentInstancesMaintainState() {
        TestAttempt other = new TestAttempt(7, 70, 12, 0.70, 5);
        attempt.setGrade(95);
        attempt.setPercent(0.95);

        assertEquals(95, attempt.getGrade());
        assertEquals(0.95, attempt.getPercent(), 1e-6);

        assertEquals(70, other.getGrade());
        assertEquals(0.70, other.getPercent(), 1e-6);
    }

    @Test
    void percentBoundsEdgeCases() {
        attempt.setPercent(0.0);
        assertEquals(0.0, attempt.getPercent(), 1e-6);

        attempt.setPercent(1.0);
        assertEquals(1.0, attempt.getPercent(), 1e-6);
    }
}
