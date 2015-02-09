package com.dron.sender.controllers.root;

import java.io.File;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import com.dron.sender.controllers.root.controls.AutoFillSequenceTextBox;
import com.dron.sender.controllers.root.controls.RootConfig;
import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.controllers.root.models.UISequence;
import com.dron.sender.sequence.models.Sequence;

public abstract class ModelRootController extends Parent {

	protected static final int DEFAULT_SELECTED_UI_PLUGIN = 0;

	protected static final double DEFAULT_ACCORDION_WIDTH = 300.0;

	@FXML
	protected TextField tfUrl;

	@FXML
	protected TextField tfNewPluginName;

	@FXML
	protected TextArea txaResponce;

	@FXML
	protected TextArea txaPostBody;

	@FXML
	protected TextArea txaConsole;

	@FXML
	protected HBox hbHeadersParamsTable;

	@FXML
	protected ToggleButton tbtnHeaders;

	@FXML
	protected ToggleButton tbtnParams;

	@FXML
	protected Button btnSendActivePlugin;

	@FXML
	protected Button btnSendSequence;

	@FXML
	protected Button btnAddNewPlugin;

	@FXML
	protected ChoiceBox<String> cbMethods;

	@FXML
	protected Accordion accPlugins;

	@FXML
	protected TableView<UIHttpHeaders> tblHeaders;

	@FXML
	protected TableView<UIParam> tblParams;

	@FXML
	protected AutoFillSequenceTextBox autoFillSequenceTextBox;

	protected UISequence uiSequence;

	// The sequence is used for share sequence between IMPORT_SEQUENCE and
	// PREPARE_SEQUENCE strategies
	private Sequence tmpImportSequence;

	// The File is used for share file between SHOW_IMPORT_DIALOG_SEQUENCE and
	// IMPORT_SEQUENCE strategies
	private File tmpImportFile;

	public void setUp(ModelRootController modelRootController) {
		this.tfUrl = modelRootController.getTfUrl();
		this.tfNewPluginName = modelRootController.getTfNewPluginName();
		this.txaResponce = modelRootController.getTxaResponce();
		this.txaPostBody = modelRootController.getTxaPostBody();
		this.txaConsole = modelRootController.getTxaConsole();
		this.hbHeadersParamsTable = modelRootController
				.getHbHeadersParamsTable();
		this.tbtnHeaders = modelRootController.getTbtnHeaders();
		this.tbtnParams = modelRootController.getTbtnParams();
		this.cbMethods = modelRootController.getCbMethods();
		this.accPlugins = modelRootController.getAccPlugins();
		this.tblHeaders = modelRootController.getTblHeaders();
		this.tblParams = modelRootController.getTblParams();
		this.uiSequence = modelRootController.getUiSequence();
		this.btnSendActivePlugin = modelRootController.getBtnSendActivePlugin();
		this.btnSendSequence = modelRootController.getBtnSendSequence();
		this.btnAddNewPlugin = modelRootController.getBtnAddNewPlugin();
		this.autoFillSequenceTextBox = modelRootController
				.getAutoFillSequenceTextBox();
		this.tmpImportSequence = modelRootController.getTmpImportSequence();
		this.tmpImportFile = modelRootController.getTmpImportFile();
	}

	protected void updateControls() {
		hbHeadersParamsTable.setPrefHeight(RootConfig
				.getHbHeadersParamsHeight());
		txaPostBody.setPrefHeight(RootConfig.getPostBodyHeight());
		txaResponce.setPrefHeight(RootConfig.getResponceHeight());
	}

	public TextField getTfUrl() {
		return tfUrl;
	}

	public TextField getTfNewPluginName() {
		return tfNewPluginName;
	}

	public TextArea getTxaResponce() {
		return txaResponce;
	}

	public TextArea getTxaConsole() {
		return txaConsole;
	}

	public TableView<UIHttpHeaders> getTblHeaders() {
		return tblHeaders;
	}

	public TableView<UIParam> getTblParams() {
		return tblParams;
	}

	public HBox getHbHeadersParamsTable() {
		return hbHeadersParamsTable;
	}

	public ToggleButton getTbtnHeaders() {
		return tbtnHeaders;
	}

	public ToggleButton getTbtnParams() {
		return tbtnParams;
	}

	public TextArea getTxaPostBody() {
		return txaPostBody;
	}

	public ChoiceBox<String> getCbMethods() {
		return cbMethods;
	}

	public Accordion getAccPlugins() {
		return accPlugins;
	}

	public UISequence getUiSequence() {
		return uiSequence;
	}

	public Button getBtnSendActivePlugin() {
		return btnSendActivePlugin;
	}

	public Button getBtnSendSequence() {
		return btnSendSequence;
	}

	public Button getBtnAddNewPlugin() {
		return btnAddNewPlugin;
	}

	public AutoFillSequenceTextBox getAutoFillSequenceTextBox() {
		return autoFillSequenceTextBox;
	}

	public Sequence getTmpImportSequence() {
		return tmpImportSequence;
	}

	public File getTmpImportFile() {
		return tmpImportFile;
	}

	public void setTmpImportSequence(Sequence tmpImportSequence) {
		this.tmpImportSequence = tmpImportSequence;
	}

	public void setTmpImportFile(File tmpImportFile) {
		this.tmpImportFile = tmpImportFile;
	}

}
