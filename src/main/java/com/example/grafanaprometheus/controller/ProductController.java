package com.example.grafanaprometheus.controller;

import com.example.grafanaprometheus.model.product.Product;
import com.example.grafanaprometheus.model.product.ProductDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/products")
public class ProductController {

    final static Logger logger = LoggerFactory.getLogger(ProductController.class);

    private static ProductDAO productDAO = new ProductDAO();
    private static final Product invalidProduct = new Product("non-existing product", "no-brand", 0);


    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<Product>> getAll(){
        List<Product> listProducts= productDAO.getAll();
        if(listProducts.isEmpty()) {
            logger.info("Product list is currently empty");
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(listProducts, HttpStatus.OK);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Product getById(@PathVariable("id") long id){
        Product product = productDAO.get(id).orElseGet(()->invalidProduct);
        if(product == null) {
            logger.warn("There is no such log with id="+id);
            ResponseEntity.notFound().build();
        }
        return product;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<Product> createNewProduct(@RequestBody Product product){
        productDAO.save(product);
        logger.info("Successfully save new product: "+product.toString());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<Product> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody Product productForm) {
        Optional<Product> optionalProduct = productDAO.get(id);
        return optionalProduct.map(prod -> {
            String oldProd = prod.toString();
            prod.setName(productForm.getName());
            prod.setBrand(productForm.getBrand());
            prod.setPrice(productForm.getPrice());
            logger.info("Successfully update " + oldProd + " to " + prod.toString());
            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("There is no such log with id="+id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        Optional<Product> optionalProduct = productDAO.get(id);
        return optionalProduct.map(prod -> {
            productDAO.delete(prod);
            logger.info("Successfully remove product " + prod.toString());
            return new ResponseEntity(HttpStatus.OK);
        }).orElseGet(() -> {
            logger.warn("There is no such log with id="+id);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        });
    }
}
