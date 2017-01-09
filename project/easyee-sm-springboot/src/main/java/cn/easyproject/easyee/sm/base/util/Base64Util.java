package cn.easyproject.easyee.sm.base.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.commons.codec.binary.Base64;

/**
 * Base64 编码，解码工具类
 * @author  easyproject.cn
 *
 */
public class Base64Util {
	/**
	 * 将文件编码为Base64字符串
	 * @param data
	 * @return
	 */
	public static String encodeBase64String(byte[] data){
		byte[] encode=Base64.encodeBase64(data);
		return new String(encode);
	}
	/**
	 * 将文件编码为Base64字符串
	 * @param file
	 * @return
	 */
	public static String encodeBase64String(File file){
		FileInputStream inputFile=null;
		try {
			inputFile = new FileInputStream(file);
			byte[] buffer = new byte[(int) file.length()];
			inputFile.read(buffer);
			inputFile.close();
			return encodeBase64String(buffer);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return "";
		} catch (IOException e) {
			e.printStackTrace();
			return "";
		}
	

	}

//	public static void main(String[] args) throws IOException {
//		File file = new File("E:/desktop/default.jpg");
//		FileInputStream inputFile = new FileInputStream(file);
//		byte[] buffer = new byte[(int) file.length()];
//		inputFile.read(buffer);
//		inputFile.close();
//		 System.out.println(encodeBase64String(buffer));
//	}
}
