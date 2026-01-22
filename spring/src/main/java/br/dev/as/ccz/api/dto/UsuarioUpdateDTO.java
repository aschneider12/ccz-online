package br.dev.as.ccz.api.dto;

import com.fasterxml.jackson.annotation.*;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import java.util.Objects;

/**
 * UsuarioUpdateDTO
 */
public class UsuarioUpdateDTO   {
  @JsonProperty("nome")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private String nome = null;

  @JsonProperty("cartaoSus")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private Long cartaoSus = null;

  /**
   * Perfil de acesso do usuário
   */
  public enum PerfilEnum {
    ADMIN("ADMIN"),
    
    AGENTE("AGENTE"),
    
    CIDADAO("CIDADAO"),
    
    CCZ("CCZ");

    private String value;

    PerfilEnum(String value) {
      this.value = value;
    }

    @Override
    @JsonValue
    public String toString() {
      return String.valueOf(value);
    }

    @JsonCreator
    public static PerfilEnum fromValue(String text) {
      for (PerfilEnum b : PerfilEnum.values()) {
        if (String.valueOf(b.value).equals(text)) {
          return b;
        }
      }
      return null;
    }
  }
  @JsonProperty("perfil")

  @JsonInclude(JsonInclude.Include.NON_ABSENT)  // Exclude from JSON if absent
  @JsonSetter(nulls = Nulls.FAIL)    // FAIL setting if the value is null
  private PerfilEnum perfil = null;


  public UsuarioUpdateDTO nome(String nome) { 

    this.nome = nome;
    return this;
  }

  /**
   * Nome completo do usuário
   * @return nome
   **/
  
  @Schema(example = "João da Silva Santos", description = "Nome completo do usuário")
  
@Size(min=3,max=255)   public String getNome() {  
    return nome;
  }



  public void setNome(String nome) { 
    this.nome = nome;
  }

  public UsuarioUpdateDTO cartaoSus(Long cartaoSus) { 

    this.cartaoSus = cartaoSus;
    return this;
  }

  /**
   * Número do Cartão SUS
   * @return cartaoSus
   **/
  
  @Schema(example = "123456789012345", description = "Número do Cartão SUS")
  
  public Long getCartaoSus() {  
    return cartaoSus;
  }



  public void setCartaoSus(Long cartaoSus) { 
    this.cartaoSus = cartaoSus;
  }

  public UsuarioUpdateDTO perfil(PerfilEnum perfil) { 

    this.perfil = perfil;
    return this;
  }

  /**
   * Perfil de acesso do usuário
   * @return perfil
   **/
  
  @Schema(example = "ADMIN", description = "Perfil de acesso do usuário")
  
  public PerfilEnum getPerfil() {  
    return perfil;
  }



  public void setPerfil(PerfilEnum perfil) { 
    this.perfil = perfil;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UsuarioUpdateDTO usuarioUpdateDTO = (UsuarioUpdateDTO) o;
    return Objects.equals(this.nome, usuarioUpdateDTO.nome) &&
        Objects.equals(this.cartaoSus, usuarioUpdateDTO.cartaoSus) &&
        Objects.equals(this.perfil, usuarioUpdateDTO.perfil);
  }

  @Override
  public int hashCode() {
    return Objects.hash(nome, cartaoSus, perfil);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UsuarioUpdateDTO {\n");
    
    sb.append("    nome: ").append(toIndentedString(nome)).append("\n");
    sb.append("    cartaoSus: ").append(toIndentedString(cartaoSus)).append("\n");
    sb.append("    perfil: ").append(toIndentedString(perfil)).append("\n");
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
