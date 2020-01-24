package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.CapUpdaterClient;
import org.junit.Test;

public class CapUpdaterTest {

    @Test
    public void test() throws InterruptedException {
        CapUpdaterClient client = new CapUpdaterClient("localhost", 50051);
        try {
            client.downscaleCap("bank", 100);
        } finally {
            client.shutdown();
        }
    }
}
