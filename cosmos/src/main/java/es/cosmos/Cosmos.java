package es.cosmos;

/**
 * Clase principal de la aplicación Cosmos.
 * Gestiona operaciones con base de datos SQLite para almacenar información de tripulantes.
 * 
 * @author Cosmos Team
 * @version 1.0
 * @since 2025
 */
public class Cosmos {
    /**
     * Método principal de la aplicación.
     * Inicializa la base de datos, crea la tabla de tripulantes,
     * inserta datos de ejemplo y consulta los registros.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Hello cruel world!");
        
        // Ejemplo de uso de SQLite
        DatabaseManager dbManager = new DatabaseManager("cosmos.db");
        
        try {
            // Conectar a la base de datos
            dbManager.connect();
            
            // Crear tabla tripulantes
            dbManager.createTripulantesTable();
            
            // Insertar tripulantes
            dbManager.insertTripulante("James T. Kirk", "Capitán");
            dbManager.insertTripulante("Spock", "Comandante");
            dbManager.insertTripulante("Leonard McCoy", "Doctor");
            dbManager.insertTripulante("Montgomery Scott", "Ingeniero Jefe");
            
            // Consultar y mostrar los tripulantes
            dbManager.queryTripulantes();
            
            // Cerrar la conexión
            dbManager.disconnect();
            
        } catch (Exception e) {
            System.err.println("Error al trabajar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}