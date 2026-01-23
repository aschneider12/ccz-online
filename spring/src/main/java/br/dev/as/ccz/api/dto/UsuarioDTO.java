package br.dev.as.ccz.api.dto;

import br.dev.as.ccz.enums.PerfilEnum;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.validation.annotation.Validated;

import java.util.Objects;

/**
 * UsuarioDTO
 */
public class UsuarioDTO   {
  @JsonProperty("id")
  private Long id = null;
  @JsonProperty("nome")

  private String nome = null;
  @JsonProperty("cartaoSus")

  private Long cartaoSus = null;


  @JsonProperty("perfil")
  private PerfilEnum perfil = null;

  @JsonProperty("cpf")

  private String cpf = null;


  public UsuarioDTO id(Long id) { 

    this.id = id;
    return this;
  }

  /**
   * ID único do usuário
   * @return id
   **/
  
  @Schema(example = "1", required = true, description = "ID único do usuário")
  
  @NotNull
  public Long getId() {  
    return id;
  }



  public void setId(Long id) { 

    this.id = id;
  }

  public UsuarioDTO nome(String nome) { 

    this.nome = nome;
    return this;
  }

  /**
   * Nome completo do usuário
   * @return nome
   **/
  
  @Schema(example = "João da Silva", required = true, description = "Nome completo do usuário")
  
  @NotNull
  public String getNome() {  
    return nome;
  }



  public void setNome(String nome) { 

    this.nome = nome;
  }

  public UsuarioDTO cartaoSus(Long cartaoSus) { 

    this.cartaoSus = cartaoSus;
    return this;
  }

  /**
   * Número do Cartão SUS
   * @return cartaoSus
   **/
  
  @Schema(example = "123456789012345", required = true, description = "Número do Cartão SUS")
  
  @NotNull
  public Long getCartaoSus() {  
    return cartaoSus;
  }



  public void setCartaoSus(Long cartaoSus) { 

    this.cartaoSus = cartaoSus;
  }

  public UsuarioDTO perfil(PerfilEnum perfil) { 

    this.perfil = perfil;
    return this;
  }

  /**
   * Perfil de acesso do usuário
   * @return perfil
   **/
  
  @Schema(example = "AGENTE", required = true, description = "Perfil de acesso do usuário")
  
  @NotNull
  public PerfilEnum getPerfil() {  
    return perfil;
  }



  public void setPerfil(PerfilEnum perfil) { 

    this.perfil = perfil;
  }

  public UsuarioDTO cpf(String cpf) { 

    this.cpf = cpf;
    return this;
  }

  /**
   * CPF do usuário
   * @return cpf
   **/
  
  @Schema(example = "12345678901", required = true, description = "CPF do usuário")
  
  @NotNull
@Pattern(regexp="^\\d{11}$")   public String getCpf() {  
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
    UsuarioDTO usuarioDTO = (UsuarioDTO) o;
    return Objects.equals(this.id, usuarioDTO.id) &&
        Objects.equals(this.nome, usuarioDTO.nome) &&
        Objects.equals(this.cartaoSus, usuarioDTO.cartaoSus) &&
        Objects.equals(this.perfil, usuarioDTO.perfil) &&
        Objects.equals(this.cpf, usuarioDTO.cpf);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, nome, cartaoSus, perfil, cpf);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioDTO {\n");
    
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
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
