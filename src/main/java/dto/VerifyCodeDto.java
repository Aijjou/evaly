package dto;

import validator.ValidVerifyCode;

public class VerifyCodeDto {

	@ValidVerifyCode
	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
	
}
