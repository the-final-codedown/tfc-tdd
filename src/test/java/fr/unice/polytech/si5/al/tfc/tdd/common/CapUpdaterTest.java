package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.CapUpdaterClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tfc.cap.updater.TfcCapUpdater;

import static org.junit.Assert.assertTrue;

public class CapUpdaterTest {

    private static CapUpdaterClient client;

    @BeforeClass
    public static void init() {
        client = new CapUpdaterClient("localhost", 50051);
    }

    @AfterClass
    public static void shutdown() throws InterruptedException {
        client.shutdown();
    }

    @Test
    public void bankTest() throws InterruptedException {
        try {
            TfcCapUpdater.DownscaleResponse response = client.downscaleCap("bank", 100);
            assertTrue(response.getAccepted());
            System.out.println(response.getDownscale());
        } finally {
            client.shutdown();
        }
    }

    @Test
    public void unknownTest() throws InterruptedException {
        CapUpdaterClient client = new CapUpdaterClient("localhost", 50051);
        try {
            TfcCapUpdater.DownscaleResponse response = client.downscaleCap("unknown", 100);
            assertTrue(response.getAccepted());
            System.out.println(response);
        } finally {
            client.shutdown();
        }
    }
}
