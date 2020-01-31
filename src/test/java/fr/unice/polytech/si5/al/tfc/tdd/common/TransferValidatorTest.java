package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class TransferValidatorTest {

    private static TransferValidatorClient client;

    @BeforeClass
    public static void init() {
        client = new TransferValidatorClient("localhost", 50052);
    }

    @AfterClass
    public static void shutdown() throws InterruptedException {
        client.shutdown();
    }

    @Test
    public void payTest() throws InterruptedException {
        System.out.println(client.pay("bank", "bank", 10));
        Thread.sleep(5000);
        System.out.println(client.pay("unknown", "unknown", 10));
    }
}
