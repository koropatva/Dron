package com.dron.sender.pattern.services.transformers;

import com.dron.sender.controllers.root.models.RootUIPlugin;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IBaseTransformer;

public class RootUIPluginTransformer implements
		IBaseTransformer<UIPlugin, RootUIPlugin> {

	@Override
	public RootUIPlugin transform(UIPlugin uiPlugin, RootUIPlugin rootUIPlugin) {
		rootUIPlugin.setName(uiPlugin.getName().get());
		rootUIPlugin.setUrl(uiPlugin.getUrl().get());
		rootUIPlugin.setPostBody(uiPlugin.getPostBody().get());
		rootUIPlugin.setMethod(uiPlugin.getMethod().get());

		rootUIPlugin.getHeadersList().clear();
		rootUIPlugin.getHeadersList().addAll(uiPlugin.getHeadersList());
		rootUIPlugin.getFutureParams().clear();
		rootUIPlugin.getFutureParams().addAll(uiPlugin.getFutureParams());

		return rootUIPlugin;
	}

	@Override
	public UIPlugin reverseTransform(UIPlugin uiPlugin,
			RootUIPlugin rootUIPlugin) {
		uiPlugin.setName(rootUIPlugin.getName().get());
		uiPlugin.setUrl(rootUIPlugin.getUrl().get());
		uiPlugin.setPostBody(rootUIPlugin.getPostBody().get());
		uiPlugin.setMethod(rootUIPlugin.getMethod().get());
		uiPlugin.getHeadersList().clear();
		uiPlugin.getHeadersList().addAll(rootUIPlugin.getHeadersList());
		uiPlugin.getFutureParams().clear();
		uiPlugin.getFutureParams().addAll(rootUIPlugin.getFutureParams());

		return uiPlugin;
	}
}
