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
import java.util.UUID;

public class CrewGenerator {

    // Bancos de datos para aleatoriedad
    private static final String[] NOMBRES = {"James", "Jean-Luc", "Nyota", "Spock", "Hikaru", "Geordi", "Deanna", "William", "Kathryn", "Seven", "Pavel", "Montgomery", "Leonard", "Beverly"};
    private static final String[] APELLIDOS = {"Kirk", "Picard", "Uhura", "Sulu", "La Forge", "Troi", "Riker", "Janeway", "of Nine", "Chekov", "Scott", "McCoy", "Crusher", "Worf"};
    private static final String[] RANGOS = {"Almirante","Capitán", "Comandante", "Teniente Comandante", "Teniente", "Alférez", "Suboficial", "Cadete"};

    public static void main(String[] args) {
        try {
            // Cargar configuración
            ConfigManager configManager = ConfigManager.getInstance();
            String crewPath = configManager.getCrewPath();
            
            // 1. Crear el documento vacío
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.newDocument();

            // 2. Crear el elemento raíz <crew>
            Element rootElement = doc.createElement("crew");
            doc.appendChild(rootElement);

            Random random = new Random();

            // 3. Bucle para generar 1000 tripulantes
            for (int i = 0; i < 1000; i++) {
                
                // Crear elemento <crewmember>
                Element crewmember = doc.createElement("crewmember");
                
                // --- Generar Datos Aleatorios ---
                
                // ID Único (Usamos UUID para garantizar que sea único)
                String idValor = UUID.randomUUID().toString().substring(0, 8).toUpperCase(); 
                
                // Nombre (Nombre + Apellido aleatorio)
                String nombreValor = NOMBRES[random.nextInt(NOMBRES.length)] + " " + APELLIDOS[random.nextInt(APELLIDOS.length)];
                
                // Graduación aleatoria
                String rangoValor = RANGOS[random.nextInt(RANGOS.length)];
                
                // Edad (entre 18 y 65)
                String edadValor = String.valueOf(18 + random.nextInt(48));

                // --- Añadir hijos al crewmember ---
                crewmember.appendChild(crearElemento(doc, "id", "ID-" + idValor));
                crewmember.appendChild(crearElemento(doc, "nombre", nombreValor));
                crewmember.appendChild(crearElemento(doc, "graduacion", rangoValor));
                crewmember.appendChild(crearElemento(doc, "edad", edadValor));

                // Añadir el crewmember a la raíz
                rootElement.appendChild(crewmember);
            }

            // 4. Escribir el contenido en un archivo XML
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            
            // Formato bonito (indentación)
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");

            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(crewPath));

            transformer.transform(source, result);

            System.out.println("¡Archivo " + crewPath + " generado con éxito con 1000 registros!");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para crear elementos simples con texto
    private static Element crearElemento(Document doc, String etiqueta, String valor) {
        Element node = doc.createElement(etiqueta);
        node.appendChild(doc.createTextNode(valor));
        return node;
    }
}

