package vasilkov.lab1bpls.service;

import lombok.AllArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vasilkov.lab1bpls.entity.Model;
import vasilkov.lab1bpls.entity.Order;
import vasilkov.lab1bpls.entity.Product;
import vasilkov.lab1bpls.entity.User;
import vasilkov.lab1bpls.exception.ResourceNotFoundException;
import vasilkov.lab1bpls.model.MessageResponse;
import vasilkov.lab1bpls.model.OrderRequest;
import vasilkov.lab1bpls.repository.*;
import vasilkov.lab1bpls.specifications.OrderWithBrandName;
import vasilkov.lab1bpls.specifications.OrderWithCityName;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class OrderService {

    UserRepository userRepository;
    BrandRepository brandRepository;
    ModelRepository modelRepository;
    OrderRepository orderRepository;
    ProductRepository productRepository;


    public MessageResponse save(OrderRequest orderRequestModel) {
        Order order = new Order();
        if (orderRequestModel.getDescription() != null) order.setDescription(orderRequestModel.getDescription());
        if (orderRequestModel.getColor() != null) order.setColor(orderRequestModel.getColor());
        if (orderRequestModel.getMaterial() != null) order.setMaterial(orderRequestModel.getMaterial());
        if (orderRequestModel.getCountry_of_origin() != null)
            order.setCountry_of_origin(orderRequestModel.getCountry_of_origin());
        if (orderRequestModel.getNumber_of_pieces_in_a_package() != null)
            order.setNumber_of_pieces_in_a_package(orderRequestModel.getNumber_of_pieces_in_a_package());
        if (orderRequestModel.getGuarantee_period() != null)
            order.setGuarantee_period(orderRequestModel.getGuarantee_period());
        order.setBrand(brandRepository.findBrandByName(orderRequestModel.getBrandName())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Brand Not Found")));

        Model model = modelRepository.findModelByName(orderRequestModel.getModelName())
                .orElseThrow(() -> new ResourceNotFoundException("Error: Model Not Found"));

        if (orderRequestModel.getBrandName().equals(model.getBrand().getName())) {
            order.setModel(model);
        } else {
            throw new ResourceNotFoundException("Error: This brand doesn't have this model");
        }
        User user = userRepository.findUserByEmail(((UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getUsername())
                .orElseThrow(() -> new ResourceNotFoundException("Error: User Not Found"));

        user.getOrders().add(order);
        order.setUser(user);

        userRepository.save(user);

        return (new MessageResponse("order registered successfully!"));

    }

    public MessageResponse findAndSave(Integer id) {
        //todo переписать
        Product product = productRepository.findByArticle(id).orElseThrow(() -> new ResourceNotFoundException("Error: Product Not Found"));
        save(new OrderRequest(product.getDescription(), product.getColor(), product.getMaterial(),
                product.getNumber_of_pieces_in_a_package(), product.getCountry_of_origin(),
                product.getBrand().getName(), product.getModel().getName(), product.getGuarantee_period())
        );
        return (new MessageResponse("order registered successfully!"));
    }


    public List<Order> findAllOrdersBySpecification(Map<String, String> values) {
        return orderRepository.findAll(Specification
                .where(new OrderWithBrandName(values.get("brand")))
                .and(new OrderWithCityName(values.get("city")))
        );
    }

}

