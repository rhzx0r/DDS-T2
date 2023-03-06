import java.util.ArrayList;
import java.util.List;
import org.fusesource.jansi.Ansi;

public class ListaAlumnos { //* Clase con los metodos para operar una lista de alumnos */

  private List<Alumno> lista = new ArrayList<Alumno>(); //* Creación de una lista de objetos de clase Alumno */
  private static int count = 0;

  public void crearAlumno(String str) { //* Metodo para crear alumno */
    this.lista.add(new Alumno(str));
  }

  public void mostrarLista() { //* Metodo para mostrar la lista completa de alumnos */

    if(this.lista.isEmpty())
      System.out.println(Ansi.ansi().fgBrightRed().a("\n* No existe informacion de alumnos para mostrar *\n"));
    else {
      System.out.println(Ansi.ansi().fgCyan().a("\nMatricula\tNombre\t\t\tMateria\t\t\tCalificación")); //* Cabecera */
      
      for(Alumno alumno: this.lista) { //* Imprime la lista de alumnos */
        String cal = alumno.tieneCalificacion() ? Integer.toString(alumno.obtenerCalificacion()) : "*Sin calificación*"; //* Si un alumno no tiene calificación se le agrega el comentario *Sin calificación* de otra forma se le asigna la calificación */
        System.out.println(Ansi.ansi().fgMagenta().a(alumno.obtenerMatricula() + "\t\t" + alumno.obtenerNombreC() + "\t" +  alumno.obtenerMateria() + "\t" + cal));
      }
    }
  }

  public int validarCal(String arg) { //* Metodo para validar que la calificación proporcionada sea un entero en un rango de 0-100 */

    while(true) { //* Ciclo while infinito que solo termina hasta que se cumpla con las condiciones */
      try {

        int num = Integer.parseInt(arg); //* Intenta convertir el parametro a un entero en el caso de no poder lanza un error */
        if(num  > 100 || num < 0)  //* En el caso de no cumplir con el rango dispara un error */
          throw new IllegalArgumentException();

        return num; //* En el caso de pasar todas las condiciones retorna un entero */
      } catch (Exception e) { //* Todo este bloque sirve para pedir el valor de nuevo  admeas de capturar todas las excepciones */
        System.out.println(Ansi.ansi().fgRed().a("\n[!] El valor es incorrecto!!! " + e.getMessage()));
        System.out.println(Ansi.ansi().fgYellow().a("Porfavor ingrese un numero entero entre los rangos [0-100]..."));
        System.out.print(Ansi.ansi().fgBrightBlue().a("> "));
        arg = App.sc.nextLine();
      }
    }
  }

  public void ponerCalificaciones() { //* Metodo para poner calificaciones a cada uno de los alumnos */

    if(this.lista.isEmpty()) //* Comprueba que la lista no este vacia */
      System.out.println("\n* No hay alunos para calificar *");

    for(Alumno alumno : this.lista) {

      if (alumno.tieneCalificacion()) continue; //* Si algun alumno ya tiene calificacion salta al siguiente */
      
      System.out.println("\nEscriba la calificacion del alumno: " + Ansi.ansi().fgMagenta().a(alumno.obtenerNombreC()));
      System.out.println(Ansi.ansi().fgBrightBlue().a("Si desea dejar cancelar escriba x"));
      System.out.print("> ");
      String cal = App.sc.nextLine();
      
      if("".equals(cal)) continue; //* En el caso de que el usuario no introduzca una calificacion permanecera como null */
      
      if("x".equals(cal.toLowerCase())) break; //* Si introduce la letra x rompe el bucle for */

      alumno.ponerCal(validarCal(cal)); //* Ingresa la calificación del alumno luego de validarla*/
      count += 1;
    }

    if(this.lista.size() == count && !this.lista.isEmpty()) //* Comprueba si todos los alumnos ya tienen una calificación */
      System.out.println("\n* Todos los alumnos tienen calificacion *");
  }

  public void generarCSV(String nuevoCSV) { //* Metodo para generar el CSV */

    if(this.lista.stream().anyMatch(alumno -> !alumno.tieneCalificacion()) || this.lista.size() == 0) //* Funcion lambda que comprueba si algun alumno no tiene calificación o si la lista esta vacia */
      System.out.println(Ansi.ansi().fgRed().a("\n[!] Uno o mas alumnos no tiene calificacion porfavor introduzca las calificaciones utilizando la opción [3]!"));
    else { //* Si todos los alumnos tienen calificación ejecuta el siguiente bloque */
      // CSV csv = new CSV(nuevoCSV); //* Crea una instancia de la clase CSV con el nombre del nuevo archivo */
      CSV.escribirCSVAlumnos(this.lista, nuevoCSV); //* Llama al metodo de la instancia para escribir el archivo en el disco */
    }
  }
}