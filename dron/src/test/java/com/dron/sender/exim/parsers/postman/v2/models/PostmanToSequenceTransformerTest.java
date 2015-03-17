package com.dron.sender.exim.parsers.postman.v2.models;

import static org.fest.assertions.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.dron.sender.sequence.models.Plugin;
import com.dron.sender.sequence.models.Sequence;
import com.dron.sender.transformers.TransformKey;
import com.dron.sender.transformers.TransformerFactory;

public class PostmanToSequenceTransformerTest {

	private static final String TEST_REQUEST_ID = "Test request id";
	private static final String TEST_REQUEST_NAME = "Test request name";
	private static final String TEST_POST_DATA = "Test post data";
	private static final String TEST_NAME = "Test name";
	private static final String ID = "12345";
	private static final List<String> orders = new ArrayList<String>();
	private static final List<RequestModel> requestModels = new ArrayList<RequestModel>();
	private static final List<Plugin> plugins = new ArrayList<Plugin>();
	static {
		orders.add("Test");
		orders.add("Test1");

		Plugin plugin = new Plugin();
		plugin.setId(ID);
		plugin.setId(ID);
		plugin.setId(ID);
		plugins.add(plugin);

		RequestModel requestModel = new RequestModel();
		requestModel.setId(TEST_REQUEST_ID);
		requestModel.setName(TEST_REQUEST_NAME);
		requestModel.setData(TEST_POST_DATA);
		requestModels.add(requestModel);
	}

	@Test
	public void transform() {
		PostmanModel postmanModel = new PostmanModel();
		postmanModel.setId(ID);
		postmanModel.setName(TEST_NAME);
		postmanModel.setOrder(orders);
		postmanModel.setRequests(requestModels);

		Sequence sequence = new Sequence();
		TransformerFactory.transformEntity(postmanModel, sequence,
				TransformKey.POSTMAN_V2_TO_SEQUENCE);

		assertThat(postmanModel.getId()).as(
				"Postman id should be equals to Sequence id").isEqualTo(
				sequence.getId());
		assertThat(postmanModel.getName()).as(
				"Postman name should be equals to Sequence name").isEqualTo(
				sequence.getName());
		assertThat(postmanModel.getOrder()).as(
				"Postman orders should be equals to Sequence orders")
				.isEqualTo(sequence.getOrder());
	}

	@Test
	public void reverseTransform() {
		Sequence sequence = new Sequence();
		sequence.setId(ID);
		sequence.setName(TEST_NAME);
		sequence.setOrder(orders);
		sequence.setPlugins(plugins);

		PostmanModel postmanModel = new PostmanModel();
		TransformerFactory.reverseTransformEntity(postmanModel, sequence,
				TransformKey.POSTMAN_V2_TO_SEQUENCE);

		assertThat(sequence.getId()).as(
				"Postman id should be equals to Sequence id").isEqualTo(
				postmanModel.getId());
		assertThat(sequence.getName()).as(
				"Postman name should be equals to Sequence name").isEqualTo(
				postmanModel.getName());
		assertThat(sequence.getOrder()).as(
				"Postman orders should be equals to Sequence orders")
				.isEqualTo(postmanModel.getOrder());
	}
}
