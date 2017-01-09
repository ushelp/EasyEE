package cn.easyproject.easyee.ssh.sys.service.impl;

import java.util.Arrays;
import java.util.HashSet;

public class Test {
	@SuppressWarnings("unused")
	public static void main(String[] args) {

		String str="#AAAA#BBBB#CCCC#DDDD#EEEE#FFFF#GGGG#HHHH#IIII#JJJJ#KKKK#LLLL#MMMM#NNNN#";
		
		HashSet<String> set=new HashSet<String>();
		set.add("AAAA");
		set.add("BBBB");
		set.add("CCCC");
		set.add("DDDD");
		set.add("EEEE");
		set.add("FFFF");
		set.add("GGGG");
		set.add("HHHH");
		set.add("IIII");
		set.add("JJJJ");
		set.add("KKKK");
		set.add("LLLL");
		set.add("MMMM");
		set.add("NNNN");
		
		long s=System.currentTimeMillis();
		for(int i=0;i<=100000;i++){
//			str.indexOf("NNNN");
			"".trim().equals("");
		}
		long e=System.currentTimeMillis();
		
		long s2=System.currentTimeMillis();
		for(int i=0;i<=100000;i++){
//			set.contains("NNNN");
			boolean f="fsffsaaaaaaaa".trim().length()>0;
		}
		long e2=System.currentTimeMillis();
		
		System.out.println("str："+(e-s)+"ms");
		System.out.println("set："+(e2-s2)+"ms");
		
		
		System.out.println(Arrays.toString("a,b,c".split(",")));
		System.out.println(Arrays.toString("a,b,c,".split(",")));
		System.out.println(Arrays.toString(",a,b,c,".split(",")));
		System.out.println(Arrays.toString(",a,b,c,".split("")));
		System.out.println(Arrays.toString(",a#b,c,".split(",|#")));
		System.out.println(Arrays.toString("fasfsa/a.fd".split(",|#")));
	
	}
}
