package cn.easyproject.easyee.ssh.sys.service.impl;

public class ThreadLocalTest {
	
	
	ThreadLocal<Integer> tl=new ThreadLocal<Integer>();
	
	
	public static void main(String[] args) {
		
		final ThreadLocalTest tt=new ThreadLocalTest();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(100);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
				tt.tl.set(200);
				System.out.println(Thread.currentThread().getName()+": "+tt.tl.get());
			}
		}).start();
	}

}
