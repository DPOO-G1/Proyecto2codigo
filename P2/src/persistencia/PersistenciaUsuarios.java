package persistencia;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class PersistenciaUsuarios {

    private static final String FILE_NAME = "usuarios.ser";

    public static void guardarUsuarios(Map<String, Usuario> usuarios) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(usuarios);
            System.out.println("Usuarios guardados exitosamente.");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar los usuarios.");
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    public static Map<String, Usuario> cargarUsuarios() {
        Map<String, Usuario> usuarios = new HashMap<>();
        
        File file = new File(FILE_NAME);
        if (!file.exists()) {
            System.out.println("Archivo de usuarios no encontrado.");
            System.out.println("Desea crear un archivo vacío?\n1. Si\n2. No");
            Scanner scanner = new Scanner(System.in);
            int opcion = scanner.nextInt();
            scanner.nextLine(); // Clear the newline character

            if (opcion == 1) {
               
                guardarUsuarios(usuarios);
                System.out.println("Archivo de usuarios vacío creado exitosamente: " + FILE_NAME);
            } else {
                System.out.println("No se creará ningún archivo.");
            }

            scanner.close();
            return usuarios;
        }

        
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            usuarios = (Map<String, Usuario>) in.readObject();
            System.out.println("Usuarios cargados exitosamente.");
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Ocurrió un error al cargar los usuarios.");
            e.printStackTrace();
        }
        
        return usuarios;
    }

    // Optional: method to add a single user and save the entire map
    public static void agregarUsuario(String nombre, String correo, String password, String tipoUsuario, Map<String, Usuario> usuarios) {
        Usuario usuario;
        if (tipoUsuario.equals("Estudiante")) {
            usuario = new Estudiante(nombre, correo, password);
        } else if (tipoUsuario.equals("Profesor")) {
            usuario = new Profesor(nombre, correo, password);
        } else {
            System.out.println("Tipo de usuario no reconocido.");
            return;
        }
        usuarios.put(correo, usuario);
        guardarUsuarios(usuarios); // Save the updated map
    }
}