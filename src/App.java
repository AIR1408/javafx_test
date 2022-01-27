import java.io.IOException;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class App extends Application {

   
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(App::showError);
        Parent root = FXMLLoader.load(getClass().getResource("table.fxml"));
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Tables");
        stage.setWidth(1000);
        stage.setHeight(670);
        stage.show();
    }

    private static void showError(Thread t, Throwable e) {
        System.err.println("***Default exception handler***");
        if (Platform.isFxApplicationThread()) {
            showErrorDialog(e);
        } else {
            System.err.println("An unexpected error occurred in " + t);
        }
    }

    private static void showErrorDialog(Throwable e) {
        e.printStackTrace();
        Stage dialog = new Stage();
        dialog.initModality(Modality.APPLICATION_MODAL);
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/error.fxml"));
        try {
            Parent root = loader.load();
            dialog.setScene(new Scene(root, 250, 250));
            dialog.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }
}