import groovy.util.logging.Log
import org.apache.http.HttpHeaders;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.apache.http.entity.StringEntity;
import java.util.Map;
import java.util.HashMap;

@Log
class HTTP {

    public def sendHttp() {
        def content_type = "application/json";
        def url = "http://localhost:8090/actions"
        log.info("content_type : " + content_type);
        log.info("url : " + url);

        Map<String, String> result = new HashMap<>();

        RequestConfig requestConfig = RequestConfig
                .custom()
                .setConnectTimeout(10000)
                .setSocketTimeout(10000)
                .build();


        RequestBuilder requestBuilder = RequestBuilder
                .get()
                .setConfig(requestConfig)
                .setUri(url)
                .setHeader("Content-Type", content_type)

        HttpUriRequest request = requestBuilder.build();

        HttpClientBuilder
                .create()
                .build()
                .withCloseable {
                    httpClient ->
                        httpClient.execute(request)
                                .withCloseable{ response ->
                                    String res = "RESPONSE CODE:" + "\n" + response.getStatusLine() + "\n" + "Headers: " + response.getAllHeaders() + "\n" + "Response Body: " + EntityUtils.toString(response.getEntity())
                                    log.info("--------Result: " + res)
                                    result.put("result", res)
                                }
                }

        return result
    }

}
