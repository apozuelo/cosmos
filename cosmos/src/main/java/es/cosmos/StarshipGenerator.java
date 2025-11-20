package es.cosmos;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.Random;

/**
 * Generador de naves estelares para la aplicación Cosmos.
 * Genera un archivo XML con 90 naves distribuidas entre tres facciones.
 * 
 * @author Cosmos Team
 * @version 1.0
 * @since 2025
 */
public class StarshipGenerator {

    // Nombres de naves de la Federación
    private static final String[] NOMBRES_FEDERACION = {
        "Enterprise", "Voyager", "Defiant", "Discovery", "Reliant", 
        "Excalibur", "Constellation", "Yamato", "Phoenix", "Prometheus",
        "Equinox", "Titan", "Aurora", "Odyssey", "Sovereign"
    };
    
    // Nombres de naves Romulanas
    private static final String[] NOMBRES_ROMULANOS = {
        "Valdore", "Devoras", "Khazara", "Algeron", "Terix",
        "Haakona", "Dividices", "Makar", "D'deridex", "Mogai",
        "Norexan", "Praetus", "Belak", "Keras", "Talvath"
    };
    
    // Nombres de naves Klingon
    private static final String[] NOMBRES_KLINGON = {
        "Pagh", "Bortas", "Hegh'ta", "Rotarran", "Ch'Tang",
        "Korinar", "Maht-H'a", "K'mpec", "Kronos One", "Negh'Var",
        "Buruk", "Drovana", "Gr'oth", "Klothos", "Somraw"
    };
    
    // Prefijos para identificadores
    private static final String[] PREFIJOS_FEDERACION = {"NCC", "NX"};
    private static final String PREFIJO_ROMULANO = "IRW";
    private static final String PREFIJO_KLINGON = "IKS";

    /**
     * Método principal que genera el archivo XML de naves.
     * 
     * @param args Argumentos de línea de comandos (no utilizados)
     */
    public static void main(String[] args) {
        try {
            // Cargar configuración
            ConfigManager configManager = ConfigManager.getInstance();
            String starshipPath = configManager.getStarshipPath();
            
            // Crear el documento vacío
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // Crear el elemento raíz <starships>
            Element rootElement = doc.createElement("starships");
            doc.appendChild(rootElement);

            Random random = new Random();

            // Generar 30 naves de la Federación
            generarNavesFactcion(doc, rootElement, "Federacion Unida de Planetas", 
                                NOMBRES_FEDERACION, PREFIJOS_FEDERACION, 30, random);
            
            // Generar 30 naves del Imperio Romulano
            generarNavesFactcion(doc, rootElement, "Imperio Estelar Romulano", 
                                NOMBRES_ROMULANOS, new String[]{PREFIJO_ROMULANO}, 30, random);
            
            // Generar 30 naves del Imperio Klingon
            generarNavesFactcion(doc, rootElement, "Imperio Klingon", 
                                NOMBRES_KLINGON, new String[]{PREFIJO_KLINGON}, 30, random);

            // Escribir el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Formato bonito (indentación)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(starshipPath));

            transformer.transform(source, result);

            System.out.println("¡Archivo " + starshipPath + " generado con éxito con 90 naves!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Genera naves para una facción específica.
     * 
     * @param doc Documento XML
     * @param rootElement Elemento raíz del documento
     * @param faccion Nombre de la facción
     * @param nombres Array de nombres de naves
     * @param prefijos Array de prefijos para identificadores
     * @param cantidad Cantidad de naves a generar
     * @param random Generador de números aleatorios
     */
    private static void generarNavesFactcion(Document doc, Element rootElement, String faccion,
                                            String[] nombres, String[] prefijos, int cantidad, Random random) {
        for (int i = 0; i < cantidad; i++) {
            // Crear elemento <starship>
            Element starship = doc.createElement("starship");
            
            // Generar identificador único
            String prefijo = prefijos[random.nextInt(prefijos.length)];
            String numeroRegistro = String.format("%05d", random.nextInt(100000));
            String identificador = prefijo + "-" + numeroRegistro;
            
            // Generar nombre (nombre base + sufijo aleatorio si es necesario)
            String nombreBase = nombres[random.nextInt(nombres.length)];
            String sufijo = random.nextInt(100) < 30 ? "-" + (char)('A' + random.nextInt(26)) : "";
            String nombreNave = nombreBase + sufijo;
            
            // Añadir elementos hijo
            starship.appendChild(crearElemento(doc, "id", identificador));
            starship.appendChild(crearElemento(doc, "nombre", nombreNave));
            starship.appendChild(crearElemento(doc, "faccion", faccion));
            
            // Añadir la nave a la raíz
            rootElement.appendChild(starship);
        }
    }

    /**
     * Método auxiliar para crear elementos simples con texto.
     * 
     * @param doc Documento XML
     * @param etiqueta Nombre de la etiqueta del elemento
     * @param valor Valor de texto del elemento
     * @return Elemento creado
     */
    private static Element crearElemento(Document doc, String etiqueta, String valor) {
        Element node = doc.createElement(etiqueta);
        node.appendChild(doc.createTextNode(valor));
        return node;
    }
}
