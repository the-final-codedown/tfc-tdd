package fr.unice.polytech.si5.al.tfc.tdd;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.AccountClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.SavingsClient;
import fr.unice.polytech.si5.al.tfc.tdd.common.model.AccountType;
import fr.unice.polytech.si5.al.tfc.tdd.common.model.Transaction;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;


public class SavingsTest {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private final double interest = 1.1;
    private long moneyExpected;

    @Before
    public void init() throws UnsupportedEncodingException {
        AccountClient.createAccount(300,AccountType.SAVINGS.toString(),"florian@email");
    }

    @Test
    public void computingSavings() throws IOException, ParseException, InterruptedException {

        final String accoundIdExpected;

        String savingAccounts = AccountClient.getAccountByType(AccountType.SAVINGS.toString());
        System.out.println(savingAccounts);
        JSONArray savingAccountsArr = (JSONArray) new JSONParser().parse(savingAccounts);
        JSONObject account = ((JSONObject) savingAccountsArr.get(0));

        moneyExpected = (long) account.get("money");
        moneyExpected *= interest;
        accoundIdExpected = (String)account.get("accountId");

        SavingsClient.computingSavings();

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + accoundIdExpected);
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);

        String newAccountStr = RequestUtils.loopExecuteRequest(httpGet, function(moneyExpected), 200, 1000);
        JSONObject newAccount = (JSONObject) new JSONParser().parse(newAccountStr);
        assertEquals(moneyExpected,newAccount.get("money"));

    }

    private static RequestUtils.FuncInterface function(long moneyExpected) {
        return (String content) -> {
            JSONObject newAccount = (JSONObject) new JSONParser().parse(content);
            System.out.println("Money should be equal to " + moneyExpected + "but is equal to " + newAccount.get("money"));
            assertNotEquals(moneyExpected, newAccount.get("money"));
        };
    }


}
