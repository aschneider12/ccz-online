package br.dev.as.ccz.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * LoginRequest
 */

public class LoginRequest   {
  @JsonProperty("cpf")

  private String cpf = null;


  public LoginRequest cpf(String cpf) { 

    this.cpf = cpf;
    return this;
  }

  /**
   * CPF do usuário (apenas números)
   * @return cpf
   **/
  
  @Schema(example = "12345678901", required = true, description = "CPF do usuário (apenas números)")
  
  @NotNull
@Pattern(regexp="^\\d{11}$") @Size(min=11,max=14)   public String getCpf() {
    return cpf;
  }



  public void setCpf(String cpf) {
  if(cpf != null)
    cpf = cpf.replace("-","").replace(".","");
    this.cpf = cpf;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    LoginRequest loginRequest = (LoginRequest) o;
    return Objects.equals(this.cpf, loginRequest.cpf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cpf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LoginRequest {\n");
    
    sb.append("    cpf: ").append(toIndentedString(cpf)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}
