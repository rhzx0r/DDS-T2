public class Usuario {

  private String usuario;
  private String contrasena; //* Variables iniciales */

  public Usuario(String str) {
    String[] datosUsuario = str.split(",");
    this.usuario = datosUsuario[0];
    this.contrasena = datosUsuario[1];
  }

  public String obtenerNombreUsuario() { // * Metodo que retorna el nombre completo del usuario */
    return this.usuario;
  }

  public String obtenerContrasenaUsuario() { // * Metodo que retorna la contraseña del usuario */
    return this.contrasena;
  }
}
