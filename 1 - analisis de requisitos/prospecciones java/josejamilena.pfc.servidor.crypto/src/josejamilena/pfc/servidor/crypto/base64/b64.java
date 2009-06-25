package josejamilena.pfc.servidor.crypto.base64;

import josejamilena.pfc.servidor.crypto.base64.utils.Base64Coder;

/**
 *
 * @author Jose Antonio Jamilena Daza
 */
public class b64 {

    /**
     * Constructor
     */
    public b64() {
    }

    /**
     * Codifica en Base64
     * @param s texto plano
     * @return cadena en Base64
     */
    public String codificarB64(String s) {
        return Base64Coder.encodeString(s);
    }

    /**
     * Descodifica a String
     * @param b cadena en Base64
     * @return texto plano
     */
    public String decodificar64(String b) {
        return Base64Coder.decodeString(b);
    }
}
