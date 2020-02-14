package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.client.AccountClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.SavingsClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.model.AccountType;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.junit.Assert.assertEquals;


public class SavingsTest {

    private final double interest = 1.1;

    @Test
    public void computingSavings() throws UnsupportedEncodingException, ParseException, InterruptedException {
        long moneyExpected;
        final String accoundIdExpected;


        String savingAccounts = AccountClient.getAccountByType(AccountType.SAVINGS.toString());
        System.out.println(savingAccounts);
        JSONArray savingAccountsArr = (JSONArray) new JSONParser().parse(savingAccounts);
        JSONObject account = ((JSONObject) savingAccountsArr.get(0));

        moneyExpected = (long) account.get("money");
        moneyExpected *= interest;
        accoundIdExpected = (String)account.get("accountId");

        SavingsClient.computingSavings();
        Thread.sleep(5000);

        String newAccountStr = AccountClient.viewAccount(accoundIdExpected);
        JSONObject newAccount = (JSONObject) new JSONParser().parse(newAccountStr);
        assertEquals(moneyExpected,newAccount.get("money"));

    }




}
