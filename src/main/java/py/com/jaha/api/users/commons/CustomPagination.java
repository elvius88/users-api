package py.com.jaha.api.users.commons;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import py.com.jaha.api.users.config.DtoMeta;

@DtoMeta
@NoArgsConstructor
@Getter
@Setter
@ToString
public class CustomPagination {

  private Links links = new Links();
  private Integer page;
  private Integer totalPages;
  private Long totalElements;
  private Integer pageSize;



  @JsonIgnoreProperties(ignoreUnknown = true)
  @NoArgsConstructor
  @Getter
  @Setter
  @ToString
  public static class Links {
    private String first;
    private String last;
    private String previous;
    private String next;

  }
}
