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
     * Inicializa la base de datos, crea las tablas necesarias automáticamente,
     * inserta datos de ejemplo y consulta los registros.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        System.out.println("Hello cruel world!");
        
        try {
            // Cargar configuración desde config.json
            ConfigManager configManager = ConfigManager.getInstance();
            String dbPath = configManager.getDatabasePath();
            
            // Crear gestor de base de datos con la ruta del config
            DatabaseManager dbManager = new DatabaseManager(dbPath);
            
            // Conectar a la base de datos
            dbManager.connect();
            
            // Inicializar base de datos (crear tablas si no existen)
            dbManager.initializeDatabase();
            
            // Insertar tripulantes
            dbManager.insertTripulante("James T. Kirk", "Capitán");
            dbManager.insertTripulante("Spock", "Comandante");
            dbManager.insertTripulante("Leonard McCoy", "Doctor");
            dbManager.insertTripulante("Montgomery Scott", "Ingeniero Jefe");
            dbManager.insertPlayer("Tony Stark");
            
            // Consultar y mostrar los tripulantes
            dbManager.queryTripulantes();
            dbManager.queryPlayers();
            
            // Cerrar la conexión
            dbManager.disconnect();
            
        } catch (Exception e) {
            System.err.println("Error al trabajar con la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }
}