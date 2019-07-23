package View;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NumericTextFieldTest extends Application {
    public static void main(String[] args) { launch(args); }

    @Override public void start(Stage stage) {
        TextField numberField = new TextField() {
            @Override public void replaceText(int start, int end, String text) {
                if (text.matches("[0-9]*")) {
                    super.replaceText(start, end, text);
                }
            }

            @Override public void replaceSelection(String text) {
                if (text.matches("[0-9]*")) {
                    super.replaceSelection(text);
                }
            }
        };

        stage.setScene(new Scene(numberField));
        stage.show();
    }
}