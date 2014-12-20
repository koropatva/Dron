package com.dron.frontend.services;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToolBar;
import javafx.scene.control.ToolBarBuilder;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import com.dron.frontend.interfaces.IInitControlService;

@Service
public class InitControlService implements IInitControlService {

    private static final String HEADER_VB = "header_box";
    private static final String POST_EDITOR_VB = "post_editor";
    private static final String HTTP_HEADERS_BTN = "http_headers_btn";
    private static final String HTTP_METHODS_CB = "second-editable";
    private static final Integer WIDTH = 800;
    private static final Integer HEIGHT = 300;

    private Stage primaryStage;


    private final ObservableList<String> strings = FXCollections.observableArrayList(
            HttpMethod.GET.name(), HttpMethod.POST.name(), HttpMethod.PUT.name(),
            HttpMethod.DELETE.name());

    EventHandler<ActionEvent> httpHeadersButtonClick = new EventHandler<ActionEvent>() {
        @Override
        public void handle(ActionEvent e) {
            ToggleButton headersBtn =
                    (ToggleButton) primaryStage.getScene().lookup(getId(HTTP_HEADERS_BTN));
            VBox headerBox = (VBox) primaryStage.getScene().lookup(getId(HEADER_VB));
            headerBox.setVisible(headersBtn.isSelected());
        }
    };

    ChangeListener<String> httpMethodConmoBoxListener = new ChangeListener<String>() {

        public void changed(ObservableValue<? extends String> selected, String oldValue,
                String newValue) {
            newValue = selected.getValue();

            VBox vBox = (VBox) primaryStage.getScene().lookup(getId(POST_EDITOR_VB));
            vBox.setVisible(HttpMethod.POST.name().equals(newValue));
        }
    };

    public void init(Stage primaryStage) {
        this.primaryStage = primaryStage;

        Group root = new Group();
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, WIDTH, HEIGHT));

        VBox vmainBox = new VBox(10);
        vmainBox.getChildren().addAll(createMainToolbar(), createHeader(), createPostEditor());

        root.getChildren().add(vmainBox);

    }

    private VBox createMainToolbar() {
        TextField urlText = new TextField();
        urlText.setPromptText("Enter request URL here...");
        urlText.setMinWidth(600);

        ComboBox<String> httpMethodsComboBox = new ComboBox<String>();
        httpMethodsComboBox.setMinWidth(60);
        httpMethodsComboBox.setVisibleRowCount(2);
        httpMethodsComboBox.setId(HTTP_METHODS_CB);
        httpMethodsComboBox.setPromptText("Edit or Choose...");
        httpMethodsComboBox.setValue(HttpMethod.GET.name());
        httpMethodsComboBox.setItems(strings);
        httpMethodsComboBox.setEditable(true);
        httpMethodsComboBox.valueProperty().addListener(httpMethodConmoBoxListener);

        ToggleButton httpHeadersButton = new ToggleButton("Headers");
        httpHeadersButton.setId(HTTP_HEADERS_BTN);
        httpHeadersButton.setOnAction(httpHeadersButtonClick);

        ToolBar toolBar =
                ToolBarBuilder.create().id("main_toolbar").minWidth(800)
                        .items(urlText, httpMethodsComboBox, httpHeadersButton).build();

        VBox vtoolbarBox = new VBox(3);
        vtoolbarBox.getChildren().add(toolBar);
        return vtoolbarBox;
    }

    private VBox createHeader() {
        Label pluginName = new Label("Plugin main name");
        Label pluginDescription = new Label("Plugin description");

        VBox vboxMeals = new VBox(3);
        vboxMeals.setId(HEADER_VB);
        vboxMeals.getChildren().addAll(pluginName, pluginDescription);
        vboxMeals.setAlignment(Pos.CENTER_LEFT);
        vboxMeals.setVisible(false);
        return vboxMeals;
    }

    private VBox createPostEditor() {
        TextField urlText = new TextField();
        urlText.setPromptText("Enter request URL here...");
        urlText.setMinWidth(400);
        urlText.setMaxSize(600, 20);

        VBox postEditor = new VBox(3);
        postEditor.setId(POST_EDITOR_VB);
        postEditor.managedProperty().bind(postEditor.visibleProperty());
        postEditor.setVisible(false);
        postEditor.getChildren().add(urlText);
        return postEditor;
    }

    private String getId(String id) {
        return "#" + id;
    }

}
