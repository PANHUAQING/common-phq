package com.phq.common.filedown;

import java.io.IOException;
import java.util.Date;
import java.util.concurrent.ExecutionException;

public class Test {
	public static void main(String[] args) {
        Date startDate = new Date();
        DownloadFileWithThreadPool pool = new DownloadFileWithThreadPool();
        try {
            pool.getFileWithThreadPool("http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3", "D:\\1.mp3", 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println(new Date().getTime() - startDate.getTime());
    }
	
    public void test2() throws ExecutionException, InterruptedException, IOException {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 15; i++) {
            byte[] by1 = FileDownConnManager.fileDown("http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3");
           // FileUtils.writeByteArrayToFile(new File("D:\\test2_"+i+".mp3"), by1);
        }
        System.out.println(System.currentTimeMillis() - time1);
    }

    public void test3() throws IOException {
        long time1 = System.currentTimeMillis();
        for(int i = 0; i < 15; i++) {
           // byte[] by1 = FileFromUrlUtil.getInputStreamFromUrl("http://mpge.5nd.com/2016/2016-11-15/74847/1.mp3");
            //FileUtils.writeByteArrayToFile(new File("D:\\test3_"+i+".mp3"), by1);
        }
        System.out.println(System.currentTimeMillis() - time1);
    }
}
