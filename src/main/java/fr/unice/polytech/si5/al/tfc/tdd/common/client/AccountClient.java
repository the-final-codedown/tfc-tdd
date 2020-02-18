package fr.unice.polytech.si5.al.tfc.tdd.common.client;

import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.parser.ParseException;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class AccountClient {

    public static String createAccount(long moneyExpected, String accountTypeExpected, String emailOwnerExpected) throws UnsupportedEncodingException {
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + emailOwnerExpected + "/accounts");
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("money", moneyExpected);
        jsonObject.addProperty("accountType", accountTypeExpected);
        RequestUtils.generateBody(httpPost, jsonObject.toString());

        return RequestUtils.executeRequest(httpPost, 200, false);
    }

    public static String viewAccount(String accountId) {
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + accountId);
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        return RequestUtils.executeRequest(httpGet, 200, false);
    }

    public static String getCap(String accountId) {
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + accountId + "/cap");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        return RequestUtils.executeRequest(httpGet, 200, false);
    }

    public static String getAccountByType(String accountTypeExpected) throws ParseException {
        URI uri = RequestUtils.getURI(SERVICE.ACCOUNT, AccountServicePath.ACCOUNT + "/" + accountTypeExpected + "/accounts");
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        return RequestUtils.executeRequest(httpGet, 200, false);
    }

}
