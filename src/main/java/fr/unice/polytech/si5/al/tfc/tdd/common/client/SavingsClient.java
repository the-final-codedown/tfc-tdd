package fr.unice.polytech.si5.al.tfc.tdd.common.client;

import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.SavingsServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpPost;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class SavingsClient {

    public static String computingSavings() throws UnsupportedEncodingException {
        URI uri = RequestUtils.getURI(SERVICE.SAVINGS, SavingsServicePath.SAVINGS);
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        JsonObject jsonObject = new JsonObject();
        RequestUtils.generateBody(httpPost, jsonObject.toString());

        return RequestUtils.executeRequest(httpPost, 200, false);
    }
}
