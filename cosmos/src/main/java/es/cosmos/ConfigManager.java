package es.cosmos;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Gestor de configuración de la aplicación Cosmos.
 * Lee la configuración desde el archivo config.json ubicado en la raíz del proyecto.
 * 
 * @author Cosmos Team
 * @version 1.0
 * @since 2025
 */
public class ConfigManager {
    private static final String CONFIG_FILE = "config.json";
    private static ConfigManager instance;
    private Config config;

    /**
     * Clase interna que representa la estructura de configuración.
     */
    public static class Config {
        @SerializedName("database")
        private DatabaseConfig database;

        /**
         * Obtiene la configuración de la base de datos.
         * 
         * @return Configuración de la base de datos
         */
        public DatabaseConfig getDatabase() {
            return database;
        }

        /**
         * Establece la configuración de la base de datos.
         * 
         * @param database Configuración de la base de datos
         */
        public void setDatabase(DatabaseConfig database) {
            this.database = database;
        }
    }

    /**
     * Clase interna que representa la configuración de la base de datos.
     */
    public static class DatabaseConfig {
        @SerializedName("path")
        private String path;

        /**
         * Obtiene la ruta de la base de datos.
         * 
         * @return Ruta del archivo de base de datos
         */
        public String getPath() {
            return path;
        }

        /**
         * Establece la ruta de la base de datos.
         * 
         * @param path Ruta del archivo de base de datos
         */
        public void setPath(String path) {
            this.path = path;
        }
    }

    /**
     * Constructor privado para patrón Singleton.
     * 
     * @throws IOException Si ocurre un error al leer el archivo de configuración
     */
    private ConfigManager() throws IOException {
        loadConfig();
    }

    /**
     * Obtiene la instancia única del gestor de configuración.
     * 
     * @return Instancia del ConfigManager
     * @throws IOException Si ocurre un error al cargar la configuración
     */
    public static ConfigManager getInstance() throws IOException {
        if (instance == null) {
            instance = new ConfigManager();
        }
        return instance;
    }

    /**
     * Carga la configuración desde el archivo config.json.
     * 
     * @throws IOException Si el archivo no se encuentra o no se puede leer
     */
    private void loadConfig() throws IOException {
        // Buscar el archivo config.json en la raíz del proyecto (cosmos/)
        Path configPath = Paths.get("cosmos", CONFIG_FILE);
        
        // Si no existe, intentar buscar en el directorio actual
        if (!configPath.toFile().exists()) {
            configPath = Paths.get(CONFIG_FILE);
        }
        
        try (FileReader reader = new FileReader(configPath.toFile(), StandardCharsets.UTF_8)) {
            Gson gson = new Gson();
            config = gson.fromJson(reader, Config.class);
            
            System.out.println("Configuración cargada correctamente desde: " + configPath.toAbsolutePath());
        } catch (IOException e) {
            throw new IOException("No se encuentra el archivo de configuración: " + configPath.toAbsolutePath(), e);
        }
    }

    /**
     * Obtiene la configuración actual.
     * 
     * @return Objeto de configuración
     */
    public Config getConfig() {
        return config;
    }

    /**
     * Obtiene la ruta de la base de datos desde la configuración.
     * 
     * @return Ruta del archivo de base de datos
     */
    public String getDatabasePath() {
        return config.getDatabase().getPath();
    }
}
