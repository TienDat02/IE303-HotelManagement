package app.ie303hotelmanagement;

public class ChosenSceneSingleton {
    private static ChosenSceneSingleton instance = new ChosenSceneSingleton();
    private String chosenScene;
    private ChosenSceneSingleton() {}
    public static ChosenSceneSingleton getInstance() {
        return instance;
    }
    public void setChosenScene(String chosenScene) {
        this.chosenScene = chosenScene;
    }
    public String getChosenScene() {
        return chosenScene;
    }
}
