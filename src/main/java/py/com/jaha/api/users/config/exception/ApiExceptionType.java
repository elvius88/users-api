package py.com.jaha.api.users.config.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public enum ApiExceptionType {
  SYSTEM,      // Cualquier error interno. bd, conf, memoria..
  AUTHENTICATION, //Error al autenticarse
  SECURITY,      //Error de seguridad, acceder a recursos sin permisos
  COMMUNICATION, //Comunicacion con un sistema externo
  APPLICATION   //error del cliente o logica de negocio
}
