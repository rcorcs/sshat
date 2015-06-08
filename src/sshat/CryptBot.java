package sshat;

import org.jibble.pircbot.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public abstract class CryptBot extends PircBot {
	private Cipher cipher;
	private SecretKey key;
	private IvParameterSpec p;

	public CryptBot(String name, String keyString) throws GeneralSecurityException {
		setName(name);
        
		byte[] keyBytes = new byte[24];
		int length = Math.min(keyBytes.length, keyString.length( ));
		System.arraycopy(keyString.getBytes( ), 0, keyBytes, 0, length);
        
		DESedeKeySpec spec = new DESedeKeySpec(keyBytes);
		SecretKeyFactory keyFactory = 
		SecretKeyFactory.getInstance("DESede");
		key = keyFactory.generateSecret(spec);
		cipher = Cipher.getInstance("DESede/CBC/PKCS5Padding");
		p = new IvParameterSpec(keyBytes);
	}

	public byte[] crypt(byte[] input, boolean encrypt) {
		byte[] output = input;
		try{
			cipher.init(encrypt ? Cipher.ENCRYPT_MODE:Cipher.DECRYPT_MODE, key, p);
			output = cipher.doFinal(input);
		}catch (GeneralSecurityException e) {
            // Unable to encypt or decrypt. Leave the input as it was.
		}
		return output;
	}

	public void sendEncryptedMessage(String target, String message) {
		byte[] plainText = message.getBytes( );
		byte[] encrypted = crypt(plainText, true);
		StringBuffer buffer = new StringBuffer( );
		for(int i = 0; i < encrypted.length; i++) {
			String hex = Integer.toString((encrypted[i] & 0xff) + 0x100, 16).substring(1);
			buffer.append(hex);
		}
		sendMessage(target, buffer.toString( ));
	}

	public void onPrivateMessage(String sender, String login,String hostname, String message) {
		try{
            byte[] encrypted = new byte[message.length()/2];
            for(int i=0; i<message.length( ); i+=2) {
                String hex = message.substring(i, i+2);
                encrypted[i/2] = (byte)Integer.parseInt(hex,16);
            }
            
            byte[] plainText = crypt(encrypted, false);
            message = new String(plainText);
				onPrivateDecryptedMessage(sender,login,hostname,message);
		}catch(Exception e){
         //Message was not in a suitable format.
			e.printStackTrace();
		}
	}

	public abstract void onPrivateDecryptedMessage(String sender, String login, String hostname, String message);

	public void onMessage(String channel, String sender, String login,String hostname, String message) {
        try{
            byte[] encrypted = new byte[message.length()/2];
            for(int i=0; i<message.length( ); i+=2) {
                String hex = message.substring(i, i+2);
                encrypted[i/2] = (byte)Integer.parseInt(hex,16);
            }
            
            byte[] plainText = crypt(encrypted, false);
            message = new String(plainText);
				onPrivateDecryptedMessage(sender,login,hostname,message);
		}catch(Exception e){
         //Message was not in a suitable format.
			e.printStackTrace();
		}
	}
	public abstract void onDecryptedMessage(String channel, String sender, String login, String hostname, String message);
}
