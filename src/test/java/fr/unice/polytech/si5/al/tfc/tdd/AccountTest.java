package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.AccountClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.model.AccountType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


public class AccountTest {

    private final String accountId = "5e3d36af9194be0001672eb4";
    private final long moneyExpected = 300;
    private long amountSlidingWindowExpected = 300;


    @Test
    public void createAccount() throws UnsupportedEncodingException, ParseException {
        final long moneyExpected = 300;
        final String accountTypeExpected = AccountType.SAVINGS.toString();
        final String emailOwnerExpected = "florian@email";

        String accountCreated = AccountClient.createAccount(moneyExpected, accountTypeExpected, emailOwnerExpected);
        JSONObject accountCreatedObj = (JSONObject) new JSONParser().parse(accountCreated);

        assertEquals(moneyExpected, accountCreatedObj.get("money"));
        assertEquals(accountTypeExpected, accountCreatedObj.get("accountType"));


        String account = AccountClient.viewAccount((String) accountCreatedObj.get("accountId"));
        assertEquals(accountCreated, account);
    }


    @Test
    public void viewAccount() throws ParseException {
        final String accountTypeExpected = AccountType.CHECK.toString();
        final long lastWindowExpected = 0;

        String accountString = AccountClient.viewAccount(accountId);

        JSONParser parser = new JSONParser();
        JSONObject accountObject = ((JSONObject) parser.parse(accountString));

        assertEquals(moneyExpected, accountObject.get("money"));
        assertEquals(accountTypeExpected, accountObject.get("accountType"));
        assertEquals(lastWindowExpected, accountObject.get("lastWindow"));
    }

    @Test
    public void getCap() throws ParseException {
        String cap = AccountClient.getCap(accountId);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(cap)).get("money"));
        assertEquals(amountSlidingWindowExpected, ((JSONObject) parser.parse(cap)).get("amountSlidingWindow"));
    }


    @Test
    public void viewAccountsByType() throws ParseException {
        final String accountTypeExpected = AccountType.CHECK.toString();

        String account = AccountClient.getAccountByType(accountTypeExpected);
        JSONParser parser = new JSONParser();
        JSONArray arrayAccount = (JSONArray) parser.parse(account);

        for (Object acc : arrayAccount.toArray()) {
            assertEquals(accountTypeExpected, ((JSONObject) acc).get("accountType"));
        }
    }


}
