package com.phq.common.word;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import freemarker.template.Configuration;
import freemarker.template.Template;
/**
 * 
 * @ClassName:  WordUtil   
 * @Description:
      word 导出
 * @author: panhuaqing
 * @date:   2019年3月19日 下午4:01:36   
 *
 */
public class WordUtil {
	//1.创建模板
	private static final Configuration config = new Configuration();
	
	public static Configuration getInstance() {
        return WordUtil.config;
    }
	/**
	 * 
	 * @Title: wordInit   
	 * @Description: 
	       word 初始化并数据装载
	 * @param: @param map   数据 
	 * @param: @param templateLoadDir  模板�?在目�? 
	 * @param: @param templeFileName   模板名称
	 * @param: @param exportPath       导出路径
	 * @return: void      
	 * @throws
	 */
	public static void wordInit(Map map,String templateLoadDir, String templeFileName,String exportPath) {
        // 这里我们的模板是放在templateLoadDir包下�?  
		try {
			config.setDefaultEncoding("utf-8");
		    config.setDirectoryForTemplateLoading(new File(templateLoadDir));
	        // 输出文档路径及名�?
	        File outFile = new File(exportPath);

            //templeFileName为要装载的模�? 
            Template  t = config.getTemplate(templeFileName);
            t.setEncoding("utf-8");
            Writer  out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), "utf-8"));
            //数据填充到模板中 
            t.process(map, out);
            out.close();
        
		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}
	/**
	 * 
	 * @Title: packWordFileToZip   
	 * @Description: 
	      打包到zip文件�?
	 * @param: @param zipPath    zip放置的目�?
	 * @param: @param filePath   文件的目�?   
	 * @return: void      
	 * @throws
	 */
	public static void packWordFileToZip(String zipPath,String filePath) {
		 //使用字节数组作为数据的中转站
		 byte[] buffer = new byte[1024];
		 try {
			ZipOutputStream out = new ZipOutputStream(new FileOutputStream(zipPath));
			 List<File> fileNameList = new ArrayList<File>();
			 //获取文件夹下�?有文件文件路�?
			getAllFileName(filePath, fileNameList);
			
			for(File file:fileNameList) {
				 FileInputStream fis = new FileInputStream(file.getAbsolutePath());  
				 
	               out.putNextEntry(new ZipEntry(file.getName())); 
	               int len;  
	               // 打包到zip文件  
	               while ((len = fis.read(buffer)) > 0) {  
	                   out.write(buffer, 0, len);  
	               }  
	               out.closeEntry();  
	               fis.close();  
			  }
			  out.flush();
			 out.close();  
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		
	}
	/**
	 * 
	 * @Title: getAllFileName   
	 * @Description: 
	        通过目录获取下面�?有的文件
	 * @param: @param path 目录位置 
	 * @param: @param fileNameList    文件名称列表   
	 * @return: void      
	 * @throws
	 */
	public static void getAllFileName(String path,List<File> fileNameList) {
        File file = new File(path);
        File[] tempList = file.listFiles();
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
                if(tempList[i].getName().contains("doc")) {
                	  fileNameList.add(tempList[i].getAbsoluteFile());
                }
            }
            if (tempList[i].isDirectory()) {
                getAllFileName(tempList[i].getAbsolutePath(),fileNameList);
            }
        }
        return;
    }


    public static void main(String[] args) {

        List<Map<String, Object>> newsList=new ArrayList<Map<String,Object>>();
        
        for(int i=1;i<=10;i++){
            Map<String, Object> map=new HashMap<String, Object>();
            map.put("TEST", "测试"+i);
            newsList.add(map);
        }
        
        //遍历list进行数据打包
        for(Map m : newsList) {
            wordInit(m,"G:\\phq\\test","test.xml","G:\\phq\\test\\"+m.get("TEST")+".doc");
        }
        
        packWordFileToZip("G:\\phq\\test\\test.zip","G:\\phq\\test\\");
        
        
        
	}
   

}
