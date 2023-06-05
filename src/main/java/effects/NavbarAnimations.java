package effects;

import javafx.scene.control.Button;

public class NavbarAnimations {
    public void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: #E7F1FB; -fx-text-fill: #000000; -fx-font-weight: bold; -fx-border-radius: 10, 10, 10, 10");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-color:  #1C2127; -fx-background-radius: 0; -fx-text-fill: #f5f5f5");
        });
    }
    public void ChosenButtonEffect(Button button) {
        button.setStyle("-fx-background-color: #4c4c6c; -fx-text-fill: #FFFFFF; -fx-font-weight: bold; -fx-border-radius: 10, 10, 10, 10");
    }
}
