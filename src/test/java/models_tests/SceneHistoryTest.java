package models_tests;

import com.example.eksamens_vm.models.SceneHistory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceneHistoryTest {

    private SceneHistory history;

    @BeforeEach
    void setUp() {
        history = new SceneHistory("login.fxml", "Login");
    }

    @Test
    void constructorAndGetters() {
        assertEquals("login.fxml", history.getFxmlFile());
        assertEquals("Login", history.getTitle());
    }

    @Test
    void settersUpdateFields() {
        history.setFxmlFile("dashboard.fxml");
        history.setTitle("Dashboard");
        assertEquals("dashboard.fxml", history.getFxmlFile());
        assertEquals("Dashboard", history.getTitle());
    }

    @Test
    void toStringContainsBothProperties() {
        String repr = history.toString();
        assertTrue(repr.contains("fxmlFile='login.fxml'"));
        assertTrue(repr.contains("title='Login'"));
    }

    @Test
    void independentInstancesMaintainState() {
        SceneHistory other = new SceneHistory("settings.fxml", "Settings");
        history.setFxmlFile("home.fxml");
        history.setTitle("Home");
        assertEquals("home.fxml", history.getFxmlFile());
        assertEquals("Home", history.getTitle());
        assertEquals("settings.fxml", other.getFxmlFile());
        assertEquals("Settings", other.getTitle());
    }
}
