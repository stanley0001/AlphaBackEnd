package com.example.demo.services.bps;

import com.example.demo.model.Charges;
import com.example.demo.model.Products;
import com.example.demo.model.models.productCreation;
import com.example.demo.services.ChargeService;
import com.example.demo.services.ProductService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@Log4j2
public class Bps {
    private final ProductService productService;
    private final ChargeService chargeService;

    @Autowired
    public Bps(ProductService productService, ChargeService chargeService) {
        this.productService = productService;
        this.chargeService = chargeService;
    }

    public productCreation saveProduct(productCreation product){
        Products modifiedProduct=product.getProduct();

        Products product1=productService.saveProduct(modifiedProduct);
        List<Charges> charges=product.getCharges();
        for (Charges c:charges
             ) {
            c.setCreatedAt(LocalDate.now());
            c.setUpdatedAt(LocalDate.now());
            c.setProductId(product1.getId().toString());
             chargeService.createCharge(c);
        }
        return product;
    }
}
