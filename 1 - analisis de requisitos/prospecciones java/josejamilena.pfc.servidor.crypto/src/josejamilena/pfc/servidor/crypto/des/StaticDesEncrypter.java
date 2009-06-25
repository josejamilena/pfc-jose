package josejamilena.pfc.servidor.crypto.des;

import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.log4j.Logger;

public class StaticDesEncrypter {

    static Logger log = Logger.getLogger(StaticDesEncrypter.class);
    private static StaticDesEncrypter instance;    // Clave para los cifrados 
    private final static byte[] clave = {(byte) 0x81, (byte) 0x98, (byte) 0xD9, (byte) 0x49, (byte) 0x17, (byte) 0x00, (byte) 0xD6, (byte) 0xD7, (byte) 0x24, (byte) 0xD6, (byte) 0x52, (byte) 0x69, (byte) 0x6E, (byte) 0x6E, (byte) 0x4C, (byte) 0x17, (byte) 0xDD, (byte) 0x83, (byte) 0xB8, (byte) 0x55, (byte) 0xEC, (byte) 0x67, (byte) 0x42, (byte) 0xA0};
    private DesEncrypter crypter;

    public StaticDesEncrypter() throws Exception {
        DESKeySpec keyspec = new DESKeySpec(clave);
        SecretKeyFactory kf = SecretKeyFactory.getInstance("DES");
        SecretKey k = kf.generateSecret(keyspec);
        crypter = new DesEncrypter(k);
    }

    public static StaticDesEncrypter getInstance() throws Exception {
        if (instance == null) {
            instance = new StaticDesEncrypter();
        }
        return instance;
    }

    public String encode(String s) {
        try {
            return crypter.encrypt(s);
        } catch (Exception e) {
            log.error("Error encriptando '" + s + "'. Causa: " + e.getMessage());
        }
        return null;
    }

    public String decode(String s) {
        try {
            return crypter.decrypt(s);
        } catch (Exception e) {
            log.error("Error desencriptando '" + s + "'. Causa: " + e.getMessage());
        }
        return null;
    }
    /*public static void main(String[] args) {
    try{
    String cadena="15001.0";
    String encode=StaticDesEncrypter.getInstance().encode(cadena);
    String decode=StaticDesEncrypter.getInstance().decode(encode);
    System.out.println(cadena);
    System.out.println(encode);
    System.out.println(decode);
    } catch (Exception e){
    System.out.println(e.getMessage());
    
    }
    
    }*/
}
