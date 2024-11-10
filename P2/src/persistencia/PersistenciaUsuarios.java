package persistencia;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import usuarios.Estudiante;
import usuarios.Profesor;
import usuarios.Usuario;

public class PersistenciaUsuarios {
    
    public static void guardarUsuarioEnCSV(String nombre, String correo, String password, String tipoUsuario) {
        try (FileWriter writer = new FileWriter("usuarios.csv", true)) {
      
            writer.append(nombre).append(",").append(correo).append(",").append(password).append(",").append(tipoUsuario).append("\n");
        } catch (IOException e) {
            System.out.println("Ocurrió un error al guardar el usuario.");
            e.printStackTrace();
        }
    }

    public static Map<String, Usuario> cargarUsuariosDeCSV() {
        Map<String, Usuario> usuarios = new HashMap<>();
        try (BufferedReader reader = new BufferedReader(new FileReader("usuarios.csv"))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
  
                String[] datos = linea.split(",");
                String nombre = datos[0];
                String correo = datos[1];
                String password = datos[2];
                String tipoUsuario = datos[3];

   
                if (tipoUsuario.equals("Estudiante")) {
                    usuarios.put(correo, new Estudiante(nombre, correo, password));
                } else if (tipoUsuario.equals("Profesor")) {
                    usuarios.put(correo, new Profesor(nombre, correo, password));
                }
            }
        } catch (IOException e) {
			System.out.println("Ocurrió un error al cargar los usuarios.");
	        System.out.println("Desea crear un archivo?\n1. Si\n2. No");
	        Scanner scanner = new Scanner(System.in);
	        int opcion = scanner.nextInt();
	        scanner.nextLine(); // Clear the newline character

	        if (opcion == 1) {
	            // Create an empty CSV file
	            String fileName = "usuarios.csv";
	            try (FileWriter writer = new FileWriter(fileName)) {
	                System.out.println("Archivo CSV vacío creado exitosamente: " + fileName);
	            } catch (IOException e1) {
	                System.out.println("Error al crear el archivo CSV.");
	                e.printStackTrace();
	            }
	        } else {
	            System.out.println("No se creará ningún archivo.");
	        }

	        scanner.close();
	    }
     
        return usuarios;
    }
}
