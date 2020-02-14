package fr.unice.polytech.si5.al.tfc.tdd;

import com.google.gson.JsonObject;
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

public class ProfileTest {

    @Test
    public void test() throws UnsupportedEncodingException, ParseException, URISyntaxException {
        URI uri = RequestUtils.getURI(SERVICE.PROFILE, ProfileServicePath.PROFILE);
        System.out.println(uri);
        HttpPost httpPost = new HttpPost(uri);
        String email = "florian.salord@etu.unice.fr";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("email", email);
        RequestUtils.generateBody(httpPost, jsonObject.toString());
        String profile = RequestUtils.executeRequest(httpPost, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("email"));

        uri = new URI(RequestUtils.getURI(SERVICE.PROFILE, ProfileServicePath.PROFILE) + "/florian.salord@etu.unice.fr");
        HttpGet httpGet = new HttpGet(uri);
        profile = RequestUtils.executeRequest(httpGet, 200);

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("email"));

    }

}
