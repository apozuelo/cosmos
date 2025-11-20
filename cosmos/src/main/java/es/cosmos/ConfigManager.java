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
        
        @SerializedName("crew")
        private CrewConfig crew;
        
        @SerializedName("starship")
        private StarshipConfig starship;

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
        
        /**
         * Obtiene la configuración de crew.
         * 
         * @return Configuración de crew
         */
        public CrewConfig getCrew() {
            return crew;
        }
        
        /**
         * Establece la configuración de crew.
         * 
         * @param crew Configuración de crew
         */
        public void setCrew(CrewConfig crew) {
            this.crew = crew;
        }
        
        /**
         * Obtiene la configuración de starship.
         * 
         * @return Configuración de starship
         */
        public StarshipConfig getStarship() {
            return starship;
        }
        
        /**
         * Establece la configuración de starship.
         * 
         * @param starship Configuración de starship
         */
        public void setStarship(StarshipConfig starship) {
            this.starship = starship;
        }
    }

    /**
     * Clase interna que representa la configuración de la base de datos.
     */
    public static class DatabaseConfig {
        @SerializedName("pathdb")
        private String pathdb;

        /**
         * Obtiene la ruta de la base de datos.
         * 
         * @return Ruta del archivo de base de datos
         */
        public String getPath() {
            return pathdb;
        }

        /**
         * Establece la ruta de la base de datos.
         * 
         * @param path Ruta del archivo de base de datos
         */
        public void setPath(String path) {
            this.pathdb = path;
        }
    }

    /**
     * Clase interna que representa la configuración de crew.
     */
    public static class CrewConfig {
        @SerializedName("pathcrew")
        private String pathcrew;

        /**
         * Obtiene la ruta del archivo crew.
         * 
         * @return Ruta del archivo crew
         */
        public String getPath() {
            return pathcrew;
        }

        /**
         * Establece la ruta del archivo crew.
         * 
         * @param path Ruta del archivo crew
         */
        public void setPath(String path) {
            this.pathcrew = path;
        }
    }

    /**
     * Clase interna que representa la configuración de starship.
     */
    public static class StarshipConfig {
        @SerializedName("pathstarship")
        private String pathstarship;

        /**
         * Obtiene la ruta del archivo starship.
         * 
         * @return Ruta del archivo starship
         */
        public String getPath() {
            return pathstarship;
        }

        /**
         * Establece la ruta del archivo starship.
         * 
         * @param path Ruta del archivo starship
         */
        public void setPath(String path) {
            this.pathstarship = path;
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
    
    /**
     * Obtiene la ruta del archivo crew desde la configuración.
     * 
     * @return Ruta del archivo crew
     */
    public String getCrewPath() {
        return config.getCrew().getPath();
    }
    
    /**
     * Obtiene la ruta del archivo starship desde la configuración.
     * 
     * @return Ruta del archivo starship
     */
    public String getStarshipPath() {
        return config.getStarship().getPath();
    }
}
