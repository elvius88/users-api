package py.com.jaha.api.users.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@DtoMeta
@Data
@NoArgsConstructor
@AllArgsConstructor
public class IdNameDto<K, V> {

  private K id;
  private V name;

  public static <K, V> IdDescDto<K, V> of(K id, V description) {
    return new IdDescDto<>(id, description);
  }
}
