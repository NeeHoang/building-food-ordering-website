//package com.food.repositories.custom.impl;
//
//import com.food.model.builders.ProductBuilder;
//import com.food.model.entities.Product;
//import com.food.repositories.custom.CustomFoodRepository;
//import com.food.utils.UtilsCheckNumber;
//import jakarta.persistence.EntityManager;
//import jakarta.persistence.PersistenceContext;
//import jakarta.persistence.Query;
//import org.springframework.stereotype.Repository;
//
//import java.lang.reflect.Field;
//import java.util.List;
//@Repository
//public class CustomFoodRepositoryImpl implements CustomFoodRepository {
//    @PersistenceContext
//    private EntityManager entityManager;
//    @Override
//    public List<Product> findProduct(ProductBuilder productBuilder) {
//        String sql = "select p.* FROM products p";
//        sql += join_table(productBuilder);
//        sql += " where 1 = 1 ";
//        sql += handle_query(productBuilder);
//        sql += handle_special_query(productBuilder);
//        sql += " group by p.id; ";
//        Query query = entityManager.createNativeQuery(sql,Product.class);
//        return query.getResultList();
//    }
//    public String join_table(ProductBuilder productBuilder) {
//        String query = "";
//        Long productId = productBuilder.getCategory_id();
//        if(productId != null) {
//            query = " inner join categories c on p.category_id = c.id ";
//        }
//        return query;
//    }
//
//    public String handle_query(ProductBuilder productBuilder) {
//        String query = "";
//        try{
//            Field field[] = ProductBuilder.class.getDeclaredFields();
//            for(Field k : field){
//                k.setAccessible(true);
//                if(!k.getName().equals("category_id")){
//                    Object value = k.get(productBuilder);
//                    if(value != null){
//                        if(UtilsCheckNumber.checkNumber(value.toString())){
//                            query += " and p." + k.getName() + " = " + value;
//                        }
//                        else if (!value.toString().equals("")){
//                            query += " and p." + k.getName() + " like '%" + value + "%' ";
//                        }
//                    }
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return query;
//    }
//    public String handle_special_query(ProductBuilder productBuilder) {
//        String query = "";
//        if(productBuilder.getCategory_id() != null) {
//            query = " and p.category_id = " + productBuilder.getCategory_id();
//        }
//        return query;
//    }
//}
