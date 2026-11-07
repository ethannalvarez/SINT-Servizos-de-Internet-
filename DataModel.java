package p2;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.util.*;

public class DataModel {
    private Document doc;
    private final String XML_URL = "https://alberto.gil.webs.uvigo.es/SINT/26-27/p2/tvml.xml";

    public DataModel() throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        doc = db.parse(XML_URL);
        doc.getDocumentElement().normalize();
    }

    public ArrayList<Canal> getQ1Canales() {
        ArrayList<Canal> canales = new ArrayList<>();
        NodeList nlCanales = doc.getElementsByTagName("Canal");
        
        for (int i = 0; i < nlCanales.getLength(); i++) {
            if (nlCanales.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element canalEl = (Element) nlCanales.item(i);
                String idC = canalEl.getAttribute("idC");
                String lang = canalEl.getAttribute("defaultLang");
                
                String nombre = "";
                NodeList nlNombre = canalEl.getElementsByTagName("NombreCanal");
                if (nlNombre.getLength() > 0) {
                    nombre = nlNombre.item(0).getTextContent().trim();
                }
                
                canales.add(new Canal(idC, lang, nombre));
            }
        }
        
        canales.sort(Comparator.comparing((Canal c) -> c.langDefault).thenComparing(c -> c.idC));
        return canales;
    }

    public ArrayList<Programa> getQ1Programas(String idC) {
        ArrayList<Programa> programas = new ArrayList<>();
        NodeList nlCanales = doc.getElementsByTagName("Canal");
        
        for (int i = 0; i < nlCanales.getLength(); i++) {
            if (nlCanales.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element canalEl = (Element) nlCanales.item(i);
                
                if (canalEl.getAttribute("idC").equals(idC)) {
                    NodeList nlProgramas = canalEl.getElementsByTagName("Programa");
                    
                    for (int j = 0; j < nlProgramas.getLength(); j++) {
                        if (nlProgramas.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element progEl = (Element) nlProgramas.item(j);
                            String idP = progEl.getAttribute("oidP");
                            String langs = progEl.getAttribute("langs");
                            
                            String edadMin = null;
                            if (progEl.hasAttribute("edadMinima")) {
                                edadMin = progEl.getAttribute("edadMinima");
                            }
                            
                            String nombre = "";
                            NodeList nlNombre = progEl.getElementsByTagName("NombrePrograma");
                            if (nlNombre.getLength() > 0) {
                                nombre = nlNombre.item(0).getTextContent().trim();
                            }
                            
                            programas.add(new Programa(idP, edadMin, langs, nombre));
                        }
                    }
                    break;
                }
            }
        }
        
        programas.sort((p1, p2) -> p2.idP.compareTo(p1.idP));
        return programas;
    }

    public ArrayList<Participante> getQ1Participantes(String idC, String idP) {
        ArrayList<Participante> participantes = new ArrayList<>();
        NodeList nlCanales = doc.getElementsByTagName("Canal");
        
        for (int i = 0; i < nlCanales.getLength(); i++) {
            if (nlCanales.item(i).getNodeType() == Node.ELEMENT_NODE) {
                Element canalEl = (Element) nlCanales.item(i);
                
                if (canalEl.getAttribute("idC").equals(idC)) {
                    NodeList nlProgramas = canalEl.getElementsByTagName("Programa");
                    
                    for (int j = 0; j < nlProgramas.getLength(); j++) {
                        if (nlProgramas.item(j).getNodeType() == Node.ELEMENT_NODE) {
                            Element progEl = (Element) nlProgramas.item(j);
                            
                            if (progEl.getAttribute("oidP").equals(idP)) {
                                NodeList nlParticipantes = progEl.getElementsByTagName("Participante");
                                
                                for (int k = 0; k < nlParticipantes.getLength(); k++) {
                                    if (nlParticipantes.item(k).getNodeType() == Node.ELEMENT_NODE) {
                                        Element partEl = (Element) nlParticipantes.item(k);
                                        String idS = partEl.getAttribute("idS");
                                        
                                        String nombre = "";
                                        NodeList nlNombre = partEl.getElementsByTagName("NombreParticipante");
                                        if (nlNombre.getLength() > 0) {
                                            nombre = nlNombre.item(0).getTextContent().trim();
                                        }
                                        
                                        String role = "";
                                        NodeList nlRole = partEl.getElementsByTagName("Role");
                                        if (nlRole.getLength() > 0) {
                                            role = nlRole.item(0).getTextContent().trim();
                                        }
                                        
                                        participantes.add(new Participante(idS, nombre, role));
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
        
        participantes.sort(Comparator.comparing((Participante p) -> p.nombre).thenComparing(p -> p.idS));
        return participantes;
    }
}