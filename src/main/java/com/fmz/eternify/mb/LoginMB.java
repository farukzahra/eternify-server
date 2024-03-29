package com.fmz.eternify.mb;

import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Named;

import org.springframework.beans.factory.annotation.Autowired;

import com.fmz.eternify.controller.FaturaController;
import com.fmz.eternify.controller.UsuarioController;
import com.fmz.eternify.model.Endereco;
import com.fmz.eternify.model.Usuario;
import com.fmz.eternify.utils.BuscarCep;
import com.fmz.eternify.utils.JSFHelper;
import com.fmz.eternify.utils.UF;

import lombok.Data;

@Named
@SessionScoped
@Data
public class LoginMB {

    @Autowired
    private UsuarioController usuarioController;
    
    @Autowired
    private FaturaController faturaController;

    private Usuario usuario = new Usuario();

    private Usuario usuarioLogado;

    private String senhaAtual, novaSenha, novaSenhaConfirmacao;

    public String doLogin() {
        usuarioLogado = usuarioController.login(usuario);
        JSFHelper.getSession().setAttribute("USUARIO_LOGADO", usuarioLogado);
        if (usuarioLogado != null) {
        	faturaController.atualizarFaturas(usuarioLogado);
            JSFHelper.redirect("pessoa.jsf");
            return "";
        } else {
            JSFHelper.addError("Email/Senha inválidos", "");
        }
        return "";
    }

    public void carregarEndereco() {
        String cep = usuario.getCep();
        if (cep != null && !cep.isEmpty()) {
            cep = cep.replaceAll("\\.", "");
            cep = cep.replaceAll("\\-", "");
            if (cep.length() == 8) {
                Endereco endereco = BuscarCep.buscarCep(cep);
                if (endereco != null) {
                    usuario.setCep(endereco.getCep());
                    usuario.setRua(endereco.getLogradouro());
                    usuario.setCidade(endereco.getCidade());
                    usuario.setBairro(endereco.getBairro());
                    usuario.setEstado(endereco.getEstado());
                }
            }
        }
    }

    public List<UF> getListUF() {
        return UF.getList();
    }

    public String doCadastro() {
        try {
        	usuario.setLogin(usuario.getEmail());
        	usuario.setCreditos(0);
        	usuarioController.cadastrarIugu(usuario);
            String msg = usuarioController.cadastrar(usuario);            
            JSFHelper.addInfo(msg, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "login.xhtml";
    }

    public void doTrocarSenha() {
        try {
            String msg = usuarioController.trocarSenha(usuarioLogado, senhaAtual, novaSenha, novaSenhaConfirmacao);
            JSFHelper.addInfo(msg, "");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
