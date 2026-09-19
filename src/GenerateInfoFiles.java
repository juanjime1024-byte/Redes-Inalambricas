import files.GeneradorAP;
import files.GeneradorConexiones;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Random;

public class GenerateInfoFiles {

    private static final int CANTIDAD_AP = 8;
    private static final int CANTIDAD_CONEXIONES = 50;

    public static void main(String[] args) {
        Path archivoAP = Path.of("aps.csv");
        Path archivoConexiones = Path.of("conexiones.txt");

        List<String> ubicaciones = List.of(
                "Biblioteca",
                "Cafetería",
                "Laboratorio",
                "Sala de estudio",
                "Auditorio",
                "Bloque administrativo",
                "Cafetería central",
                "Zona deportiva");

        Random random = new Random();

        GeneradorAP generadorAP = new GeneradorAP(ubicaciones, random);
        GeneradorConexiones generadorConexiones =
                new GeneradorConexiones(random);

        try {
            List<String> identificadoresAP =
                    generadorAP.generar(archivoAP, CANTIDAD_AP);

            generadorConexiones.generar(
                    archivoConexiones,
                    identificadoresAP,
                    CANTIDAD_CONEXIONES);

            System.out.println("Archivos generados correctamente:");
            System.out.println("- " + archivoAP.toAbsolutePath());
            System.out.println("- " + archivoConexiones.toAbsolutePath());
        } catch (IOException | IllegalArgumentException error) {
            System.err.println(
                    "No fue posible generar los archivos: " + error.getMessage());
        }
    }
}
