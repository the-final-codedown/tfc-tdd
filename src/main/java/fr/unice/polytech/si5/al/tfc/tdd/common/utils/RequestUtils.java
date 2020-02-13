package fr.unice.polytech.si5.al.tfc.tdd.common.utils;

import fr.unice.polytech.si5.al.tfc.tdd.common.path.GlobalServicePath;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpEntityEnclosingRequestBase;
import org.apache.http.client.methods.HttpHead;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class RequestUtils {

    private static Logger LOG = Logger.getLogger(RequestUtils.class.getName());

    public static void printRequest(HttpUriRequest request, CloseableHttpResponse response, String content) {
        LOG.info(String.format("\t%-20s%-40s", "Request URI : ", request.getURI()));
        LOG.info(String.format("\t%-20s%-40s", "Response STATUS : ", response.getStatusLine()));
        LOG.info(String.format("\t%-20s%-40s", "Response CONTENT : ", content));
    }

    public static String executeRequest(HttpUriRequest request, int expectedStatusCode) {
        return executeRequest(request, expectedStatusCode, true);
    }

    public static String executeRequest(HttpUriRequest request) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                HttpEntity entity = response.getEntity();
                String content = entity == null ? "" : EntityUtils.toString(entity);
                printRequest(request, response, content);
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static String executeRequest(HttpUriRequest request, int expectedStatusCode, boolean print) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                assertEquals(expectedStatusCode, response.getStatusLine().getStatusCode());
                HttpEntity entity = response.getEntity();
                String content = entity == null ? "" : EntityUtils.toString(entity);
                if (print) {
                    printRequest(request, response, content);
                }
                return content;
            }
        } catch (IOException e) {
            e.printStackTrace();
            return e.getMessage();
        }
    }

    public static Header[] executeRequest(HttpHead request, int expectedStatusCode) {
        try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
            try (CloseableHttpResponse response = httpclient.execute(request)) {
                assertEquals(expectedStatusCode, response.getStatusLine().getStatusCode());
                printRequest(request, response, "");
                return response.getAllHeaders();
            }
        } catch (IOException e) {
            e.printStackTrace();
            return new Header[]{};
        }
    }

    public static String loopExecuteRequest(HttpUriRequest request, FuncInterface function, int expectedStatusCode, int timeout) {
        long start = System.currentTimeMillis();
        String content = null;
        while (true) {
            try {
                function.assertion(executeRequest(request, expectedStatusCode, true));
                Thread.sleep(1000);
            } catch (AssertionError | Exception a) {
                content = executeRequest(request, expectedStatusCode);
                break;
            }
            if (System.currentTimeMillis() - timeout * 1000 > start) {
                break;
            }
        }
        return content;
    }

    public static void generateBody(HttpEntityEnclosingRequestBase request, String body) throws UnsupportedEncodingException {
        StringEntity entity = new StringEntity(body);
        request.setEntity(entity);
        request.setHeader("Accept", "application/json");
        request.setHeader("Content-type", "application/json");
    }

    public static URI getURI(SERVICE service, String path) {
        try {
            String architecture = System.getProperty("architecture");
            if ("hybrid".equals(architecture)) {
                return new URI(GlobalServicePath.class.getField("HYBRID_" + service.toString() + "_PATH").get(null) + path);
            } else if ("micro".equals(architecture)) {
                return new URI(GlobalServicePath.class.getField("MICRO_" + service.toString() + "_PATH").get(null) + path);
            }
        } catch (URISyntaxException | NoSuchFieldException | IllegalAccessException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    public interface FuncInterface {
        void assertion(String content) throws Exception;
    }

}
