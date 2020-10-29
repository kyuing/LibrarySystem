package ie.cct._2018316.dev;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HexManager {
	
	public static Charset encodingType = StandardCharsets.UTF_8;
	
	// Text To Hex
	public static String textToHex(String text) {
		byte[] buf = null;
	    buf = text.getBytes(encodingType);
	    char[] HEX_CHARS = "0123456789abcdef".toCharArray();
	    char[] chars = new char[2 * buf.length];
	    
	    for(int i = 0; i < buf.length; ++i) {
	    	chars[2 * i] = HEX_CHARS[(buf[i] & 0xF0) >>> 4];
	        chars[2 * i + 1] = HEX_CHARS[buf[i] & 0x0F];
	     }
	     
	    return new String(chars);
	 }
}
