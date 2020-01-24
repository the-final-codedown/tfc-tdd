package fr.unice.polytech.si5.al.tfc.tdd.common;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import org.junit.Test;

public class TransferValidatorTest {

    @Test
    public void payTest() {
        TransferValidatorClient client = new TransferValidatorClient("localhost", 50052);
        client.pay("bank", "bank", 10);
    }

}
