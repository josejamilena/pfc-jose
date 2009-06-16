/*
 * cron4j - A pure Java cron-like scheduler
 * 
 * Copyright (C) 2007-2009 Carlo Pelliccia (www.sauronsoftware.it)
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License version
 * 2.1, as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License 2.1 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License version 2.1 along with this program.
 * If not, see <http://www.gnu.org/licenses/>.
 */

import GUI.Seleccion;
import conex.Comun;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is the simplest task form: a class implementing the {@link Runnable}
 * interface.
 */
public class MyTask implements Runnable {

    public void run() {
        String nombreFichero;
        conex.Comun comun = new conex.Comun();
        try {
            comun.estadisticas = new conex.Estadisticas();
            nombreFichero = comun.getFicheroConsultas();
            comun.lcf = new conex.LanzarConsultasDeFichero(comun.conexion, comun.estadisticas, nombreFichero);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Seleccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Seleccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Seleccion.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Seleccion.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            System.out.println("Si ves esto, pueda que vaya." + new Date());
            System.out.println(comun.lcf.mediaDeTiempos());
        }
    }
}
