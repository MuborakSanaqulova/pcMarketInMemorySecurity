package uz.pdp.vazifa.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BasketProductDto {

    private Integer count;

    private Integer productId;

    private Integer basketId;

}
