package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class MainFormController {
    private final String ls = System.getProperty("line.separator");
    private final File file = null;
    private final List<Index> SearchIndex = new ArrayList<>();

    public Button btnNew;
    public Button btnOpen;
    public Button btnSave;
    public Button btnCut;
    public Button btnCopy;
    public Button btnPaste;
    public Button btnFind;
    public TextArea txtContent;
    public Label lblFindCount;
    public Label lblWordCount;
    public Button btnUp;
    public Button btnDown;
    public ToggleButton btnRegEx;
    public ToggleButton btnCaseSens;
    public TextField txtSearch;
    FileChooser chooser = null;
    private String Copy;
    private String cut;
    private int findOffSet = 1;
    private boolean textChanged = false;
    private Matcher matcher;
    private int findAllCount=0;
    private int searchCount=0;



    public void initialize() {


        txtSearch.textProperty().addListener((observable, oldValue, newValue) -> textChanged = true);


        txtContent.textProperty().addListener((observable, oldValue, newValue) -> {
            Stage stage =(Stage) txtContent.getScene().getWindow();
            if (stage.getTitle().charAt(0)!='*'){
                stage.setTitle("*"+stage.getTitle());
            }



        });
    }



    private void readFromFile(File file) {
        if (file != null) {
            String text = "", buff = "";
            try (BufferedReader br = new BufferedReader(new FileReader(file))) {
                text = br.readLine();
                while ((buff = br.readLine()) != null) {
                    text += ls + buff;
                }
            } catch (IOException ex) {
                new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK).show();
            }
            txtContent.setText(text);
        }
    }


    public void open() {
        chooser = new FileChooser();
        chooser.setTitle("Open a text file");
        chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt"),
                new FileChooser.ExtensionFilter("All Files", "*.*"));
        File file = chooser.showOpenDialog(null);
        if (file != null) readFromFile(file);
    }

    private void writeToFile(File file) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(file, false))) {
            bw.write(txtContent.getText());
        } catch (IOException ex) {
            new Alert(Alert.AlertType.ERROR, ex.toString(), ButtonType.OK).show();
        }
    }

    public void save() {
        if (file != null) {
            if (file.exists()) writeToFile(file);
        } else {
            chooser = new FileChooser();
            chooser.setTitle("Save file");
            chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text File", "*.txt")
                    , new FileChooser.ExtensionFilter("All Files", "*.*"));
            File file = chooser.showSaveDialog(null);
            if (file != null) if (file.exists()) {
                writeToFile(file);
            } else {
                try {
                    file.createNewFile();
                } catch (IOException ex) {
                    return;
                }
                writeToFile(file);
            }
        }
    }

    public void copy() {
        byte[] bytes = txtContent.getSelectedText().getBytes(StandardCharsets.UTF_8);
        Copy = new String(bytes);

    }

    public void cut() {
        byte[] bytes = txtContent.getSelectedText().getBytes(StandardCharsets.UTF_8);
        cut = new String(bytes);
        int caretPosition = txtContent.getCaretPosition();
        txtContent.setText(txtContent.getText().replace(txtContent.getSelectedText(), ""));
        txtContent.positionCaret(caretPosition);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtContent.clear();
        txtSearch.clear();
        txtContent.requestFocus();
    }

    public void paste() {
        int caretPosition = txtContent.getCaretPosition();
        txtContent.insertText(caretPosition, Copy);

    }

    public void btnOpenOnAction(ActionEvent actionEvent) {
        open();
    }

    public void btnSaveOnAction(ActionEvent actionEvent) {
        save();
    }

    public void btnCutOnAction(ActionEvent actionEvent) {
        cut();
    }

    public void btnCopyOnAction(ActionEvent actionEvent) {
        copy();
    }

    public void btnPasteOnAction(ActionEvent actionEvent) {
        paste();
    }

    public void btnFindOnAction(ActionEvent actionEvent) {

        txtContent.deselect();
        if (textChanged) {
            int flags = 0;
            if (!btnRegEx.isSelected()) flags = flags | Pattern.LITERAL;
            if (!btnCaseSens.isSelected()) flags = flags | Pattern.CASE_INSENSITIVE;

            matcher = Pattern.compile(txtSearch.getText(), flags)
                    .matcher(txtContent.getText());
            textChanged = false;
        }

      if (matcher.find()) {
            txtContent.selectRange(matcher.start(), matcher.end());
        } else {
            matcher.reset();
        }

    }

    public void mnbNew(ActionEvent actionEvent) {
        txtContent.clear();
        txtSearch.clear();
    }

    public void mnbOpen(ActionEvent actionEvent) {
        open();
    }

    public void mnbSave(ActionEvent actionEvent) {
        save();
    }

    public void mnbPrint(ActionEvent actionEvent) {
    }

    public void mnbExit(ActionEvent actionEvent) {
        Platform.exit();
        System.exit(0);
    }

    public void mnbCut(ActionEvent actionEvent) {

        cut();
    }

    public void mnbCopy(ActionEvent actionEvent) {

        copy();
    }

    public void mnbPaste(ActionEvent actionEvent) {
        paste();
    }

    public void mnbSelectAll(ActionEvent actionEvent) {

        txtContent.selectAll();
    }

    public void mnbEdit(ActionEvent actionEvent) {

        txtContent.setEditable(true);
    }

    public void mnbReplace(ActionEvent actionEvent) {
    }

    public void mnbReplaceAll(ActionEvent actionEvent) {
    }

    public void mnbAboutUs(ActionEvent actionEvent) throws IOException {
        URL resource = getClass().getResource("/AboutUs.fxml");
        Parent load = FXMLLoader.load(resource);
        Scene scene = new Scene(load);
        Stage stage = new Stage();
        stage.setScene(scene);

        stage.show();
    }

    public void btnUpOnAction(ActionEvent actionEvent) {
        if (!SearchIndex.isEmpty()) {
            if (findOffSet == -1) {
                findOffSet = 0;

                txtContent.selectRange(SearchIndex.get(findOffSet).startingIndex, SearchIndex.get(findOffSet).endIndex);
                findOffSet++;
                if (findOffSet >= SearchIndex.size()) {
                    findOffSet = 0;
                }
            }
        }
    }

    public void btnDownOnAction(ActionEvent actionEvent) {

        makeMatcher();
        searchCount++;


        }



    private void makeMatcher() {
        txtContent.deselect();
        if (textChanged) {
            int flags = 0;
            if (!btnRegEx.isSelected()) flags = flags | Pattern.LITERAL;
            if (!btnCaseSens.isSelected()) flags = flags | Pattern.CASE_INSENSITIVE;

            matcher = Pattern.compile(txtSearch.getText(), flags)
                    .matcher(txtContent.getText());
            textChanged = false;
        }


        if (matcher.find()) {

            txtContent.selectRange(matcher.start(), matcher.end());
            SearchIndex.add(new Index(matcher.start(), matcher.end()));

        } else {
            matcher.reset();
        }


    }


    public void btnRegExOnAction(ActionEvent actionEvent) {
    }

    public void btnCaseSensOnAction(ActionEvent actionEvent) {
        try {
            textChanged = true;
            btnFind.fire();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void findCount() {


    }
    }



class Index {
    int startingIndex;
    int endIndex;

    public Index(int startingIndex, int endIndex) {
        this.startingIndex = startingIndex;
        this.endIndex = endIndex;
    }

}
