package fr.unice.polytech.si5.al.tfc.tdd.common.cli.api;

import com.google.gson.JsonObject;
import fr.unice.polytech.si5.al.tfc.tdd.common.path.ProfileServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpPost;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class ProfileClient {

    public static String createProfile(String email) throws UnsupportedEncodingException {
        URI uri = RequestUtils.getURI(SERVICE.PROFILE, ProfileServicePath.PROFILE);
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);

        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        RequestUtils.generateBody(httpPost, jsonObject.toString());
        return RequestUtils.executeRequest(httpPost, 200, false);
    }
}
