package cn.easyproject.easyee.ssh.sys.service.impl;

import cn.easyproject.easyee.sh.base.util.MD5;

public class MD5Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		System.out.println(MD5.getMd5("admin123","admin".toLowerCase()));
		System.out.println(MD5.getMd5("user123","user".toLowerCase()));
		System.out.println(MD5.getMd5("hr123","hr".toLowerCase()));
		System.out.println(MD5.getMd5("111111","manager".toLowerCase()));
	}

}
