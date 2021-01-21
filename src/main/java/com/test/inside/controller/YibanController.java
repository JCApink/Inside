package com.test.inside.controller;

import cn.yiban.open.Authorize;

import cn.yiban.open.common.User;
import cn.yiban.util.HTTPSimple;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.test.inside.config.AppContext;
import com.test.inside.model.entity.CodeType;
import com.test.inside.model.entity.DataMap;
import com.test.inside.model.entity.JsonResult;
import com.test.inside.service.UserService;
import javax.net.ssl.SSLContext;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustStrategy;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.net.URL;
import java.security.KeyStore;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.List;


@CrossOrigin
@RestController
@ResponseBody
public class YibanController {

    @Autowired
    private UserService userService;

    private final String YIBAN_OPEN_URL = "https://openapi.yiban.cn/";

    private final String YIBAN_USER_ME_INFO = "user/me";

    private final String YIBAN_LOGIN = "http://121.199.77.178/#/login";

    private static JSONObject jsonObject;

    @RequestMapping(value = "/yiban")
    @ResponseBody
    public void YibanLogin(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Authorize authorize = new Authorize(AppContext.APP_ID, AppContext.APP_SECRET);
        String url = authorize.forwardurl(AppContext.BACK_URL, "QUERY", Authorize.DISPLAY_TAG_T.WEB);
        System.out.println(url);
        response.sendRedirect(url);
    }

    @RequestMapping(value = "/yibanlogin", method = RequestMethod.GET)
    public void Comeback(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //获取URL中返回了可以访问令牌的授权码
        String code = request.getParameter("code");

        System.out.println(code);

        String text = doPost(code);

        // System.out.println(text);
        com.alibaba.fastjson.JSONObject json = JSON.parseObject(text);
        AppContext.ACCESS_TOKEN = json.getString(AppContext.KEY_TOKEN);
        //User yibanUser = new User(AppContext.ACCESS_TOKEN);
        net.sf.json.JSONObject userInfo = net.sf.json.JSONObject.fromObject(me(AppContext.ACCESS_TOKEN)).getJSONObject("info");

        /*

        Authorize au = new Authorize(AppContext.APP_ID, AppContext.APP_SECRET);


        String text = au.querytoken(code, AppContext.BACK_URL);


        System.out.println(text);

        //实例化json对象
        JSONObject json = JSON.parseObject(text);

        //获得accessToken授权凭证
        String accessToken = json.getString("access_token");

        System.out.println("access_token" + accessToken);

        //用授权凭证获得易班的User用户对象
        cn.yiban.open.common.User yibanUser = new cn.yiban.open.common.User(accessToken);

        System.out.println(yibanUser);
        //把user保存到session方便后续获取数据
        request.getSession().setAttribute("yibanUser", yibanUser);

        //得到user的info转化成文本
        String jsontext = yibanUser.me();

        //将json文本转化为jsonobject
        JSONObject m = JSON.parseObject(jsontext);

        //new一个userInfo作为m的子对象
        net.sf.json.JSONObject userInfo = net.sf.json.JSONObject.fromObject(m.getString("info"));*/

        Integer yibanId = userInfo.getInt("yb_userid");//获取用户id

        String name = userInfo.getString("yb_usernick");//获取用户昵称

        jsonObject = userService.yiban(yibanId, name);

        response.sendRedirect(YIBAN_LOGIN+"?token:"+jsonObject.getString("token"));

    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String doGet() {
        if (jsonObject == null) {
            jsonObject.put(CodeType.USER_NOT_LOGIN.getMessage(), CodeType.USER_NOT_LOGIN.getCode());
            return JsonResult.build(DataMap.fail().setData(jsonObject)).toJSON();
        } else {
            return JsonResult.build(DataMap.success().setData(jsonObject)).toJSON();
        }

    }



    private String doPost(String code){
        String result = "";

        List<NameValuePair> param = new ArrayList<NameValuePair>();

        param.add(new BasicNameValuePair("client_id", AppContext.APP_ID));
        param.add(new BasicNameValuePair("client_secret", AppContext.APP_SECRET));
        param.add(new BasicNameValuePair("code", code));
        param.add(new BasicNameValuePair("redirect_uri", AppContext.BACK_URL));

        String url = "https://openapi.yiban.cn/oauth/access_token";
        String responseContext = "";
        int found = url.indexOf('?');
        if (found > 0){
            url = url.substring(0, found);
            System.out.println("11111"+url);
        }
        try{
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new UrlEncodedFormEntity(param));
            CloseableHttpResponse response = httpclient.execute(httpPost);
            int status = response.getStatusLine().getStatusCode();

            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    return HTTPSimple.POST(h[0].toString().substring(10), param);
                }
            }
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        result = responseContext;
        System.out.println("result="+result);
        return result;
    }

    static CloseableHttpClient getClientInstance(String url) throws Exception{
        CloseableHttpClient httpclient = null;
        if (isSecurity(url))
        {
            KeyStore myTrustKeyStore = KeyStore.getInstance(
                    KeyStore.getDefaultType()
            );
            SSLContext sslcontext = SSLContexts.custom().loadTrustMaterial(myTrustKeyStore, new TrustStrategy() {
                @Override
                public boolean isTrusted(final X509Certificate[] chain, final String authType) throws CertificateException {
                    return true;
                }
            }).build();
            SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                    sslcontext,
                    new String[] { "TLSv1" },
                    null,
                    SSLConnectionSocketFactory.getDefaultHostnameVerifier()
            );
            httpclient = HttpClients.custom().setSSLSocketFactory(sslsf).build();
        }
        else
        {
            httpclient = HttpClients.createDefault();
        }
        return httpclient;
    }

    public static String GET(String url){
        String responseContext = "";
        try
        {
            CloseableHttpClient httpclient = getClientInstance(url);
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = httpclient.execute(httpGet);
            int status = response.getStatusLine().getStatusCode();
            if( status > 300 && status < 310)
            {
                Header[] h = response.getHeaders("Location");
                if(h.length > 0)
                {
                    httpclient.close();
                    return GET(h[0].toString().substring(10));
                }
            }
            HttpEntity entity = response.getEntity();
            responseContext = EntityUtils.toString(entity);
            EntityUtils.consume(entity);
            httpclient.close();
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        return responseContext;
    }

    private static boolean isSecurity(String url) throws Exception{
        URL u = new URL(url);
        return u.getProtocol().contentEquals("https");
    }

    private String me(String token){
        String query = YIBAN_OPEN_URL;
        query += YIBAN_USER_ME_INFO;
        query += "?access_token=";
        query += token;
        return GET(query);
    }


}
