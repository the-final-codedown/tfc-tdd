package fr.unice.polytech.si5.al.tfc.tdd;

import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.model.AccountType;
import fr.unice.polytech.si5.al.tfc.tdd.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.path.ProfileServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class AccountTest {



    @Test
    public void CreateAccount() throws UnsupportedEncodingException, ParseException {
        final int moneyExpected = 300;
        final String accountTypeExpected = AccountType.CHECK.toString();

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.CREATE_ACCOUNT+"/theos.mariani@gmail.com");
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("money", moneyExpected);
        jsonObject.addProperty("accountType", accountTypeExpected);
        RequestUtils.generateBody(httpPost, jsonObject.toString());
        String account = RequestUtils.executeRequest(httpPost, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(account)).get("money"));
        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(account)).get("accountType"));

        String owner = (String) ((JSONObject) parser.parse(account)).get("owner");
        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(owner)).get("email"));
    }

    @Test
    public void ViewAccount() throws ParseException{
        final int moneyExpected = 300;
        final String accountTypeExpected = AccountType.CHECK.toString();

        //TODO ACCOUNT_ID
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/ACCOUNT_ID");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String account = RequestUtils.executeRequest(httpGet, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(account)).get("money"));
        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(account)).get("accountType"));

        String owner = (String) ((JSONObject) parser.parse(account)).get("owner");
        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(owner)).get("email"));
    }

    @Test
    public void GetCap() throws ParseException {
        final int moneyExpected = 300;
        final int amountSlidingWindowExpected = 0;

        //TODO ACCOUNT_ID
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/ACCOUNT_ID/cap");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String account = RequestUtils.executeRequest(httpGet, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(account)).get("money"));
        assertEquals(amountSlidingWindowExpected, ((JSONObject) parser.parse(account)).get("amountSlidingWindow"));

    }

    @Test
    public void viewAccountsByType() throws ParseException{
        final String accountTypeExpected = AccountType.CHECK.toString();

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/ACCOUNT_ID");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String account = RequestUtils.executeRequest(httpGet, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(account)).get("accountType"));

        String owner = (String) ((JSONObject) parser.parse(account)).get("owner");
        assertEquals(accountTypeExpected, ((JSONObject) parser.parse(owner)).get("email"));
    }
}
