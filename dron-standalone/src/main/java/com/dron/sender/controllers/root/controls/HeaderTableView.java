package com.dron.sender.controllers.root.controls;

import org.apache.commons.lang3.StringUtils;

import com.dron.sender.controllers.base.controls.BasePropertyTableView;
import com.dron.sender.controllers.root.models.UIHttpHeaders;

public class HeaderTableView extends BasePropertyTableView<UIHttpHeaders> {

	private static final int TABLE_PROPERTY_WIDTH = 120;

	@Override
	protected int getMinWidth() {
		return TABLE_PROPERTY_WIDTH;
	}

	@Override
	protected String getKeyName() {
		return UIHttpHeaders.PROPERTY_HEADER;
	}

	@Override
	protected String getKey(UIHttpHeaders row) {
		return row.getHeader();
	}

	@Override
	protected String getValueName() {
		return UIHttpHeaders.PROPERTY_VALUE;
	}

	@Override
	protected String getValue(UIHttpHeaders row) {
		return row.getValue();
	}

	@Override
	protected UIHttpHeaders getEmptyInstance() {
		return new UIHttpHeaders();
	}

	@Override
	protected boolean isEmpty(UIHttpHeaders row) {
		return StringUtils.isBlank(row.getHeader())
				&& StringUtils.isBlank(row.getValue());
	}

	@Override
	protected void updateKey(UIHttpHeaders row, String newKey) {
		row.setHeader(newKey);
	}

	@Override
	protected void updateValue(UIHttpHeaders row, String newValue) {
		row.setValue(newValue);
	}
}
