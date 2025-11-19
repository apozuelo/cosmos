package es.cosmos;

import java.sql.*;

/**
 * Gestor de base de datos SQLite para la aplicación Cosmos.
 * Proporciona métodos para conectar, desconectar, crear tablas y gestionar datos.
 * 
 * @author Cosmos Team
 * @version 1.0
 * @since 2025
 */
public class DatabaseManager {
    /** Conexión activa a la base de datos SQLite */
    private Connection connection;
    
    /** Ruta al archivo de base de datos */
    private final String dbPath;

    /**
     * Constructor del gestor de base de datos.
     * 
     * @param dbPath Ruta al archivo de base de datos SQLite
     */
    public DatabaseManager(String dbPath) {
        this.dbPath = dbPath;
    }

    /**
     * Establece la conexión con la base de datos SQLite.
     * Si el archivo no existe, SQLite lo creará automáticamente.
     * 
     * @throws SQLException Si ocurre un error al conectar con la base de datos
     */
    public void connect() throws SQLException {
        String url = "jdbc:sqlite:" + dbPath;
        connection = DriverManager.getConnection(url);
        System.out.println("Conexión a SQLite establecida.");
    }

    /**
     * Inicializa la base de datos creando todas las tablas necesarias.
     * Este método debe ser llamado después de establecer la conexión.
     * 
     * @throws SQLException Si ocurre un error al crear las tablas
     */
    public void initializeDatabase() throws SQLException {
        createTripulantesTable();
        createPlayerTable();
        System.out.println("Base de datos inicializada con todas las tablas.");
    }

    /**
     * Cierra la conexión con la base de datos SQLite.
     * Verifica si la conexión está activa antes de cerrarla.
     * 
     * @throws SQLException Si ocurre un error al cerrar la conexión
     */
    public void disconnect() throws SQLException {
        if (connection != null && !connection.isClosed()) {
            connection.close();
            System.out.println("Conexión a SQLite cerrada.");
        }
    }

    /**
     * Crea una tabla genérica con columnas id, nombre y descripción.
     * Si la tabla ya existe, no realiza ninguna acción.
     * 
     * @param tableName Nombre de la tabla a crear
     * @throws SQLException Si ocurre un error al crear la tabla
     */
    public void createTable(String tableName) throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS " + tableName + " (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombre TEXT NOT NULL,\n"
                + " descripcion TEXT\n"
                + ");";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla '" + tableName + "' creada o ya existe.");
        }
    }

    /**
     * Crea la tabla 'tripulantes' con columnas id, nombre y graduación.
     * Si la tabla ya existe, no realiza ninguna acción.
     * 
     * @throws SQLException Si ocurre un error al crear la tabla
     */
    public void createTripulantesTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS tripulantes (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombre TEXT NOT NULL,\n"
                + " graduacion TEXT NOT NULL\n"
                + ");";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'tripulantes' creada o ya existe.");
        }
    }

    /**
     * Crea la tabla 'player' con columnas id y nombre.
     * Si la tabla ya existe, no realiza ninguna acción.
     * 
     * @throws SQLException Si ocurre un error al crear la tabla
     */
    public void createPlayerTable() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS player (\n"
                + " id INTEGER PRIMARY KEY AUTOINCREMENT,\n"
                + " nombre TEXT NOT NULL\n"
                + ");";
        
        try (Statement stmt = connection.createStatement()) {
            stmt.execute(sql);
            System.out.println("Tabla 'player' creada o ya existe.");
        }
    }

    /**
     * Inserta datos en una tabla genérica.
     * Utiliza PreparedStatement para prevenir inyección SQL.
     * 
     * @param tableName Nombre de la tabla donde insertar los datos
     * @param nombre Valor para la columna 'nombre'
     * @param descripcion Valor para la columna 'descripcion'
     * @throws SQLException Si ocurre un error al insertar los datos
     */
    public void insertData(String tableName, String nombre, String descripcion) throws SQLException {
        String sql = "INSERT INTO " + tableName + " (nombre, descripcion) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, descripcion);
            pstmt.executeUpdate();
            System.out.println("Datos insertados en '" + tableName + "'.");
        }
    }

    /**
     * Inserta un nuevo tripulante en la tabla 'tripulantes'.
     * Utiliza PreparedStatement para prevenir inyección SQL.
     * 
     * @param nombre Nombre del tripulante
     * @param graduacion Graduación o rango del tripulante
     * @throws SQLException Si ocurre un error al insertar el tripulante
     */
    public void insertTripulante(String nombre, String graduacion) throws SQLException {
        String sql = "INSERT INTO tripulantes (nombre, graduacion) VALUES (?, ?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.setString(2, graduacion);
            pstmt.executeUpdate();
            System.out.println("Tripulante insertado: " + nombre + " - " + graduacion);
        }
    }

    /**
     * Inserta un nuevo jugador en la tabla 'player'.
     * Utiliza PreparedStatement para prevenir inyección SQL.
     * 
     * @param nombre Nombre del jugador
     * @throws SQLException Si ocurre un error al insertar el jugador
     */
    public void insertPlayer(String nombre) throws SQLException {
        String sql = "INSERT INTO player (nombre) VALUES (?)";
        
        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setString(1, nombre);
            pstmt.executeUpdate();
            System.out.println("Jugador insertado: " + nombre);
        }
    }

    /**
     * Consulta y muestra todos los datos de una tabla genérica.
     * Imprime los resultados en la consola.
     * 
     * @param tableName Nombre de la tabla a consultar
     * @throws SQLException Si ocurre un error al consultar los datos
     */
    public void queryData(String tableName) throws SQLException {
        String sql = "SELECT * FROM " + tableName;
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nDatos de la tabla '" + tableName + "':");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 ", Nombre: " + rs.getString("nombre") + 
                                 ", Descripción: " + rs.getString("descripcion"));
            }
        }
    }

    /**
     * Consulta y muestra todos los tripulantes registrados.
     * Imprime el ID, nombre y graduación de cada tripulante en la consola.
     * 
     * @throws SQLException Si ocurre un error al consultar los tripulantes
     */
    public void queryTripulantes() throws SQLException {
        String sql = "SELECT * FROM tripulantes";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nTripulantes registrados:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 ", Nombre: " + rs.getString("nombre") + 
                                 ", Graduación: " + rs.getString("graduacion"));
            }
        }
    }

    /**
     * Consulta y muestra todos los jugadores registrados.
     * Imprime el ID y nombre de cada jugador en la consola.
     * 
     * @throws SQLException Si ocurre un error al consultar los jugadores
     */
    public void queryPlayers() throws SQLException {
        String sql = "SELECT * FROM player";
        
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            
            System.out.println("\nJugadores registrados:");
            while (rs.next()) {
                System.out.println("ID: " + rs.getInt("id") + 
                                 ", Nombre: " + rs.getString("nombre"));
            }
        }
    }

    /**
     * Obtiene la conexión actual a la base de datos.
     * 
     * @return La conexión activa a la base de datos SQLite
     */
    public Connection getConnection() {
        return connection;
    }
}
