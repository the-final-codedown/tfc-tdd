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
    public void bankTest() {
        TfcCapUpdater.DownscaleResponse response = client.downscaleCap("bank", 100);
        assertTrue(response.getAccepted());
    }

    /**
     * This test is supposed to pass as it does not check
     * if the downscale is supposed to happen or not but just to do it
     */
    @Test
    public void unknownTest() {
        TfcCapUpdater.DownscaleResponse response = client.downscaleCap("unknown", 100);
        assertTrue(response.getAccepted());

    }
}
