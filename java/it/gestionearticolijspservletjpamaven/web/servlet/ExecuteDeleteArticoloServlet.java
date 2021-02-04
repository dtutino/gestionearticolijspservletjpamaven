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

@WebServlet("/ExecuteDeleteArticoloServlet")
public class ExecuteDeleteArticoloServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public ExecuteDeleteArticoloServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String idArrivoStringParam = request.getParameter("idArticolo");
		
		Long idArrivoParsed = parseIdArrivoFromString(idArrivoStringParam);
		
		try {
			Articolo articoloInstance = MyServiceFactory.getArticoloServiceInstance().caricaSingoloElemento(idArrivoParsed);
			MyServiceFactory.getArticoloServiceInstance().rimuovi(articoloInstance);
			
			request.setAttribute("listaArticoliAttribute", MyServiceFactory.getArticoloServiceInstance().listAll());
			request.setAttribute("successMessage", "Operazione effettuata con successo");
		} catch (Exception e) {
			e.printStackTrace();
			request.setAttribute("errorMessage", "Attenzione si è verificato un errore.");
			request.getRequestDispatcher("/articolo/delete.jsp").forward(request, response);
			return;
		}
		
		request.getRequestDispatcher("/articolo/results.jsp").forward(request, response);
		
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
