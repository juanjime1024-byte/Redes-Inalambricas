package files;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class GeneradorConexiones {

    private final Random random;

    /**
     * Crea un generador de conexiones.
     *
     * @param random generador de números aleatorios
     */
    public GeneradorConexiones(Random random) {
        this.random = Objects.requireNonNull(random, "Random no puede ser null");
    }

    /**
     * Genera conexiones y las guarda en un archivo de texto.
     * Cada quinta conexión repite la conexión anterior para incluir datos
     * duplicados que permitan probar el conteo de dispositivos únicos.
     *
     * @param archivo ruta del archivo conexiones.txt
     * @param identificadoresAP identificadores de los AP existentes
     * @param cantidad número de conexiones que se crearán
     * @throws IOException si ocurre un error al escribir el archivo
     */
    public void generar(
            Path archivo,
            List<String> identificadoresAP,
            int cantidad)
            throws IOException {

        Objects.requireNonNull(archivo, "La ruta del archivo no puede ser null");
        Objects.requireNonNull(
                identificadoresAP,
                "La lista de identificadores no puede ser null");

        if (cantidad < 0) {
            throw new IllegalArgumentException(
                    "La cantidad de conexiones no puede ser negativa");
        }

        if (cantidad > 0 && identificadoresAP.isEmpty()) {
            throw new IllegalArgumentException(
                    "No se pueden crear conexiones sin puntos de acceso");
        }

        String apAnterior = null;
        String macAnterior = null;

        try (BufferedWriter writer = Files.newBufferedWriter(
                archivo,
                StandardCharsets.UTF_8)) {

            for (int numero = 1; numero <= cantidad; numero++) {
                String identificadorAP;
                String mac;

                if (numero > 1 && numero % 5 == 0) {
                    identificadorAP = apAnterior;
                    mac = macAnterior;
                } else {
                    identificadorAP = elegirAP(identificadoresAP);
                    mac = generarMac();
                }

                writer.write(identificadorAP + ";" + mac);
                writer.newLine();

                apAnterior = identificadorAP;
                macAnterior = mac;
            }
        }
    }

    /**
     * Selecciona aleatoriamente uno de los AP existentes.
     */
    private String elegirAP(List<String> identificadoresAP) {
        int posicion = random.nextInt(identificadoresAP.size());
        return identificadoresAP.get(posicion);
    }

    /**
     * Genera una dirección MAC formada por seis bloques hexadecimales.
     */
    private String generarMac() {
        StringBuilder mac = new StringBuilder();

        for (int bloque = 0; bloque < 6; bloque++) {
            int valor = random.nextInt(256);
            mac.append(String.format("%02X", valor));

            if (bloque < 5) {
                mac.append(":");
            }
        }

        return mac.toString();
    }
}
