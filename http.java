package foo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class Http {
	public HttpResponse sendpost(String urlstr, Map params) throws IOException {
		String BOUNDARYSTR = "WebKitFormBoundaryGQQj0u1siTTQJgT6";
		String BOUNDARY = "----" + BOUNDARYSTR + "\r\n";
		HttpResponse response = new HttpResponse();
		OutputStream os = null;
		BufferedReader reader = null;
		try {
			URL url = new URL(urlstr);
			HttpURLConnection httpURLconnection = (HttpURLConnection) url.openConnection();
			httpURLconnection.setRequestMethod("POST");
			httpURLconnection.setDoOutput(true);
//			httpURLconnection.setRequestProperty("Cookie", "Hm_lpvt_9ac0f18d7ef56c69aaf41ca783fcb10c=1436870336");
			httpURLconnection.setRequestProperty("Content-Type", "multipart/form-data;boundary=----" + BOUNDARYSTR);
			httpURLconnection.setRequestProperty("Host", "www.bjjs.gov.cn");
			httpURLconnection.setRequestProperty("Origin", "http://www.bjjs.gov.cn");
			httpURLconnection.setRequestProperty("Referer", "http://www.bjjs.gov.cn/tabid/1199/Default.aspx");
			httpURLconnection.setRequestProperty("Connection", "keep-alive");
			os = httpURLconnection.getOutputStream();
			StringBuffer paramBuffer = new StringBuffer();
	        Iterator it = params.keySet().iterator();
	        while (it.hasNext()) {
		        String str = (String)it.next();
		        paramBuffer.append("--" + BOUNDARY);
		        paramBuffer.append("Content-Disposition:form-data;name=\"");
		        paramBuffer.append(str);
		        paramBuffer.append("\"\r\n\r\n");
		        paramBuffer.append(params.get(str));
		        paramBuffer.append("\r\n");
	        }
	        paramBuffer.append("------WebKitFormBoundaryGQQj0u1siTTQJgT6--\r\n");
	        os.write(paramBuffer.toString().getBytes());
			os.flush();
			os.close();
			response.setResponseCode(httpURLconnection.getResponseCode());
			reader = new BufferedReader(new InputStreamReader(httpURLconnection.getInputStream()));
			String strContent = "";
			String str="";
			while (true) {
				str = reader.readLine();
				if (str == null) {
					break;
				}
				strContent+=str;
			}
			response.setContent(strContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			os.close();
			reader.close();
		}
		return response;
	}

	public HttpResponse sendget(String urlstr) throws IOException {
		HttpResponse response = new HttpResponse();
		BufferedReader reader = null;
		try {
			URL url = new URL(urlstr);
			HttpURLConnection httpURLconnection = (HttpURLConnection) url.openConnection();
			httpURLconnection.connect();
			httpURLconnection.setReadTimeout(30*1000);
			response.setResponseCode(httpURLconnection.getResponseCode());
			reader = new BufferedReader(new InputStreamReader(httpURLconnection.getInputStream()));
			String strContent = "";
			String str;
			while (true) {
				str = reader.readLine();
				if (str == null) {
					break;
				}
				strContent+=str;
			}
			response.setContent(strContent);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			reader.close();
		}
		return response;
	}

	public static void main(String[] args) throws IOException {
//		HttpResponse r = new Http().sendget("http://www.bjjs.gov.cn/tabid/1199/Default.aspx?__EVENTTARGET=ess$ctr7035$ZFBZ_JZQYZZZS_New_List$lbtnWe&ess$__EVENTARGUMENT=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtQYMC=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtYYZZZCH=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZSBH=&ctr7035$ZFBZ_JZQYZZZS_New_List$ddlZCJJLX=-1&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZSHI=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZX=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtJump=&ess$ctr7035$ZFBZ_JZQYZZZS_New_List$HFTJ=&select8=相关协会&select8=市政府机构网站&select8=各区县建委网站&ScrollTop=&__essVariable=&__VIEWSTATE=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");
		HashMap pMap = new HashMap();
		pMap.put("__EVENTTARGET", "ess$ctr7035$ZFBZ_JZQYZZZS_New_List$lbtnJump");
		pMap.put("__EVENTARGUMENT", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtQYMC", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtYYZZZCH", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZSBH", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$ddlZCJJLX", "-1");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZSHI", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtZCDZX", "");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$txtJump", "11");
		pMap.put("ess$ctr7035$ZFBZ_JZQYZZZS_New_List$HFTJ", "");
		pMap.put("ScrollTop", "");
		pMap.put("__essVariable", "");
		pMap.put("__VIEWSTATE", "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");
		HttpResponse r = new Http().sendpost("http://www.bjjs.gov.cn/tabid/1199/Default.aspx",pMap);
		int index = r.getContent().indexOf(">浏 览</a>");
		String link = r.getContent().substring(index-76, index-17);
		System.out.println(link);
	}
}
