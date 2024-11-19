package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import core.Controller;
import learningPath.LearningPath;
import persistencia.PersistenciaLearningPaths;
import persistencia.PersistenciaUsuarios;
import usuarios.Calificador;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

class CargarPersistencia {

	private static Map<String, LearningPath> learningpathsStatic;
    private static Map<String, Usuario> usuariosStatic;

    @BeforeAll
    static void setUp() {
        Controller.borrarDatosPersistenciaUsuarios();
        Controller.borrarDatosPersistenciaLearningPaths();

        usuariosStatic = new HashMap<>();
        learningpathsStatic = new HashMap<>();

        Estudiante estudiante = new Estudiante("Juan Perez", "juan@example.com", "password123");
        Profesor profesor = new Profesor("Ana Gomez", "ana@example.com", "securePass");
        Calificador calificador = new Calificador("Carlos Ruiz", "carlos@example.com", "pass456");

        usuariosStatic.put(estudiante.getCorreo(), estudiante);
        usuariosStatic.put(profesor.getCorreo(), profesor);
        usuariosStatic.put(calificador.getCorreo(), calificador);

        LearningPath learningPath1 = new LearningPath(
            "Introduction to Java",
            "Learn the fundamentals of Java programming.",
            "Understand basic Java syntax, variables, loops, and methods.",
            "Beginner"
        );
        learningpathsStatic.put("Introduction to Java", learningPath1);

        LearningPath learningPath2 = new LearningPath(
            "Advanced Java",
            "Deep dive into advanced Java topics",
            "Master OOP, Streams, and Concurrency",
            "Advanced"
        );
        learningpathsStatic.put("Advanced Java", learningPath2);

        PersistenciaUsuarios.guardarUsuarios(usuariosStatic);
        PersistenciaLearningPaths.guardarLearningPaths(learningpathsStatic);
    }

    @AfterEach
    void tearDown() {
        Controller.borrarDatosPersistenciaUsuarios();
        Controller.borrarDatosPersistenciaLearningPaths();
        usuariosStatic.clear();
        learningpathsStatic.clear();
    }

    @Test
    void cargarUsuarios() {
    	Map<String, Usuario> usuarios =PersistenciaUsuarios.cargarUsuarios();
		
		 Map<String, Usuario> expectedUsuarios = new HashMap<>();

	        Estudiante expectedEstudiante = new Estudiante("Juan Perez", "juan@example.com", "password123");
	        Profesor expectedProfesor = new Profesor("Ana Gomez", "ana@example.com", "securePass");
	        Calificador expectedCalificador = new Calificador("Carlos Ruiz", "carlos@example.com", "pass456");
	        
	        expectedUsuarios.put(expectedEstudiante.getCorreo(), expectedEstudiante);
	        expectedUsuarios.put(expectedProfesor.getCorreo(), expectedProfesor);
	        expectedUsuarios.put(expectedCalificador.getCorreo(), expectedCalificador);
	        
	        assertEquals(expectedUsuarios.get(expectedEstudiante.getCorreo()).getNombre(), usuarios.get(expectedEstudiante.getCorreo()).getNombre());
	        assertEquals(expectedUsuarios.get(expectedProfesor.getCorreo()).getNombre(), usuarios.get(expectedProfesor.getCorreo()).getNombre());
	        assertEquals(expectedUsuarios.get(expectedCalificador.getCorreo()).getNombre(), usuarios.get(expectedCalificador.getCorreo()).getNombre());

    }
    @Test
    void cargarLearningPaths() {
        Map<String, LearningPath> actualLearningPaths = PersistenciaLearningPaths.cargarLearningPaths(usuariosStatic);

        assertEquals(learningpathsStatic, actualLearningPaths); 
    }
}