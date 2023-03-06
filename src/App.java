
/*
 * Tarea 2 Diseño de software
 * Author: Ricardo B
 */
import java.util.Scanner;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class App { // * Clase principal de la aplicacion */

  public static Scanner sc = new Scanner(System.in);
  private static final String dir = System.getProperty("user.dir"); //* Ruta del directorio actual */
  private static final String claveMaestra = "9516487320491380"; //* Clave maestra para desencriptar las constrañas de usuario */
  private static final String alumnosCSV = dir + "\\files\\Alumnos.csv";
  private static final String usuariosCSV = dir + "\\files\\Usuarios.csv";
  private static final String nuevoAlumnosCSV = dir + "\\files\\NuevoAlumnos.csv"; //* Constantes de los archivos CSV */

  public static void clearScreen() {//TODO: Función para limpiar la pantalla de la terminal eliminar en caso de tener problemas
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  public static void esperar() { //TODO: Función para pausar el hilo de ejecución del programa eliminar en caso de tener problemas
    System.out.print(Ansi.ansi().fgYellow().a("\nPresione Enter para contiuar... "));
    sc.nextLine();
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private static void menu() { // * Menu principal del programa */
    System.out.print(Ansi.ansi().fgBrightYellow().a(
    "\n                    - Menu -                   \n" +
    "                                                 \n" +
    "    >[1] Cargar CSV                              \n" +
    "    >[2] Ver Lista de Alumnos                    \n" +
    "    >[3] Ingresar Calificacion de Alumnos        \n" +
    "    >[4] Generar Nuevo CSV                       \n" +
    "    >[5] Salir                                   \n" +
    "                                                 \n" +
    "=> Ingrese una opción:" ));
  }

  public static void main(String[] args) throws Exception { // * Metodo main [Se añadio un throws Exception ya que varias de los metodos utilizados para decifrar las contraseñas lanzan errores que deben ser capturados]*/

    System.setProperty("file.encoding", "UTF-8"); // * Define el tipo de charset que se usara en todo el programa
    AnsiConsole.systemInstall();
    
    Login login = new Login(claveMaestra); //* Crea una instancia de la clase Login con la clave maestra */
    CSV csv = new CSV(alumnosCSV); //* Crea una instancia de la clase CargaCSV con el archivo csv de los alumnos*/
    ListaAlumnos listaAlumnos = new ListaAlumnos(); //* Crea una lista vacia de tipo ListaAlumnos */

    if(login.lanzarLogin(usuariosCSV)){ //* llama al metodo login con el archivo csv de los usuarios y en caso de ser verdadero entra al bloque principal del progra, de otra forma simplemente manda un mensaje y termina el programa */
      while (true) { // * Loop infinito para el menu principal */
      
        clearScreen();
        System.out.println(Ansi.ansi().fgBrightCyan().a("\n    Usuario activo: " + login.nombreUsuario()));
        menu();
        String opc = sc.nextLine();
  
        switch (opc) { // * Opciones del menu */
          case "1":
            listaAlumnos = csv.cargarListaAlumnos(); //* Carga la lista con los elementos retornados del CSV */
            esperar();
            break;
          case "2":
            listaAlumnos.mostrarLista();
            esperar();
            break;
          case "3":
            listaAlumnos.ponerCalificaciones();
            esperar();
            break;
          case "4":
            listaAlumnos.generarCSV(nuevoAlumnosCSV);
            esperar();
            break;
          case "5":
            System.out.println("Adios!!!");
            return;
          default:
            System.out.println(Ansi.ansi().fgRed().a("[!] Seleccione una opción correcta!!!"));
            esperar();
        }
      }
    }

    System.out.println(Ansi.ansi().fgRed().a("\n[!] No tiene autorización para usar este programa verifique sus credenciales!!!"));
    System.out.println(Ansi.ansi().fgYellow().a("[?] Puede leer el README.txt para mas informacion..."));
    AnsiConsole.systemUninstall();
  }
}
