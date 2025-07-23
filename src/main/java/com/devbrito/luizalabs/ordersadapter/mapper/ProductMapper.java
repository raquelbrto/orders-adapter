package com.devbrito.luizalabs.ordersadapter.mapper;

import com.devbrito.luizalabs.ordersadapter.domain.document.Product;
import com.devbrito.luizalabs.ordersadapter.domain.dto.ProductDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "product_id", source = "productId")
    ProductDTO toProductDTO(Product product);

    List<ProductDTO> toProductDTOList(List<Product> products);

    @Mapping(target = "productId", source = "product_id")
    Product toProduct(ProductDTO productDTO);

    List<Product> toProductList(List<ProductDTO> productDTOs);
}
