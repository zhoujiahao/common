package cn.zhou.utils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.*;

public class HttpClientUtil {
//    private static final Logger logger = LoggerFactory.getLogger(HttpClientUtil.class);
    private static final String NEW_LINE = "\n";
    private static final String TAB_STR = "\t";
    private static final String SPACE = " ";

    // 默认连接超时
    public static final int DEFAULT_CONNECTION_TIMEOUT_MILLISECOND = 3000;
    // 默认socket超时
    public static final int DEFAULT_SOCKET_TIMEOUT_MILLISECOND = 3000;
    // 默认从连接池获取连接超时
    public static final int DEFAULT_CONNECTION_REQUEST_TIMEOUT_MILLISECOND = 3000;

    private static RequestConfig defaultConfig = RequestConfig.custom().setConnectTimeout(DEFAULT_CONNECTION_TIMEOUT_MILLISECOND)
            .setSocketTimeout(DEFAULT_SOCKET_TIMEOUT_MILLISECOND).setConnectionRequestTimeout(DEFAULT_CONNECTION_REQUEST_TIMEOUT_MILLISECOND).build();

    private RequestConfig config;
    private boolean ssl;

    public static HttpClientUtil createInstance() {
        return new HttpClientUtil();
    }

    public String doGet(String httpUrl) {
        return doGetWithParams(httpUrl, null);
    }

    public String doGetWithParams(String httpUrl, Map<String, Object> params) {
        String urlWithParams = contactUrlAndParams(httpUrl, params);
        HttpGet httpGet = new HttpGet(urlWithParams);
        return exeRequest(httpGet, new StringBuilder(""));
    }

    public String doPost(String httpUrl) {
        return doPostWithParams(httpUrl, null);
    }

    public String doPostWithJson(String httpUrl, String json) {
        StringBuilder sb = new StringBuilder("");
        sb.append(DateUtil.formatDateTime(new Date())).append(TAB_STR);
        sb.append("======= doPost URL [" + httpUrl + "] =======");
        sb.append(NEW_LINE);
        sb.append("======= doPost URL [" + httpUrl + "] =======");
        sb.append(NEW_LINE);


        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        try {
            // 设置参数
            StringEntity stringEntity = new StringEntity(json, "UTF-8");
            stringEntity.setContentType(ContentType.APPLICATION_JSON.toString());
            httpPost.setEntity(stringEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exeRequest(httpPost, sb);
    }

    public String doPostWithParams(String httpUrl, Map<String, Object> params) {
        StringBuilder sb = new StringBuilder("");
        sb.append(DateUtil.formatDateTime(new Date())).append(TAB_STR);
        sb.append("======= doPost URL [" + httpUrl + "] =======");
        sb.append(NEW_LINE);
        sb.append("======= doPost params [" + params + "] =======");
        sb.append(NEW_LINE);
        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        if (params != null) {
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            for (String key : params.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, params.get(key).toString()));
            }
            try {
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return exeRequest(httpPost, sb);
    }

    public String doPostWithAttach(String httpUrl, Map<String, String> params, List<File> fileLists) {
        StringBuilder sb = new StringBuilder("");
        sb.append(DateUtil.formatDateTime(new Date())).append(TAB_STR);
        sb.append("======= doPost URL [" + httpUrl + "] =======");
        sb.append(NEW_LINE);
        sb.append("======= doPost params [" + params + "] =======");
        sb.append(NEW_LINE);
        sb.append("======= doPost fileLists [" + fileLists + "] =======");
        sb.append(NEW_LINE);

        HttpPost httpPost = new HttpPost(httpUrl);// 创建httpPost
        MultipartEntityBuilder meBuilder = MultipartEntityBuilder.create();
        if (params != null) {
            for (String key : params.keySet()) {
                meBuilder.addPart(key, new StringBody(params.get(key), ContentType.TEXT_PLAIN));
            }
        }
        if (fileLists != null) {
            for (File file : fileLists) {
                FileBody fileBody = new FileBody(file);
                meBuilder.addPart("files", fileBody);
            }
        }
        HttpEntity reqEntity = meBuilder.build();
        httpPost.setEntity(reqEntity);
        return exeRequest(httpPost, sb);
    }

    public boolean downloadFile(String inputurl, Map<String, Object> params, String fileName) {
        boolean result = false;
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        OutputStream os = null;
        try {
            httpClient = HttpClients.createDefault();
            String urlWithParams = contactUrlAndParams(inputurl, params);
            HttpGet httpGet = new HttpGet(urlWithParams);
            httpGet.setConfig(config == null ? defaultConfig : config);
            response = httpClient.execute(httpGet);
            entity = response.getEntity();

            os = new FileOutputStream(fileName);
            IOUtils.copy(entity.getContent(), os);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private String exeRequest(HttpRequestBase request, StringBuilder sb) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        HttpEntity entity = null;
        String responseContent = null;
        try {
            if (ssl) {
                PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.load(new URL(request.getURI().toString()));
                DefaultHostnameVerifier hostnameVerifier = new DefaultHostnameVerifier(publicSuffixMatcher);
                httpClient = HttpClients.custom().setSSLHostnameVerifier(hostnameVerifier).build();
            } else {
                httpClient = HttpClients.createDefault();
            }
            request.setConfig(config == null ? defaultConfig : config);
            response = httpClient.execute(request);
            entity = response.getEntity();
            responseContent = EntityUtils.toString(entity, "UTF-8");
            sb.append("======= doPost responseContent [" + responseContent + "] =======");
            sb.append(NEW_LINE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return responseContent;
    }

    public static String contactUrlAndParams(String inputurl, Map<String, Object> params) {
        Assert.notNull(inputurl);
        if (params != null && params.size() > 0) {
            StringBuffer buffer = new StringBuffer();
            buffer.append(inputurl);
            if (inputurl.indexOf("?") == -1) {
                buffer.append("?");
            } else {
                buffer.append("&");
            }
            Set<String> keySet = params.keySet();
            for (String key : keySet) {
                buffer.append(key + "=" + params.get(key) + "&");
            }
            if (buffer.length() > 0) {
                buffer.deleteCharAt(buffer.length() - 1);
            }
            return buffer.toString();
        }
        return inputurl;
    }

    public RequestConfig getConfig() {
        return config;
    }

    public void setConfig(RequestConfig config) {
        this.config = config;
    }

    public boolean isSsl() {
        return ssl;
    }

    public void setSsl(boolean ssl) {
        this.ssl = ssl;
    }
}
