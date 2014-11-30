package consuming.hello;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;

import consuming.hello.exceptions.EmptyDataException;
import consuming.hello.models.Param;
import consuming.hello.models.Sequence;
import consuming.hello.utils.Sequences;

public class Application {

    private static final String COUPON_TYPE = "CPN-57740315";
    private static final String CATALOG_TYPE = "LBP-45387878";
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
     */
    public static void main(String[] args) throws JsonProcessingException, IOException,
            EmptyDataException {

        // Sequence sequence = new Sequence();
        // Plugins.addCancelPlugin(sequence);
        //
        // ObjectMapper objectMapper = new ObjectMapper();
        //
        // objectMapper.writeValue(new File("Test,json"), sequence);

        Sequence sequence;
        // sequence =
        // Sequences.createEbookFulfillmentSequence(URL_TEST3, ORDER_PORT,
        // URL_TEST3, CATALOG_PORT, CATALOG_TYPE);
//        sequence =
//                Sequences.createEbookCouponFulfillmentSequence(LOCALHOST_TEST3, LOCAL_ORDER_PORT,
//                        URL_TEST3, CATALOG_PORT, CATALOG_TYPE, COUPON_TYPE);
        // sequence =
        // Sequences.createEbookExtensionSequence(LOCALHOST_TEST3, LOCAL_ORDER_PORT, "220564393",
        // "[747130301]");

         sequence =
                Sequences.createCancelSequence(LOCALHOST_TEST3, LOCAL_ORDER_PORT, "220566005",
                        "[747132585]");

        sequence.runSequence();
    }
}
