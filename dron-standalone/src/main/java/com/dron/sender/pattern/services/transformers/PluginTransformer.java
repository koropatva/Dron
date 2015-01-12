package com.dron.sender.pattern.services.transformers;

import org.springframework.http.HttpMethod;

import com.dron.sender.controllers.root.models.UIPlugin;
import com.dron.sender.pattern.interfaces.IBaseTransformer;
import com.dron.sender.pattern.models.transformers.TransformKey;
import com.dron.sender.sequence.models.Plugin;

public class PluginTransformer implements IBaseTransformer<Plugin, UIPlugin> {

	@Override
	public UIPlugin transform(Plugin plugin, UIPlugin uiPlugin) {
		uiPlugin.setName(plugin.getName());
		uiPlugin.setUrl(plugin.getUrl());
		uiPlugin.setPostBody(plugin.getPostBody());
		uiPlugin.setMethod(plugin.getHttpMethod().name());

		TransformerFactory.transformEntity(plugin.getHeaders(),
				uiPlugin.getHeadersList(), TransformKey.HTTP_HEADERS);

		TransformerFactory.transformEntity(plugin.getFutureParams(),
				uiPlugin.getFutureParams(), TransformKey.FUTURE_PARAMS);
		return uiPlugin;
	}

	@Override
	public Plugin reverseTransform(Plugin plugin, UIPlugin uiPlugin) {
		plugin.setName(uiPlugin.getName().get());
		plugin.setUrl(uiPlugin.getUrl().get());
		plugin.setPostBody(uiPlugin.getPostBody().get());
		plugin.setHttpMethod(HttpMethod.valueOf(uiPlugin.getMethod().get()));

		TransformerFactory.reverseTransformEntity(plugin.getHeaders(),
				uiPlugin.getHeadersList(), TransformKey.HTTP_HEADERS);

		TransformerFactory.reverseTransformEntity(plugin.getFutureParams(),
				uiPlugin.getFutureParams(), TransformKey.FUTURE_PARAMS);

		return plugin;
	}
}
