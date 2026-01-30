package br.dev.as.ccz.api.dto;

import br.dev.as.ccz.enums.PerfilEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * UsuarioCreateDTO
 */
public class UsuarioCreateDTO   {

  @JsonProperty("nome")
  private String nome = null;

  @JsonProperty("cartaoSus")
  private Long cartaoSus = null;

  @JsonProperty("perfil")
  private PerfilEnum perfil;

  @JsonProperty("cpf")
  private String cpf = null;

  public UsuarioCreateDTO nome(String nome) {

    this.nome = nome;
    return this;
  }

  /**
   * Nome completo do usuário
   * @return nome
   **/
  
  @Schema(example = "Maria Santos", required = true, description = "Nome completo do usuário")
  
  @NotNull
@Size(min=3,max=255)   public String getNome() {  
    return nome;
  }



  public void setNome(String nome) { 

    this.nome = nome;
  }

  public UsuarioCreateDTO cartaoSus(Long cartaoSus) { 

    this.cartaoSus = cartaoSus;
    return this;
  }

  /**
   * Número do Cartão SUS
   * @return cartaoSus
   **/
  
  @Schema(example = "987654321012345", required = true, description = "Número do Cartão SUS")
  
  @NotNull
  public Long getCartaoSus() {  
    return cartaoSus;
  }



  public void setCartaoSus(Long cartaoSus) { 

    this.cartaoSus = cartaoSus;
  }

  public UsuarioCreateDTO perfil(PerfilEnum perfil) { 

    this.perfil = perfil;
    return this;
  }

  /**
   * Perfil de acesso do usuário
   * @return perfil
   **/
  
  @Schema(example = "CIDADAO", required = true, description = "Perfil de acesso do usuário")
  
  @NotNull
  public PerfilEnum getPerfil() {  
    return perfil;
  }



  public void setPerfil(PerfilEnum perfil) { 

    this.perfil = perfil;
  }

  public UsuarioCreateDTO cpf(String cpf) { 

    this.cpf = cpf;
    return this;
  }

  /**
   * CPF do usuário (apenas números)
   * @return cpf
   **/
  
  @Schema(example = "98765432109", required = true, description = "CPF do usuário (apenas números)")
  
  @NotNull
@Pattern(regexp="^\\d{11}$") @Size(min=11,max=11)   public String getCpf() {  
    return cpf;
  }



  public void setCpf(String cpf) { 

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
    UsuarioCreateDTO usuarioCreateDTO = (UsuarioCreateDTO) o;
    return Objects.equals(this.nome, usuarioCreateDTO.nome) &&
        Objects.equals(this.cartaoSus, usuarioCreateDTO.cartaoSus) &&
        Objects.equals(this.perfil, usuarioCreateDTO.perfil) &&
        Objects.equals(this.cpf, usuarioCreateDTO.cpf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cartaoSus, perfil, cpf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioCreateDTO {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cartaoSus: ").append(toIndentedString(cartaoSus)).append("\n");
    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
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
