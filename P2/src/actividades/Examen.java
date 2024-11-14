package actividades;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import learningPath.LearningPath;

public class Examen extends Actividad {
    
	public List<PreguntaAbierta> preguntas; 
	public Map<PreguntaAbierta, String> respuestas;

	
    public Examen(String descripcion, String objetivo, String nivelDificultad, double duracion,
                  Date fechaLim, boolean obligatoria, List<PreguntaAbierta> preguntas) {
        super("Examen", descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
        this.preguntas = preguntas;
        this.respuestas = new HashMap<>();
        
    }
    public List<PreguntaAbierta> getPreguntas() {
        return preguntas;
    }

    @Override
    public void menu() {
    }
	@Override
	public void completar() {
		this.completado = true;
	}
	@Override
	public void iniciar() {
		try (Scanner scanner = new Scanner(System.in)) {
			for(int i =0;i<preguntas.size();i++) {
				PreguntaAbierta pregunta = preguntas.get(i);
				System.out.println(pregunta);
				System.out.println("Escriba su respuesta: ");
				String respuesta = scanner.nextLine();
				this.respuestas.put(pregunta, respuesta);
			}
		}
		completar();		
	}
	public Map<PreguntaAbierta, String> getRespuestas() {
		return respuestas;
	}
	public void setRespuestas(Map<PreguntaAbierta, String> respuestas) {
		this.respuestas = respuestas;
	}
	
}