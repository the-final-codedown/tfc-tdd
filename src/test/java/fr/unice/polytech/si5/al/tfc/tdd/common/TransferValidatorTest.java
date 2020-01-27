package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import org.junit.Test;

public class TransferValidatorTest {

    @Test
    public void payTest() throws InterruptedException {
        TransferValidatorClient client = new TransferValidatorClient("localhost", 50052);
        System.out.println(client.pay("bank", "bank", 10));
        Thread.sleep(5000);
        System.out.println(client.pay("unkown", "unknown", 10));
    }

}
