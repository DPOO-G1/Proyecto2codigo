package usuarios;

import java.util.Map;
import java.util.Scanner;

import actividades.Actividad;
import actividades.Examen;
import actividades.PreguntaAbierta;

public class Calificador extends Usuario {

	public Calificador(String nombre, String correo, String password) {
		super(nombre, correo, password);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void menu() {
		// TODO Auto-generated method stub

	}
	
	public void calificarExamen(Examen examen){
		Map<PreguntaAbierta, String> respuestas = examen.getRespuestas();
		for (Map.Entry<PreguntaAbierta, String> entry : respuestas.entrySet()) {
		    PreguntaAbierta pregunta = entry.getKey();
		    String respuesta = entry.getValue();
		    System.out.println("\"" + pregunta + "\": " + respuesta);
		}
		
		ponerCalificacion(examen);
	}
	
	
	
	public void ponerCalificacion(Actividad actividad) {
	    Scanner scanner = new Scanner(System.in);
	    double calificacion = -1;
	    
	    while (calificacion < 0.0 || calificacion > 5.0) {
	        System.out.print("Ingrese la calificación de la actividad (0.0 - 5.0): ");
	        
	        if (scanner.hasNextDouble()) {
	            calificacion = scanner.nextDouble();
	            
	            if (calificacion < 0.0 || calificacion > 5.0) {
	                System.out.println("La calificación debe estar entre 0.0 y 5.0. Inténtelo de nuevo.");
	            }
	        } else {
	            System.out.println("Entrada no válida. Ingrese un número entre 0.0 y 5.0.");
	            scanner.next(); // Descarta la entrada no numérica
	        }
	    }
	    
	    actividad.setCalificacion(calificacion); // Asigna la calificación a la actividad (asegúrate de que Actividad tenga este método)
	    System.out.println("Calificación asignada: " + calificacion);
	}

		
	

}
