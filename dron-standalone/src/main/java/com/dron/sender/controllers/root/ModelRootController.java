package com.dron.sender.controllers.root;

import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;

import com.dron.sender.controllers.root.models.RootUIPlugin;
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
	protected HBox hbHeadersParamsTable;

	@FXML
	protected ToggleButton tbtnHeaders;

	@FXML
	protected ToggleButton tbtnParams;

	@FXML
	protected ChoiceBox<String> cbMethods;

	@FXML
	protected Accordion accPlugins;

	@FXML
	protected TableView<UIHttpHeaders> tblHeaders;

	@FXML
	protected TableView<UIParam> tblParams;

	protected UISequence uiSequence;

	// Sequence that uses for share sequence between IMPORT_SEQUENCE and
	// PREPARE_SEQUENCE strategies
	private Sequence tmpImportSequence;

	protected RootUIPlugin rootUiPlugin;

	public void setUp(ModelRootController modelRootController) {
		this.tfUrl = modelRootController.getTfUrl();
		this.tfNewPluginName = modelRootController.getTfNewPluginName();
		this.txaResponce = modelRootController.getTxaResponce();
		this.txaPostBody = modelRootController.getTxaPostBody();
		this.hbHeadersParamsTable = modelRootController
				.getHbHeadersParamsTable();
		this.tbtnHeaders = modelRootController.getTbtnHeaders();
		this.tbtnParams = modelRootController.getTbtnParams();
		this.cbMethods = modelRootController.getCbMethods();
		this.accPlugins = modelRootController.getAccPlugins();
		this.tblHeaders = modelRootController.getTblHeaders();
		this.tblParams = modelRootController.getTblParams();
		this.uiSequence = modelRootController.getUiSequence();
		this.rootUiPlugin = modelRootController.getRootUiPlugin();
		this.tmpImportSequence = modelRootController.getTmpImportSequence();
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

	public RootUIPlugin getRootUiPlugin() {
		return rootUiPlugin;
	}

	public Sequence getTmpImportSequence() {
		return tmpImportSequence;
	}

	public void setTmpImportSequence(Sequence sequence) {
		this.tmpImportSequence = sequence;
	}

}
