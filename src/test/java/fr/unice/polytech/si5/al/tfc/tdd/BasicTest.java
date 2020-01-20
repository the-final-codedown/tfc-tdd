package fr.unice.polytech.si5.al.tfc.tdd;

import fr.unice.polytech.si5.al.tfc.tdd.path.GlobalServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.path.ProfileServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.utils.RequestUtils;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Test;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.UnsupportedEncodingException;
import java.net.URI;

import static org.junit.Assert.assertEquals;

public class BasicTest {

    @Test
    public void test() throws UnsupportedEncodingException, ParseException {
        URI uri = UriComponentsBuilder.fromUriString(GlobalServicePath.PROFILE_SERVICE + ProfileServicePath.PROFILE)
                .build().toUri();
        HttpPost httpPost = new HttpPost(uri);
        String json = "{\"email\": \"florian.salord@etu.unice.fr\"}";
        RequestUtils.generateBody(httpPost, json);
        String profile = RequestUtils.executeRequest(httpPost, 200, false);

        JSONParser parser = new JSONParser();

        assertEquals("florian.salord@etu.unice.fr", ((JSONObject) parser.parse(profile)).get("_id"));
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
