package pl.sda.service;

import pl.sda.dao.ImageDao;
import pl.sda.dao.ImageDaoImpl;
import pl.sda.dao.ProductDao;
import pl.sda.dao.ProductDaoImpl;
import pl.sda.entity.ProductEntity;
import pl.sda.mapper.ProductMapper;
import pl.sda.model.Brand;
import pl.sda.model.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static pl.sda.mapper.ProductMapper.*;

public class ProductServiceImpl implements ProductService {

    private ProductDao productDao = new ProductDaoImpl();
    private ImageDao imageDao = new ImageDaoImpl();

    @Override
    public List<Product> getAllProducts() {
        List<ProductEntity> allProductsEntities = productDao.getAllProductsEntities();
        List<Product> products = new ArrayList<>();
        for (ProductEntity productEntity : allProductsEntities) {
            products.add(mapToProduct(productEntity, imageDao.getImagePath(productEntity.getId())));
        }
        return products;
    }

    @Override
    public Product getProductById(Long id) {
        return mapToProduct(productDao.getProductById(id), imageDao.getImagePath(id));
    }

    @Override
    public List<Product> getProductsByBrands(List<Brand> brands) {

       return getAllProducts().stream().filter(p->brands.contains(p.getBrand())).collect(Collectors.toList());

    }
}
