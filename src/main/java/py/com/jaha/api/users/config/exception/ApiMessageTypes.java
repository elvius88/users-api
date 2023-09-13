package py.com.jaha.api.users.config.exception;

public enum ApiMessageTypes {
  INFO,
  WARNING,
  ERROR,
  FATAL,
  CRITICAL,
  APPLICATION;

  private ApiMessageTypes() {
  }
}
