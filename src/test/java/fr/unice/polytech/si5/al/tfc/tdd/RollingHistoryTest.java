package fr.unice.polytech.si5.al.tfc.tdd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.TransferValidatorClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.model.Transaction;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import tfc.transfer.validator.TfcTransferValidator;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class RollingHistoryTest {


    private static TransferValidatorClient client;
    private static ObjectMapper objectMapper = new ObjectMapper();
    private static String clientId = "5e3d36af9194be0001672eb6";
    private static String accountToRestore;
    private static URI uriTransaction = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.TRANSACTION);
    private static URI uriAccount = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT);
    private static int sizeOfTransactionsBefore = 0;


    @BeforeClass
    public static void init() throws IOException, URISyntaxException {
        client = new TransferValidatorClient("localhost", 50052);
        HttpGet httpGetAccount = new HttpGet(new URI(uriAccount + "/" + clientId));
        accountToRestore = RequestUtils.executeRequest(httpGetAccount, 200, false);
        HttpGet httpGet = new HttpGet(uriTransaction);
        List<Transaction> transactionsBefore = objectMapper.readValue(RequestUtils.executeRequest(httpGet, 200, false), new TypeReference<List<Transaction>>() {
        });
        sizeOfTransactionsBefore = transactionsBefore.isEmpty() ? 0 : (int) transactionsBefore.stream().filter(transaction -> transaction.getSource().equals(clientId)).count();

    }

    @AfterClass
    public static void shutdown() throws InterruptedException, IOException, URISyntaxException, ParseException {
        System.out.println("afterClass");
        HttpPut httpPut = new HttpPut(uriAccount);
        JsonObject jsonObject = new JsonObject();
        JSONParser parser = new JSONParser();
        System.out.println(accountToRestore);
        jsonObject.addProperty("accountId", String.valueOf(((JSONObject) parser.parse(accountToRestore)).get("accountId")));
        jsonObject.addProperty("amountSlidingWindow", Integer.parseInt(String.valueOf(((JSONObject) parser.parse(accountToRestore)).get("amountSlidingWindow"))));
        jsonObject.addProperty("money", Integer.parseInt(String.valueOf(((JSONObject) parser.parse(accountToRestore)).get("money"))));
        jsonObject.addProperty("lastWindow", Integer.parseInt(String.valueOf(((JSONObject) parser.parse(accountToRestore)).get("lastWindow"))));
        JsonObject email = new JsonObject();
        email.addProperty("email", String.valueOf(((JSONObject) ((JSONObject) parser.parse(accountToRestore)).get("owner")).get("email")));
        jsonObject.add("owner", email);
        jsonObject.addProperty("accountType", String.valueOf(((JSONObject) parser.parse(accountToRestore)).get("accountType")));

        RequestUtils.generateBody(httpPut, jsonObject.toString());
        accountToRestore = RequestUtils.executeRequest(httpPut, 200, false);
        HttpGet httpGet = new HttpGet(uriTransaction);
        List<Transaction> transactions = objectMapper.readValue(RequestUtils.loopExecuteRequest(httpGet, function(3), 200, 1000), new TypeReference<List<Transaction>>() {
        });
        for (Transaction transaction : transactions) {
            String updatedTransaction = putUpdateTransactionDate(transaction);
            // TODO : wut?
        }

        client.shutdown();
    }

    private static RequestUtils.FuncInterface function(int expectedNumberOfTransaction) {
        return (String content) -> {
            List<Transaction> transactions = null;
            transactions = objectMapper.readValue(content, new TypeReference<List<Transaction>>() {
            });
            int i = 0;
            for (Transaction transaction1 : transactions) {
                if (transaction1.getSource().equals(clientId)) {
                    i++;
                }
            }
            System.out.println("Should be equal to " + expectedNumberOfTransaction + " but is equal to " + (i - sizeOfTransactionsBefore));
            assertNotEquals(expectedNumberOfTransaction, i - sizeOfTransactionsBefore);
        };
    }

    private static String putUpdateTransactionDate(Transaction transaction) throws UnsupportedEncodingException {
        HttpPut httpPut = new HttpPut(uriTransaction);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("id", transaction.getId());
        jsonObject.addProperty("source", transaction.getSource());
        jsonObject.addProperty("receiver", transaction.getReceiver());
        jsonObject.addProperty("amount", transaction.getAmount());
        jsonObject.addProperty("date", "2019-02-11T22:00+01:00");
        RequestUtils.generateBody(httpPut, jsonObject.toString());

        return RequestUtils.executeRequest(httpPut, 200, false);
    }

    @Test
    public void rollingHistory() throws IOException, InterruptedException, URISyntaxException {
        HttpGet httpGet = new HttpGet(uriTransaction);

        ObjectMapper objectMapper = new ObjectMapper();
        TfcTransferValidator.TransferValidation validation = client.pay(clientId, "bank", 110);

        assertTrue(validation.getValidated());
        validation = client.pay(clientId, "bank", 110);

        assertTrue(validation.getValidated());
        validation = client.pay(clientId, "bank", 110);

        assertFalse(validation.getValidated());

        List<Transaction> transactionsAfter = objectMapper.readValue(RequestUtils.loopExecuteRequest(httpGet, function(2), 200, 1000), new TypeReference<List<Transaction>>() {
        });

        List<Transaction> transactionsOfToday = getTransactionsOfToday(transactionsAfter);

        String transactionUpdated = putUpdateTransactionDate(transactionsOfToday.get(0));

        HttpGet httpGet1 = new HttpGet(new URI(uriAccount + "/" + clientId));

        RequestUtils.FuncInterface functionVerifyLastWindow = (String content) -> {
            JSONParser jsonParser = new JSONParser();
            JSONObject transactionJson = (JSONObject) jsonParser.parse(content);
            int lastWindow = Integer.parseInt(transactionJson.get("lastWindow").toString());
            assertNotEquals(110, lastWindow);
        };
        RequestUtils.loopExecuteRequest(httpGet1, functionVerifyLastWindow, 200, 1000);

        validation = client.pay(clientId, "bank", 110);
        assertTrue(validation.getValidated());

        validation = client.pay(clientId, "bank", 110);
        assertFalse(validation.getValidated());
    }

    private List<Transaction> getTransactionsOfToday(List<Transaction> transactions) {
        return transactions.stream().filter(transaction -> (transaction.getDate().getYear() == LocalDateTime.now().getYear() && transaction.getSource().equals(clientId))).collect(Collectors.toList());
    }
}
