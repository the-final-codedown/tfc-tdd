package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tfc.transfer.validator.TfcTransferValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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
    public void payTest() {
        TfcTransferValidator.TransferValidation validation = client.pay("bank", "bank", 10);
        assertTrue(validation.getValidated());

        // This account doesn't exist
        validation = client.pay("unknown", "unknown", 10);
        assertFalse(validation.getValidated());
    }
}
