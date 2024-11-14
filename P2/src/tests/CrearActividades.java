package tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import actividades.Encuesta;
import actividades.Opcion;
import actividades.PreguntaAbierta;
import actividades.PreguntaCerrada;
import actividades.Quiz;
import core.Controller;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class  CrearActividades{

    private Quiz quiz1;
    private Encuesta encuesta1;

    @BeforeEach
    void setUp(TestInfo testInfo) {
        if (testInfo.getDisplayName().equals("testCrearQuiz")) {
            String descripcion = "Quiz de Java";
            String objetivo = "Evaluar conocimientos básicos";
            String nivelDificultad = "Principiante";
            int duracion = 30;
            Date fechaLimite = Controller.convertirStringADate("2024-12-31");
            double calificacionMin = 70.0;
            boolean esObligatoria = true;

            Quiz quiz1 = new Quiz(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, calificacionMin, esObligatoria);

           
            String explicacion1 = "¿Qué es Java?";
            List<Opcion> opciones1 = new ArrayList<>();
            opciones1.add(new Opcion("Compilado"));
            opciones1.add(new Opcion("Correcto"));
            opciones1.add(new Opcion("Interpretado"));
            opciones1.add(new Opcion("Ambos"));
            Opcion opcionCorrecta1 = opciones1.get(3); 
            PreguntaCerrada pregunta1 = new PreguntaCerrada(explicacion1, opciones1, opcionCorrecta1);
            quiz1.addPregunta(pregunta1);

            // Question 2
            String explicacion2 = "¿Qué significa JVM?";
            List<Opcion> opciones2 = new ArrayList<>();
            opciones2.add(new Opcion("Java Virtual Machine"));
            opciones2.add(new Opcion("Java Variable Method"));
            opciones2.add(new Opcion("Just Virtual Memory"));
            opciones2.add(new Opcion("Java Verification Module"));
            Opcion opcionCorrecta2 = opciones2.get(0); 
            PreguntaCerrada pregunta2 = new PreguntaCerrada(explicacion2, opciones2, opcionCorrecta2);
            quiz1.addPregunta(pregunta2);

        } else if (testInfo.getDisplayName().equals("testCrearEncuesta")){
        	String descripcion = "Encuesta sobre Java";
            String objetivo = "Evaluar conocimientos de Java";
            String nivelDificultad = "Principiante";
            double duracion = 45.0;
            Date fechaLimite = Controller.convertirStringADate("2024-12-31");
            boolean esObligatoria = true;

            // Step 2: Create a list of PreguntaAbierta objects for the survey questions
            List<PreguntaAbierta> preguntas = new ArrayList<>();
            preguntas.add(new PreguntaAbierta("¿Qué es Java?"));
            preguntas.add(new PreguntaAbierta("¿Qué significa JVM?"));

            // Step 3: Construct the Encuesta object using the fields defined above
            Encuesta encuesta1 = new Encuesta(descripcion, objetivo, nivelDificultad, duracion, fechaLimite, esObligatoria, preguntas);

           
        }
    }

    @AfterEach
    void tearDown() {
        System.out.println("AfterEach: Test completed.");
    }

    @Test
    void testCrearQuiz() {
        String inputUsuario = "Quiz de Java\n" +     // descripcion
                              "Evaluar conocimientos básicos\n" + // objetivo
                              "Principiante\n" +                 // nivelDificultad
                              "30\n" +                           // duracion (in minutes)
                              "2024-12-31\n" +                   // fechaLim (yyyy-mm-dd)
                              "1\n" +                            // es obligatoria (1 for true)
                              "2\n" +                            // cantidadPreguntas
                              "70\n" +                           // minimoQuiz
                              "¿Qué es Java?\n" +                // explicacion for question 1
                              "4\n" +                            // cantidadOpciones for question 1
                              "Compilado\nCorrecto\nInterpretado\nAmbos\n" + // options
                              "3\n" +                            // correct answer index for question 1
                              "¿Qué significa JVM?\n" +          // explicacion for question 2
                              "4\n" +                            // cantidadOpciones for question 2
                              "Java Virtual Machine\n" +
                              "Java Variable Method\n" +
                              "Just Virtual Memory\n" +
                              "Java Verification Module\n" +
                              "1\n";                             // correct answer index for question 2
        
        ByteArrayInputStream inputStream = new ByteArrayInputStream(inputUsuario.getBytes());
        Scanner scanner = new Scanner(inputStream);

        Quiz quizPrueba = Controller.crearQuiz(scanner);
        
        //AssertEquals de todo
        assertEquals(quiz1.getDescripcion(), quizPrueba.getDescripcion());
        assertEquals(quiz1.getObjetivo(), quizPrueba.getObjetivo());
        assertEquals(quiz1.getNivelDificultad(), quizPrueba.getNivelDificultad());
        assertEquals(quiz1.getDuracion(), quizPrueba.getDuracion());
        assertEquals(quiz1.getFechaLim(), quizPrueba.getFechaLim());
        assertEquals(quiz1.isObligatoria(), quizPrueba.isObligatoria());
        assertEquals(quiz1.getTipo(), quizPrueba.getTipo());
        assertEquals(quiz1.getPreguntas().size(), quizPrueba.getPreguntas().size());
 
	}
    void 
}

