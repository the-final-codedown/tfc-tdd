package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.DumpClient;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertTrue;

public class DumpTest {

    @Test
    public void test() throws UnsupportedEncodingException, ParseException, URISyntaxException {
        String dumpResult = DumpClient.dump();
        System.out.println(dumpResult);
        assertTrue(dumpResult.contains("theos@email"));
        assertTrue(dumpResult.contains("gregoire@email"));
        assertTrue(dumpResult.contains("mathieu@email"));
        assertTrue(dumpResult.contains("florian@email"));
        assertTrue(dumpResult.contains("bank"));
    }
}
