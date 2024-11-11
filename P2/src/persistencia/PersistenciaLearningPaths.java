package persistencia;

import java.io.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import learningPath.LearningPath;
import usuarios.Profesor;
import usuarios.Usuario;

public class PersistenciaLearningPaths {

    private static final String FILE_NAME = "learningPaths.ser";

    // Save the entire map of learning paths to the file
    public static void guardarLearningPaths(Map<String, LearningPath> mapaLearningPaths) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(mapaLearningPaths);
            System.out.println("Learning paths guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar los learning paths.");
            e.printStackTrace();
        }
    }

    // Load the map of learning paths from the file
    @SuppressWarnings("unchecked")
    public static Map<String, LearningPath> cargarLearningPaths(Map<String, Usuario> usuarios) {
        Map<String, LearningPath> mapaLearningPaths = new HashMap<>();
        File file = new File(FILE_NAME);

        // Check if the file exists; if not, offer to create it
        if (!file.exists()) {
            System.out.println("Archivo de learning paths no encontrado.");
            System.out.println("Desea crear un archivo vacío?\n1. Si\n2. No");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            if (opcion == 1) {
                guardarLearningPaths(mapaLearningPaths); // Save an empty map to create the file
                System.out.println("Archivo de learning paths vacío creado exitosamente: " + FILE_NAME);
            } else {
                System.out.println("No se creará ningún archivo.");
            }
            scanner.close();
            return mapaLearningPaths; // Return an empty map if the file was not created
        }

        // Load existing learning paths from the file
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            mapaLearningPaths = (Map<String, LearningPath>) in.readObject();
            System.out.println("Learning paths cargados exitosamente.");

            // Associate loaded learning paths with their corresponding profesores
            for (Map.Entry<String, LearningPath> entry : mapaLearningPaths.entrySet()) {
                LearningPath learningPath = entry.getValue();
                Usuario correo = learningPath.getProfesor();
                
                Usuario profesor = usuarios.get(correo);
                if (profesor instanceof Profesor) {
                    ((Profesor) profesor).addLearningPath(learningPath);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ocurrió un error al cargar los learning paths.");
            e.printStackTrace();
        }

        return mapaLearningPaths;
    }

    // Add a single learning path to the map and save the updated map to the file
    public static void agregarLearningPath(LearningPath learningPath, String correo, Map<String, Usuario> usuarios) {
        // Load existing learning paths from the file
        Map<String, LearningPath> mapaLearningPaths = cargarLearningPaths(usuarios);

        // Associate the learning path with the professor
        Usuario profesor = usuarios.get(correo);
        if (profesor instanceof Profesor) {
            ((Profesor) profesor).addLearningPath(learningPath);
            learningPath.setProfesor(profesor);
            mapaLearningPaths.put(learningPath.getTitulo(), learningPath);

            // Save the updated map back to the file
            guardarLearningPaths(mapaLearningPaths);
            System.out.println("Learning path añadido y guardado exitosamente.");
        } else {
            System.out.println("El usuario con el correo proporcionado no es un profesor.");
        }
    }

    // Utility method to convert a String date to LocalDate
    public static LocalDate convertirFecha(String fecha) {
        return LocalDate.parse(fecha);
    }
}