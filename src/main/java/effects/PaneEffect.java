package effects;

import javafx.scene.layout.Pane;

public class PaneEffect {
    public void addDropShadow(Pane pane){
        pane.setStyle("-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);");
    }
}
