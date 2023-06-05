package effects;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.scene.control.Button;
import javafx.scene.effect.Glow;
import javafx.scene.transform.Scale;
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
    public static void addHoverAnimation(Button button) {
        Scale scale = new Scale(1, 1);
        scale.setPivotX(button.getWidth() / 2);
        scale.setPivotY(button.getHeight() / 2);
        scale.setX(1.2);
        scale.setY(1.2);

        button.setOnMouseEntered(event -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(scale.xProperty(), 1.0), new KeyValue(scale.yProperty(), 1.0)),
                    new KeyFrame(Duration.millis(200), new KeyValue(scale.xProperty(), 1.2), new KeyValue(scale.yProperty(), 1.2))
            );
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            timeline.play();
        });

        button.setOnMouseExited(event -> {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.ZERO, new KeyValue(scale.xProperty(), 1.2), new KeyValue(scale.yProperty(), 1.2)),
                    new KeyFrame(Duration.millis(100), new KeyValue(scale.xProperty(), 1.0), new KeyValue(scale.yProperty(), 1.0))
            );
            timeline.setCycleCount(1);
            timeline.setAutoReverse(false);
            timeline.play();
        });

        button.getTransforms().add(scale);
    }
    public static void addHoverEffect(Button button) {
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-border-radius: 30, 30, 30, 30; -fx-background-color: #3474d4; -fx-text-fill: #FFFFFF; -fx-font-weight: bold");
        });
        button.setOnMouseExited(e -> {
            button.setStyle("-fx-background-radius: 20 20 20 20; -fx-background-color:  #1C2127; -fx-text-fill: #f5f5f5");
        });
    }
}
