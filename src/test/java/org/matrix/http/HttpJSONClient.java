package org.matrix.http;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;

/**
 * 通过http远程接口获取json格式的数据
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013年11月11日 下午5:12:57
 */
public class HttpJSONClient {

	public static HttpJSONClient newInstance() {
		return new HttpJSONClient();
	}

	public String doPost(String uri, Map<String, String> parameter, String encoding) throws ClientProtocolException,
			IOException {
		HttpClient client = getHttpClient(getHttpParams());
		System.err.println("Fetching URI:" + uri);
		HttpPost post = new HttpPost(uri);
		post.setEntity(new UrlEncodedFormEntity(getNameValuePair(parameter)));
		HttpResponse response = client.execute(post);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
		} else {
			post.abort();
		}
		return "";
	}

	public String doPost(String uri, Map<String, String> parameter, String paramEncoding, String encoding)
			throws ClientProtocolException, IOException {
		HttpClient client = getHttpClient(getHttpParams());
		System.err.println("Fetching URI:" + uri);
		HttpPost post = new HttpPost(uri);
		post.setEntity(new UrlEncodedFormEntity(getNameValuePair(parameter), paramEncoding));
		HttpResponse response = client.execute(post);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
		} else {
			post.abort();
		}
		return "";
	}

	public String doGet(String uri, String encoding) throws ClientProtocolException, IOException {
		HttpClient client = getHttpClient(getHttpParams());
		System.err.println("Fetching URI:" + uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
		} else {
			get.abort();
		}
		return "";
	}

	public String doGet(String uri, Map<String, String> parameter, String encoding) throws ClientProtocolException,
			IOException {
		HttpClient client = getHttpClient(getHttpParams());
		String params = getParameter(parameter);
		if (params != null && !params.isEmpty()) {
			uri += ("?" + params);
		}
		System.err.println("Fetching URI:" + uri);
		HttpGet get = new HttpGet(uri);
		HttpResponse response = client.execute(get);
		if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
			return EntityUtils.toString(response.getEntity(), Charset.forName(encoding));
		} else {
			get.abort();
		}
		return "";
	}

	public HttpClient getHttpClient(HttpParams parameter) {
		return new DefaultHttpClient(parameter);
	}

	public HttpParams getHttpParams() {
		HttpParams parameter = new BasicHttpParams();
		HttpConnectionParams.setConnectionTimeout(parameter, 30000);
		HttpConnectionParams.setSoTimeout(parameter, 30000);
		HttpConnectionParams.setSocketBufferSize(parameter, 8 * 1024);
		return parameter;
	}

	public String getParameter(Map<String, String> parameter) {
		if (parameter == null || parameter.isEmpty()) {
			return "";
		}
		StringBuilder builder = new StringBuilder();
		for (Map.Entry<String, String> e : parameter.entrySet()) {
			builder.append(e.getKey() + "=" + e.getValue() + "&");
		}
		return builder.toString();
	}

	public List<NameValuePair> getNameValuePair(Map<String, String> m) {
		List<NameValuePair> list = new ArrayList<NameValuePair>();
		for (String k : m.keySet()) {
			NameValuePair v = new BasicNameValuePair(k, m.get(k));
			list.add(v);
		}
		return list;
	}
}