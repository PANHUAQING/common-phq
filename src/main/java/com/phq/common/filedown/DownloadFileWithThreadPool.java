package com.phq.common.filedown;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DownloadFileWithThreadPool {
	  
	//声明静态线程池对象
	 private static ExecutorService threadPool = Executors.newCachedThreadPool();
	
	 public void getFileWithThreadPool(String urlLocation, String filePath, int poolLength) throws IOException {
	       
	        long len = getContentLength(urlLocation);
	        System.out.println(len);
	        for (int i = 0; i < poolLength; i++) {
	            long start = i * len / poolLength;
	            long end = (i + 1) * len / poolLength - 1;
	            if (i == poolLength - 1) {
	                end = len;
	            }
	            System.out.println(start+"---------------"+end);
	            DownloadWithRange download = new DownloadWithRange(urlLocation, filePath, start, end);
	            threadPool.execute(download);
	        }
	        threadPool.shutdown();
	    }
        //获取链接的数据内容长度
	    public static long getContentLength(String urlLocation) throws IOException {
	        URL url = null;
	        if (urlLocation != null) {
	            url = new URL(urlLocation);
	        }
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
	        conn.setReadTimeout(5000);
	        conn.setRequestMethod("GET");
	        long len = conn.getContentLength();

	        return len;
	    }
}
