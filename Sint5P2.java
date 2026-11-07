package p2;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Sint5P2 extends HttpServlet {
    private DataModel model;
    private FrontEnd view;

    @Override
    public void init() throws ServletException {
        try {
            model = new DataModel();
            view = new FrontEnd();
        } catch (Exception e) {
            throw new ServletException("Error inicializando modelo DOM", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
            
        String pathInfo = request.getPathInfo();
        PrintWriter out = response.getWriter();
        response.setCharacterEncoding("UTF-8");

        // RUTA API REST
        if (pathInfo != null && pathInfo.startsWith("/v1")) {
            response.setContentType("application/json");
            String[] pathParts = pathInfo.split("/");
            
            if (pathParts.length == 3 && pathParts[2].equals("canales")) {
                ArrayList<Canal> canales = model.getQ1Canales();
                out.print(view.generarJSONCanales(canales));
            } 
            else if (pathParts.length == 5 && pathParts[2].equals("canales") && pathParts[4].equals("programas")) {
                String idC = pathParts[3];
                ArrayList<Programa> programas = model.getQ1Programas(idC);
                if (programas.isEmpty()) {
                    response.setStatus(404);
                    out.print(view.generarErrorJSON());
                } else {
                    out.print(view.generarJSONProgramas(programas));
                }
            } 
            else if (pathParts.length == 7 && pathParts[2].equals("canales") && pathParts[4].equals("programas") && pathParts[6].equals("participantes")) {
                String idC = pathParts[3];
                String idP = pathParts[5];
                
                ArrayList<Programa> programas = model.getQ1Programas(idC);
                boolean programaExiste = programas.stream().anyMatch(p -> p.idP.equals(idP));
                
                if (!programaExiste) {
                    response.setStatus(404);
                    out.print(view.generarErrorJSON());
                } else {
                    ArrayList<Participante> participantes = model.getQ1Participantes(idC, idP);
                    out.print(view.generarJSONParticipantes(participantes));
                }
            } 
            else if (pathInfo.equals("/v1") || pathInfo.equals("/v1/")) {
                response.setStatus(404);
                out.print(view.generarJSONReady());
            } 
            else {
                response.setStatus(404);
                out.print(view.generarErrorJSON());
            }
        } 
        // RUTA HTML
        else {
            response.setContentType("text/html");
            String pphase = request.getParameter("pphase");
            
            if (pphase == null || pphase.equals("0")) {
                String ip = request.getRemoteAddr();
                String agent = request.getHeader("User-Agent");
                String urlXML = "https://alberto.gil.webs.uvigo.es/SINT/26-27/p2/tvml.xml";
                out.print(view.generarHTMLFase0(urlXML, ip, agent));
            } 
            else if (pphase.equals("1")) {
                ArrayList<Canal> canales = model.getQ1Canales();
                out.print(view.generarHTMLFase1(canales));
            } 
            else if (pphase.equals("2")) {
                String pidC = request.getParameter("pidC");
                ArrayList<Programa> programas = model.getQ1Programas(pidC);
                out.print(view.generarHTMLFase2(programas, pidC));
            } 
            else if (pphase.equals("3")) {
                String pidC = request.getParameter("pidC");
                String pidP = request.getParameter("pidP");
                ArrayList<Participante> participantes = model.getQ1Participantes(pidC, pidP);
                out.print(view.generarHTMLFase3(participantes, pidC, pidP));
            }
        }
    }
}