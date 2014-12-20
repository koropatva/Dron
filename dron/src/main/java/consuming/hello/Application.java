package consuming.hello;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.paypal.core.rest.PayPalRESTException;

import consuming.hello.exceptions.EmptyDataException;
import consuming.hello.models.Param;
import consuming.hello.models.Sequence;
import consuming.hello.services.SequenceService;
import consuming.hello.utils.Sequences;

public class Application {

    private static final String ORDER_LINE_IDS = "{{orderLineIds}}";
    private static final String ORDER_ID = "{{orderId}}";
    private static final String COUPON_TYPE = "CPN-57740315";
    private static final String CATALOG_TYPE = "LBP-45387878";
    private static final String CATALOG_TEXTBOOK_TYPE = "LBP-33444327";
    private static final String URL_TEST3 = "http://cops-web01.test3.cloud.cheggnet.com";
    private static final String LOCALHOST_TEST3 = "http://localhost";
    private static final String CATALOG_PORT = "6001";
    private static final String ORDER_PORT = "6004";
    private static final String LOCAL_ORDER_PORT = "8090";

    private static final List<Param> params = new ArrayList<Param>();

    /**
     * @param args
     * @throws IOException
     * @throws JsonProcessingException
     * @throws EmptyDataException
     * @throws PayPalRESTException
     */
    public static void main(String[] args) throws JsonProcessingException, IOException,
            EmptyDataException, PayPalRESTException {

        Sequence sequence;
        // sequence =
        // Sequences
        // .createConfirmFraudSequence(LOCALHOST_TEST3, LOCAL_ORDER_PORT, "220584167");
        ObjectMapper mapper = new ObjectMapper();

        // Create new configuration file for new sequence
        // mapper.writerWithDefaultPrettyPrinter()
        // .writeValue(
        // new File(
        // "/Users/admin/Documents/spring/gs-rest-service/initial/src/main/resources/json/ConfirmFraud.json"),
        // sequence);

        sequence =
                mapper.readValue(
                        new File(
                                "/Users/admin/Documents/spring/gs-rest-service/initial/src/main/resources/json/TextbookSubscription.json"),
                        Sequence.class);
//        sequence.updateParam(new Param(ORDER_ID, "220587003"));
//        sequence.updateParam(new Param(ORDER_LINE_IDS, "[747160613]"));
        SequenceService sequenceService = new SequenceService(sequence);

        sequenceService.runSequence();
    }
}
