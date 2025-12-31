package com.smartvillage.smart_village_backend.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    @NotBlank
    private String name;

    @Size(min = 10, max = 10)
    private String mobile;

    @NotBlank
    private String email;

    public String getName() { return name; }
    public String getMobile() { return mobile; }
    public String getEmail() { return email; }

    public void setName(String name) { this.name = name; }
    public void setMobile(String mobile) { this.mobile = mobile; }
    public void setEmail(String email) { this.email = email; }
}
