package org.example.controles;

import javafx.scene.Scene;
import javafx.stage.Stage;
import java.util.HashMap;

public class SceneManager {
    private final Stage stage;
    private final HashMap<String, Scene> scenes = new HashMap<>();
    private final HashMap<String, Object> controllers = new HashMap<>();
    private String currentScene;

    public SceneManager(Stage stage){
        this.stage = stage;
    }

    public void addScene(String name, Scene scene, Object controller) {
        scenes.put(name, scene);
        controllers.put(name, controller);
    }

    public void switchTo(String sceneName) {
        Scene scene = scenes.get(sceneName);
        if (scene != null) {
            currentScene = sceneName;
            stage.setScene(scene);
            stage.centerOnScreen();
        } else {
            throw new IllegalArgumentException("Cena não registrada: " + sceneName);
        }
    }

    public <T> T getCOntroller(String sceneName) {
        return (T) controllers.get(sceneName);
    }

    public String getCurrentScene(){
        return currentScene;
    }
}
