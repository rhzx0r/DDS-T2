import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import org.fusesource.jansi.Ansi;

/**
 * CargaCSV
 */
public class CSV { //* Clase para manejar todo lo correspondiete con los archivos csv */

  private String csv = "";
  private static final String CABECERA = "Nombre,Asignatura,Calificación";
  
  CSV(String csv) { //* Carga el nombre del CSV */
    this.csv = csv;
  }

  public ListaAlumnos cargarListaAlumnos() { //* Metodo para cargar alumnos a una lista */
    
    ListaAlumnos lista = new ListaAlumnos(); // * Creacion una instancia de la clase Lista Alumnos */
    String line = "";
    
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.csv)); // * Crea un buffer de caracteres en base al CSV */
      br.readLine(); // * Salto de la cabecera */
      while ((line = br.readLine()) != null) { // * Recorre todo el CSV */
        lista.crearAlumno(line); // * Crea un alumno por cada linea del CSV */
      }
      br.close(); // * Cierra el buffer */
    } catch (Exception e) {
      e.printStackTrace(); // ! Imprime los errores
    }
    System.out.println(Ansi.ansi().fgGreen().a("\n[*] CSV cargado correctamente!"));

    return lista;
  }

  public List<Usuario> cargarListaUsuarios() { //* Metodo para cargar usuarios a una lista */
    
    List<Usuario> lista = new ArrayList<Usuario>(); //* crea una lista de objetos tipo Usuario vacia */
    String line = "";
    
    try {
      BufferedReader br = new BufferedReader(new FileReader(this.csv)); // * Crea un buffer de caracteres en base al CSV de usuarios */
      br.readLine(); // * Salto de la cabecera */
      while ((line = br.readLine()) != null) { // * Recorre todo el CSV de usuarios*/
        lista.add(new Usuario(line)); // * Crea un usuario por cada linea del CSV */
      }
      br.close(); // * Cierra el buffer */
    } catch (Exception e) {
      e.printStackTrace(); // ! Imprime los errores
    }

    return lista;
  }

  public static void escribirCSVAlumnos(List<Alumno> lista, String csvNombre) { //* Recibe la lista de los alumnos y escribe el nuevo CSV en el disco */
    
    try {
        
      FileWriter file = new FileWriter(csvNombre); //* Crea el archivo CSV en disco */
      file.append(CABECERA); //* Agrega la cabecera para el nuevo CSV */
      file.append("\n");

      for (Alumno alumno : lista) { //* Llena el nuevo CSV con los datos de los alumnos */
        file.append(alumno.obtenerNombreC());
        file.append(",");
        file.append(alumno.obtenerMateria());
        file.append(",");
        file.append(Integer.toString(alumno.obtenerCalificacion()));
        file.append("\n");
      }

      file.close(); //* Cierra el uso del archivo CSV */
      System.out.println(Ansi.ansi().fgGreen().a("\n[*] CSV generado correctamente!"));
      System.out.println(Ansi.ansi().fgBrightBlue().a("\nRuta del archivo: " + System.getProperty("user.dir") + "\\" + csvNombre));
    } catch (Exception e) {
      System.out.println(Ansi.ansi().fgRed().a("Error al generar el CSV: " + e.getMessage())); //! Imprime los errores
    }
  }
}