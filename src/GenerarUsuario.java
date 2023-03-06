import java.io.BufferedReader;
import java.io.Console;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

final class GenerarUsuario {

  private static final String usuariosCSV = System.getProperty("user.dir") + "\\files\\Usuarios.csv";
  private static final String CABECERA = "Usuario,Constraseña";
  private static final String claveMaestra = "9516487320491380"; //* Clave maestra para desencriptar las constrañas de usuario */
  
  private static List<Usuario> cargarListaUsuarios() { //* Metodo para cargar usuarios a una lista */

    List<Usuario> lista = new ArrayList<Usuario>(); //* crea una lista de objetos tipo Usuario vacia */
    String line = "";
    
    try {
      BufferedReader br = new BufferedReader(new FileReader(usuariosCSV)); // * Crea un buffer de caracteres en base al CSV de usuarios */
      br.readLine(); // * Salto de la cabecera */
      while ((line = br.readLine()) != null) { // * Recorre todo el CSV de usuarios*/
        lista.add(new Usuario(line)); // * Crea un usuario por cada linea del CSV */
      }
      br.close(); // * Cierra el buffer */
    } catch (Exception e) {
      e.printStackTrace(); // ! Imprime los errores
    }

    return lista; //* Retorna la lista con todos los usuarios del CSV */
  }

  private static void escribirCSV(List<Usuario> listaUsuarios, String nuevoUsuario, String nuevoPassword) {
    
    try {
        
      FileWriter file = new FileWriter(usuariosCSV); //* Crea el archivo CSV en disco */ 
      file.append(CABECERA + "\n"); //* Agrega la cabecera para el nuevo CSV */

      for (Usuario usuario : listaUsuarios) { //* Llena el nuevo CSV con los datos de los anteriores usuarios */
        file.append(usuario.obtenerNombreUsuario() + "," + usuario.obtenerContrasenaUsuario() + "\n");
      }

      file.append(nuevoUsuario + "," + nuevoPassword + "\n"); //* Agrega el nuevo usuario al CSV *
      
      file.close(); //* Cierra el uso del archivo CSV */
      
      System.out.println("\nCSV generado correctamente!");
      System.out.println( "\nRuta del archivo: " + usuariosCSV);
    } catch (Exception e) {
      System.out.println("Error al generar el CSV: " + e.getMessage()); //! Imprime los errores
    }
  }

  private static String encriptar(String textoPlano, String clave) throws Exception { //* Metodo para desencriptar la contraseña de usuario, recibe como parametros un array de bits en base64 y la clave maaestra*/
    Cipher cifrado = Cipher.getInstance("AES"); //* Crea una instancia de la clase Cipher con tipo de encriptación AES */
    SecretKeySpec claveSecreta = new SecretKeySpec(clave.getBytes(), "AES"); //* Crea una instancia de la clase SecretKeySpec de tipo aes en base a la clave introducida*/
    cifrado.init(Cipher.ENCRYPT_MODE, claveSecreta); //* Inicializa el objeto cifrado con el parametros en modo encriptar y el objeto SecreyKeySpec creado anteriormente */
    byte[] bytesTextoEncriptado = cifrado.doFinal(textoPlano.getBytes()); //* Devuelve un arreglo de bytes */
    return Base64.getEncoder().encodeToString(bytesTextoEncriptado); //* Retorna un nuevo String con la constraseña encriptada*/
  }

  public static void main(String[] args) throws Exception {

    System.setProperty("file.encoding", "UTF-8");// * Define el tipo de charset que se usara en todo el programa
    Console console = System.console(); //* Instancia la clase Consola para no mostrar la constraseña por consola */
    List<Usuario> lista = cargarListaUsuarios(); //* Carga la lista de usuarios */

    System.out.println("\n\tGenerarar nuevo usuario");
    System.out.println("\nEscribe el nombre del usuario");
    System.out.print("> ");
    String usuario = App.sc.nextLine();
    System.out.println("\nEscribe la contraseña del usuario");
    System.out.print("> ");
    String password = new String(console.readPassword());

    escribirCSV(lista, usuario, encriptar(password, claveMaestra)); //* Escribe el usuario nuevo en el CSV de usuarios */
  }
}
