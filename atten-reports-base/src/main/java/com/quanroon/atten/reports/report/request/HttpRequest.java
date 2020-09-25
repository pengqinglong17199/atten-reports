package com.quanroon.atten.reports.report.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.quanroon.atten.reports.report.constant.AuthenticationMode;
import com.quanroon.atten.reports.report.constant.DataMode;
import com.quanroon.atten.reports.report.constant.RequestFormat;
import com.quanroon.atten.reports.report.constant.RequestType;
import com.quanroon.atten.reports.report.definition.ReportParamDefinition;
import com.quanroon.atten.reports.report.entity.ReportConfig;
import com.quanroon.atten.reports.report.entity.ReportParam;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * http请求上报
 * @author 彭清龙
 * @date 2020/7/14 15:18
 */
public class HttpRequest implements BaseRequest {

    private static HttpParams httpParams;

    private static PoolingClientConnectionManager pccm ;

    /**
     * 最大连接数
     */
    public final static int MAX_TOTAL_CONNECTIONS = 800;

    /**
     * 获取连接的最大等待时间
     */
    public final static int WAIT_TIMEOUT = 60000;

    /**
     * 每个路由最大连接数
     */
    public final static int MAX_ROUTE_CONNECTIONS = 400;

    static {
        // 设置组件参数, HTTP协议的版本,1.1/1.0/0.9
        HttpParams params = new BasicHttpParams();
        HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
        HttpProtocolParams.setUserAgent(params, "HttpComponents/1.1");
        HttpProtocolParams.setUseExpectContinue(params, true);

        //设置连接超时时间
        int REQUEST_TIMEOUT = 10*1000;  //设置请求超时10秒钟

        int SO_TIMEOUT = 10*1000;       //设置等待数据超时时间10秒钟

        //HttpConnectionParams.setConnectionTimeout(params, REQUEST_TIMEOUT);
        //HttpConnectionParams.setSoTimeout(params, SO_TIMEOUT);
        params.setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, REQUEST_TIMEOUT);
        params.setParameter(CoreConnectionPNames.SO_TIMEOUT, SO_TIMEOUT);

        //设置访问协议
        SchemeRegistry schreg = new SchemeRegistry();
        schreg.register(new Scheme("http",80, PlainSocketFactory.getSocketFactory()));
        schreg.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));

        //多连接的线程安全的管理器
        pccm = new PoolingClientConnectionManager(schreg);
        pccm.setDefaultMaxPerRoute(MAX_ROUTE_CONNECTIONS); //每个主机的最大并行链接数
        pccm.setMaxTotal(MAX_TOTAL_CONNECTIONS);          //客户端总并行链接最大数
    }

    private HttpClient getHttpClient() {
        return new DefaultHttpClient(pccm, httpParams);
    }

    private ReportConfig reportConfig;

    public HttpRequest(ReportConfig reportConfig){
        this.reportConfig = reportConfig;
    }

    @Override
    public String call(ReportParam reportParam, ReportParamDefinition paramDefinition) {

        RequestFormat requestFormat = reportConfig.getRequestFormat();

        // 获取请求鉴权信息
        AuthenticationMode auth = reportConfig.getAuth();
        Map<String, String> signMap = reportConfig.getSignMap(reportParam);

        RequestType requestType = reportConfig.getRequestType();

        if(RequestType.GET == requestType){
            return httpGet(reportParam, paramDefinition);
        }else if(RequestType.POST == requestType){
            return httpPost(reportParam, paramDefinition);
        }
        return null;
    }

    /**
     * http get请求
     * @param reportParam, paramDefinition
     * @return java.lang.String
     * @author 彭清龙
     * @date 2020/7/15 10:43
     */
    private String httpGet(ReportParam reportParam, ReportParamDefinition paramDefinition) {
        AuthenticationMode auth = reportConfig.getAuth();
        Map<String, String> signMap = reportConfig.getSignMap(reportParam);

        String url = reportConfig.getUrl(paramDefinition.getReportType());
        try {
            // 建造url
            URIBuilder uriBuilder = new URIBuilder(url);

            // 封装参数
            JSONObject jsonObject = JSONObject.parseObject(JSON.toJSONString(reportParam));
            List<NameValuePair> list = new LinkedList<>();
            for (Map.Entry<String, Object> entry : jsonObject.entrySet()) {
                list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
            }

            // 鉴权数据不在请求头中
            if(AuthenticationMode.HEADER != auth){
                for (Map.Entry<String, String> entry : signMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }
            uriBuilder.addParameters(list);
            HttpGet httpGet = new HttpGet(uriBuilder.build());

            // 鉴权在请求头中
            if(AuthenticationMode.HEADER == auth){
                for (String key : signMap.keySet()) {
                    httpGet.setHeader(key, signMap.get(key));
                }
            }

            HttpClient httpClient = getHttpClient();
            HttpResponse execute = httpClient.execute(httpGet);
            int statusCode = execute.getStatusLine().getStatusCode();
            if(HttpStatus.SC_OK == statusCode){
                String result = EntityUtils.toString(execute.getEntity(), "UTF-8");
                return result;
            }
        } catch (URISyntaxException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String httpPost(ReportParam reportParam, ReportParamDefinition paramDefinition) {
        return null;
    }
}
