package fr.univ.orleans.webservices.livedemosecurity.modele;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;

public class Utilisateur {
    

	private static final String[] ROLES_ADMIN = {"ROLE_USER","ROLE_ADMIN"};
    private static final String[] ROLES_USER  = {"ROLE_USER"};
    
    private final String login;
    
    private String password;
    
    private boolean isAdmin;
    
    private Long idHotel;
    public Long getIdHotel() {
		return idHotel;
	}

	public void setIdHotel(Long idHotel) {
		this.idHotel = idHotel;
	}

	private final List<Message> messages = new ArrayList<>();

    public Utilisateur(String login, String password, boolean isAdmin,Long idHotel) {
        this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.idHotel=idHotel;
    }
   
    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public String[] getRoles() {
        return isAdmin() ? ROLES_ADMIN : ROLES_USER;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void addMessage(Message message) {
        message.setUtilisateur(this);
        messages.add(message);
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
}
