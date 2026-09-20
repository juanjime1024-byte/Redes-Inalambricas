package services;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Clase encargada de analizar las conexiones de la red
 * y determinar la cantidad de dispositivos únicos
 * asociados a cada Access Point.
 *
 * Persona 3 - Procesamiento y conteo de dispositivos únicos.
 */
public class AnalizadorRed {

    // Almacena cada AP junto con sus direcciones MAC únicas
    private final Map<String, Set<String>> dispositivosPorAP;

    /**
     * Constructor del analizador.
     */
    public AnalizadorRed() {
        dispositivosPorAP = new HashMap<>();
    }

    /**
     * Registra una conexión entre un Access Point y una MAC.
     * Si la MAC ya existe para ese AP, no se vuelve a contar.
     *
     * @param accessPoint identificador del Access Point
     * @param mac dirección MAC del dispositivo
     */
    public void registrarConexion(String accessPoint, String mac) {

        if (accessPoint == null || mac == null) {
            return;
        }

        accessPoint = accessPoint.trim();
        mac = mac.trim().toUpperCase();

        if (accessPoint.isEmpty() || mac.isEmpty()) {
            return;
        }

        dispositivosPorAP
                .computeIfAbsent(accessPoint, clave -> new HashSet<>())
                .add(mac);
    }

    /**
     * Obtiene la cantidad de dispositivos únicos
     * registrados para un Access Point.
     *
     * @param accessPoint identificador del Access Point
     * @return cantidad de dispositivos únicos
     */
    public int obtenerCantidadDispositivos(String accessPoint) {

        Set<String> dispositivos = dispositivosPorAP.get(accessPoint);

        if (dispositivos == null) {
            return 0;
        }

        return dispositivos.size();
    }

    /**
     * Obtiene el resultado completo del análisis.
     *
     * @return mapa con cada AP y su cantidad de dispositivos únicos
     */
    public Map<String, Integer> obtenerResultados() {

        Map<String, Integer> resultados = new HashMap<>();

        for (Map.Entry<String, Set<String>> entrada
                : dispositivosPorAP.entrySet()) {

            resultados.put(
                    entrada.getKey(),
                    entrada.getValue().size()
            );
        }

        return resultados;
    }

    /**
     * Verifica si una dirección MAC ya fue registrada
     * en un Access Point determinado.
     *
     * @param accessPoint identificador del AP
     * @param mac dirección MAC
     * @return true si ya existe, false en caso contrario
     */
    public boolean existeDispositivo(String accessPoint, String mac) {

        if (accessPoint == null || mac == null) {
            return false;
        }

        Set<String> dispositivos = dispositivosPorAP.get(accessPoint.trim());

        return dispositivos != null
                && dispositivos.contains(mac.trim().toUpperCase());
    }

    /**
     * Limpia todos los resultados almacenados.
     */
    public void limpiarResultados() {
        dispositivosPorAP.clear();
    }
}
