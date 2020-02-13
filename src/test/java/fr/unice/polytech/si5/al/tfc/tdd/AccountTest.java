package fr.unice.polytech.si5.al.tfc.tdd;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.model.AccountType;
import fr.unice.polytech.si5.al.tfc.tdd.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.path.ProfileServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URI;

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

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/"+emailOwnerExpected+"/accounts");
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("money", moneyExpected);
        jsonObject.addProperty("accountType", accountTypeExpected);
        RequestUtils.generateBody(httpPost, jsonObject.toString());

        String account = RequestUtils.executeRequest(httpPost, 200, false);
        System.out.println("account"+ account);
        JSONObject accountObj = (JSONObject) new JSONParser().parse(account);

        assertEquals(moneyExpected, accountObj.get("money"));
        assertEquals(accountTypeExpected, accountObj.get("accountType"));
    }

    @Test
    public void ViewAccount() throws ParseException{
        final String accountTypeExpected = AccountType.CHECK.toString();
        final long lastWindowExpected = 0;

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/"+accountId);
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String accountString = RequestUtils.executeRequest(httpGet, 200, false);

        JSONParser parser = new JSONParser();
        JSONObject accountObject = ((JSONObject) parser.parse(accountString));

        assertEquals(moneyExpected, accountObject.get("money"));
        assertEquals(accountTypeExpected, accountObject.get("accountType"));
        assertEquals(lastWindowExpected, accountObject.get("lastWindow"));
    }

    @Test
    public void GetCap() throws ParseException {

        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT+"/"+accountId+"/cap");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String cap = RequestUtils.executeRequest(httpGet, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals(moneyExpected, ((JSONObject) parser.parse(cap)).get("money"));
        assertEquals(amountSlidingWindowExpected, ((JSONObject) parser.parse(cap)).get("amountSlidingWindow"));

    }

    @Test
    public void viewAccountsByType() throws ParseException{
        final String accountTypeExpected = AccountType.CHECK.toString();

        JSONArray arrayAccount = getAccountByType(accountTypeExpected);

        for(Object acc : arrayAccount.toArray()){
            assertEquals(accountTypeExpected,((JSONObject)acc).get("accountType"));
        }

    }

    private JSONArray getAccountByType(String accountTypeExpected) throws ParseException {
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + accountTypeExpected + "/accounts");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        String account = RequestUtils.executeRequest(httpGet, 200, false);
        JSONParser parser = new JSONParser();
        return (JSONArray) parser.parse(account);
    }
}
