package animation;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.util.Duration;

public class ButtonAnimation {
    public static void addGlowAnimation(Button button) {
        Glow glow = new Glow();
        glow.setLevel(0.9); // adjust the level to your preference
        button.setEffect(glow);

        Timeline timeline = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(glow.levelProperty(), 0.0)),
                new KeyFrame(Duration.seconds(1), new KeyValue(glow.levelProperty(), 0.9))
        );
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.setAutoReverse(true);
        timeline.play();
    }
    public static void addTextAnimation(Button button, String text) {
        Timeline timeline = new Timeline();
        for (int i = 0; i < text.length(); i++) {
            final int index = i;
            KeyFrame keyFrame = new KeyFrame(Duration.millis(500 * i), event -> {
                button.setText(text.substring(0, index + 1));
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.play();
    }
}
