package ecommerce.infrastructure.adapter.in.web.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDto {
  private Integer id;

  @NotBlank(message = "Name is mandatory")
  private String name;

  @NotBlank(message = "Name is mandatory")
  private String description;

  @NotNull
  @Min(value = 1, message = "Price must be greater than zero")
  private Double price;

  @NotNull
  @Min(value = 0, message = "Stock cannot be negative")
  private Integer stock;

  private String imageUrl;
}
