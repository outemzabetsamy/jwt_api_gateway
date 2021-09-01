package fr.univ.orleans.webservices.livedemosecurity.dto;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
@Entity
public class UtilisateurDTO {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
    private String login;
    private String password;
    private boolean isAdmin;
    private Long idHotel;
    //private List<Long> messagesIds;

    public Long getIdHotel() {
		return idHotel;
	}


	public void setIdHotel(Long idHotel) {
		this.idHotel = idHotel;
	}


	public UtilisateurDTO(Integer id,String login, String password, boolean isAdmin, Long idHotel) {
        this.id=id;
    	this.login = login;
        this.password = password;
        this.isAdmin = isAdmin;
        this.idHotel=idHotel;
    }


    public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public UtilisateurDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }
/*
    public List<Long> getMessagesIds() {
        return messagesIds;
    }

    public void setMessagesIds(List<Long> messagesIds) {
        this.messagesIds = messagesIds;
    }
    */
}
