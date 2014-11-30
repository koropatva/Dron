package consuming.hello.utils;

import org.springframework.http.HttpMethod;

import consuming.hello.models.Plugin;
import consuming.hello.models.Sequence;

public class Plugins {

    public static void addOrderInfoPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/v2/order/ORDER_INFORMATION");
        plugin.setPostBody("{\"orderId\":{{orderId}},\"inputData\" : "
                + "{ \"include.extension\":true, \"include.purchase\":true }}");

        sequence.addPlugin(plugin);

        plugin.addFutureParam("{{extCatalogItemId}}",
                "[0].order.orderLines[0].transitoryData.extensionOptions.+45 days.catalogItemId");
        plugin.addFutureParam("{{extPricingId}}",
                "[0].order.orderLines[0].transitoryData.extensionOptions.+45 days.pricingLogId");
    }

    public static void addExtensionPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/v2/order/EXTEND_RENTAL");
        plugin.setPostBody("{"
                + "\"orderId\": {{orderId}},"
                + "\"orderLineIds\": {{orderLineIds}},"
                + "\"itemList\": ["
                + "{ \"catalogItemId\": \"{{extCatalogItemId}}\", \"pricingId\": \"{{extPricingId}}\" }"
                + "],\"inputData\" : {\"confirm.EXTEND_RENTAL\": true},\"paymentInfo\": ["
                + "{\"mopIdentifier\": 57685499,\"notes\": null,\"cvv\": 123,"
                + "\"billingAddressId\": 42768171,\"authInfo\": null,"
                + "\"availableAmount\": null,\"assignedAmount\": null,"
                + "\"mopType\": \"CREDITCARD\"}]}");
        sequence.addPlugin(plugin);
    }

    public static void addCancelPlugin(Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/v2/order/CANCEL");
        plugin.setPostBody("{\"orderId\":{{orderId}},\"orderLineIds\": {{orderLineIds}},"
                + "\"inputData\": { \"confirm.CANCEL\" : true, \"skipChangeWindow\" : true}}");
        sequence.addPlugin(plugin);
    }

    public static void addCouponIdPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.GET);
        plugin.setUrl("{{catalogUrl}}:{{catalogPort}}/catalog-api/rest/catalog/priced/byId/{{couponTypeId}}");

        sequence.addPlugin(plugin);

        plugin.addFutureParam("{{couponType}}", "prices[0].logId");
    }


    public static void addLogIdPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.GET);
        plugin.setUrl("{{catalogUrl}}:{{catalogPort}}/catalog-api/rest/catalog/priced/byId/{{catalogTypeId}}");

        sequence.addPlugin(plugin);

        plugin.addFutureParam("{{logId}}", "prices[0].logId");
    }

    public static void addCreateOrderPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/order/create");
        plugin.setPostBody("{\"items\":[{\"catalogItemId\":\"{{catalogTypeId}}"
                + "\",\"pricingId\":\"{{logId}}\",\"quantity\":1}],\"userId\":\"42958594\"}");

        sequence.addPlugin(plugin);

        plugin.addFutureParam("{{orderId}}", "id");
        plugin.addFutureParamList("{{orderLineIds}}", "orderLines[0].id");
    }

    public static void addCreateOrderWithCouponPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/order/create");
        plugin.setPostBody("{\"items\":[{\"catalogItemId\":\"{{catalogTypeId}}"
                + "\",\"pricingId\":\"{{logId}}\",\"quantity\":1},{\"catalogItemId\":\"{{couponTypeId}}"
                + "\",\"pricingId\":\"{{couponType}}\",\"quantity\":1}],\"userId\":\"42958594\"}");

        sequence.addPlugin(plugin);

        plugin.addFutureParam("{{orderId}}", "id");
        plugin.addFutureParamList("{{orderLineIds}}", "orderLines[0].id");
        plugin.addFutureParamList("{{orderLineIds}}", "orderLines[1].id");
    }


    public static void addCheckoutPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setPostBody("[\"shipping_address\", \"billing_address\", \"shipping_choices\", \"calculate_order_price\", \"method_of_payment\", \"payment_authorize\", \"payment_capture\", \"confirm_checkout\", \"assign_order_key\"]");
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/order/processor/plugin/{{orderId}}");
        sequence.addPlugin(plugin);
    }

    public static void addCheckoutParamsPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/order/processor/plugin/data/{{orderId}}");
        plugin.setPostBody(" [{\"namespace\":\"shipping_address\",\"key\":\"fname\",\"value\":\"EAGLESAVER\"},"// Krisgen
                + "{\"namespace\":\"shipping_address\",\"key\":\"lname\",\"value\":\"#174179\"},"// Ancheta
                + "{\"namespace\":\"shipping_address\",\"key\":\"line1\",\"value\":\"1216 Oak Creek Way\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"line2\",\"value\":\"\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"city\",\"value\":\"Sunnyvale\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"state\",\"value\":\"CA\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"country\",\"value\":\"US\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"zip\",\"value\":\"94089\"},"
                + "{\"namespace\":\"shipping_address\",\"key\":\"phone\",\"value\":\"0000000000\"},"// 4086368302
                + "{\"namespace\":\"shipping_address\",\"key\":\"address_id\",\"value\":\"128103598\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"fname\",\"value\":\"Krisgen\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"lname\",\"value\":\"Ancheta\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"line1\",\"value\":\"1216 Oak Creek Way\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"line2\",\"value\":\"\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"city\",\"value\":\"Sunnyvale\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"state\",\"value\":\"CA\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"country\",\"value\":\"US\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"zip\",\"value\":\"94089\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"phone\",\"value\":\"\"},"
                + "{\"namespace\":\"billing_address\",\"key\":\"address_id\",\"value\":\"128103600\"},"
                + "{\"namespace\":\"method_of_payment1\",\"key\":\"type\",\"value\":\"CREDITCARD\"},"
                + "{\"namespace\":\"method_of_payment1\",\"key\":\"id\",\"value\":\"71084052\"},"
                + "{\"namespace\":\"method_of_payment1\",\"key\":\"amount\",\"value\":null},"
                + "{\"namespace\":\"confirm_checkout\",\"key\":\"confirmed\",\"value\":\"1\"}]");
        sequence.addPlugin(plugin);
    }

    public static void addCheckoutOptionPlugin(final Sequence sequence) {
        Plugin plugin = new Plugin(HttpMethod.POST);
        plugin.setUrl("{{url}}:{{orderPort}}/order-api/rest/order/processor/plugin/data/evaluate/{{orderId}}/CHECKOUT");

        sequence.addPlugin(plugin);
    }
}
