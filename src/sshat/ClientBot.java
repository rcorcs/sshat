package sshat;

import org.jibble.pircbot.*;
import java.security.*;
import javax.crypto.*;
import javax.crypto.spec.*;

public class ClientBot extends CryptBot{
	public ClientBot(String name, String keyString) throws GeneralSecurityException {
		super(name, keyString);
	}
	public void onDecryptedMessage(String channel,String sender, String login, String hostname, String message){
		System.out.println(message);
	}
	public void onPrivateDecryptedMessage(String sender, String login, String hostname, String message) {
		System.out.println(message);
	} 
}
