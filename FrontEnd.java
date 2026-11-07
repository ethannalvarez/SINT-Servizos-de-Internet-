package p2;

import java.util.ArrayList;

public class FrontEnd {

    private final String CSS_FILE = "estilos.css";
    private final String AUTOR = "Tu Nombre (2026-2027)"; // ¡ATENCIÓN! Cambia esto por tu nombre real

    // ==========================================
    // MÉTODOS HTML
    // ==========================================

    public String generarHTMLFase0(String urlXML, String ip, String userAgent) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Servicio de carteleras</title>\n");
        html.append("<link rel='stylesheet' type='text/css' href='").append(CSS_FILE).append("'>\n");
        html.append("<meta charset='UTF-8'>\n</head>\n<body>\n");
        html.append("<h2>Servicio de informacion sobre carteleras de TV</h2>\n");
        html.append("<h3>Bienvenido a este servicio</h3>\n");
        html.append("<p><b>Fichero TVML procesado:</b> ").append(urlXML).append("</p>\n");
        html.append("<p><b>IP del cliente:</b> ").append(ip).append("</p>\n");
        html.append("<p><b>Navegador:</b> ").append(userAgent).append("</p>\n");
        html.append("<h4>Selecciona una consulta:</h4>\n");
        html.append("<ul>\n");
        html.append("  <li><a href='?pphase=1'>Participantes de un programa de un canal</a></li>\n");
        html.append("</ul>\n");
        html.append("<hr>\n<footer>&copy; ").append(AUTOR).append("</footer>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }

    public String generarHTMLFase1(ArrayList<Canal> canales) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Fase 1</title>\n");
        html.append("<link rel='stylesheet' type='text/css' href='").append(CSS_FILE).append("'>\n");
        html.append("<meta charset='UTF-8'>\n</head>\n<body>\n");
        html.append("<h2>Servicio de informacion sobre carteleras de TV</h2>\n");
        html.append("<h3>Fase 1</h3>\n");
        html.append("<h4>Selecciona un canal:</h4>\n");
        
        html.append("<ol>\n"); 
        for (Canal c : canales) {
            html.append("<li><a href='?pphase=2&pidC=").append(c.idC).append("'>")
                .append(c.nombre).append("</a> - idC = ").append(c.idC)
                .append(" - defaultLang = ").append(c.langDefault).append("</li>\n");
        }
        html.append("</ol>\n");
        
        html.append("<div class='navegacion'>\n");
        html.append("<form method='GET' action=''><input type='hidden' name='pphase' value='0'><input type='submit' value='Inicio'></form>\n"); 
        html.append("</div>\n");
        
        html.append("<hr>\n<footer>&copy; ").append(AUTOR).append("</footer>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }

    public String generarHTMLFase2(ArrayList<Programa> programas, String pidC) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Fase 2</title>\n");
        html.append("<link rel='stylesheet' type='text/css' href='").append(CSS_FILE).append("'>\n");
        html.append("<meta charset='UTF-8'>\n</head>\n<body>\n");
        html.append("<h2>Servicio de informacion sobre carteleras de TV</h2>\n");
        html.append("<h3>Fase 2 (Canal pidC = ").append(pidC).append(")</h3>\n");
        html.append("<h4>Selecciona un programa:</h4>\n");
        
        html.append("<ol>\n");
        for (Programa p : programas) {
            html.append("<li><a href='?pphase=3&pidC=").append(pidC).append("&pidP=").append(p.idP).append("'>")
                .append(p.nombre).append("</a> --- idP = ").append(p.idP);
            
            if (p.edadMinima != null && !p.edadMinima.isEmpty()) {
                html.append(" --- edadMinima = ").append(p.edadMinima);
            }
            html.append(" --- langs = ").append(p.langs).append("</li>\n");
        }
        html.append("</ol>\n");
        
        html.append("<div class='navegacion'>\n");
        html.append("<form method='GET' action=''><input type='hidden' name='pphase' value='0'><input type='submit' value='Inicio'></form>\n");
        html.append("<form method='GET' action=''><input type='hidden' name='pphase' value='1'><input type='submit' value='Atras'></form>\n");
        html.append("</div>\n");
        
        html.append("<hr>\n<footer>&copy; ").append(AUTOR).append("</footer>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }

    public String generarHTMLFase3(ArrayList<Participante> participantes, String pidC, String pidP) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html>\n<head>\n<title>Fase 3</title>\n");
        html.append("<link rel='stylesheet' type='text/css' href='").append(CSS_FILE).append("'>\n");
        html.append("<meta charset='UTF-8'>\n</head>\n<body>\n");
        html.append("<h2>Servicio de informacion sobre carteleras de TV</h2>\n");
        html.append("<h3>Fase 3 (Canal pidC = ").append(pidC).append(", Programa pidP = ").append(pidP).append(")</h3>\n");
        html.append("<h4>Este es el resultado de la consulta:</h4>\n");
        
        html.append("<ol>\n");
        for (Participante p : participantes) {
            html.append("<li>Nombre = ").append(p.nombre)
                .append(" --- Rol = ").append(p.role)
                .append(" --- idS = ").append(p.idS).append("</li>\n");
        }
        html.append("</ol>\n");
        
        html.append("<div class='navegacion'>\n");
        html.append("<form method='GET' action=''><input type='hidden' name='pphase' value='0'><input type='submit' value='Inicio'></form>\n");
        html.append("<form method='GET' action=''><input type='hidden' name='pphase' value='2'><input type='hidden' name='pidC' value='").append(pidC).append("'><input type='submit' value='Atras'></form>\n");
        html.append("</div>\n");
        
        html.append("<hr>\n<footer>&copy; ").append(AUTOR).append("</footer>\n");
        html.append("</body>\n</html>");
        return html.toString();
    }

    // ==========================================
    // MÉTODOS JSON
    // ==========================================

    public String generarJSONCanales(ArrayList<Canal> canales) {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < canales.size(); i++) {
            Canal c = canales.get(i);
            json.append("  {\"name\": \"").append(c.nombre)
                .append("\", \"idC\": \"").append(c.idC)
                .append("\", \"langDefault\": \"").append(c.langDefault).append("\"}");
            
            if (i < canales.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }

    public String generarJSONProgramas(ArrayList<Programa> programas) {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < programas.size(); i++) {
            Programa p = programas.get(i);
            json.append("  {\"name\": \"").append(p.nombre)
                .append("\", \"idP\": \"").append(p.idP).append("\"");
            
            if (p.edadMinima != null && !p.edadMinima.isEmpty()) {
                json.append(", \"edadMinima\": \"").append(p.edadMinima).append("\"");
            }
            
            json.append(", \"langs\": \"").append(p.langs).append("\"}");
            
            if (i < programas.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }

    public String generarJSONParticipantes(ArrayList<Participante> participantes) {
        StringBuilder json = new StringBuilder("[\n");
        for (int i = 0; i < participantes.size(); i++) {
            Participante p = participantes.get(i);
            json.append("  {\"name\": \"").append(p.nombre)
                .append("\", \"idS\": \"").append(p.idS)
                .append("\", \"role\": \"").append(p.role).append("\"}");
            
            if (i < participantes.size() - 1) json.append(",");
            json.append("\n");
        }
        json.append("]");
        return json.toString();
    }

    public String generarJSONReady() {
        return "{\"status\": 404, \"error\": \"Not Found\", \"message\": \"ready\"}";
    }

    public String generarErrorJSON() {
        return "{\"status\": 404, \"error\": \"Not Found\", \"message\": \"La página no existe\"}";
    }
}