package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.path.ProfileServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;
import fr.unice.polytech.si5.al.tfc.tdd.common.client.ProfileClient;
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
        String email = "florian.salord@etu.unice.fr";

        String profile = ProfileClient.createProfile(email);

        JSONParser parser = new JSONParser();

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("email"));
        URI uri = RequestUtils.getURI(SERVICE.PROFILE, ProfileServicePath.PROFILE);
        System.out.println(uri);

        uri = new URI(RequestUtils.getURI(SERVICE.PROFILE, ProfileServicePath.PROFILE) + "/florian.salord@etu.unice.fr");
        HttpGet httpGet = new HttpGet(uri);
        profile = RequestUtils.executeRequest(httpGet, 200);

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("email"));

    }

}
