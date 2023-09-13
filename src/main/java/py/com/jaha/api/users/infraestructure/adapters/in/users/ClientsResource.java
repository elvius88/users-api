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
import py.com.jaha.api.users.domain.commands.clients.GetClientPageableResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientResponse;
import py.com.jaha.api.users.domain.commands.clients.GetClientsResponse;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientPort;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPageablePort;
import py.com.jaha.api.users.domain.ports.in.clients.GetClientsPort;
import py.com.jaha.api.users.infraestructure.adapters.mappers.ClientsCommandMapper;

@RestController
@RequestMapping(value = "/" + API_BASE + "/clients/" + API_VERSION_V1)
@Tag(name = "ClientsResource")
@RequiredArgsConstructor
@Slf4j
public class ClientsResource {
    
    private final GetClientPort getClientUseCase;
    private final GetClientsPort getClientsUseCase;
    private final GetClientsPageablePort getClientsPageableUseCase;
    private final PageableComponent pageableComponent;

    @Operation(summary = "Client", description = "Get client data by ID")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetClientResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/clients/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetClientResponse> getClient(@PathVariable String id) {
        return ApiResponse.of(
            getClientUseCase.execute(
                ClientsCommandMapper.INSTANCE.toCommand(id, null, null, null, null, null)));
    }

    @Operation(summary = "Clients", description = "Get clients data by parameters filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetClientsResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/clients")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<GetClientsResponse> getClients(@RequestParam(required = false) String name,
                                                      @RequestParam(required = false) String lastname,
                                                      @RequestParam(required = false) String email,
                                                      @RequestParam(required = false) String city) {
        return ApiResponse.of(
            getClientsUseCase.execute(
                ClientsCommandMapper.INSTANCE.toCommand(null, name, lastname, email,  city,null)));
    }

    @Operation(summary = "Clients", description = "Get clients data by parameters filters")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "Ok", content = @Content(mediaType = "application/json", schema = @Schema(implementation = GetClientPageableResponse.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "400", description = "Bad Request", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "401", description = "Unauthorized", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "500", description = "Internal Server Error", content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))) })
    @Filterable
    @GetMapping("/clients-search")
    @ResponseStatus(HttpStatus.OK)
    public ApiPageableResponse<GetClientPageableResponse> getClientsBy(@RequestParam(required = false) String name,
                                                                       @RequestParam(required = false) String lastname,
                                                                       @RequestParam(required = false) String email,
                                                                       @RequestParam(required = false) String city,
                                                                       @RequestParam(name = "page", defaultValue = "0") Integer page,
                                                                       @RequestParam(name = "page_size", defaultValue = "10") Integer pageSize) {
        return Try.of(() -> getClientsPageableUseCase.execute(
            ClientsCommandMapper.INSTANCE.toCommand(null, name, lastname, email, city, PageRequest.of(page, pageSize))))
            .map(pageableComponent::toClientsResourceApiPageableResponse)
            .getOrElseThrow(ex -> buildErrorCommon(log, ex));
    }
}
