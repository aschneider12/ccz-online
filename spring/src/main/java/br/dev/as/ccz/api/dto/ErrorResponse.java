package br.dev.as.ccz.api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import org.springframework.validation.annotation.Validated;

import java.time.OffsetDateTime;
import java.util.Objects;

/**
 * ErrorResponse
 */
@Validated
public class ErrorResponse   {
  @JsonProperty("timestamp")

  private OffsetDateTime timestamp = null;

  @JsonProperty("status")

  private Integer status = null;

  @JsonProperty("error")

  private String error = null;

  @JsonProperty("message")

  private String message = null;

  @JsonProperty("path")

  private String path = null;


  public ErrorResponse timestamp(OffsetDateTime timestamp) { 

    this.timestamp = timestamp;
    return this;
  }

  /**
   * Data e hora do erro
   * @return timestamp
   **/
  
  @Schema(example = "2026-01-22T10:30Z", required = true, description = "Data e hora do erro")
  
@Valid
  @NotNull
  public OffsetDateTime getTimestamp() {  
    return timestamp;
  }



  public void setTimestamp(OffsetDateTime timestamp) { 

    this.timestamp = timestamp;
  }

  public ErrorResponse status(Integer status) { 

    this.status = status;
    return this;
  }

  /**
   * Código de status HTTP
   * @return status
   **/
  
  @Schema(example = "400", required = true, description = "Código de status HTTP")
  
  @NotNull
  public Integer getStatus() {  
    return status;
  }



  public void setStatus(Integer status) { 

    this.status = status;
  }

  public ErrorResponse error(String error) { 

    this.error = error;
    return this;
  }

  /**
   * Tipo do erro
   * @return error
   **/
  
  @Schema(example = "Bad Request", required = true, description = "Tipo do erro")
  
  @NotNull
  public String getError() {  
    return error;
  }



  public void setError(String error) { 

    this.error = error;
  }

  public ErrorResponse message(String message) { 

    this.message = message;
    return this;
  }

  /**
   * Mensagem descritiva do erro
   * @return message
   **/
  
  @Schema(example = "CPF inválido", required = true, description = "Mensagem descritiva do erro")
  
  @NotNull
  public String getMessage() {  
    return message;
  }



  public void setMessage(String message) { 

    this.message = message;
  }

  public ErrorResponse path(String path) { 

    this.path = path;
    return this;
  }

  /**
   * Caminho da requisição que gerou o erro
   * @return path
   **/
  
  @Schema(example = "/api/v1/auth/login", required = true, description = "Caminho da requisição que gerou o erro")
  
  @NotNull
  public String getPath() {  
    return path;
  }



  public void setPath(String path) { 

    this.path = path;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ErrorResponse errorResponse = (ErrorResponse) o;
    return Objects.equals(this.timestamp, errorResponse.timestamp) &&
        Objects.equals(this.status, errorResponse.status) &&
        Objects.equals(this.error, errorResponse.error) &&
        Objects.equals(this.message, errorResponse.message) &&
        Objects.equals(this.path, errorResponse.path);
  }

  @Override
  public int hashCode() {
    return Objects.hash(timestamp, status, error, message, path);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ErrorResponse {\n");
    
    sb.append("    timestamp: ").append(toIndentedString(timestamp)).append("\n");
    sb.append("    status: ").append(toIndentedString(status)).append("\n");
    sb.append("    error: ").append(toIndentedString(error)).append("\n");
    sb.append("    message: ").append(toIndentedString(message)).append("\n");
    sb.append("    path: ").append(toIndentedString(path)).append("\n");
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
