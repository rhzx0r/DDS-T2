import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import org.fusesource.jansi.Ansi;
import java.io.Console;
import java.util.Base64;

public class Login { //* Clase del login */
  
  private Boolean bandera = false; //* Bandera para verificar si el usuario y contraseña es correcto */
  private String clave = "";
  private String nombreUsuario = "";
  
  Login(String clave) {
    this.clave = clave;
  }

  private String desencriptar(String textoEncriptado, String clave) throws Exception { //* Metodo para desencriptar la contraseña de usuario, recibe como parametros un array de bits en base64 y la clave maaestra*/
    Cipher cifrado = Cipher.getInstance("AES"); //* Crea una instancia de la clase Cipher con tipo de encriptación AES */
    SecretKeySpec claveSecreta = new SecretKeySpec(clave.getBytes(), "AES"); //* Crea una instancia de la clase SecretKeySpec de tipo aes en base a la clave introducida*/
    cifrado.init(Cipher.DECRYPT_MODE, claveSecreta); //* Inicializa el objeto cifrado con el parametros en modo desencriptar y el objeto SecreyKeySpec creado anteriormente */
    byte[] bytesTextoEncriptado = Base64.getDecoder().decode(textoEncriptado);
    return new String(cifrado.doFinal(bytesTextoEncriptado)); //* Retorna un nuevo String que devuelve el metodo doFinal del objeto cifrado dependiendo del parametro textoEncriptado*/
  }

  public String nombreUsuario() {
    return this.nombreUsuario.substring(0, 1).toUpperCase() + this.nombreUsuario.substring(1); //* Retorna el nombre del usuario actual capitalizado */
  }

  public Boolean lanzarLogin(String csvUsuario) throws Exception { //* metodo para comprobar los datos del usuario */

    Console console = System.console(); //* Instancia la clase Consola para no mostrar la constraseña por consola */
    CSV csv = new CSV(csvUsuario); //* Crea una instancia de la clase CSV con el archivo csv de usuarios  */
    List<Usuario> usuarios = csv.cargarListaUsuarios(); //* Carga los usuarios del csv en una lista de usuarios */
  
    System.out.println(Ansi.ansi().fgBrightBlue().a("\n                - Login -\n")); //* Login de usuarios */
    System.out.println(Ansi.ansi().fgBrightBlue().a("Escribe el nombre de usuario: "));
    System.out.print(Ansi.ansi().fgBrightBlue().a("> "));
    String usuarioNombre = App.sc.nextLine();
    System.out.println(Ansi.ansi().fgBrightBlue().a("\nEscribe la contrasena de usuario: "));
    System.out.print(Ansi.ansi().fgBrightBlue().a("> "));
    String usuarioContrasena = new String(console.readPassword());
  
    for(Usuario usuario : usuarios) { //* Recorre toda la lista de usuarios en busqueda de que coincida el usuario y contraseña ingresada con alguno de la lista */
      if(usuarioNombre.equals(usuario.obtenerNombreUsuario()) && usuarioContrasena.equals(desencriptar(usuario.obtenerContrasenaUsuario(), this.clave))) { //* Conprueba que tanto el nombre de usuario como la constraseña desencriptada del objeto usuario sea igual a los introducidos en el login */
        bandera = true; //* En caso de encontrar al usuario y la contraseña correctos vuelve la bandera verdadera */
        this.nombreUsuario = usuario.obtenerNombreUsuario();
        // clearScreen();
        System.out.println(Ansi.ansi().fgBrightBlue().a("\nUsuario logueado correctamente!!!"));
        System.out.println(Ansi.ansi().fgBrightBlue().a("\nEntrando al programa..."));
        TimeUnit.SECONDS.sleep(2); //* Duerme el hilo de ejecución por 2 minutos para mostrar el mensaje anterior */
      }
    }

    return bandera;
  }
}
