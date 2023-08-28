package lk.ijse.dep11;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.DirectoryChooser;

import java.io.File;
import java.util.ArrayList;

public class MainFormController {
    public AnchorPane root;
    public TextField txtFolderPath;
    public Button btnBrowse;
    public ProgressBar pbLoader;
    public TilePane tlpImageContainer;
    public ImageView imgGallery;
    public Label hbxgallery;

    public void initialize(){
        tlpImageContainer.prefWidthProperty().bind(root.widthProperty());
        tlpImageContainer.prefHeightProperty().bind(root.heightProperty());
        //imgGallery.fitWidthProperty().bind(root.prefWidthProperty());
        hbxgallery.prefWidthProperty().bind(root.prefWidthProperty());
    }

    public void btnBrowseOnAction(ActionEvent actionEvent) {
        DirectoryChooser directoryChooser = new DirectoryChooser();
        directoryChooser.setTitle("Select a Folder");

        File file = directoryChooser.showDialog(root.getScene().getWindow());
        if(file==null){
            txtFolderPath.clear();
        } else{
            txtFolderPath.setText(file.getAbsolutePath());
            File [] files = file.listFiles();
            ArrayList<File>imageFileList= new ArrayList<>();

            for(File file1: files){
                if(!file1.isFile()) continue;
                if(!isImageFile(file1.getAbsolutePath())) continue;

                System.out.println(file1);
                imageFileList.add(file1);

            }

            Platform.runLater(()->{
                tlpImageContainer.getChildren().clear();
                int count=0;

                for(File imaageFile: imageFileList){
                    Image image = new Image(imaageFile.toURI().toString());
                    ImageView view = new ImageView(image);
                    view.setFitWidth(100);
                    view.setFitHeight(100);
                    tlpImageContainer.getChildren().add(view);
                    count++;

                    double progress = count/ imageFileList.size()*1.0;
                    pbLoader.setProgress(progress);
                }
            });



        }
    }
    public boolean isImageFile(String filepath){
        String [] dir = {".png",".jpeg",".jpg"};
        for(String str: dir){
            if(filepath.endsWith(str)){
                return true;
            }
        }
        return false;
    }

}
