package core;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import actividades.Encuesta;
import actividades.Examen;
import actividades.Quiz;
import actividades.Recurso;
import learningPath.LearningPath;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class Consola {
	private static Map<String, Usuario> usuarios = new HashMap<>();
	private static Map<String, LearningPath> mapaLearningPaths = new HashMap<>();
    private static Scanner scanner = new Scanner(System.in);
   
	public static void main(String[] args) {
		usuarios = Controller.cargarPersistenciaUsuarios();
		mapaLearningPaths = Controller.cargarPersistenciaLearningPaths(usuarios);	
		
		//PersistenciaActividades.cargarActividades(mapaLearningPaths);
		//(PersistenciaInscripciones.cargarInscripciones(usuarios, mapaLearningPaths);
		menu();
	}
	
	public static void menu() {
		
        while (true) {
    		
    		
    		
        	System.out.println("");
        	System.out.println("");
            System.out.println("Bienvenido a la consola de Learning Paths");
            System.out.println("1. Registrar usuario");
            System.out.println("2. Ingresar");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            int opcion = scanner.nextInt();
            scanner.nextLine(); 
            
            switch (opcion) {
                case 1:
                    registrarUsuario();
                    break;
                case 2:
                    ingresar();
                    break;
                case 3:
                    System.out.println("Saliendo...");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Opción inválida, intente de nuevo.");
            }
        }
	}
    
    private static void registrarUsuario() {
        System.out.print("Ingrese nombre del usuario: ");
        String nombre = scanner.nextLine();
        System.out.print("Ingrese nombre el correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();
        System.out.print("Es usted (1) Estudiante o (2) Profesor?: ");
        int tipo = scanner.nextInt();
        scanner.nextLine();  
        
        Controller.crearUsuario(usuarios, nombre, correo, password, tipo);
        	
            
      
    }
    
    private static void ingresar() {
        System.out.print("Ingrese el correo: ");
        String correo = scanner.nextLine();
        System.out.print("Ingrese contraseña: ");
        String password = scanner.nextLine();

        if (Controller.verificarIdentidad(usuarios, correo, password)) {
            	Usuario usuario = usuarios.get(correo);
        		if (usuario instanceof Estudiante) {
                    ((Estudiante) usuario).menu();
                    int opcion = scanner.nextInt();
                    scanner.nextLine();
                    if (opcion==1) {
                    	Controller.inscribirseLearningPath(usuarios,mapaLearningPaths,usuario,scanner);
                         
                    }
                    else if(opcion==2) {
                    	Controller.mostrarLearningPathsInscritos(usuario);
                    	
                    }
                    
                    else if(opcion==3) {
                    	Controller.mostrarActividadesLearningPath(usuario, scanner);
                    	
                    }
                    else if(opcion==4) {
                    	Controller.iniciarActividadLearningPath(usuario, scanner);
       
                    }
                    else if (opcion == 5) {
                    	Controller.añadirRating(usuario, scanner);
                    	
                    }
                    
                    
                    }
                    
                 else if (usuario instanceof Profesor) {
                    ((Profesor) usuario).menu();
                    int opcion = scanner.nextInt();
                    scanner.nextLine(); 
                    if (opcion==1) {
                    	Controller.crearLearningPath(mapaLearningPaths,usuario,scanner);
                	}
                    else if(opcion == 2) {
                    	LearningPath learningpath= Controller.mostrarLearningPathsSeleccionar(usuario, scanner);
                    	int opcion2 = scanner.nextInt();
                    	scanner.nextLine(); 
                    	if (opcion2 == 1) {
                    		learningpath.mostrarActividades();
                    	}
                    	else if (opcion2 == 2) {
                    		System.out.println("Tipo de actividad: ");
                    		System.out.println("1. Recurso");
                            System.out.println("2. Tarea");
                            System.out.println("3. Quiz");
                            System.out.println("4. Examen");
                            System.out.println("5. Encuesta");
                    		int tipoActividad = scanner.nextInt();
                    		scanner.nextLine();
                
                    		if (tipoActividad == 1) {
                            	Recurso recurso = Controller.crearRecurso(scanner);
                            	System.out.print("Tiene Actividades Prerrequisito (1)Si (0)No: ");
                            	int tienePreReq = scanner.nextInt();
                            	scanner.nextLine();
                            	if (tienePreReq == 1) {
                            		Controller.añadirPreq(learningpath, recurso, scanner);
                            	}
                            	Controller.guardarActividad(mapaLearningPaths,learningpath, recurso);
                       
                            	for(int i = 0; i<learningpath.getActividades().size();i++) {
	                    			
	                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
	                				System.out.println(learningpath.getActividades().size());
	                    		}
	                    	}
                    		else if (tipoActividad == 2) {
                    			
                    				for(int i = 0; i<learningpath.getActividades().size();i++) {
	                    			
	                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
	                				System.out.println(learningpath.getActividades().size());
	                    		}
                    		}
                    		
                    		else if (tipoActividad == 3) {
                            	Quiz quiz = Controller.crearQuiz(scanner);
                            	System.out.print("Tiene Actividades Prerrequisito (1)Si (0)No: ");
                            	int tienePreReq = scanner.nextInt();
                            	scanner.nextLine();
                            	if (tienePreReq == 1) {
                            		Controller.añadirPreq(learningpath, quiz, scanner);
                            	}
                            	Controller.guardarActividad(mapaLearningPaths,learningpath, quiz);
	                    		for(int i = 0; i<learningpath.getActividades().size();i++) {
	                    			
	                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
	                				System.out.println(learningpath.getActividades().size());
	                    		}
	                    }
                    		
                    
		                    else if (tipoActividad == 4) {
		                            	Examen examen = Controller.crearExamen(scanner);	                    	
		                            	

		                            	System.out.print("Tiene Actividades Prerrequisito (1)Si (0)No: ");
		                            	int tienePreReq = scanner.nextInt();
		                            	scanner.nextLine();
		                            	if (tienePreReq == 1) {
		                            		Controller.añadirPreq(learningpath, examen , scanner);
		                            	}
		                            	Controller.guardarActividad(mapaLearningPaths,learningpath, examen);
		                            	learningpath.addActividad(examen);
		                            	for(int i = 0; i<learningpath.getActividades().size();i++) {
			                    			
			                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
			                				System.out.println(learningpath.getActividades().size());
			                    		}
			                    		
                    		}
		                    else if (tipoActividad == 5) {
		                    	Encuesta encuesta = Controller.crearEncuesta(scanner);	                    	
                            	learningpath.addActividad(encuesta);
                            	System.out.print("Tiene Actividades Prerrequisito (1)Si (0)No: ");
                            	int tienePreReq = scanner.nextInt();
                            	scanner.nextLine();
                            	if (tienePreReq == 1) {
                            		Controller.añadirPreq(learningpath, encuesta, scanner);
                            	}
                            	Controller.guardarActividad(mapaLearningPaths,learningpath, encuesta);
                            	learningpath.addActividad(encuesta);
	                    		for(int i = 0; i<learningpath.getActividades().size();i++) {
	                    			
	                				System.out.println(learningpath.getActividades().get(i).getDescripcion());
	                				System.out.println(learningpath.getActividades().size());
	                    		}
		                    	
		                    	
		                    }
                    	}
                    }
                    else if(opcion ==3) {
                    	menu();
                    }
                 }

        }else {
            System.out.println("1. Volver a intentar\n"
            		+ "2. Devolver a menu anterior");
            int opcion =(int) scanner.nextInt();
            switch(opcion) {
        	case 1:
            	ingresar();
            	break;
            case 2:
            	menu();
            	break;
            default:
            	 System.out.println("Opción invalida, regresando al menu anterior");
            	 menu();
            	 break;
        	}
        }
  
    }
        
//prueba commit 3

}
