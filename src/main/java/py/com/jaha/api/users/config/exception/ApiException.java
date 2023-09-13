package py.com.jaha.api.users.config.exception;

public class ApiException extends RuntimeException {
  private String code;
  private ApiExceptionType type;
  private String userMessage;
  private Boolean useApiMessage;
  private Integer httpStatus;

  public ApiException(String message) {
    super(message);
  }

  public ApiException(String message, Throwable cause) {
    super(message, cause);
  }

  public ApiException(Throwable cause) {
    super(cause);
  }

  public ApiException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public ApiException(String code, String message, Throwable cause) {
    super(message, cause);
    this.userMessage = message;
    this.code = code;
  }

  public ApiException(String code, String message, String userMessage, Integer httpStatus, Boolean useApiMessage) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
    this.userMessage = userMessage;
    this.useApiMessage = useApiMessage;
  }

  public ApiException(String code, String message, String userMessage, Integer httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
    this.userMessage = userMessage;
    this.useApiMessage = true;
  }

  public ApiException(String code, String message, Integer httpStatus, Boolean useApiMessage) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
    this.userMessage = message;
    this.useApiMessage = useApiMessage;
  }

  public ApiException(String code, String message, Integer httpStatus) {
    super(message);
    this.code = code;
    this.httpStatus = httpStatus;
    this.userMessage = message;
    this.useApiMessage = true;
  }

  public ApiMessage toErrorMessage() {
    ApiMessage em = new ApiMessage();
    em.setCode(this.code);
    em.setMessage(this.getMessage());
    em.setType(ApiMessageTypes.ERROR);
    em.setUserMessage(this.userMessage);
    em.setUseApiMessage(this.useApiMessage);
    return em;
  }

  public static ApiException fromErrorMessage(ApiMessage errorMessage, Integer httpStatus) {
    return new ApiException(errorMessage.getCode(), errorMessage.getMessage(), errorMessage.getUserMessage(), httpStatus, errorMessage.getUseApiMessage());
  }

  /** @deprecated */
  @Deprecated
  public ApiException(String message, String code, ApiExceptionType type, String userMessage, Boolean useApiMessage) {
    super(message);
    this.code = code;
    this.type = type;
    this.userMessage = userMessage;
    this.useApiMessage = useApiMessage;
  }

  public ApiException(String code, String message, ApiExceptionType type) {
    super(message);
    this.userMessage = message;
    this.code = code;
    this.type = type;
  }

  /** @deprecated */
  @Deprecated
  public static ApiException fromErrorMessage(ApiMessage errorMessage) {
    return new ApiException(errorMessage.getCode(), errorMessage.getMessage(), ApiExceptionType.APPLICATION);
  }

  /** @deprecated */
  @Deprecated
  public ApiExceptionType getType() {
    return this.type;
  }

  /** @deprecated */
  @Deprecated
  public void setType(ApiExceptionType type) {
    this.type = type;
  }

  public static ApiExceptionBuilder builder() {
    return new ApiExceptionBuilder();
  }

  public ApiException() {
  }

  public ApiException(final String code, final ApiExceptionType type, final String userMessage, final Boolean useApiMessage, final Integer httpStatus) {
    this.code = code;
    this.type = type;
    this.userMessage = userMessage;
    this.useApiMessage = useApiMessage;
    this.httpStatus = httpStatus;
  }

  public String getCode() {
    return this.code;
  }

  public String getUserMessage() {
    return this.userMessage;
  }

  public Boolean getUseApiMessage() {
    return this.useApiMessage;
  }

  public Integer getHttpStatus() {
    return this.httpStatus;
  }

  public void setCode(final String code) {
    this.code = code;
  }

  public void setUserMessage(final String userMessage) {
    this.userMessage = userMessage;
  }

  public void setUseApiMessage(final Boolean useApiMessage) {
    this.useApiMessage = useApiMessage;
  }

  public void setHttpStatus(final Integer httpStatus) {
    this.httpStatus = httpStatus;
  }

  public static class ApiExceptionBuilder {
    private String code;
    private ApiExceptionType type;
    private String userMessage;
    private Boolean useApiMessage;
    private Integer httpStatus;

    ApiExceptionBuilder() {
    }

    public ApiExceptionBuilder code(final String code) {
      this.code = code;
      return this;
    }

    public ApiExceptionBuilder type(final ApiExceptionType type) {
      this.type = type;
      return this;
    }

    public ApiExceptionBuilder userMessage(final String userMessage) {
      this.userMessage = userMessage;
      return this;
    }

    public ApiExceptionBuilder useApiMessage(final Boolean useApiMessage) {
      this.useApiMessage = useApiMessage;
      return this;
    }

    public ApiExceptionBuilder httpStatus(final Integer httpStatus) {
      this.httpStatus = httpStatus;
      return this;
    }

    public ApiException build() {
      return new ApiException(this.code, this.type, this.userMessage, this.useApiMessage, this.httpStatus);
    }

    public String toString() {
      return "ApiException.ApiExceptionBuilder(code=" + this.code + ", type=" + this.type + ", userMessage=" + this.userMessage + ", useApiMessage=" + this.useApiMessage + ", httpStatus=" + this.httpStatus + ")";
    }
  }
}