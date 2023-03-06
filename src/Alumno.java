public class Alumno { // * Clase con los metodos para operar un alumno */

  private String matricula; // * Variables iniciales */
  private String nombres;
  private String pApellido;
  private String sApellido;
  private String materia;
  private Integer cal; 

  public Alumno(String str) { // * Constructor de la clase alumno */

    String[] datosAlumno = str.split(","); // * Guarda en un arreglo los datos del alumno usando la funcion split */

    this.matricula = datosAlumno[0];
    this.pApellido = datosAlumno[1];
    this.sApellido = datosAlumno[2];
    this.nombres = datosAlumno[3];
    this.materia = "Diseno de Software";
    this.cal = null; // * Asignacion de valores a las variables locales */
  }

  public String obtenerNombreC() { // * Metodo que retorna el nombre completo del alumno */
    return this.pApellido + " " + this.sApellido + " " + this.nombres;
  }

  public void ponerCal(int cal) { // * Asigna la calificacion a la variable local */
    this.cal = cal;
  }

  public String obtenerMatricula() { // * Retorna la matricula del alumno */
    return this.matricula;
  }

  public String obtenerMateria() { // * Retorna el nombre de la materia del alumno */
    return this.materia;
  }

  public Integer obtenerCalificacion() { // * Retorna la calificación del alumno */
    return this.cal;
  }
  
  public Boolean tieneCalificacion() { // * Retorna un booleano dependiendo si el alumno tiene o no calificación
    return this.cal != null;
  }

  @Override // ? Se muestra cuando la clase sea llamada sin ningun metodo *no utilizada aún*
  public String toString() {
    return "\nDatos: " + this.matricula + " " + obtenerNombreC() + " " + this.cal + "\n";
  }
}