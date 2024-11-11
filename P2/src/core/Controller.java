package core;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Map;

import actividades.Actividad;
import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import actividades.Recurso;
import learningPath.LearningPath;
import persistencia.PersistenciaUsuarios;
import persistencia.PersistenciaActividades;
import persistencia.PersistenciaInscripciones;
import persistencia.PersistenciaLearningPaths;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;
import java.util.Scanner;

public class Controller {


//Opciones Generales
	public static void crearUsuario(Map<String, Usuario> usuarios,String nombre, String correo, String password, int tipo){
			
		
		if(tipo==1)
			if(!usuarios.containsKey(correo)) {
    		usuarios.put(correo, new Estudiante(nombre, correo, password));
    		PersistenciaUsuarios.guardarUsuarios(usuarios);
            System.out.println("Estudiante registrado con éxito.");
			}else{
        		System.out.print("El correo ya se encuentra registrado!");
        }
			
		else if (tipo == 2) {
        	if(!usuarios.containsKey(correo)) {
        		usuarios.put(correo, new Profesor(nombre,correo, password));
                PersistenciaUsuarios.guardarUsuarios(usuarios);
                System.out.println("Profesor registrado con éxito.");
        	}
        	else {
        		System.out.print("El correo ya se encuentra registrado!");
        	}
        } 
			else {
            System.out.println("Opción inválida, registro fallido.");
        	}
		}
	




	public static Map<String,Usuario> cargarPersistenciaUsuarios() {
	
	return (HashMap<String, Usuario>) PersistenciaUsuarios.cargarUsuarios();
	
	}

	public static Map<String, LearningPath> cargarPersistenciaLearningPaths(Map<String, Usuario> usuarios){
	
	
	return PersistenciaLearningPaths.cargarLearningPaths(usuarios);
	
	
	}
		
	public static void añadirLearningPath(Map<String,Usuario> mapaUsuarios,Map<String, LearningPath> mapaLearningPaths, String tituloLP,Usuario usuario) {
	 
	if (mapaLearningPaths.containsKey(tituloLP)) {
         LearningPath learningPath = (LearningPath) mapaLearningPaths.get(tituloLP);
         ((Estudiante) usuario).addLearningPath(learningPath);
         String correo = usuario.getCorreo();
         mapaUsuarios.put(correo, usuario);
         persistencia.PersistenciaUsuarios.guardarUsuarios(mapaUsuarios);
     
	} else {
         System.out.println("El Learning Path con título '" + tituloLP + "' no está disponible.");
     }
	}

	public static Boolean verificarIdentidad(Map<String, Usuario> usuarios,String correo, String password) {
		
		if (usuarios.containsKey(correo)) {
        	Usuario usuario = usuarios.get(correo);
            if (usuario.getPassword().equals(password)) {
            	return true;
            
            }else {
	            System.out.println("Contraseña no valida");
	            return false;
            }
            
		}else {
			System.out.println("Usuario no encontrado");
			return false;
		}
	}

	
	public static void guardarActividad(Map<String, LearningPath> mapaLearningPaths,LearningPath learningpath, Actividad actividad) {
		learningpath.addActividad(actividad);
		String titulo =learningpath.getTitulo();
		//Se actualiza el learningpath
		mapaLearningPaths.put(titulo, learningpath);
		PersistenciaLearningPaths.guardarLearningPaths(mapaLearningPaths);
		System.out.print("La actividad ha sido guardada con exito");
	}
	
	//Metodos Estudiante
	public static void inscribirseLearningPath(Map<String,Usuario> mapaUsuarios, Map<String, LearningPath> mapaLearningPaths, Usuario usuario, Scanner scanner) {
    	((Estudiante) usuario).mostrarLearningPathsDisponibles(mapaLearningPaths);
    	 System.out.print("Ingrese el título del Learning Path para inscribirse: ");
         String tituloLP = scanner.nextLine();
         Controller.añadirLearningPath(mapaUsuarios, mapaLearningPaths, tituloLP, usuario);

	}


	public static void mostrarLearningPathsInscritos(Usuario usuario) {
		((Estudiante) usuario).mostrarLearningPathsInscritos();
	
	}

	public static void mostrarActividadesLearningPath(Usuario usuario, Scanner scanner) {
	    ((Estudiante) usuario).mostrarLearningPathsInscritos();

	    boolean validInput = false;
	    while (!validInput) {
	        try {
	            System.out.println("Escoja el Learning Path del que quiere ver las actividades: ");
	            int lp = scanner.nextInt();
	            scanner.nextLine(); 

	            LearningPath lpSeleccionado = ((Estudiante) usuario).getLearningPaths().get(lp - 1);
	            lpSeleccionado.mostrarActividades();
	            validInput = true; 

	        } catch (InputMismatchException e) {
	            System.out.println("Error: escriba un número válido.");
	            scanner.nextLine(); 

	        } catch (IndexOutOfBoundsException e) {
	            System.out.println("El número ingresado no corresponde a un Learning Path válido.");
	        }
	    }
	}




	public static void iniciarActividadLearningPath(Usuario usuario, Scanner scanner) {
		((Estudiante) usuario).mostrarLearningPathsInscritos();
		
		try {
		System.out.println("Escoja el Learning Path del que quiere ver las actividades: ");
		int lp = scanner.nextInt();
		scanner.nextLine();
		
		LearningPath lpSeleccionado = ((Estudiante) usuario).getLearningPaths().get(lp-1);
		
		lpSeleccionado.mostrarActividades();
		
		int actividadSeleccionada = scanner.nextInt();
		scanner.nextLine();
		lpSeleccionado.getActividades().get(actividadSeleccionada).iniciar();
		
			}catch(InputMismatchException e) {
				System.out.print("No se escribio un numero");
				iniciarActividadLearningPath(usuario, scanner);
			}catch (IndexOutOfBoundsException e) {
		        System.out.println("El número ingresado no corresponde a una opcion valida");
		        iniciarActividadLearningPath(usuario, scanner);
			}		
		}





	public static void añadirRating(Usuario usuario, Scanner scanner) {
		((Estudiante) usuario).mostrarLearningPathsInscritos();
    	
    	try {
		System.out.println("Escoja el Learning Path del que quiere ver las actividades: ");
    	int lp = scanner.nextInt();
    	scanner.nextLine();
    	
    	LearningPath lpSeleccionado = ((Estudiante) usuario).getLearningPaths().get(lp-1);
    	
    	lpSeleccionado.mostrarActividades();
    	
    	int actividadSeleccionada = scanner.nextInt();
    	scanner.nextLine();    	
    	System.out.println("Su reseña: ");
    	String cuerpo = scanner.nextLine();
    	System.out.println("Ingrese el rating: ");
    	int rating = scanner.nextInt();
    	scanner.nextLine();
    	Actividad actividadSelec = lpSeleccionado.getActividades().get(actividadSeleccionada);
    	((Estudiante) usuario).añadirReseñaAActividad(actividadSelec, cuerpo, rating);
    		}catch(InputMismatchException e) {
				System.out.print("No se escribio un numero");
				añadirRating(usuario, scanner);
			}catch (IndexOutOfBoundsException e) {
		        System.out.println("El número ingresado no corresponde a una opcion valida");
		        añadirRating(usuario, scanner);
			}		
		}

	//Menu Profesor

	public static void crearLearningPath(Map<String, LearningPath> mapaLearningPaths, Usuario usuario,Scanner scanner) {
		System.out.print("Ingrese el título: ");
    	String titulo = scanner.nextLine();
    	System.out.print("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.print("Ingrese los objetivos: ");
    	String objetivos = scanner.nextLine();
    	System.out.print("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	
    	LearningPath learningpath = ((Profesor) usuario).crearLearningPath(titulo,descripcion,objetivos,nivelDificultad);
    	             	
    	((Profesor) usuario).addLearningPath(learningpath);
    	mapaLearningPaths.put(titulo, learningpath);
    	persistencia.PersistenciaLearningPaths.guardarLearningPaths(mapaLearningPaths);
    	System.out.print("Learning Path guardado exitosamente");
	}

	public static LearningPath mostrarLearningPathsSeleccionar(Usuario usuario,Scanner scanner) {
		((Profesor) usuario).mostrarLearningPaths();
    	try {
    		int learningPathSeleccionado1 = scanner.nextInt();
        	scanner.nextLine();
    		LearningPath learningpath = ((Profesor) usuario).getLearningpaths().get(learningPathSeleccionado1);
    		learningpath.menu();
    		return learningpath;
    	}catch(InputMismatchException e) {
    		System.out.print("No se escribió un numero");
    		mostrarLearningPathsSeleccionar(usuario,scanner);
    	}catch(IndexOutOfBoundsException e) {
    		System.out.println("El número ingresado no corresponde a una opcion valida");
    		mostrarLearningPathsSeleccionar(usuario,scanner);
    	}
		return null;
	}
	
	public static Recurso crearRecurso(Scanner scanner) {
        String descripcion = "";
        String objetivo = "";
        String nivelDificultad = "";
        int duracion = 0;
        String fechaLim = "";
        int obligatoria = 0;
        String tipoRecurso = "";

        boolean validInput = false;
        while (!validInput) {
            try {
                System.out.print("Ingrese la descripción: ");
                descripcion = scanner.nextLine();

                System.out.print("Ingrese el objetivo: ");
                objetivo = scanner.nextLine();

                System.out.print("Ingrese el nivel de dificultad: ");
                nivelDificultad = scanner.nextLine();

                System.out.print("Ingrese la duración esperada en minutos: ");
                duracion = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Ingrese la fecha límite sugerida (yyyy-mm-dd): ");
                fechaLim = scanner.nextLine();

                System.out.print("Es obligatoria (1:Si | 0:NO): ");
                obligatoria = scanner.nextInt();
                scanner.nextLine();

                System.out.print("Ingrese el tipo de recurso: ");
                tipoRecurso = scanner.nextLine();

                validInput = true; 

            } catch (InputMismatchException e) {
                System.out.println("Error: se esperaba un número. Intente de nuevo.");
                scanner.nextLine(); 
            }
        }

     
        boolean esObligatoria = opcional(obligatoria);
        Date fechaLimite = convertirStringADate(fechaLim);

        return new Recurso(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, esObligatoria, tipoRecurso);
    }


	
	public static Quiz crearQuiz(Scanner scanner) {
    	System.out.println("Ingrese la descripción: ");
    	String descripcion = scanner.nextLine();
    	System.out.println("Ingrese el objetivo: ");
    	String objetivo = scanner.nextLine();
    	System.out.println("Ingrese el nivel de dificultad: ");
    	String nivelDificultad = scanner.nextLine();
    	System.out.println("Ingrese la duración esperada en minutos: ");
    	int duracion = scanner.nextInt();
    	scanner.nextLine();
    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
    	String fechaLim = scanner.nextLine();
    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
    	int obligatoria = scanner.nextInt();
    	scanner.nextLine();
    	boolean esObligatoria = opcional(obligatoria);
    	Date fechaLimite = convertirStringADate(fechaLim);
    	System.out.println("Ingrese la cantidad de preguntas que va a tener el quiz: ");
		int cantidadPreguntas = scanner.nextInt();
		System.out.println("Ingrese el puntaje mínimo para pasar: ");
		int minimoQuiz = scanner.nextInt();
		scanner.nextLine();
		Quiz quiz = new Quiz(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, minimoQuiz, esObligatoria);
		for(int i = 1; i<= cantidadPreguntas; i++) {
		
			System.out.println("Ingrese la pregunta 1: ");
			String explicacion = scanner.nextLine();
			System.out.println("Ingrese el numero opciones de la pregunta (4 max): ");
			int cantidadOpciones = scanner.nextInt();
			scanner.nextLine();
			try {
				Map<String, List<Opcion>> mapaOpciones =crearOpcionesPregunta(cantidadOpciones, scanner);
				List<Opcion> listaOpciones =mapaOpciones.get("lista");
				List<Opcion> listaCorrecta =mapaOpciones.get("correcta");
				Opcion opcionCorrecta =listaCorrecta.get(0);
				PreguntaCerrada pregunta = new PreguntaCerrada(explicacion, listaOpciones,opcionCorrecta);
				quiz.addPregunta(pregunta);
			
			}
			catch (IllegalArgumentException e) {
				System.out.print("Error: Numero de opciones mayor a 4");
				crearQuiz(scanner);
			}
			
			
		}
		
		
		return quiz;
    }
	 public static Map<String, List<Opcion>> crearOpcionesPregunta(int cantidadOpciones, Scanner scanner) { 
	    	if (cantidadOpciones >5 ) {
	    		throw new IllegalArgumentException();
	    		
	    	}
	    	else {
	    		
	    		Map<String, List<Opcion>> mapaRetorno= new  HashMap<>();
	    		List<Opcion> listaOpciones= new ArrayList<Opcion>();
	    		for(int i= 1;i<=cantidadOpciones;i++) {
	    			System.out.print("Ingrese la opción "+i+" : ");
	    			String explicacionOpcion = scanner.nextLine();
	    			Opcion opcion = new Opcion(explicacionOpcion);
	    			System.out.print("Es una opcion correcta ?(1:Si | 0:NO)");
	    			int correcta = scanner.nextInt();
	    			scanner.nextLine();
	    			if(correcta == 1) {
	    				Opcion opcionCorrecta=opcion;
	    				List<Opcion> opcionCorrectaLista= new ArrayList<Opcion>();
	    				opcionCorrectaLista.add(opcionCorrecta);
	    				mapaRetorno.put("correcta", listaOpciones);
	    			}
	    			listaOpciones.add(opcion);
	    			
	    		}
	    		mapaRetorno.put("lista", listaOpciones);
	
	    		return mapaRetorno ;
	    	}
	    }
	 
	 public static Examen crearExamen(Scanner scanner) {
	    	System.out.println("Ingrese la descripción: ");
	    	String descripcion = scanner.nextLine();
	    	System.out.println("Ingrese el objetivo: ");
	    	String objetivo = scanner.nextLine();
	    	System.out.println("Ingrese el nivel de dificultad: ");
	    	String nivelDificultad = scanner.nextLine();
	    	System.out.println("Ingrese la duración esperada en minutos: ");
	    	int duracion = scanner.nextInt();
	    	scanner.nextLine();
	    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
	    	String fechaLimStr = scanner.nextLine();
	    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
	    	int obligatoria = scanner.nextInt();
	    	scanner.nextLine();
	    	boolean esObligatoria = opcional(obligatoria);
	    	System.out.println("Ingrese la cantidad de preguntas: ");
	    	int cantidadPreguntas = scanner.nextInt();
	    	List<PreguntaAbierta> preguntas = new ArrayList<>();
	    	for(int i =0;i<cantidadPreguntas;i++) {
	    		System.out.println("Escriba la pregunta abierta: ");
	        	String cuerpoPregunta = scanner.nextLine();
	        	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
	        	preguntas.add(preguntaAbierta);
	    	}
	    	Date fechaLim = null;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            fechaLim = sdf.parse(fechaLimStr);
	        } catch (ParseException e) {
	            System.out.println("Formato de fecha incorrecto.");
	            return null; 
	        }
	    	Examen examen = new Examen(descripcion, objetivo, nivelDificultad, duracion, fechaLim, esObligatoria, preguntas);
	    	return examen;
	    }
	 
	 public static Encuesta crearEncuesta(Scanner scanner) {
	    	System.out.println("Ingrese la descripción: ");
	    	String descripcion = scanner.nextLine();
	    	System.out.println("Ingrese el objetivo: ");
	    	String objetivo = scanner.nextLine();
	    	System.out.println("Ingrese el nivel de dificultad: ");
	    	String nivelDificultad = scanner.nextLine();
	    	System.out.println("Ingrese la duración esperada en minutos: ");
	    	int duracion = scanner.nextInt();
	    	scanner.nextLine();
	    	System.out.println("Ingrese la fecha límite sugerida (yyyy-mm-dd: ");
	    	String fechaLimStr = scanner.nextLine();
	    	System.out.println("Es obligatoria (1:Si | 0:NO): ");
	    	int obligatoria = scanner.nextInt();
	    	scanner.nextLine();
	    	boolean esObligatoria = opcional(obligatoria);
	    	System.out.println("Ingrese la cantidad de preguntas: ");
	    	int cantidadPreguntas = scanner.nextInt();
	    	List<PreguntaAbierta> preguntas = new ArrayList<>();
	    	for(int i =0;i<cantidadPreguntas;i++) {
	    		System.out.println("Escriba la pregunta abierta: ");
	        	String cuerpoPregunta = scanner.nextLine();
	        	PreguntaAbierta preguntaAbierta= new PreguntaAbierta(cuerpoPregunta);
	        	preguntas.add(preguntaAbierta);
	    	
	    	}
	    	Date fechaLim = null;
	        try {
	            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	            fechaLim = sdf.parse(fechaLimStr);
	        } catch (ParseException e) {
	            System.out.println("Formato de fecha incorrecto.");
	            return null; 
	        }
	    	Encuesta encuesta = new Encuesta(descripcion, objetivo, nivelDificultad, duracion, fechaLim, esObligatoria, preguntas);
	    	return encuesta;
	    }
	 
	 public static void  añadirPreq(LearningPath learningpath,Actividad actividad, Scanner scanner) {
		learningpath.mostrarActividades();
     	System.out.print("Escoja la actividad prerrequisito: ");
     	for(int i = 0; i<learningpath.getActividades().size();i++) {
			System.out.println(i+" - "+learningpath.getActividades().get(i).getDescripcion());
     	}
     	try {
     	int actividadSeleccionada = scanner.nextInt();
     	Actividad actividadPreReq = learningpath.getActividades().get(actividadSeleccionada);
     	actividad.addPreReq(actividadPreReq);
     	
     	}catch(InputMismatchException e) {
    		System.out.print("No se escribió un numero");
    		añadirPreq(learningpath, actividad, scanner);
     	}
	 }
	 
	 
	//Metodos auxiliares
	
	public static Date convertirStringADate(String fecha) {
        // Definir el formato de la fecha
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        
        // Convertir el String a Date
        try {
            Date fechaConvertida = formato.parse(fecha);
            return fechaConvertida;
        } catch (ParseException e) {
            System.out.println("Error al convertir la fecha: " + e.getMessage());
            return null;
        }
    }
	public static boolean opcional(int opcion) {
		if (opcion == 1) {
			return true;
		}
		return false;
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
		
