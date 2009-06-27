package ToritoWebServer;

import java.util.LinkedList;
import java.util.List;

public class Grafico {

    private String tituloEjeX;
    private String tituloEjeY;
    private LinkedList<String> lista;

    public Grafico() {
    }

    public Grafico(String tituloEjeX, String tituloEjeY, List<String> lista) {
        this.tituloEjeX = tituloEjeX;
        this.tituloEjeY = tituloEjeY;
        this.lista = new LinkedList<String> (lista);
    }

    public List<String> getLista() {
        return lista;
    }

    public void setLista(List<String> lista) {
        this.lista = new LinkedList<String> (lista);
    }

    public String getTituloEjeX() {
        return tituloEjeX;
    }

    public void setTituloEjeX(String tituloEjeX) {
        this.tituloEjeX = tituloEjeX;
    }

    public String getTituloEjeY() {
        return tituloEjeY;
    }

    public void setTituloEjeY(String tituloEjeY) {
        this.tituloEjeY = tituloEjeY;
    }

}
