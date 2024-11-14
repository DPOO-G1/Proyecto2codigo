package actividades;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class Quiz extends Actividad {
	
	List<PreguntaCerrada> preguntas;
	double calificacionMin;
	double valorPregunta;
	
	public Quiz(String descripcion, String objetivo, String nivelDificultad, int duracion,
			 Date fechaLim,double calificacionMin, boolean obligatoria) {
		super("Quiz",descripcion, objetivo, nivelDificultad, duracion, fechaLim, obligatoria);
		
		this.preguntas = new ArrayList<>();
		this.calificacionMin = calificacionMin;
		this.valorPregunta = 0;
	}
	
	public void menu() {
		
	}

	public void addPregunta(PreguntaCerrada pregunta) {
		this.preguntas.add(pregunta);
		calcularValorPregunta();
	}
	public void calcularValorPregunta() {
		this.valorPregunta= 1/this.preguntas.size();
	}

	public List<PreguntaCerrada> getPreguntas() {
		return preguntas;
	}

	public void setPreguntas(List<PreguntaCerrada> preguntas) {
		this.preguntas = preguntas;
	}

	public double getCalificacionMin() {
		return calificacionMin;
	}

	public void setCalificacionMin(double calificacionMin) {
		this.calificacionMin = calificacionMin;
	}

	@Override
	public void completar() {
		this.completado = true;
		
	}

	@Override
	public void iniciar() {
		this.setCalificacion(0);
		Scanner scanner = new Scanner(System.in);
		if(preguntas.size()>1) {
			
			for(PreguntaCerrada pregunta : preguntas) {
				System.out.println(pregunta.getCuerpo());
				List<Opcion> opciones = pregunta.getOpciones();
				int i = 0;
				for(Opcion opcion:opciones) {
					System.out.println(i+": "+opcion.getExplicacion());
					i++;
				}
				System.out.println("Escoja la opción que crea correcta: ");
				int opcionEscogida =  scanner.nextInt();
				if (opciones.get(opcionEscogida) == pregunta.getOpcionCorrecta()) {
					this.calificacion+=this.valorPregunta;
				}
				
			}
			if (this.calificacion >= this.calificacionMin){
				this.setEstado("Aprobado");
			}
			else {
				this.setEstado("Reprobado");
			}
			completar();
		}
		
	}
	
	

}