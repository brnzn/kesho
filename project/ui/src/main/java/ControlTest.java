//import com.kesho.ui.control.autofilltextbox.AutoFillTextBox;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import org.controlsfx.dialog.Dialogs;
//import np.com.ngopal.control.AutoFillTextBox;

/**
 * Created with IntelliJ IDEA.
 * User: orenberenson
 * Date: 12/22/13
 * Time: 9:17 PM
 * To change this template use File | Settings | File Templates.
 */
public class ControlTest extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("AutoFillTextBox without FilterMode");

        //SAMPLE DATA
        ObservableList data = FXCollections.observableArrayList();
        String[] s = new String[]{"apple","ball","cat","doll","elephant",
                "fight","georgeous","height","ice","jug",
                "aplogize","bank","call","done","ego",
                "finger","giant","hollow","internet","jumbo",
                "kilo","lion","for","length","primary","stage",
                "scene","zoo","jumble","auto","text",
                "root","box","items","hip-hop","himalaya","nepal",
                "kathmandu","kirtipur","everest","buddha","epic","hotel"};

        for(int j=0; j<s.length; j++){
            data.add(s[j]);
        }

        //Layout
        HBox hbox = new HBox();
        hbox.setSpacing(10);

        //CustomControl
 //       final AutoFillTextBox box = new AutoFillTextBox(data);
        //Label
        Label l = new Label("AutoFillTextBox: ");
        l.translateYProperty().set(5);
        l.translateXProperty().set(5);

        Button b = new Button("click");
        b.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Dialogs.create()
                        .owner( primaryStage)
                        .title("You do want dialogs right?")
                        .masthead("Just Checkin'")
                        .message( "I was a bit worried that you might not want them, so I wanted to double check.")
                        .showError();
            }
        });
        hbox.getChildren().addAll(b);
        Scene scene = new Scene(hbox,300,200);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("/test/control.css");
        primaryStage.show();

    }
}