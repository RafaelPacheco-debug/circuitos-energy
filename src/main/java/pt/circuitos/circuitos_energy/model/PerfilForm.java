package pt.circuitos.circuitos_energy.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class PerfilForm {

    @NotBlank(message = "O nome é obrigatório")
    private String fullName;

    @Email(message = "Email inválido")
    private String email;

    private String password;

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
