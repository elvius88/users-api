package py.com.jaha.api.users.config;

import static py.com.jaha.api.users.constants.GlobalConstants.PARAM_PAGE_KEY_NAME;
import static py.com.jaha.api.users.constants.GlobalConstants.PARAM_PAGE_SIZE_NAME;

import java.util.Map;
import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.web.util.UriComponentsBuilder;
import py.com.jaha.api.users.commons.ApiPageableResponse;
import py.com.jaha.api.users.commons.CustomPagination;

@Aspect
@Component
public class CustomPageableAspect {

  @Around("@annotation(py.com.jaha.api.users.config.CustomPageable) && execution(py.com.jaha.api.users.commons.ApiPageableResponse+ *(..)) && args(page, ..)")
  public Object pageLinksAdvice(ProceedingJoinPoint joinPoint, Page<?> page) throws Throwable {
    HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
    UriComponentsBuilder original = originalUri(request);
    ApiPageableResponse<?> response = (ApiPageableResponse<?>) joinPoint.proceed();
    CustomPagination customPagination = new CustomPagination();

    final String uriForFirstPage = constructFirstPageUri(original, page.getSize());
    customPagination.getLinks().setFirst(uriForFirstPage);

    if (page.hasNext()) {
      UriComponentsBuilder nextBuilder = replacePageParams(original, page.nextPageable());
      customPagination.getLinks().setNext(nextBuilder.encode().toUriString());
    }

    if (page.hasPrevious()) {
      UriComponentsBuilder prevBuilder = replacePageParams(original, page.previousPageable());
      customPagination.getLinks().setPrevious(prevBuilder.encode().toUriString());
    }

    final String uriForLastPage = constructLastPageUri(original, page.getTotalPages(), page.getSize());
    customPagination.getLinks().setLast(uriForLastPage);

    customPagination.setPage(page.getNumber());
    customPagination.setTotalPages(page.getTotalPages());
    customPagination.setTotalElements(page.getTotalElements());
    customPagination.setPageSize(page.getSize());
    response.setPagination(customPagination);

    return response;
  }
  String constructFirstPageUri(final UriComponentsBuilder original, final int size) {
    UriComponentsBuilder builder = original.cloneBuilder();
    return builder.replaceQueryParam(PARAM_PAGE_KEY_NAME, 0).replaceQueryParam(PARAM_PAGE_SIZE_NAME, size).build().encode().toUriString();
  }

  String constructLastPageUri(final UriComponentsBuilder original, final int totalPages, final int size) {
    UriComponentsBuilder builder = original.cloneBuilder();
    return builder.replaceQueryParam(PARAM_PAGE_KEY_NAME, totalPages - 1).replaceQueryParam(PARAM_PAGE_SIZE_NAME, size).build().encode().toUriString();
  }

  private UriComponentsBuilder originalUri(HttpServletRequest request) {
    ServletUriComponentsBuilder baseUri = ServletUriComponentsBuilder.fromCurrentRequest();
    baseUri.scheme(null).host(null).port(null);
    for (Map.Entry<String, String[]> entry : request.getParameterMap().entrySet()) {
      for (String value : entry.getValue()) {
        baseUri.queryParam(entry.getKey(), value);
      }
    }
    return baseUri;
  }

  private UriComponentsBuilder replacePageParams(UriComponentsBuilder original, Pageable pageable) {
    UriComponentsBuilder builder = original.cloneBuilder();
    builder.replaceQueryParam(PARAM_PAGE_KEY_NAME, pageable.getPageNumber());
    builder.replaceQueryParam(PARAM_PAGE_SIZE_NAME, pageable.getPageSize());
    return builder;
  }
}




