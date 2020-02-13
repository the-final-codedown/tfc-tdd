package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.common.cli.api.ProfileClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class ProfileTest {

    @Test
    public void test() throws UnsupportedEncodingException, ParseException, URISyntaxException {
        String email = "florian.salord@etu.unice.fr";

        String profile = ProfileClient.createProfile(email);

        JSONParser parser = new JSONParser();

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("email"));

/*
        uri = UriComponentsBuilder.fromUriString(GlobalServicePath.PROFILE_SERVICE + ProfileServicePath.PROFILE)
                .build().toUri();
        HttpGet httpGet = new HttpGet(uri);

        json = "{\"email\": \"florian.salord@etu.unice.fr\"}";
        RequestUtils.generateBody(httpGet, json);
        profile = RequestUtils.executeRequest(httpGet, 200);

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("_id"));
*/

    }


}
