package cn.easyproject.easyee.ssh.sys.service.impl;

import org.apache.shiro.codec.Base64;
import org.apache.shiro.crypto.AesCipherService;
import org.apache.shiro.crypto.CipherService;
import org.apache.shiro.io.DefaultSerializer;
import org.apache.shiro.io.Serializer;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.util.ByteSource;

public class ShiroTest {

	public static void main(String[] args) {
		String base64="zGkG0zevFbnIioooS3MfnnbWeBUeiZrScKVJms0CZAdJ2MYTddPkvBHGmMhvgdKA5QJk8FSb9T1Y1tFlUnnCsIvcK+iX4cfrwD7voGdU5bW9AWjwNvl3BDrAgRf+hvjrI3T5nBTFeW7uI6GzfFrIx92qER9lQ97g19Dn555uwIzf0ULvc8jICZTraLLrf2avh1hy2kUJJblO6u5IHiBbAXBNitZ6W1yjLEWmSFnb+EsEWAGB3WwV1u1HZO045LB4G57UIH4QGM0xfJjWWeahiTS4j9IAEJBbghwfL1A5pdzFiXJzzA5GF85vtP+6jLwQfGaQJyv35PvNNsDmCqFe8eUSBLji5z5y/y+yKfZk9izXiEvFjKQ5kqMqKfLMp+Vn5OuO+syc4CfJL4PLI16vwVUPV1EWAzyxUhK7DtD5OMVcLPwVtwZ11dG88wkZtjXvBymLyGCj5/Tk8gTWYsdcNKD5i8WvbMLT45S4iWsZxa/5offIiCipkkqoqvxCppJLTzBoaR/wlqoa1Bc/cvpijiJTIbSCj+iFloQRdax1mMQ";
		 base64 = ensurePadding(base64);
		  byte[] decoded = Base64.decode(base64);
		  
		  
		   byte[] serialized = decoded;
	        CipherService cipherService = new AesCipherService();
	        if (cipherService != null) {
	            ByteSource byteSource = cipherService.decrypt(decoded, new byte[]{-112, -15, -2, 108, -116, 100, -28, 61, -99, 121, -104, -120, -59, -58, -102, 104});
	            serialized = byteSource.getBytes();
	        }
	        Serializer<PrincipalCollection> serializer = new DefaultSerializer<PrincipalCollection>();
	        ;
	        System.out.println(serializer.deserialize(serialized));
	        
	        	SimplePrincipalCollection p=(SimplePrincipalCollection) serializer.deserialize(serialized);	

		  System.out.println(p.getPrimaryPrincipal());
		  System.out.println(p.getRealmNames());
		  System.out.println(p);
	}
	
	   private static  String ensurePadding(String base64) {
	        int length = base64.length();
	        if (length % 4 != 0) {
	            StringBuilder sb = new StringBuilder(base64);
	            for (int i = 0; i < length % 4; ++i) {
	                sb.append('=');
	            }
	            base64 = sb.toString();
	        }
	        return base64;
	    }
    

}
