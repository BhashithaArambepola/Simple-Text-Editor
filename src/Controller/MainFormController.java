package Controller;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.stage.FileChooser;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class MainFormController {
    private final String ls = System.getProperty("line.separator");
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
    private final File file = null;
    private String Copy;
    private String cut;

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

    public void save(){
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
    public void copy(){
        byte[] bytes = txtContent.getSelectedText().getBytes(StandardCharsets.UTF_8);
        Copy = new String(bytes);

    }
    public void cut(){
        byte[] bytes = txtContent.getSelectedText().getBytes(StandardCharsets.UTF_8);
        cut = new String(bytes);
        int caretPosition = txtContent.getCaretPosition();
        txtContent.setText(txtContent.getText().replace(txtContent.getSelectedText(), ""));
        txtContent.positionCaret(caretPosition);
    }

    public void btnNewOnAction(ActionEvent actionEvent) {
        txtContent.clear();
        txtSearch.clear();
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
    }

    public void btnFindOnAction(ActionEvent actionEvent) {
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
    }

    public void mnbSelectAll(ActionEvent actionEvent) {
    }

    public void mnbEdit(ActionEvent actionEvent) {
    }

    public void mnbReplace(ActionEvent actionEvent) {
    }

    public void mnbReplaceAll(ActionEvent actionEvent) {
    }

    public void mnbAboutUs(ActionEvent actionEvent) {
    }

    public void btnUpOnAction(ActionEvent actionEvent) {
    }

    public void btnDownOnAction(ActionEvent actionEvent) {
    }

    public void btnRegExOnAction(ActionEvent actionEvent) {
    }

    public void btnCaseSensOnAction(ActionEvent actionEvent) {
    }
}
