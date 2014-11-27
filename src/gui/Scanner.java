/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.NumberFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ToolBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.filechooser.FileSystemView;

/**
 *
 * @author Gian
 */
public class Scanner extends Application {

    private Label outputPathLabel;
    private TableView tableView;
    private Stage stage;
    private VBox fileBox;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("XClavis");
        stage.setMinHeight(650);
        stage.setMinWidth(650);
        //stage.getIcons().add(new Image(Start.class.getResourceAsStream("logo.jpg")));

        BorderPane mainPane = new BorderPane();

        VBox topBox = new VBox();

        //Main Menu MULTILANG!!!!!!!!!!!!!!!!!
        MenuBar menuBar = new MenuBar();
        //Menu File
        Menu menuFile = new Menu("Datei");
        MenuItem openFile = new MenuItem("Öffnen");
        openFile.setOnAction((event) -> {
            showFileOpener();
        });
        MenuItem exit = new MenuItem("Beenden");
        menuFile.getItems().addAll(openFile, exit);
        //Menu Settings
        Menu menuSettings = new Menu("Einstellungen");
        MenuItem security = new MenuItem("Sicherheit");
        MenuItem language = new MenuItem("Sprache");
        MenuItem general = new MenuItem("Allgemein");
        menuSettings.getItems().addAll(security, language, general);
        //Menu Help
        Menu menuHelp = new Menu("Hilfe");
        MenuItem about = new MenuItem("Über");
        about.setOnAction((event) -> {
            //todo
        });
        MenuItem documentation = new MenuItem("Dokumentation");
        MenuItem license = new MenuItem("Lizenzen");
        menuHelp.getItems().addAll(about, documentation, license);

        menuBar.getMenus().addAll(menuFile, menuSettings, menuHelp);
        topBox.getChildren().add(menuBar);

        //Tool Bar edit Paths
        ToolBar toolBar = new ToolBar();
        Button openFileBtn = new Button("Datei öffnen");
        openFileBtn.setGraphic(new ImageView("file:icons/add10.png"));
        openFileBtn.setOnAction((event) -> {
            showFileOpener();
        });
        Button qrScannerBtn = new Button("QR-Code scannen");
        qrScannerBtn.setGraphic(new ImageView("file:icons/camera1.png"));
        Button keyListBtn = new Button("Schlüsselliste anzeigen");
        keyListBtn.setGraphic(new ImageView("file:icons/key5.png"));
        toolBar.getItems().addAll(openFileBtn, qrScannerBtn, keyListBtn);

        topBox.getChildren().add(toolBar);
        mainPane.setTop(topBox);

        //Main Content
        VBox mainBox = new VBox(10);
        mainBox.setAlignment(Pos.CENTER);
        mainBox.setPadding(new Insets(25, 25, 25, 25));

        Text topTxt = new Text("Wählen Sie die gewünschte Datei aus:");

        Text middleTxt = new Text("Ausgewählte Dateien");

        tableView = new TableView();
        TableColumn iconColumn = new TableColumn("Icon");
        TableColumn filenameColumn = new TableColumn("Dateiname");
        TableColumn extensionColumn = new TableColumn("Dateityp");
        TableColumn filesizeColumn = new TableColumn("Grösse");
        tableView.getColumns().addAll(iconColumn, filenameColumn, extensionColumn, filesizeColumn);
        
        fileBox = new VBox();

        Text outputPathTxt = new Text("Zielordner:");
        outputPathLabel = new Label();
        outputPathLabel.setDisable(true);

        mainBox.getChildren().addAll(topTxt, middleTxt, tableView, outputPathTxt, outputPathLabel);

        mainPane.setCenter(mainBox);

        Scene scene = new Scene(mainPane);

        //Drag & Drop File Transfer
//        scene.setOnDragOver((event) -> {
//            Dragboard dragboard = event.getDragboard();
//            if (dragboard.hasFiles()) {
//                event.acceptTransferModes(TransferMode.COPY);
//            } else {
//                event.consume();
//            }
//        });
//
//        scene.setOnDragDropped((event) -> {
//            Dragboard dragboard = event.getDragboard();
//            if (dragboard.hasFiles()) {
//                dragboard.getFiles().stream().forEach((droppedFile) -> {
//                    loadFile(droppedFile);
//                });
//            }
//        });

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    private void showFileOpener() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Datei auswählen");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(stage);
        if (selectedFiles != null) {
            selectedFiles.stream().forEach((selectedFile) -> {
                loadFile(selectedFile);
            });
        }
    }

    private void loadFile(File file) {
        if (!file.isDirectory() && file.isFile()) {
            ImageView fileIcon = getIcon(file);
            String fileName = getName(file);
            String fileSize = getSize(file);
            Label fileLabel = new Label(fileName + " - " + fileSize);
            fileLabel.setGraphic(fileIcon);
            fileBox.getChildren().add(fileLabel);
            outputPathLabel.setDisable(false);
            outputPathLabel.setText(file.getParent() + File.separator + file.getName().substring(0, file.getName().lastIndexOf('.')) + ".encrypted");
        }
    }

    private ImageView getIcon(File file) {
        ImageIcon swtIcon = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file); //16x16 Icon
        BufferedImage bufferedImage = new BufferedImage(swtIcon.getIconWidth(), swtIcon.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(swtIcon.getImage(), 0, 0, swtIcon.getImageObserver());
        Image fxIcon = SwingFXUtils.toFXImage(bufferedImage, null);
        ImageView imageView = new ImageView();
        imageView.setImage(fxIcon);

        return imageView;
    }

    private String getSize(File file) {
        double bytesize = (double) file.length();
        NumberFormat n = NumberFormat.getInstance();
        n.setMaximumFractionDigits(2);
        String size = bytesize + " Byte";
        if (bytesize >= 1024) {
            size = n.format(bytesize / 1024) + " KB";
        }
        if (bytesize >= 1024 * 1024) {
            size = n.format(bytesize / (1024 * 1024)) + " MB";
        }

        if (bytesize >= 1024 * 1024 * 1024) {
            size = n.format(bytesize / (1024 * 1024 * 1024)) + " GB";
        }

        return size;
    }

    private String getName(File file) {
        String fileName = file.getName().substring(0, file.getName().lastIndexOf('.'));
        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.'));

        if (fileName.length() > 60) {
            fileName = fileName.substring(0, 60);
            fileName += "...";
        }

        return fileName + "." + fileExtension;
    }
    
    private void inserValueInTable() {
        //tableView
    }
}
