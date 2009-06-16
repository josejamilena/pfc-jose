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

import conex.Comun;
import it.sauronsoftware.cron4j.Scheduler;

public class Main {

    public static void main(String[] args) {

        Comun.iniciarPropiedades();

        String usuario = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("USUARIO_ORACLE");
        String dirOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("SERVIDOR_ORACLE");
        String puertoOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PUERTO_ORACLE");
        String baseDeDatos = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("BD_ORACLE");
        usuario = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("USUARIO_ESTADISTICAS");
        dirOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("SERVIDOR_ESTADISTICAS");
        puertoOracle = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PUERTO_ESTADISTICAS");
        baseDeDatos = Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("BD_ESTADISTICAS");

        MyTask task = new MyTask();
        Scheduler scheduler = new Scheduler();
        scheduler.schedule(Comun.PROPIEDADES_DE_CONFIGURACION.getProperty("PLANIFICACION"), task);
        scheduler.start();
        while (true) {
            ;
        }
    }
}
