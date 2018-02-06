package com.gs.jf.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 公共服务
 * Created by WangGang on 2016/5/27.
 */
@Service
public class CashCommonService {



	private static final Logger LOGGER = LoggerFactory.getLogger(CashCommonService.class);


	/**
	 * post 封装
	 *
	 * @param url
	 * @param param 参数 map形式传入
	 * @return json数据
	 */
	public static String post(String url, Map<String, Object> param) {


		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/x-www-form-urlencoded;charset=UTF-8");
		//        post.addHeader("Content-Type", "application/json;charset=UTF-8");

		String paramsEncoding = "UTF-8";

		StringBuilder encodedParams = new StringBuilder();
		//空值处理
		if(null == param)
			param = new HashMap<String, Object>();

		for (Map.Entry<String, Object> entry : param.entrySet()) {
			try {
				if(!StringUtils.isBlank(entry.getKey())&& null != entry.getValue()){
					encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
					encodedParams.append('=');
					encodedParams.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));
					encodedParams.append('&');
				}
			} catch (UnsupportedEncodingException e) {
				//TODO 异常
			}
		}
		byte[] body = new byte[0];
		try {
			body = encodedParams.toString().getBytes(paramsEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpEntity entity = new ByteArrayEntity(body);

		post.setEntity(entity);
		CloseableHttpClient httpClient=null;
		try {
			httpClient = HttpClients.createDefault();
			CloseableHttpResponse res = httpClient.execute(post);
			String json = EntityUtils.toString(res.getEntity());
			return json;

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "{\"code\":\"500\",\"msg\":\"网络异常\"}" ;
	}



	/**
	 * post 封装
	 *
	 * @param url
	 * @param param 参数 map形式传入
	 * @return json数据
	 */
	public static String postrest(String url, Map<String, Object> param) {

		System.out.println(param);

		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json;charset=UTF-8");

		String paramsEncoding = "UTF-8";

		StringBuilder encodedParams = new StringBuilder();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			try {
				if(!StringUtils.isBlank(entry.getKey())&& null != entry.getValue()){
					encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
					encodedParams.append('=');
					encodedParams.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));
					encodedParams.append('&');
				}
			} catch (UnsupportedEncodingException e) {
				//TODO 异常
			}
		}
		byte[] body = new byte[0];
		try {
			body = encodedParams.toString().getBytes(paramsEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpEntity entity = new ByteArrayEntity(body);

		post.setEntity(entity);
		CloseableHttpClient httpClient=null;
		try {
			httpClient = HttpClients.createDefault();
			CloseableHttpResponse res = httpClient.execute(post);
			String json = EntityUtils.toString(res.getEntity());
			return json;

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "{\"code\":\"500\",\"msg\":\"网络异常\"}" ;
	}

	/**
	 * post 封装
	 *
	 * @param url
	 * @param param 参数 map形式传入
	 * @return json数据
	 */
	public static String postJson(String url, String param) {

		LOGGER.info(param);

		HttpPost post = new HttpPost(url);
		post.addHeader("Content-Type", "application/json;charset=UTF-8");

		String paramsEncoding = "UTF-8";

		//String encodedParams = param.toString();
		byte[] body = new byte[0];
		try {
			body = param.toString().getBytes(paramsEncoding);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		HttpEntity entity = new ByteArrayEntity(body);

		post.setEntity(entity);
		CloseableHttpClient httpClient=null;
		try {
			httpClient = HttpClients.createDefault();
			CloseableHttpResponse res = httpClient.execute(post);
			String json = EntityUtils.toString(res.getEntity());
			return json;

		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			if(null != httpClient) {
				try {
					httpClient.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return "{\"code\":\"500\",\"msg\":\"网络异常\"}" ;
	}



	public static String sendPost(String url,  Map<String, Object> param){
		String paramsEncoding = "UTF-8";
		StringBuilder encodedParams = new StringBuilder();
		for (Map.Entry<String, Object> entry : param.entrySet()) {
			try {
				if(!StringUtils.isBlank(entry.getKey())&& null != entry.getValue()){
					encodedParams.append(URLEncoder.encode(entry.getKey(), paramsEncoding));
					encodedParams.append('=');
					encodedParams.append(URLEncoder.encode(entry.getValue().toString(), paramsEncoding));
					encodedParams.append('&');
				}
			} catch (Exception e) {
				LOGGER.error("发送 POST请求出现异常URLEncoder!",e);
			}
		}
		return sendPost(url,encodedParams.toString());
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 *
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			LOGGER.error("发送 POST请求出现异常!",e);
		}finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				LOGGER.error("发送 POST请求出现异常IOException!",ex);
			}
		}
		return result;
	}


	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			connection.setRequestProperty("Content-Type","text/plain;charset=UTF-8");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line+"\n";
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}


}
