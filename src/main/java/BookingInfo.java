import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookInfo {
    private String firstname;
    private String lastname;
    private Number totalprice;
    private Boolean depositpaid;
}
