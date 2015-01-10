package com.dron.sender.controllers.root.controls;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIParam;
import com.dron.sender.sequence.models.Param;

public class ParamTableView extends BasePropertyTableView<UIParam> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return Param.PROPERTY_KEY;
	}

	@Override
	protected String getKey(UIParam row) {
		return row.getKey();
	}

	@Override
	protected String getValueName() {
		return Param.PROPERTY_VALUE;
	}

	@Override
	protected String getValue(UIParam row) {
		return row.getValue();
	}

	@Override
	protected boolean isEmpty(UIParam row) {
		return StringUtils.isBlank(row.getKey())
				&& StringUtils.isBlank(row.getValue());
	}

	@Override
	protected UIParam getEmptyInstance() {
		return new UIParam();
	}

	@Override
	protected void updateKey(UIParam row, String newKey) {
		row.setKey(newKey);
	}

	@Override
	protected void updateValue(UIParam row, String newValue) {
		row.setValue(newValue);
	}

}
