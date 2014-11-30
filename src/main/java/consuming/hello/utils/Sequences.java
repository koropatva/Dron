package consuming.hello.utils;

import consuming.hello.models.Param;
import consuming.hello.models.Sequence;

public class Sequences {
    public static Sequence createEbookExtensionSequence(String orderUrl, String orderPort,
            String orderId, String orderLineIds) {
        Sequence sequence = new Sequence();
        sequence.addParam(new Param("{{orderId}}", orderId));
        sequence.addParam(new Param("{{orderLineIds}}", orderLineIds));
        sequence.addParam(new Param("{{url}}", orderUrl));
        sequence.addParam(new Param("{{orderPort}}", orderPort));

        Plugins.addOrderInfoPlugin(sequence);
        Plugins.addExtensionPlugin(sequence);
        return sequence;
    }


    public static Sequence createCancelSequence(String orderUrl, String orderPort, String orderId,
            String orderLineIds) {
        Sequence sequence = new Sequence();
        sequence.addParam(new Param("{{orderId}}", orderId));
        sequence.addParam(new Param("{{orderLineIds}}", orderLineIds));
        sequence.addParam(new Param("{{url}}", orderUrl));
        sequence.addParam(new Param("{{orderPort}}", orderPort));

        Plugins.addCancelPlugin(sequence);
        return sequence;
    }

    public static Sequence createEbookFulfillmentSequence(String orderUrl, String orderPort,
            String catalogUrl, String catalogPort, String catalogType) {
        Sequence sequence = new Sequence();
        sequence.addParam(new Param("{{url}}", orderUrl));
        sequence.addParam(new Param("{{orderPort}}", orderPort));
        sequence.addParam(new Param("{{catalogUrl}}", catalogUrl));
        sequence.addParam(new Param("{{catalogPort}}", catalogPort));
        sequence.addParam(new Param("{{catalogTypeId}}", catalogType));

        Plugins.addLogIdPlugin(sequence);
        Plugins.addCreateOrderPlugin(sequence);
        Plugins.addCheckoutPlugin(sequence);
        Plugins.addCheckoutParamsPlugin(sequence);
        Plugins.addCheckoutOptionPlugin(sequence);
        return sequence;
    }

    public static Sequence createEbookFulfillmentSequence(String orderUrl, String orderPort,
            String catalogType, String logId) {
        Sequence sequence = new Sequence();
        sequence.addParam(new Param("{{url}}", orderUrl));
        sequence.addParam(new Param("{{orderPort}}", orderPort));
        sequence.addParam(new Param("{{catalogTypeId}}", catalogType));
        sequence.addParam(new Param("{{logId}}", logId));

        Plugins.addCreateOrderPlugin(sequence);
        Plugins.addCheckoutPlugin(sequence);
        Plugins.addCheckoutParamsPlugin(sequence);
        Plugins.addCheckoutOptionPlugin(sequence);
        return sequence;
    }


    public static Sequence createEbookCouponFulfillmentSequence(String orderUrl, String orderPort,
            String catalogUrl, String catalogPort, String catalogType, String couponType) {
        Sequence sequence = new Sequence();
        sequence.addParam(new Param("{{url}}", orderUrl));
        sequence.addParam(new Param("{{catalogUrl}}", catalogUrl));
        sequence.addParam(new Param("{{orderPort}}", orderPort));
        sequence.addParam(new Param("{{catalogPort}}", catalogPort));
        sequence.addParam(new Param("{{catalogTypeId}}", catalogType));
        sequence.addParam(new Param("{{couponTypeId}}", couponType));

        Plugins.addLogIdPlugin(sequence);
        Plugins.addCouponIdPlugin(sequence);
        Plugins.addCreateOrderWithCouponPlugin(sequence);
        Plugins.addCheckoutPlugin(sequence);
        Plugins.addCheckoutParamsPlugin(sequence);
        Plugins.addCheckoutOptionPlugin(sequence);
        return sequence;
    }

}
