import com.kesho.ui.control.autofilltextbox.AutoFillTextBox;
import javafx.application.Application;
import javafx.collections.ListChangeListener;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

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
//        ObservableList<FamilyDto> data = FXCollections.observableArrayList(Lists.newArrayList(new FamilyDto(1L, "aa"), new FamilyDto(2L, "ab"), new FamilyDto(1L, "aaa")));
//        String[] s = new String[]{"apple","ball","cat","doll","elephant",
//                "fight","georgeous","height","ice","jug",
//                "aplogize","bank","call","done","ego",
//                "finger","giant","hollow","internet","jumbo",
//                "kilo","lion","for","length","primary","stage",
//                "scene","zoo","jumble","auto","text",
//                "root","box","items","hip-hop","himalaya","nepal",
//                "kathmandu","kirtipur","everest","buddha","epic","hotel"};

//        for(int j=0; j<s.length; j++){
//            data.add(s[j]);
//        }


        //Layout
        HBox hbox = new HBox();
        hbox.setSpacing(10);
        //CustomControl
        final AutoFillTextBox box = new AutoFillTextBox();
//        box.setData(data);
        box.getListview().getSelectionModel().getSelectedItems().addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change change) {
                if (!change.getList().isEmpty()) {
                    System.out.println("################" + change.getList().get(0));
                }
            }
        });
        //Label
        Label l = new Label("AutoFillTextBox: ");
//        l.translateYProperty().set(5);
//        l.translateXProperty().set(5);

        hbox.getChildren().addAll(l,box);
        Scene scene = new Scene(hbox,300,200);

        primaryStage.setScene(scene);
        scene.getStylesheets().add("/style/AutoFillTextBox.css");
        primaryStage.show();

    }
}