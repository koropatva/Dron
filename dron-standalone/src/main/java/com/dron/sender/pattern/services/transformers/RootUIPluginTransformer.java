package com.dron.sender.pattern.services.transformers;

import com.dron.sender.controllers.root.models.RootUIPlugin;
import com.dron.sender.controllers.root.models.UIHttpHeaders;
import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IBaseTransformer;

public class RootUIPluginTransformer implements
		IBaseTransformer<UIPlugin, RootUIPlugin> {

	@Override
	public RootUIPlugin transform(UIPlugin uiPlugin, RootUIPlugin rootUIPlugin) {
		rootUIPlugin.setUrl(uiPlugin.getUrl().get());
		rootUIPlugin.setPostBody(uiPlugin.getPostBody().get());
		rootUIPlugin.setMethod(uiPlugin.getMethod().get());

		rootUIPlugin.getHeadersList().clear();
		uiPlugin.getHeadersList().forEach(header -> {
			rootUIPlugin.getHeadersList().add(header.clone());
		});
		rootUIPlugin.getHeadersList().add(new UIHttpHeaders());

		return rootUIPlugin;
	}

	@Override
	public UIPlugin reverseTransform(UIPlugin uiPlugin,
			RootUIPlugin rootUIPlugin) {
		uiPlugin.setUrl(rootUIPlugin.getUrl().get());
		uiPlugin.setPostBody(rootUIPlugin.getPostBody().get());
		uiPlugin.setMethod(rootUIPlugin.getMethod().get());

		uiPlugin.getHeadersList().clear();
		rootUIPlugin.getHeadersList().forEach(header -> {
			if (!header.isEmpty()) {
				uiPlugin.getHeadersList().add(header.clone());
			}
		});

		return uiPlugin;
	}
}
