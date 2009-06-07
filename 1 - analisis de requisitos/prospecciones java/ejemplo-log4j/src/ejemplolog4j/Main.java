package ejemplolog4j;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;


public class Main {

    static Logger logger = Logger.getLogger(Main.class);

    public static void main(String[] args) {
        PropertyConfigurator.configure("log4j.properties");

        logger.error("Iniciando...");
//        int i = 0;
//        while(i < 1000000000) {
//            ;
//        }
    }

}
