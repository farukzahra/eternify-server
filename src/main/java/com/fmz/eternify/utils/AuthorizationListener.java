package com.fmz.eternify.utils;

import javax.faces.context.FacesContext;
import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;
import javax.servlet.http.HttpServletResponse;

import com.fmz.eternify.model.Usuario;

public class AuthorizationListener implements PhaseListener {

    private static final long serialVersionUID = -8237087853801435858L;

    public static final String[] PAGINAS_SEM_RESTRICAO = new String[] { "login.", "cadastro.", "findpessoa." };

    @Override
    public void beforePhase(PhaseEvent event) {
        HttpServletResponse response = JSFHelper.getResponse();

        Usuario usuarioLogado = JSFHelper.getUsuarioLogado();
        String currentPage = FacesContext.getCurrentInstance().getViewRoot().getViewId();

        if (currentPage.contains("/")) {
            String[] diretorios = currentPage.split("/");
            currentPage = diretorios[diretorios.length - 1];
        }
        if (currentPage.contains("xhtml")) {
            currentPage = currentPage.replaceAll("xhtml", "jsf");
        }
		if (!this.isPaginaSemRestricao(currentPage)) {
			if (usuarioLogado == null) {
				JSFHelper.redirect("login.jsf");
			}
		}
        response.setHeader("Expires", "-1");
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidade, proxy-revalidade, private, post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
    }

    private boolean isPaginaSemRestricao(String currentPage) {
        for (String pagina : PAGINAS_SEM_RESTRICAO) {
            if (currentPage.contains(pagina)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void afterPhase(PhaseEvent arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.RENDER_RESPONSE;
    }
}
