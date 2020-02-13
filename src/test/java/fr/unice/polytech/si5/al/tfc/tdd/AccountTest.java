package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.AccountClient;
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
    public void CreateAccount() throws UnsupportedEncodingException, ParseException {
        final long moneyExpected = 300;
        final String accountTypeExpected = AccountType.SAVINGS.toString();
        final String emailOwnerExpected = "florian.salord@etu.unice.fr";

        String account = AccountClient.createAccount(moneyExpected, accountTypeExpected, emailOwnerExpected);
        JSONObject accountObj = (JSONObject) new JSONParser().parse(account);

        assertEquals(moneyExpected, accountObj.get("money"));
        assertEquals(accountTypeExpected, accountObj.get("accountType"));
    }


    @Test
    public void ViewAccount() throws ParseException{
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
    public void GetCap() throws ParseException {

        String cap = AccountClient.getCap(accountId);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(cap)).get("money"));
        assertEquals(amountSlidingWindowExpected, ((JSONObject) parser.parse(cap)).get("amountSlidingWindow"));
    }


    @Test
    public void viewAccountsByType() throws ParseException{
        final String accountTypeExpected = AccountType.CHECK.toString();

        String account = AccountClient.getAccountByType(accountTypeExpected);
        JSONParser parser = new JSONParser();
        JSONArray arrayAccount = (JSONArray) parser.parse(account);

        for(Object acc : arrayAccount.toArray()){
            assertEquals(accountTypeExpected,((JSONObject)acc).get("accountType"));
        }
    }


}
