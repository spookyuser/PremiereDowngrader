package notDeafult;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Cursor;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.layout.GridPane;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

import org.apache.commons.io.*;


public class SampleController {
    public TextArea brows;
    public ComboBox<keyVal> dropdown  = new ComboBox<keyVal>();
    public Button saveBtn;
    public TextFlow source;
    logic lg = new logic();
    @FXML public Button browse;
    @FXML private GridPane paney;
    private Stage myStage;

    public void setStage(Stage stage) {
        this.myStage = stage;
    }


    public void sayHelloWorld(ActionEvent actionEvent){
        myStage.getScene().setCursor(Cursor.WAIT);
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Premiere Projects", "*.prproj"));
        File selectedFile = fileChooser.showOpenDialog(myStage);
        if(selectedFile!=null){
            hideButton();
            System.out.println(selectedFile.getAbsolutePath());
            initCopy(selectedFile);
        }

    }

    public void dragEvent(DragEvent event) {
        myStage.getScene().setCursor(Cursor.WAIT);
        hideButton();
        System.out.println("onDragDropped");
        Dragboard db = event.getDragboard();
        if(db.hasFiles()){
            System.out.println("File Path is:" + db.getFiles().get(0).getAbsolutePath());
            File convert = db.getFiles().get(0);
            String ext = FilenameUtils.getExtension(db.getFiles().get(0).getAbsolutePath());
            System.out.println(ext);
            if (FilenameUtils.getExtension(convert.getPath()).equals("prproj")){
                initCopy(convert);
            }
        }

    }

    public void initCopy(File convert) {

        String out = lg.copyFile(convert);
        brows.setText(out);
    }

    public void hideButton(){

        browse.setVisible(false);
        browse.setManaged(false);
        paney.setStyle("-fx-background-image: url(../resources/bgfill.png)");
        brows.setVisible(true);
        brows.setManaged(true);
        dropdown.setVisible(true);
        dropdown.setManaged(true);
        dropdown.getItems().add(new keyVal(29, "CC2015"));
        dropdown.getItems().add(new keyVal(28, "CC2014"));
        for(int i=0; i<35; i++){
            int v = (35-i);
            dropdown.getItems().add(new keyVal(v, "SaveVersion"+v));
        }
        dropdown.setValue(new keyVal(1, "Select a Version"));
        paney.setVgap(5);
        saveBtn.setManaged(true);
        saveBtn.setVisible(true);
        myStage.getScene().setCursor(Cursor.DEFAULT);


    }

    public void saveNewVersion(ActionEvent actionEvent) throws Exception {
        FileChooser fileChooser = new FileChooser();

        //Set extension filter
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Premiere Files (*.prproj)", "*.prproj");
        fileChooser.getExtensionFilters().add(extFilter);

        //Show save file dialog
        File file = fileChooser.showSaveDialog(myStage);
        System.out.println(file.getPath());

        //Get Working Dir
        myStage.getScene().setCursor(Cursor.WAIT);
        lg.ch.change(new File(lg.tmpDir.toPath() + "/xmlFile.xml"), dropdown.getValue().getKey());
        lg.compressGz(file);
        myStage.getScene().setCursor(Cursor.DEFAULT);
        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Done!", ButtonType.CLOSE);
        alert.showAndWait();
        System.exit(0);

    }

}
