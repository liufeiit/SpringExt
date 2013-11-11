package org.matrix.http;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.springframework.util.StreamUtils;

/**
 * 
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年11月11日 下午5:56:59
 */
public class HttpClientUtil {
	private static HttpParams params;
	private static ClientConnectionManager cm;
	static {
		params = new BasicHttpParams();
		params.setParameter(HttpClientParams.SO_TIMEOUT, 5000);
		params.setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
		params.setLongParameter(HttpClientParams.CONNECTION_MANAGER_TIMEOUT, 3000L);
		params.setLongParameter("http.conn-manager.timeout", 3000L);
		params.setIntParameter("http.conn-manager.max-total", 1000);
		HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", 80, PlainSocketFactory.getSocketFactory()));
		schemeRegistry.register(new Scheme("https", 443, SSLSocketFactory.getSocketFactory()));
		cm = new PoolingClientConnectionManager(schemeRegistry);
	}

	public static HttpClient getHttpclient() {
		return new DefaultHttpClient(cm, params);
	}

	public static boolean doStatusGet(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient(cm, params);
		HttpGet httpget = new HttpGet(url);
		HttpResponse res = httpclient.execute(httpget);
		return res.getStatusLine().getStatusCode() == HttpStatus.SC_OK;
	}

	public static InputStream doGet(String url) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient(cm, params);
		HttpGet httpget = new HttpGet(url);
		HttpResponse res = httpclient.execute(httpget);
		InputStream in = null;
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			in = res.getEntity().getContent();
		} else {
			httpget.abort();
			if (res != null) {  
                EntityUtils.consumeQuietly(res.getEntity());  
           }  
		}
		return in;
	}

	public static InputStream doPost(String url, Map<String, String> m, String encoding)
			throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient(cm, params);
		HttpPost httpost = new HttpPost(url);
		httpost.setEntity(new UrlEncodedFormEntity(getNameValuePair(m), encoding));
		HttpResponse res = httpclient.execute(httpost);
		InputStream in = null;
		;
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			in = res.getEntity().getContent();
		} else {
			httpost.abort();
		}
		return in;
	}

	public static InputStream doPost(String url, Map<String, String> m) throws ClientProtocolException, IOException {
		HttpClient httpclient = new DefaultHttpClient(cm, params);
		HttpPost httpost = new HttpPost(url);
		httpost.setEntity(new UrlEncodedFormEntity(getNameValuePair(m)));
		HttpResponse res = httpclient.execute(httpost);
		InputStream in = null;
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			in = res.getEntity().getContent();
		} else {
			httpost.abort();
		}
		return in;
	}

	public static InputStream doPost(String url, Map<String, String> m, File file) throws ClientProtocolException,
			IOException {
		HttpClient httpclient = new DefaultHttpClient(cm, params);
		HttpPost httpost = new HttpPost(url);
		MultipartEntity reqEntity = new MultipartEntity();
		FileBody fileBody = new FileBody(file);
		reqEntity.addPart("fileBody", fileBody);
		getNameValuePair(reqEntity, m);
		httpost.setEntity(reqEntity);
		HttpResponse res = httpclient.execute(httpost);
		InputStream in = null;
		if (res.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			HttpEntity resEntity = res.getEntity();
			if (resEntity != null) {
				in = resEntity.getContent();
			}
		} else {
			httpost.abort();
		}
		return in;
	}

	public static String post(String url, Map<String, String> m, String paramEncoding, String encoding)
			throws ClientProtocolException, IOException {
		return toString(doPost(url, m, paramEncoding), encoding);
	}

	public static String post(String url, Map<String, String> m, String paramEncoding) throws ClientProtocolException,
			IOException {
		return toString(doPost(url, m, paramEncoding));
	}

	public static String post(String url, Map<String, String> m) throws ClientProtocolException, IOException {
		return toString(doPost(url, m));
	}

	public static String post(String url, Map<String, String> m, File file) throws ClientProtocolException, IOException {
		return toString(doPost(url, m, file));
	}

	public static String get(String url, String encoding) throws ClientProtocolException, IOException {
		return toString(doGet(url), encoding);
	}

	public static String get(String url) throws ClientProtocolException, IOException {
		return toString(doGet(url));
	}

	public static String get(String url, Map<String, String> parameter, String encoding) throws ClientProtocolException, IOException {
		return toString(doGet(url + "?" + getParameter(parameter)), encoding);
	}

	public static String get(String url, Map<String, String> parameter) throws ClientProtocolException, IOException {
		return toString(doGet(url + "?" + getParameter(parameter)));
	}
	
	public static String getParameter(Map<String, String> parameter) {
		if(parameter == null || parameter.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for(Map.Entry<String, String> e : parameter.entrySet()) {
			builder.append(e.getKey() + "=" + e.getValue() + "&");
		}
		return builder.toString();
	}

	public static String toString(InputStream is, String encoding) throws IOException {
		String content = null;
		try {
			if (is != null) {
				content = StreamUtils.copyToString(is, Charset.forName(encoding));
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return content;
	}

	public static String toString(InputStream is) throws IOException {
		String content = null;
		try {
			if (is != null) {
				content = StreamUtils.copyToString(is, Charset.defaultCharset());
			}
		} catch (IOException e) {
			throw e;
		} finally {
			if (is != null) {
				is.close();
			}
		}
		return content;
	}

	public static List<NameValuePair> getNameValuePair(Map<String, String> m) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String k : m.keySet()) {
			NameValuePair v = new BasicNameValuePair(k, m.get(k));
			list.add(v);
		}
		return list;
	}

	public static void getNameValuePair(MultipartEntity reqEntity, Map<String, String> m)
			throws UnsupportedEncodingException {
		StringBody comment = null;
		for (String k : m.keySet()) {
			comment = new StringBody(m.get(k));
			reqEntity.addPart(k, comment);
		}
	}
	
	public static void main(String[] s) throws ClientProtocolException, IOException {
		String url = "http://ruoogle03/nova/rank/list";
		Map<String, String> m = new HashMap<String, String>();
		m.put("type", "3");
		m.put("rankId", "5");
		m.put("user_id", "757");
		m.put("need_user_id", "801");
		m.put("token", "zhe@shi^wo*lai(ce)shi~le");
		String data = post(url, m, "UTF-8", "UTF-8");
		System.out.println(data);
	}
}