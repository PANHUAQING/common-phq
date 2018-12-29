package com.phq.common.filedown;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.logging.Logger;

public class FileDownConnManager {

    private static final FileDownConnManager connManager = new FileDownConnManager();

    private static ExecutorService executorService = Executors.newFixedThreadPool(10); //10个线程跑

    public static FileDownConnManager getDefaultManager() {
        return connManager;
    }

    public static byte[] fileDown(final String netURL) throws ExecutionException, InterruptedException {
        Future<byte[]> future = executorService.submit(new Callable<byte[]>() {
            public byte[] call() throws Exception {
                Date date = new Date();
                URL url;
                byte[] getData = new byte[0];
                InputStream is = null;
                try {
                    url = new URL(netURL);
                    URLConnection connection = url.openConnection();
                    is = connection.getInputStream();
                    //getData = ReadInputStream(is);

                } catch (IOException e) {
                   // logger.error("从URL获得字节流数组失败 " + ExceptionUtils.getMessage(e));
                } finally {
                    try {
                        is.close();
                    } catch (IOException e) {
                       // logger.error("从URL获得字节流数组流释放失败 " + ExceptionUtils.getMessage(e));
                    }
                }
                return getData;
            }
        });
        return future.get();
    }
}
