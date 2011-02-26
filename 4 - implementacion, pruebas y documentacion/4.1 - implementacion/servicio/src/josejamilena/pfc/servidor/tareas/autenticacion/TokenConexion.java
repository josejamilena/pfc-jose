//   Copyright 2009 Jose Antonio Jamilena Daza
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//       http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.

package josejamilena.pfc.servidor.tareas.autenticacion;

/**
 * Token de Autenticación.
 * @author Jose Antonio Jamilena Daza
 */
public class TokenConexion {

    /** Hostname. */
    private String hostname = "";
    /** Driver. */
    private String driver = "";
    /** Cadena de conexión. */
    private String cadenaConexion = "";
    /** Usuario. */
    private String usuario = "";
    /** Contraseña. */
    private String password = "";

    /**
     * Constructor.
     * @param drv driver.
     * @param url cadena de conexión.
     * @param user usuario.
     * @param passwd contraseña.
     */
    public TokenConexion(final String sgbd, final String drv, final String url,
            final String user, final String passwd) {
        this.hostname = sgbd;
        this.driver = drv;
        this.cadenaConexion = url;
        this.usuario = user;
        this.password = passwd;
    }

    /**
     * @return the cadenaConexion
     */
    public final String getCadenaConexion() {
        return cadenaConexion;
    }

    /**
     * @param url the cadenaConexion to set
     */
    public final void setCadenaConexion(final String url) {
        this.cadenaConexion = url;
    }

    /**
     * @return the usuario
     */
    public final String getUsuario() {
        return usuario;
    }

    /**
     * @param user the usuario to set
     */
    public final void setUsuario(final String user) {
        this.usuario = user;
    }

    /**
     * @return the password
     */
    public final String getPassword() {
        return password;
    }

    /**
     * @param passwd the password to set
     */
    public final void setPassword(final String passwd) {
        this.password = passwd;
    }

    /**
     * @return the driver
     */
    public final String getDriver() {
        return driver;
    }

    /**
     * @param drv the driver to set
     */
    public final void setDriver(final String drv) {
        this.driver = drv;
    }

    /**
     * @return the hostname
     */
    public final String getHostname() {
        return hostname;
    }

    /**
     * @param hostname the hostname to set
     */
    public final void setHostname(final String host) {
        this.hostname = host;
    }
}
