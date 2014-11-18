import java.io.DataOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.commons.httpclient.DefaultHttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.codec.DecoderException;

public class PageDownLoader {
	public  String getFileNameByUrl(String url,String contentType)
	{
		url=url.substring(7);//remove http://
		if(contentType.indexOf("html")!=-1)//text/html
		{
			url= url.replaceAll("[\\?/:*|<>\"]", "_")+".html";
			return url;
		}
		else//application/pdf
		{
			return null;//url.replaceAll("[\\?/:*|<>\"]", "_")+"."+ contentType.substring(contentType.lastIndexOf("/")+1);
		}	
	}
	
//	private void saveToLocal(InputStream data,String filePath)
//	{
//		try {
//			DataOutputStream out=new DataOutputStream(
//					new FileOutputStream(new File(filePath)));
//			//for(int i=0;i<data.;i++)
//			String data2 = data.toString();
//			out.writeBytes(data2);
//			out.flush();
//			out.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	}
	private void saveToLocal(byte[] data,String filePath)
	{
		try {
			DataOutputStream out=new DataOutputStream( new FileOutputStream(new File(filePath)));
			for(int i=0;i<data.length;i++)
				out.write(data[i]);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public String downloadFile(String url)
	{
		String filePath = null;
		HttpClient httpClient = new HttpClient();
		httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
		
		GetMethod getMethod = new GetMethod(url);
		getMethod.getParams().setParameter(HttpMethodParams.SO_TIMEOUT, 5000);
		getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new DefaultHttpMethodRetryHandler());
		
		try{//execute the http get request
			int statusCode = httpClient.executeMethod(getMethod);
			if(statusCode != HttpStatus.SC_OK){
				System.err.println("Method failed:" + getMethod.getStatusLine());
				filePath = null;
			}else{
				byte[] responseBody = getMethod.getResponseBody();
				//InputStream responseBody = getMethod.getResponseBodyAsStream();  //=======TODO: find ways to use getResponseBodyAsStream to store files
				filePath = "pages\\" + getFileNameByUrl(url, getMethod.getResponseHeader("Content-Type").getValue());
				saveToLocal(responseBody, filePath);
			}
		}catch(HttpException e){
			System.out.println("please chech the provided http address!!!!!!!!");
			e.printStackTrace();
		}catch (IOException e){
			e.printStackTrace();
		}finally{
			getMethod.releaseConnection();
		}
		return filePath;
		
	}
//	/**main function to test PageDownLoader class
//	 * 
//	 */
//	public static void main(String[] args){
//		PageDownLoader downpage = new PageDownLoader();
//		downpage.downloadFile("http://www.imdb.com/movies-in-theaters/?ref_=nv_tp_inth_1");
//	}
}
//test