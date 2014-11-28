/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gui;

import java.awt.image.BufferedImage;
import java.io.File;
import java.text.NumberFormat;
import java.util.List;
import javafx.application.Application;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.Dragboard;
import javafx.scene.input.KeyCode;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
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
public class newgui extends Application {

    private Button btnEncryption;
    private Label lblOutputPath;
    private TableView<SelectedFile> tableView;
    private Stage stage;
    private ObservableList<SelectedFile> fileData;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        stage.setTitle("XClavis");
        stage.setResizable(false);
        stage.setMinHeight(750);
        stage.setMaxHeight(750);
        stage.setMinWidth(650);
        stage.setMaxWidth(650);
        //stage.getIcons().add(new Image(Start.class.getResourceAsStream("logo.jpg")));

        BorderPane mainPane = new BorderPane();

        VBox vbTop = new VBox();

        //Main Menu MULTILANG!!!!!!!!!!!!!!!!!
        MenuBar menuBar = new MenuBar();
        //Menu File
        Menu mFile = new Menu("Datei");
        MenuItem miOpenFile = new MenuItem("Öffnen");
        miOpenFile.setOnAction((event) -> {
            showFileOpener();
        });
        MenuItem miExit = new MenuItem("Beenden");
        mFile.getItems().addAll(miOpenFile, miExit);
        //Menu Settings
        Menu mSettings = new Menu("Einstellungen");
        MenuItem miSecurity = new MenuItem("Sicherheit");
        Menu mLanguage = new Menu("Sprache");
        MenuItem miGerman = new MenuItem("Deutsch");
        MenuItem miFrench = new MenuItem("Français");
        MenuItem miItalian = new MenuItem("Italiano");
        MenuItem miRomontsch = new MenuItem("Romontsch");
        MenuItem miEnglish = new MenuItem("English");
        mLanguage.getItems().addAll(miGerman, miFrench, miItalian, miRomontsch, miEnglish);
        MenuItem miGeneral = new MenuItem("Allgemein");
        mSettings.getItems().addAll(miSecurity, mLanguage, miGeneral);
        //Menu Help
        Menu mHelp = new Menu("Hilfe");
        MenuItem miAbout = new MenuItem("Über");
        miAbout.setOnAction((event) -> {
            //todo
        });
        MenuItem miDocumentation = new MenuItem("Dokumentation");
        MenuItem miLicense = new MenuItem("Lizenzen");
        mHelp.getItems().addAll(miAbout, miDocumentation, miLicense);

        menuBar.getMenus().addAll(mFile, mSettings, mHelp);
        vbTop.getChildren().add(menuBar);

        //Tool Bar edit Paths
        //ToolBar toolBar = new ToolBar();
        //toolBar.setOrientation(Orientation.HORIZONTAL);
        TilePane tileToolBar = new TilePane(Orientation.HORIZONTAL);
        tileToolBar.setAlignment(Pos.CENTER);
        Button btnOpenFile = new Button("Datei öffnen");
        btnOpenFile.setGraphic(new ImageView("file:icons/add10.png"));
        btnOpenFile.setMaxWidth(Double.MAX_VALUE);
        btnOpenFile.setOnAction((event) -> {
            showFileOpener();
        });
        Button btnQRScanner = new Button("QR-Code scannen");
        btnQRScanner.setGraphic(new ImageView("file:icons/camera1.png"));
        btnQRScanner.setMaxWidth(Double.MAX_VALUE);
        Button btnKeyList = new Button("Schlüssel austauschen");
        btnKeyList.setGraphic(new ImageView("file:icons/key5.png"));
        btnKeyList.setMaxWidth(Double.MAX_VALUE);
        tileToolBar.getChildren().addAll(btnOpenFile, btnQRScanner, btnKeyList);
        //toolBar.getItems().addAll(btnOpenFile, btnQRScanner, btnKeyList);

        vbTop.getChildren().add(tileToolBar);
        mainPane.setTop(vbTop);

        //Main Content
        VBox vbContent = new VBox(10);
        vbContent.setAlignment(Pos.CENTER);
        vbContent.setPadding(new Insets(25, 25, 25, 25));

        Text txtTop = new Text("Wählen Sie die gewünschte Datei aus:");

        Text txtMiddle = new Text("Ausgewählte Dateien");

        //Table with filellist
        tableView = new TableView<>();
        tableView.setEditable(false);
        tableView.setDisable(true);
        fileData = FXCollections.observableArrayList();
        tableView.setItems(fileData);
        TableColumn<SelectedFile, ImageView> clmIcon = new TableColumn<>("Icon");
        clmIcon.setCellValueFactory(new PropertyValueFactory<>("icon"));
        clmIcon.setSortable(false);
        TableColumn<SelectedFile, String> clmName = new TableColumn<>("Dateiname");
        clmName.setCellValueFactory(new PropertyValueFactory<>("name"));
        TableColumn<SelectedFile, String> clmExtension = new TableColumn<>("Dateityp");
        clmExtension.setCellValueFactory(new PropertyValueFactory<>("extension"));
        TableColumn<SelectedFile, String> clmSize = new TableColumn<>("Grösse");
        clmSize.setCellValueFactory(new PropertyValueFactory<>("size"));
        TableColumn<SelectedFile, Button> clmDelete = new TableColumn<>("Löschen");

        clmDelete.setCellValueFactory((CellDataFeatures<SelectedFile, Button> p) -> {
            Button btnDeleteRow = new Button();
            btnDeleteRow.setGraphic(new ImageView("file:icons/delete5.png"));
            btnDeleteRow.setStyle("-fx-background-color:transparent");
            btnDeleteRow.setPadding(new Insets(0, 0, 0, 0));
            btnDeleteRow.setOnMouseEntered((event) -> {
                btnDeleteRow.setGraphic(new ImageView("file:icons/delete4.png"));
            });
            btnDeleteRow.setOnMouseExited((event) -> {
                btnDeleteRow.setGraphic(new ImageView("file:icons/delete5.png"));
            });
            btnDeleteRow.setOnAction(
                    (event) -> {
                        tableView.requestFocus();
                        tableView.getSelectionModel().select(p.getValue());
                        tableView.getFocusModel().focus(tableView.getSelectionModel().getSelectedIndex());
                        fileData.remove(tableView.getSelectionModel().getSelectedItem());
                        tableView.getSelectionModel().clearSelection();
                    });

            return new ReadOnlyObjectWrapper(btnDeleteRow);
        });
        clmDelete.setSortable(false);

        tableView.getColumns().addAll(clmIcon, clmName, clmExtension, clmSize, clmDelete);
        tableView.setOnKeyPressed((event) -> {
            if (event.getCode() == KeyCode.DELETE) {
                fileData.remove(tableView.getSelectionModel().getSelectedItem());
                tableView.getSelectionModel().clearSelection();
            }
        });

        Text outputPathTxt = new Text("Zielordner:");
        lblOutputPath = new Label();
        lblOutputPath.setDisable(true);

        CheckBox cbCompression = new CheckBox("Dateien vor der Verschlüsselung komprimieren");
        cbCompression.setSelected(true);

        //Button Encryption
        btnEncryption = new Button("Verschlüsseln");
        btnEncryption.setGraphic(new ImageView("file:icons/lock1.png"));
        btnEncryption.setDisable(true);
        btnEncryption.setOnAction(
                (event) -> {
                    vbTop.getChildren().remove(tileToolBar);
                    ObservableList test = tableView.getItems();
                    mainPane.setCenter(new Encryption(test, System.getProperty("user.home") + File.separator + "test.zip", cbCompression.selectedProperty().getValue()).showContent());
                });

        vbContent.getChildren().addAll(txtTop, txtMiddle, tableView, outputPathTxt, lblOutputPath, cbCompression, btnEncryption);

        mainPane.setCenter(vbContent);
        Scene scene = new Scene(mainPane);

        //Drag & Drop File Transfer
        scene.setOnDragOver(
                (event) -> {
                    Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasFiles()) {
                        event.acceptTransferModes(TransferMode.COPY);
                    } else {
                        event.consume();
                    }
                }
        );

        scene.setOnDragDropped(
                (event) -> {
                    Dragboard dragboard = event.getDragboard();
                    if (dragboard.hasFiles()) {
                        dragboard.getFiles().stream().forEach((droppedFile) -> {
                            loadFile(droppedFile);
                        });
                    }
                }
        );

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
        if (!file.isDirectory() && file.isFile() && file.canRead()) {
            boolean exists = false;
            for (SelectedFile existingFile : fileData) {
                if (existingFile.getFile().equals(file)) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                ImageView fileIcon = getIcon(file);
                String fileName = getName(file);
                String fileSize = getSize(file);
                String fileExtension = getExtension(file);
                SelectedFile selectedFile = new SelectedFile(file, fileIcon, fileName, fileExtension, fileSize);
                tableView.setDisable(false);
                fileData.add(selectedFile);
                lblOutputPath.setDisable(false);
                lblOutputPath.setText(System.getProperty("user.home"));
                btnEncryption.setDisable(false);
            }
        }
    }

    private ImageView getIcon(File file) {
        ImageIcon iconSWT = (ImageIcon) FileSystemView.getFileSystemView().getSystemIcon(file); //16x16 Icon
        BufferedImage bufferedImage = new BufferedImage(iconSWT.getIconWidth(), iconSWT.getIconHeight(), BufferedImage.TYPE_INT_ARGB);
        bufferedImage.getGraphics().drawImage(iconSWT.getImage(), 0, 0, iconSWT.getImageObserver());
        Image iconFX = SwingFXUtils.toFXImage(bufferedImage, null);
        ImageView imageView = new ImageView();
        imageView.setImage(iconFX);

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

        if (fileName.length() > 60) {
            fileName = fileName.substring(0, 60);
            fileName += "...";
        }

        return fileName;
    }

    private String getExtension(File file) {
        String fileExtension = file.getName().substring(file.getName().lastIndexOf('.'));
        fileExtension = fileExtension.substring(1);

        return fileExtension;
    }
}
