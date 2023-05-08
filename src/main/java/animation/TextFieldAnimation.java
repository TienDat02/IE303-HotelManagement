package animation;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.control.TextField;
import javafx.util.Duration;

public class TextFieldAnimation {

    public static void animateTextField(TextField textField, String text) {
        final int numChars = text.length();
        final Duration frameGap = Duration.seconds(0.1);
        final Duration animationLength = frameGap.multiply(numChars);

        Timeline timeline = new Timeline();
        for (int i = 0; i < numChars; i++) {
            final int currChar = i;
            KeyFrame keyFrame = new KeyFrame(frameGap.multiply(i), event -> {
                String currText = text.substring(0, currChar + 1);
                textField.setText(currText);
            });
            timeline.getKeyFrames().add(keyFrame);
        }
        timeline.setOnFinished(event -> {
            textField.setText(text);
            textField.end();
        });
        timeline.setCycleCount(1);
        timeline.play();
    }

}