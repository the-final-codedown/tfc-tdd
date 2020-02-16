package fr.unice.polytech.si5.al.tfc.tdd.common.client;

import fr.unice.polytech.si5.al.tfc.tdd.common.path.AccountServicePath;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.RequestUtils;
import fr.unice.polytech.si5.al.tfc.tdd.common.utils.SERVICE;
import org.apache.http.client.methods.HttpGet;

import java.io.UnsupportedEncodingException;
import java.net.URI;

public class DumpClient {

    public static String dump() throws UnsupportedEncodingException {
        URI uri = RequestUtils.getURI(SERVICE.DUMP, AccountServicePath.DUMP);
        System.out.println(uri);
        HttpGet httpGet = new HttpGet(uri);
        return RequestUtils.executeRequest(httpGet, 200, false);

    }
}
