package com.fmz.eternify.utils;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.application.ProjectStage;
import javax.faces.application.ViewHandler;
import javax.faces.component.UIViewRoot;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fmz.eternify.model.Usuario;

public class JSFHelper {

	public static Usuario getUsuarioLogado() {
		try {
			Usuario usuario = (Usuario) JSFHelper.getSession().getAttribute("USUARIO_LOGADO");
			return usuario;
		} catch (Exception e) {

		}
		return null;
	}

	public static void addInfo(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_INFO, summary, detail);
	}

	public static void addWarn(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_WARN, summary, detail);
	}

	public static void addError(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
	}

	public static void addFatal(String summary, String detail) {
		addMessage(FacesMessage.SEVERITY_FATAL, summary, detail);
	}

	private static void addMessage(Severity severity, String summary, String detail) {
		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(severity, summary, detail));
	}

	public static HttpSession getSession() {
		HttpSession httpSession = (HttpSession) getExternalContext().getSession(true);
		return httpSession;
	}

	public static HttpServletRequest getRequest() {
		HttpServletRequest request = (HttpServletRequest) getExternalContext().getRequest();
		return request;
	}

	public static String getUrl() {
		return "http://" + getRequest().getServerName() + ":" + getRequest().getServerPort()
				+ getRequest().getContextPath();
	}

	public static HttpServletResponse getResponse() {
		HttpServletResponse response = (HttpServletResponse) getExternalContext().getResponse();
		return response;
	}

	public static Application getApplication() {
		return FacesContext.getCurrentInstance().getApplication();
	}

	public static ExternalContext getExternalContext() {
		return FacesContext.getCurrentInstance().getExternalContext();
	}

	public static UIViewRoot getUIViewRoot() {
		return FacesContext.getCurrentInstance().getViewRoot();
	}

	public static void initUIViewRoot() {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ViewHandler viewHandler = application.getViewHandler();
		UIViewRoot viewRoot = viewHandler.createView(context, context.getViewRoot().getViewId());
		context.setViewRoot(viewRoot);
		context.renderResponse();
	}

	public static String getSistemaAtual() {
		return getExternalContext().getInitParameter("SISTEMA_ATUAL");
	}

	public static void redirect(String path) {
		try {
			getExternalContext().redirect(path);
		} catch (Exception e) {
			RequestDispatcher dd = getRequest().getRequestDispatcher("login.jsf");
			try {
				dd.forward(getRequest(), getResponse());
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		}
	}

	public static String getRequestParameterMap(String param) {
		return getExternalContext().getRequestParameterMap().get(param);
	}

	public static ProjectStage getProjectStage() {
		if (FacesContext.getCurrentInstance() != null && FacesContext.getCurrentInstance().getApplication() != null
				&& FacesContext.getCurrentInstance().getApplication().getProjectStage() != null) {
			return FacesContext.getCurrentInstance().getApplication().getProjectStage();
		} else {
			return null;
		}
	}

	public static boolean isDesenv() {
		return getProjectStage() != null ? getProjectStage() == ProjectStage.Development : false;
	}

}
