package com.dron.sender.controllers.root.controls;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIFutureParam;

public class FutureParamTableView extends BasePropertyTableView<UIFutureParam> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return UIFutureParam.PROPERTY_KEY;
	}

	@Override
	protected String getKey(UIFutureParam row) {
		return row.getKey();
	}

	@Override
	protected String getValueName() {
		return UIFutureParam.PROPERTY_DEPENDENCE;
	}

	@Override
	protected String getValue(UIFutureParam row) {
		return row.getDependence();
	}

	@Override
	protected boolean isEmpty(UIFutureParam row) {
		return StringUtils.isBlank(row.getKey())
				&& StringUtils.isBlank(row.getDependence());
	}

	@Override
	protected UIFutureParam getEmptyInstance() {
		return new UIFutureParam();
	}

	@Override
	protected void updateKey(UIFutureParam row, String newKey) {
		row.setKey(newKey);
	}

	@Override
	protected void updateValue(UIFutureParam row, String newValue) {
		row.setDependence(newValue);
	}

}
