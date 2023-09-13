package py.com.jaha.api.users.constants;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ErrorCodes {
    INTERNATIONAL_COUNTRIES_NOT_ALLOWED("g1001","La busqueda por paises extranjeros no esta soportada"),
    INVALID_BANK_TYPE("g1002","El tipo de banco no es válido"),
    BANK_NOT_FOUND("g1003","El banco especificado no existe"),
    INVALID_BANK_CODE("g1004","El codigo de banco especificado es invalido"),
    INVALID_OPERATION_TYPE("g1010", "El tipo de operación es incorrecto"),
    DOCUMENT_TYPE_NOT_FOUND("g1020","El tipo de documento especificado no existe"),
    INVALID_DOCUMENT_TYPE_CODE("g1021","El codigo de tipo de documento especificado es inválido"),
    CURRENCY_NOT_FOUND("g1030","La moneda especificada no existe"),
    INVALID_CURRENCY_CODE("g1031","El codigo de moneda especificado es invalido"),
    ACCOUNT_NOT_FOUND("g1040","La cuenta especificada no ha sido encontrada"),
    ACCOUNT_CURRENCY_NOT_FOUND("g1041","No se pudo determinar la moneda de la cuenta"),
    PERIOD_NOT_FOUND("g1050","El periodo especificado no ha sido encontrado"),
    INVALID_PERIOD_CODE("g1051","El codigo de periodo especificado es inválido"),
    INVALID_SEARCH("g2000","La busqueda por el dato proporcionado aun no es soportada");

    private final String code;
    private final String message;
}
