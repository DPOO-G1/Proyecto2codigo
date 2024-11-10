package persistencia;

import java.io.BufferedReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import actividades.Recurso;
import actividades.Tarea;
import core.Consola;
import core.Controller;
import learningPath.LearningPath;
import usuarios.Profesor;
import usuarios.Usuario;

import java.util.Date;

public class PersistenciaActividades {
	 public static void guardarRecursosCSV(String nombreLearningPath,Recurso recurso) {
	        try (FileWriter writer = new FileWriter("actividades.csv", true)) {
	        	
	        		writer.append(nombreLearningPath)
	        		.append(",").append(recurso.getTipo()).append(",")
	        		.append(recurso.getDescripcion()).append(",")
	        		.append(recurso.getObjetivo()).append(",")
	        		.append(recurso.getNivelDificultad())
	        		.append(",").append(String.valueOf(recurso.getDuracion())).append(",")
	        		.append(convertirFechatoString(recurso.getFechaLim())).append(",")
	        		.append(Boolean.toString(recurso.isObligatoria())).append(",")
	        		.append(recurso.getTipoRecurso()).append("\n");
	        		
	        }
	        catch (IOException e) {
	            System.out.println("Ocurrió un error al guardar la actividad.");
	            
	            e.printStackTrace();
	        }
	        
	 }
	 
	 public static void guardarQuiz(String nombreLearningPath,Quiz quiz) {
		 
		try (FileWriter writer = new FileWriter("actividades.csv", true)) {
	        	
		List<PreguntaCerrada> listaPreguntas =quiz.getPreguntas();
 		String cantidadPreguntas = Integer.toString(listaPreguntas.size());
 		String calificacionMin = Double.toString(quiz.getCalificacionMin());
 		
 		writer.append(nombreLearningPath).append(",").append(quiz.getTipo()).append(",")
 		.append(quiz.getDescripcion()).append(",").append(quiz.getObjetivo()).append(",").append(quiz.getNivelDificultad())
 		.append(",").append(String.valueOf(quiz.getDuracion())).append(",").append(convertirFechatoString(quiz.getFechaLim())).append(",")
 		.append(Boolean.toString(quiz.isObligatoria())).append(",").append(calificacionMin).append(",").append(cantidadPreguntas).append(",");
 		
 		for(int i=0;i<listaPreguntas.size();i++) {
 			PreguntaCerrada preguntaCerrada = listaPreguntas.get(i);
 			List<Opcion> listaOpciones= preguntaCerrada.getOpciones();
 			String cantidadOpciones = Integer.toString(listaPreguntas.size());
 			String explicacionOpcionCorrecta = (preguntaCerrada.getOpcionCorrecta()).getExplicacion();
 			String cuerpoPregunta = preguntaCerrada.getCuerpo();
 			writer.write(explicacionOpcionCorrecta);
 			writer.write(",");
 			writer.write(cuerpoPregunta);
 			writer.write(",");
 			writer.write(cantidadOpciones);
 			writer.write(",");
 			
 			for(int j=0;j<listaOpciones.size();j++) {
 				String opcion = (listaOpciones.get(j).getExplicacion());
 				writer.write(opcion);
 				writer.write(",");
 			}        		        		
 		}
 		writer.write("\n");
 	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		 
		 
	 }
	 
	 public static void cargarActividades(Map<String,LearningPath> mapaLearnigPaths) {
		 	
			try (BufferedReader reader = new BufferedReader(new FileReader("actividades.csv"))) {
	            String linea;
	            while ((linea = reader.readLine()) != null) {
	  
	                String[] datos = linea.split(",");
	                String learningPathAsociado = datos[0];
	                String tipo = datos[1];
	                String descripcion = datos[2];
	                String objetivo = datos[3];
	                String nivelDificultad = datos[4];
	                String duracion = datos[5];
	                String fechaLim= datos[6];
	                String obligatoria = datos[7];
	                if(tipo.equals("Recurso")) {
		                String tipoRecurso = datos[8];
		                
		                Date fechaFinal = convertirFecha(fechaLim);
		                double duracionFinal = Double.parseDouble(duracion);
		                boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
		                
		                
		                Recurso recurso = new Recurso(descripcion,objetivo,nivelDificultad,duracionFinal,fechaFinal,obligatoriaFinal,tipoRecurso);
		                
		                mapaLearnigPaths.get(learningPathAsociado).addActividad(recurso);
	               
	                }
	                
	                
	                else if(tipo=="Quiz") {
	                
	                	double calificacionMin = Double.parseDouble( datos[7]);
	                	int cantidadPreguntas = Integer.parseInt(datos[8]);
	                	List<PreguntaCerrada> listaPreguntasCerradas = new ArrayList<>();
	                	
	                	int i =0;
	                	int posicionDatos = 9;
	                	while(i<cantidadPreguntas) {
	                		String explicacionCorrecta = datos[posicionDatos];
	                		Opcion opcionCorrecta = new Opcion(explicacionCorrecta);
	                		posicionDatos++;
	                		String cuerpoPregunta = datos[posicionDatos];
	                		posicionDatos++;
	                		int cantidadOpciones= Integer.parseInt(datos[posicionDatos]);
	                		posicionDatos++;
	                		List<Opcion> listaOpciones= new ArrayList<>();
	                		for(int j = 0;j < cantidadOpciones;j++ ) {
	                			Opcion opcion = new Opcion(datos[posicionDatos]);
	                			posicionDatos++;
	                			listaOpciones.add(opcion);
	                			
	                		}
	                		PreguntaCerrada preguntaCerrada = new PreguntaCerrada(cuerpoPregunta, listaOpciones,opcionCorrecta);
	                		listaPreguntasCerradas.add(preguntaCerrada);
	                		
	                		i++;
	                	}
	                	 Date fechaFinal = Controller.convertirFecha(fechaLim);
			             int duracionFinal = Integer.parseInt(duracion);
			             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                	
	                	Quiz quiz = new Quiz(descripcion, objetivo, nivelDificultad, duracionFinal, fechaFinal, calificacionMin, obligatoriaFinal);
 	
	                }	
	                else if(tipo=="Examen") {
		                int cantidadPreguntas =Integer.parseInt(datos[7]);
		                List<PreguntaAbierta> listaPreguntas= new ArrayList<>();
		                int duracionFinal = Integer.parseInt(duracion);
		                
		                for(int posicion = 8; posicion<= cantidadPreguntas; posicion++) {
		                	String cuerpoPregunta=datos[posicion];
		                	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
		                	listaPreguntas.add(preguntaAbierta);
		                	
		                } 
		                Date fechaFinal = Controller.convertirFecha(fechaLim);
			             int duracionFinal1 = Integer.parseInt(duracion);
			             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
			             
			             Examen examen = new Examen(descripcion, objetivo,nivelDificultad, duracionFinal1, fechaFinal, obligatoriaFinal, listaPreguntas);
	                }
	                else if(tipo=="Encuesta") {
	                    	int cantidadPreguntas = Integer.parseInt(datos[7]);
	                    	 Date fechaFinal = Controller.convertirFecha(fechaLim);
				             double duracionFinal = Integer.parseInt(duracion);
				             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                    	int posicionDatos=8;
	                    	List<PreguntaAbierta> preguntas = new ArrayList<>();
	                    	for(int i = 0;i<cantidadPreguntas;i++) {
	                    		String cuerpoPregunta = datos[posicionDatos]; 
	                    		PreguntaAbierta preguntaAbierta = new PreguntaAbierta(cuerpoPregunta);
	                    		preguntas.add(preguntaAbierta);
	                    		posicionDatos++;
	                    	}
	                    	Encuesta encuesta = new Encuesta(descripcion, objetivo,nivelDificultad, duracionFinal, fechaFinal, obligatoriaFinal, preguntas);
	                }

	                else if(tipo=="Tarea") {
	                	Date fechaFinal = Controller.convertirFecha(fechaLim);
			            double duracionFinal = Integer.parseInt(duracion);
			            boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                	int cantidadActividadesPrerequisito =Integer.parseInt(datos[8]);
	                	String[] slicedDatosPrerequisitos = Arrays.copyOfRange(datos, 8, datos.length); 
	                	List<Actividad> listaActividadesPrerequisito = cargarActividadesSecundarias(slicedDatosPrerequisitos);
	                	int posicion=0;
	                	while(!isInteger(slicedDatosPrerequisitos[posicion])) {
	                		posicion+=1;
	                	}
	                	String[] slicedDatosOpcionales = Arrays.copyOfRange(slicedDatosPrerequisitos, posicion, slicedDatosPrerequisitos.length); 
	                	List<Actividad> listaActividadesOpcionales = cargarActividadesSecundarias(slicedDatosOpcionales);
	                	Tarea tarea = new Tarea(descripcion, objetivo,nivelDificultad, duracionFinal, fechaFinal, obligatoriaFinal, listaActividadesPrerequisito, listaActividadesOpcionales);
	                		
	                	
	                	

	                	
	                }
	                
	            }
	        } 
			catch (IOException e) {
	            System.out.println("Ocurrió un error al cargar las actividades.");
	            System.out.println("Desea crear un archivo ?/n 1. Si/n 2. No");
	            Scanner scanner = new Scanner(System.in);
	            int opcion = scanner.nextInt();
	            scanner.nextLine();
	            if (opcion == 1) {
	                // Create an empty CSV file
	                String fileName = "actividades.csv";
	                try (FileWriter writer = new FileWriter(fileName)) {
	                    System.out.println("Archivo CSV vacío creado exitosamente: " + fileName);
	                } catch (IOException e1) {
	                    System.out.println("Error al crear el archivo CSV.");
	                    e.printStackTrace();
	                }
	            } else {
	                System.out.println("No se creará ningún archivo.");
	                e.printStackTrace();
	            }

	            scanner.close();
	      
	        }
		
		}
	 public static String convertirFechatoString(Date fecha) {
	     String fechaComoString = fecha.toString();
	     System.out.println(fechaComoString);
	     return fechaComoString;
	     
	 }

public static List<Actividad> cargarActividadesSecundarias(String[] datos){
	int posicion=0;
	List<Actividad> listaActividades= new ArrayList<>();
	while (!isInteger(datos[posicion]) && posicion < datos.length) {
	if(datos[posicion]=="Act") {
		posicion+=1;
		
	}
	
                String tipo = datos[0];
                String descripcion = datos[1];
                String objetivo = datos[2];
                String nivelDificultad = datos[3];
                String duracion = datos[4];
                String fechaLim= datos[5];
                String obligatoria = datos[6];
                posicion+=6;
                if(tipo == "Recurso") {
	                String tipoRecurso = datos[7];
	                posicion+=1;
	                Date fechaFinal = Controller.convertirFecha(fechaLim);
	                double duracionFinal = Double.parseDouble(duracion);
	                boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
	                
	                Recurso recurso = new Recurso(descripcion,objetivo,nivelDificultad,duracionFinal,fechaFinal,obligatoriaFinal,tipoRecurso);
	                listaActividades.add(recurso);
	                }
	        
                else if(tipo=="Quiz") {
                
                	double calificacionMin = Double.parseDouble( datos[7]);
                	int cantidadPreguntas = Integer.parseInt(datos[8]);
                	posicion+=2;
                	List<PreguntaCerrada> listaPreguntasCerradas = new ArrayList<>();
                	
                	int i =0;
                	int posicionDatos = 9;
                	posicion+=1;
                	while(i<cantidadPreguntas) {
                		String explicacionCorrecta = datos[posicionDatos];
                		Opcion opcionCorrecta = new Opcion(explicacionCorrecta);
                		posicion+=1;
                		posicionDatos++;
                		String cuerpoPregunta = datos[posicionDatos];
                		posicion+=1;
                		posicionDatos++;
                		int cantidadOpciones= Integer.parseInt(datos[posicionDatos]);
                		posicion+=1;
                		posicionDatos++;
                		List<Opcion> listaOpciones= new ArrayList<>();
                		for(int j = 0;j < cantidadOpciones;j++ ) {
                			Opcion opcion = new Opcion(datos[posicionDatos]);
                			posicion+=1;
                			posicionDatos++;
                			listaOpciones.add(opcion);
                			
                		}
                		PreguntaCerrada preguntaCerrada = new PreguntaCerrada(cuerpoPregunta, listaOpciones,opcionCorrecta);
                		listaPreguntasCerradas.add(preguntaCerrada);
                		
                		i++;
                	}
                	 Date fechaFinal = Controller.convertirFecha(fechaLim);
		             int duracionFinal = Integer.parseInt(duracion);
		             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
                	
                	Quiz quiz = new Quiz(descripcion, objetivo, nivelDificultad, duracionFinal, fechaFinal, calificacionMin, obligatoriaFinal);
                	 listaActividades.add(quiz);
            }	
                else if(tipo=="Examen") {
	                int cantidadPreguntas =Integer.parseInt(datos[7]);
	                posicion+=1;
	                List<PreguntaAbierta> listaPreguntas= new ArrayList<>();
					int duracionFinal = Integer.parseInt(duracion);
	                
	                for(int posicionDatos = 8; posicionDatos<= cantidadPreguntas; posicionDatos++) {
	                	String cuerpoPregunta=datos[posicionDatos];
	                	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
	                	listaPreguntas.add(preguntaAbierta);
	                	posicion+=1;
	                	
	                } 
	                Date fechaFinal = Controller.convertirFecha(fechaLim);
		             int duracionFinal1 = Integer.parseInt(duracion);
		             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
		             
	                Examen examen = new Examen(descripcion, objetivo,nivelDificultad, duracionFinal1, fechaFinal, obligatoriaFinal, listaPreguntas);
	                listaActividades.add(examen);
                }
                else if(tipo=="Encuesta") {
                	int cantidadPreguntas = Integer.parseInt(datos[7]);
               	 	Date fechaFinal = Controller.convertirFecha(fechaLim);
               	 	int posicionDatos=8; 
               	 	double duracionFinal = Integer.parseInt(duracion);
		             boolean obligatoriaFinal = Boolean.parseBoolean(obligatoria);
                    	posicion+=1;
                    	
                    	List<PreguntaAbierta> preguntas = new ArrayList<>();
                    	for(int i = 0;i<duracionFinal;i++) {
                    		String cuerpoPregunta = datos[posicionDatos]; 
                    		PreguntaAbierta preguntaAbierta = new PreguntaAbierta(cuerpoPregunta);
                    		preguntas.add(preguntaAbierta);
                    		posicionDatos++;
                    		posicion+=1;
                		
                    		Encuesta encuesta = new Encuesta(descripcion, objetivo,nivelDificultad, duracionFinal, fechaFinal, obligatoriaFinal, preguntas);
                    		listaActividades.add(encuesta);
                	}
                	
                	
                	
                	
                }

           
                		
                	}
                	

            return listaActividades;	
                }
                
            
         
	

		
	
	
	
	
	 
public static void guardarActividadesSecundarias(Actividad actividad, FileWriter writer) {
	
	try {
	if (actividad.getTipo().equals("Recurso")) {
		
		Recurso recurso = (Recurso) actividad;
		writer.append(recurso.getTipo()).append(",")
		.append(recurso.getDescripcion()).append(",").append(recurso.getObjetivo()).append(",").append(recurso.getNivelDificultad())
		.append(",").append(String.valueOf(recurso.getDuracion())).append(",").append(convertirFechatoString(recurso.getFechaLim())).append(",")
		.append(Boolean.toString(recurso.isObligatoria())).append(",").append(recurso.getTipoRecurso()).append("Act");
	}
	else if (actividad.getTipo().equals("Quiz")) {
		Quiz quiz = (Quiz) actividad;
		List<PreguntaCerrada> listaPreguntas =quiz.getPreguntas();
		String cantidadPreguntas = Integer.toString(listaPreguntas.size());
		String calificacionMin = Double.toString(quiz.getCalificacionMin());
		
		writer.append(quiz.getTipo()).append(",")
		.append(quiz.getDescripcion()).append(",").append(quiz.getObjetivo()).append(",").append(quiz.getNivelDificultad())
		.append(",").append(String.valueOf(quiz.getDuracion())).append(",").append(convertirFechatoString(quiz.getFechaLim())).append(",")
		.append(Boolean.toString(quiz.isObligatoria())).append(",").append(calificacionMin).append(",").append(cantidadPreguntas).append(",");
		
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaCerrada preguntaCerrada = listaPreguntas.get(i);
			List<Opcion> listaOpciones= preguntaCerrada.getOpciones();
			String cantidadOpciones = Integer.toString(listaPreguntas.size());
			String explicacionOpcionCorrecta = (preguntaCerrada.getOpcionCorrecta()).getExplicacion();
			String cuerpoPregunta = preguntaCerrada.getCuerpo();
			writer.write(explicacionOpcionCorrecta);
			writer.write(",");
			writer.write(cuerpoPregunta);
			writer.write(",");
			writer.write(cantidadOpciones);
			writer.write(",");
			
			for(int j=0;j<listaOpciones.size();j++) {
				String opcion = (listaOpciones.get(j).getExplicacion());
				writer.write(opcion);
				writer.write(",");
			}        		        		
		}
		writer.write("Act");
	}
	else if(actividad.getTipo().equals("Examen")) {
		Examen examen = (Examen) actividad;
		List<PreguntaAbierta> listaPreguntas =examen.getPreguntas();
		
		writer.append(examen.getTipo()).append(",")
		.append(examen.getDescripcion()).append(",").append(examen.getObjetivo()).append(",").append(examen.getNivelDificultad())
		.append(",").append(String.valueOf(examen.getDuracion())).append(",").append(convertirFechatoString(examen.getFechaLim())).append(",")
		.append(Boolean.toString(examen.isObligatoria())).append(",");
		
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
			String cuerpoPregunta = preguntaAbierta.getCuerpo();
			writer.write(cuerpoPregunta);
			
		}
		writer.write("Act");
	}
	
	else if(actividad.getTipo().equals("Encuesta")) {
		Encuesta encuesta = (Encuesta) actividad;
		List<PreguntaAbierta> listaPreguntas =encuesta.getPreguntas();
		writer.write(Integer.toString(listaPreguntas.size()));
		for(int i=0;i<listaPreguntas.size();i++) {
			PreguntaAbierta preguntaAbierta = listaPreguntas.get(i);
			String cuerpoPregunta = preguntaAbierta.getCuerpo();
			writer.write(cuerpoPregunta);
			
		}

		writer.write("Act");
	}
	
		
} catch (IOException e) {
	System.out.println("Ocurrió un error al guardar las actividades secundarias de una tarea");
	e.printStackTrace();
}
}
	public static boolean isInteger(String str) {
        try {
            Integer.parseInt(str);  
            return true;           
        } catch (NumberFormatException e) {
            return false;         
        }
       
    }
	
	public static Date convertirFecha(String fecha) {
		SimpleDateFormat formato = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", java.util.Locale.ENGLISH);
	    try {
	        Date fechaConvertida = formato.parse(fecha);
	        return fechaConvertida;
	    } catch (ParseException e) {
	        System.out.println("Error al convertir la fecha: " + e.getMessage());
	        return null;
		
	    }
	}
}

