package py.com.jaha.api.users.commons;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import py.com.jaha.api.users.config.CustomPageable;
import py.com.jaha.api.users.infraestructure.adapters.in.users.ClientsResource;
import py.com.jaha.api.users.infraestructure.adapters.in.users.UsersResource;

@Component
public class PageableComponent {

    @CustomPageable(ClientsResource.class)
    public <T> ApiPageableResponse<T> toClientsResourceApiPageableResponse(Page<T> page) {
        var pagination = new CustomPagination();
        pagination.setPage(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        var response = ApiPageableResponse.of(page.getContent());
        response.setPagination(pagination);
        return ApiPageableResponse.of(page.getContent());
    }

    @CustomPageable(UsersResource.class)
    public <T> ApiPageableResponse<T> toUsersResourceApiPageableResponse(Page<T> page) {
        var pagination = new CustomPagination();
        pagination.setPage(page.getNumber());
        pagination.setPageSize(page.getSize());
        pagination.setTotalElements(page.getTotalElements());
        pagination.setTotalPages(page.getTotalPages());
        var response = ApiPageableResponse.of(page.getContent());
        response.setPagination(pagination);
        return ApiPageableResponse.of(page.getContent());
    }
}
