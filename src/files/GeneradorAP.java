package files;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GeneradorAP {

    private final List<String> ubicaciones;
    private final Random random;

    /**
     * Crea un generador de puntos de acceso.
     *
     * @param ubicaciones lugares que se pueden asignar a los AP
     * @param random generador de números aleatorios
     */
    public GeneradorAP(List<String> ubicaciones, Random random) {
        Objects.requireNonNull(ubicaciones, "La lista de ubicaciones no puede ser null");
        this.random = Objects.requireNonNull(random, "Random no puede ser null");

        if (ubicaciones.isEmpty()) {
            throw new IllegalArgumentException("Debe existir al menos una ubicación");
        }

        this.ubicaciones = new ArrayList<>(ubicaciones);
    }

    /**
     * Genera los AP, los guarda en un archivo y devuelve sus identificadores.
     *
     * @param archivo ruta del archivo aps.csv
     * @param cantidad número de AP que se crearán
     * @return identificadores de los AP generados
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public List<String> generar(Path archivo, int cantidad) throws IOException {
        Objects.requireNonNull(archivo, "La ruta del archivo no puede ser null");

        if (cantidad < 0) {
            throw new IllegalArgumentException("La cantidad de AP no puede ser negativa");
        }

        List<String> identificadores = new ArrayList<>();

        try (BufferedWriter writer = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8)) {

            for (int numero = 1; numero <= cantidad; numero++) {
                String identificador = crearIdentificador(numero);
                String ubicacion = elegirUbicacion();

                writer.write(identificador + ";" + ubicacion);
                writer.newLine();
                identificadores.add(identificador);
            }
        }

        return identificadores;
    }

    /**
     * Construye identificadores como AP01, AP02 y AP03.
     */
    private String crearIdentificador(int numero) {
        return String.format("AP%02d", numero);
    }

    /**
     * Selecciona aleatoriamente una ubicación disponible.
     */
    private String elegirUbicacion() {
        int posicion = random.nextInt(ubicaciones.size());
        return ubicaciones.get(posicion);
    }
}
