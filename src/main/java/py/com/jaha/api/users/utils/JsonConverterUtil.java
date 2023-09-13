package py.com.jaha.api.users.utils;

import static py.com.jaha.api.users.config.exception.ErrorHandler.internalError;
import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.INVALID_JSON_DATA_PARAM;
import static py.com.jaha.api.users.domain.models.commons.enums.ErrorCatalog.INVALID_JSON_DATA_SERIALIZE;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.vavr.control.Try;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import py.com.jaha.api.users.constants.GlobalConstants;

@Slf4j
@UtilityClass
public class JsonConverterUtil {

  private static final ObjectMapper MAPPER;

  public static <T> String toStringJson(T object) {
    return Try.of(() -> convertStringJson(object))
        .onFailure(e -> log.error("Error al serializar los datos", e))
        .getOrElseThrow(() -> internalError(INVALID_JSON_DATA_SERIALIZE.getCode(), INVALID_JSON_DATA_SERIALIZE.getDescription()));
  }

  public static <T> String toJson(T object) {
    return Try.of(() -> convertToJson(object))
        .onFailure(e -> log.error("Error al serializar los datos", e))
        .getOrElseThrow(() -> internalError(INVALID_JSON_DATA_SERIALIZE.getCode(), INVALID_JSON_DATA_SERIALIZE.getDescription()));
  }

  public static <T> String toJsonWithoutPretty(T object) {
    return Try.of(() -> convertToJsonWithoutPretty(object))
        .onFailure(e -> log.error("Error al serializar los datos", e))
        .getOrElseThrow(() -> internalError(INVALID_JSON_DATA_SERIALIZE.getCode(), INVALID_JSON_DATA_SERIALIZE.getDescription()));
  }

  public static <T> T toObject(String jsonObject, Class<T> clazz) {
    return Try.of(() -> convertToObject(jsonObject, clazz))
        .onFailure(e -> log.error("Error al deserializar los datos", e))
        .getOrElseThrow(() -> internalError(INVALID_JSON_DATA_PARAM.getCode(), INVALID_JSON_DATA_PARAM.getDescription()));
  }

  public static <T> T toObjectFromFile(String path, Class<T> clazz) {
    return Try.of(() -> readJsonFromFile(path))
        .mapTry(jsonObject -> convertToObject(jsonObject, clazz))
        .onFailure(e -> log.error("Error al deserializar los datos", e))
        .getOrElseThrow(() -> internalError(INVALID_JSON_DATA_PARAM.getCode(), INVALID_JSON_DATA_PARAM.getDescription()));
  }

  public static <T> List<T> toObjectListFromFile(String path, Class<T> className) throws Exception {
    String jsonObject = readJsonFromFile(path);
    return convertToObjectList(className, jsonObject);
  }

  public static <T> List<T> toObjectListFromString(String jsonObject, Class<T> className) throws Exception {
    return convertToObjectList(className, jsonObject);
  }

  private static  <T> T convertToObject(String jsonObject, Class<T> clazz) throws Exception {
    return objectMapper().readValue(jsonObject, clazz);
  }

  public static <T> String convertToJson(T object) throws Exception {
    return objectMapper().writer().withDefaultPrettyPrinter().writeValueAsString(object);
  }

  private static <T> String convertToJsonWithoutPretty(T object) throws Exception {
    return objectMapper().writer().writeValueAsString(object);
  }

  public static <T> String convertStringJson(T object) throws Exception {
    return objectMapper().writeValueAsString(object);
  }

  private static String readJsonFromFile(String path) throws URISyntaxException, IOException {
    return new String(Files.readAllBytes(Paths.get(JsonConverterUtil.class.getResource(path).toURI())));
  }

  private static <T> List<T> convertToObjectList(Class<T> className, String jsonObject) throws IOException {
    ObjectMapper mapper = objectMapper();

    return mapper.readValue(jsonObject, mapper.getTypeFactory().constructCollectionType(List.class, className));
  }

  static {
    MAPPER = new Jackson2ObjectMapperBuilder()
        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
        .serializationInclusion(JsonInclude.Include.NON_NULL)
        .visibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY)
        .filters(
            new SimpleFilterProvider().addFilter(GlobalConstants.JSON_FILTER_FILTERABLE, SimpleBeanPropertyFilter.serializeAllExcept()))
        .build();
  }

  private static ObjectMapper objectMapper() {
    ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    mapper.registerModule(new Jdk8Module());
    mapper.registerModule(new JavaTimeModule());
    mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    mapper.setFilterProvider(
        new SimpleFilterProvider().addFilter(GlobalConstants.JSON_FILTER_FILTERABLE, SimpleBeanPropertyFilter.serializeAllExcept()));

    return mapper;
  }
}










