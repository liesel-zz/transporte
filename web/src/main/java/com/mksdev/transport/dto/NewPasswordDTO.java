package com.mksdev.transport.dto;

import java.io.Serializable;


/**
 * Recebe nova solicitação de senha do usuário
 * */
public class NewPasswordDTO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String uuid;
	private String newPassword;

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

}
