package fr.unice.polytech.si5.al.tfc.tdd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.common.Transaction;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import fr.unice.polytech.si5.al.tfc.tdd.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tfc.transfer.validator.TfcTransferValidator;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import static org.junit.Assert.*;

public class RollingHistoryTest {


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
    public void rollingHistory() throws IOException, InterruptedException, URISyntaxException {
        String clientId = "5e3d36af9194be0001672eb6";
        URI uriTransaction = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.TRANSACTION);
        URI uriAccount = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT);
        System.out.println(uriTransaction);
        ObjectMapper objectMapper = new ObjectMapper();
        TfcTransferValidator.TransferValidation validation = client.pay(clientId, "bank", 110);
        System.out.println("" + validation);
        assertTrue(validation.getValidated());
        validation = client.pay(clientId, "bank", 110);
        System.out.println("" + validation);
        assertTrue(validation.getValidated());
        validation = client.pay(clientId, "bank", 110);
        System.out.println("" + validation);
        assertFalse(validation.getValidated());

        RequestUtils.FuncInterface function = (String content) -> {
            List<Transaction> transactions = null;
            transactions = objectMapper.readValue(content, new TypeReference<List<Transaction>>() {
            });
            int i = 0;
            for (Transaction transaction1 : transactions) {
                if (transaction1.getSource().equals(clientId)) {
                    i++;
                }
            }
            System.out.println("i is equal to " + i);
            assertNotEquals(2, i);
        };

        HttpGet httpGet = new HttpGet(uriTransaction);
        List<Transaction> transactions = objectMapper.readValue(RequestUtils.loopExecuteRequest(httpGet, function, 200, 1000), new TypeReference<List<Transaction>>() {
        });
        System.out.println(transactions);
        HttpPut httpPut = new HttpPut(uriTransaction);
        Transaction transaction = transactions.get(0);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", transaction.getId());
        jsonObject.addProperty("source", transaction.getSource());
        jsonObject.addProperty("receiver", transaction.getReceiver());
        jsonObject.addProperty("amount", transaction.getAmount());
        jsonObject.addProperty("date", "2019-02-11T22:00+01:00");
        RequestUtils.generateBody(httpPut, jsonObject.toString());

        String transactionUpdated = RequestUtils.executeRequest(httpPut, 200, false);
        System.out.println(transactionUpdated);

        HttpGet httpGet1 = new HttpGet(new URI(uriAccount + "/" + clientId));

        RequestUtils.FuncInterface functionVerifyLastWindow = (String content) -> {
            System.out.println(content);
            JSONParser jsonParser = new JSONParser();
            JSONObject transactionJson = (JSONObject) jsonParser.parse(content);
            int lastWindow = Integer.parseInt(transactionJson.get("lastWindow").toString());
            System.out.println("lastWindow is equal to " + lastWindow);
            assertNotEquals(110, lastWindow);
        };
        RequestUtils.loopExecuteRequest(httpGet1, functionVerifyLastWindow, 200, 1000);

        validation = client.pay(clientId, "bank", 110);
        System.out.println("" + validation);
        assertTrue(validation.getValidated());

        validation = client.pay(clientId, "bank", 110);
        System.out.println("" + validation);
        assertFalse(validation.getValidated());
    }
}
