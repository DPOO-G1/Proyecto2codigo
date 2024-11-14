package tests;

import actividades.Encuesta;
import actividades.Examen;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import actividades.Reseña;
import core.Controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.TestInfo;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CrearActividades {

    static private Quiz quiz1;
    static private Encuesta encuestaEsperada;
    static private Examen examenEsperado;
    static private Reseña reseñaEsperada;
    
    @BeforeAll
    static void setUp(TestInfo testInfo) {
            String descripcion = "Quiz de Java";
            String objetivo = "Evaluar conocimientos básicos";
            String nivelDificultad = "Principiante";
            int duracion = 30;
            Date fechaLimite = Controller.convertirStringADate("2024-12-31");
            double calificacionMin = 70.0;
            boolean esObligatoria = true;

            quiz1 = new Quiz(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, calificacionMin, esObligatoria);
            String explicacion1 = "¿Qué es Java?";
            List<Opcion> opciones1 = new ArrayList<>();
            opciones1.add(new Opcion("Compilado"));
            opciones1.add(new Opcion("Correcto"));
            opciones1.add(new Opcion("Interpretado"));
            opciones1.add(new Opcion("Ambos"));
            Opcion opcionCorrecta1 = opciones1.get(3);
            PreguntaCerrada pregunta1 = new PreguntaCerrada(explicacion1, opciones1, opcionCorrecta1);
            quiz1.addPregunta(pregunta1);

            String explicacion2 = "¿Qué significa JVM?";
            List<Opcion> opciones2 = new ArrayList<>();
            opciones2.add(new Opcion("Java Virtual Machine"));
            opciones2.add(new Opcion("Java Variable Method"));
            opciones2.add(new Opcion("Just Virtual Memory"));
            opciones2.add(new Opcion("Java Verification Module"));
            Opcion opcionCorrecta2 = opciones2.get(0);
            PreguntaCerrada pregunta2 = new PreguntaCerrada(explicacion2, opciones2, opcionCorrecta2);
            quiz1.addPregunta(pregunta2);
   
            String descripcionE = "Encuesta sobre Java";
            String objetivoE = "Evaluar conocimientos de Java";
            String nivelDificultadE= "Principiante";
            double duracionE = 45.0;
            Date fechaLimiteE = Controller.convertirStringADate("2024-12-31");
            boolean esObligatoriaE = true;

            List<PreguntaAbierta> preguntasE = new ArrayList<>();
            preguntasE.add(new PreguntaAbierta("¿Qué es Java?"));
            preguntasE.add(new PreguntaAbierta("¿Qué significa JVM?"));

            encuestaEsperada = new Encuesta(descripcionE, objetivoE, nivelDificultadE, duracionE, fechaLimiteE, esObligatoriaE, preguntasE);
            
            String descripcionEx = "Examen de Java";
            String objetivoEx = "Evaluar conocimientos avanzados";
            String nivelDificultadEx = "Intermedio";
            double duracionEx = 45.0;
            Date fechaLimEx = Controller.convertirStringADate("2024-12-31");
            boolean obligatoriaEx = true;

            // Questions for the exam
            List<PreguntaAbierta> preguntasEx = new ArrayList<>();
            preguntasEx.add(new PreguntaAbierta("Explique qué es una clase en Java."));
            preguntasEx.add(new PreguntaAbierta("¿Qué es un constructor?"));


            examenEsperado = new Examen(descripcionEx, objetivoEx, nivelDificultadEx, duracionEx, fechaLimEx, obligatoriaEx, preguntasEx);
            
            
    }

    @AfterEach
    void tearDown() {
        // Optional: clear state if necessary
    }

    @Test
    void testCrearQuiz() {
        String inputUsuario = "Quiz de Java\n" +
                "Evaluar conocimientos básicos\n" +
                "Principiante\n" +
                "30\n" +
                "2024-12-31\n" +
                "1\n" +
                "2\n" +
                "70\n" +
                "¿Qué es Java?\n" +
                "4\n" +
                "Compilado\n0\nCorrecto\n0\nInterpretado\n0\nAmbos\n1\n" +
                "¿Qué significa JVM?\n" +
                "4\n" +
                "Java Virtual Machine\n1\n" +
                "Java Variable Method\n0\n" +
                "Just Virtual Memory\n0\n" +
                "Java Verification Module\n0\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputUsuario.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Quiz quizPrueba = Controller.crearQuiz(scanner);

        assertEquals(quiz1.getDescripcion(), quizPrueba.getDescripcion());
        assertEquals(quiz1.getObjetivo(), quizPrueba.getObjetivo());
        assertEquals(quiz1.getNivelDificultad(), quizPrueba.getNivelDificultad());
        assertEquals(quiz1.getDuracion(), quizPrueba.getDuracion());
        assertEquals(quiz1.getFechaLim(), quizPrueba.getFechaLim());
        assertEquals(quiz1.isObligatoria(), quizPrueba.isObligatoria());
        assertEquals(quiz1.getPreguntas().size(), quizPrueba.getPreguntas().size());
        
    }

    @Test
    void testCrearEncuesta() {
        String inputUsuario = "Encuesta sobre Java\n" +
                "Evaluar conocimientos de Java\n" +
                "Principiante\n" +
                "45\n" +
                "2024-12-31\n" +
                "1\n" +
                "2\n" +
                "¿Qué es Java?\n" +
                "¿Qué significa JVM?\n";

        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputUsuario.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Encuesta encuestaPrueba = Controller.crearEncuesta(scanner);

        assertEquals(encuestaEsperada.getDescripcion(), encuestaPrueba.getDescripcion());
        assertEquals(encuestaEsperada.getObjetivo(), encuestaPrueba.getObjetivo());
        assertEquals(encuestaEsperada.getNivelDificultad(), encuestaPrueba.getNivelDificultad());
        assertEquals(encuestaEsperada.getDuracion(), encuestaPrueba.getDuracion());
        assertEquals(encuestaEsperada.getFechaLim(), encuestaPrueba.getFechaLim());
        assertEquals(encuestaEsperada.isObligatoria(), encuestaPrueba.isObligatoria());
        assertEquals(encuestaEsperada.getPreguntas().size(), encuestaPrueba.getPreguntas().size());
    }
    @Test
    void testCrearExamen() {
    	 String inputUsuario = "Examen de Java\n" +
                 "Evaluar conocimientos avanzados\n" +
                 "Intermedio\n" +
                 "45\n" +
                 "2024-12-31\n" +
                 "1\n" +
                 "2\n" +
                 "Explique qué es una clase en Java.\n" +
                 "¿Qué es un constructor?\n";
    	 ByteArrayInputStream inputStream = new ByteArrayInputStream(inputUsuario.getBytes());
    	 Scanner scanner = new Scanner(inputStream);
    	 
    	 
    	 Examen examenPrueba = Controller.crearExamen(scanner);

        assertEquals(examenEsperado.getDescripcion(), examenPrueba.getDescripcion());
        assertEquals(examenEsperado.getObjetivo(), examenPrueba.getObjetivo());
        assertEquals(examenEsperado.getNivelDificultad(), examenPrueba.getNivelDificultad());
        assertEquals(examenEsperado.getDuracion(), examenPrueba.getDuracion());
        assertEquals(examenEsperado.getFechaLim(), examenPrueba.getFechaLim());
        assertEquals(examenEsperado.isObligatoria(), examenPrueba.isObligatoria());
        assertEquals(examenEsperado.getPreguntas().size(), examenPrueba.getPreguntas().size());
    
    }
}

