package it.gestionearticolijspservletjpamaven.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import it.gestionearticolijspservletjpamaven.model.Articolo;
import it.gestionearticolijspservletjpamaven.service.MyServiceFactory;

@WebServlet("/PrepareEditArticoloServlet")
public class PrepareEditArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String idArticoloParam = request.getParameter("idArticolo");
		
		Long idArticolo = parseIdArrivoFromString(idArticoloParam);
				
		try {
			Articolo articoloDaInviare = MyServiceFactory.getArticoloServiceInstance().caricaSingoloElemento(idArticolo);
			request.setAttribute("modifica_articolo_attr", articoloDaInviare);
		} catch (Exception e) {
			// qui ci andrebbe un messaggio nei file di log costruito ad hoc se fosse attivo
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/index.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/articolo/edit.jsp").forward(request, response);
		
	}
	
	private Long parseIdArrivoFromString(String idArrivoStringParam) {
		if (StringUtils.isBlank(idArrivoStringParam))
			return null;

		try {
			return Long.parseLong(idArrivoStringParam);
		} catch (Exception e) {
			return null;
		}
	}

}
