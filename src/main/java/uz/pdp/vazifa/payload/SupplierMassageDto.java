package uz.pdp.vazifa.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SupplierMassageDto {

    private String supplierMessage;

    private Integer userId;

}
