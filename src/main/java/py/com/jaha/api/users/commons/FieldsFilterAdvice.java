package py.com.jaha.api.users.commons;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractMappingJacksonResponseBodyAdvice;
import py.com.jaha.api.users.constants.GlobalConstants;

@ControllerAdvice
@Slf4j
public class FieldsFilterAdvice extends AbstractMappingJacksonResponseBodyAdvice {

  @Override
  public boolean supports(MethodParameter returnType, Class<? extends HttpMessageConverter<?>> converterType) {
    return super.supports(returnType, converterType);
  }

  @Override
  protected MappingJacksonValue getOrCreateContainer(Object body) {
    MappingJacksonValue orCreateContainer = super.getOrCreateContainer(body);

    FilterProvider filters = new SimpleFilterProvider()
        .addFilter(GlobalConstants.JSON_FILTER_FILTERABLE,SimpleBeanPropertyFilter.serializeAllExcept());

    orCreateContainer.setFilters(filters);
    return orCreateContainer;
  }

  @Override
  protected void beforeBodyWriteInternal(MappingJacksonValue bodyContainer, MediaType contentType, MethodParameter returnType, ServerHttpRequest request, ServerHttpResponse response) {


    //aqui recien controlamos si vamos a hacer el filtro
    if(returnType.hasMethodAnnotation(Filterable.class) &&
        (returnType.getParameterType().equals(ApiResponse.class)
            || returnType.getParameterType().equals(ApiPageableResponse.class)
            || returnType.getParameterType().equals(ApiListResponse.class))) {
      log.info("We check if we should filter the response object");

      String fields = ((ServletServerHttpRequest) request).getServletRequest().getParameter(GlobalConstants.PARAM_FIELD_NAME);

      if(fields != null){
        String[] fieldNames = String.join(",","data",fields).split(",");
        FilterProvider filters = new SimpleFilterProvider()
            .addFilter(GlobalConstants.JSON_FILTER_FILTERABLE, SimpleBeanPropertyFilter.filterOutAllExcept(fieldNames));

        bodyContainer.setFilters(filters);
      }
    }
  }
}
