package py.com.jaha.api.users.infraestructure.adapters.in.users;

import static py.com.jaha.api.users.commons.ApiVersions.API_VERSION_V1;
import static py.com.jaha.api.users.constants.GlobalConstants.API_BASE;
import static py.com.jaha.api.users.domain.usecases.utils.LogUtil.buildErrorCommon;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.vavr.control.Try;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import py.com.jaha.api.users.commons.ApiPageableResponse;
import py.com.jaha.api.users.commons.ApiResponse;
import py.com.jaha.api.users.commons.Filterable;
import py.com.jaha.api.users.commons.PageableComponent;
import py.com.jaha.api.users.config.exception.ApiError;
import py.com.jaha.api.users.domain.commands.users.GetUserPageableResponse;
import py.com.jaha.api.users.domain.commands.users.GetUserResponse;
import py.com.jaha.api.users.domain.commands.users.GetUsersResponse;
import py.com.jaha.api.users.domain.ports.in.users.GetUserPort;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPageablePort;
import py.com.jaha.api.users.domain.ports.in.users.GetUsersPort;
import py.com.jaha.api.users.infraestructure.adapters.mappers.UsersCommandMapper;

@RestController
@RequestMapping(value = "/" + API_BASE + "/users/" + API_VERSION_V1)
@Tag(name = "UsersResource")
@RequiredArgsConstructor
@Slf4j
public class UsersResource {

    private final GetUserPort getUserUseCase;
    private final GetUsersPort getUsersUseCase;
    private final GetUsersPageablePort getUsersPageableUseCase;
    private final PageableComponent pageableComponent;

    @Operation(summary = "User", description = "Get establishment data by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/establishments/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetUserResponse> getUser(@PathVariable String id) {
        return ApiResponse.of(
            getUserUseCase.execute(
                UsersCommandMapper.INSTANCE.toCommand(id, null, null, null, null, null, null)));
    }

    @Operation(summary = "Users", description = "Get establishments data by parameters filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUsersResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/establishments")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetUsersResponse> getUsers(@RequestParam(required = false) String name,
                                                  @RequestParam(name = "client_id", required = false) String clientId,
                                                  @RequestParam(name = "establishment_Id", required = false) String establishmentId) {
        return ApiResponse.of(
            getUsersUseCase.execute(
                UsersCommandMapper.INSTANCE.toCommand(null, name, clientId, establishmentId, null, null, null)));
    }

    @Operation(summary = "Users", description = "Get establishments data by parameters filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetUserPageableResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/establishments/nearby")
    @ResponseStatus(HttpStatus.OK)
    public ApiPageableResponse<GetUserPageableResponse> getUsersNearby(@RequestParam(required = false) String latitude,
                                                                                @RequestParam(required = false) String longitude,
                                                                                @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                                @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize) {
        return Try.of(() -> getUsersPageableUseCase.execute(
            UsersCommandMapper.INSTANCE.toCommand(null, null, null, null, latitude, longitude, PageRequest.of(page, pageSize))))
            .map(pageableComponent::toClientsResourceApiPageableResponse)
            .getOrElseThrow(ex -> buildErrorCommon(log, ex));
    }
}
