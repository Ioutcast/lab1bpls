package vasilkov.lab1bpls.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import vasilkov.lab1bpls.entity.Model;
import vasilkov.lab1bpls.entity.Order;
import vasilkov.lab1bpls.entity.User;
import vasilkov.lab1bpls.model.MessageResponse;
import vasilkov.lab1bpls.model.OrderRequest;
import vasilkov.lab1bpls.repository.BrandRepository;
import vasilkov.lab1bpls.repository.ModelRepository;
import vasilkov.lab1bpls.repository.OrderRepository;
import vasilkov.lab1bpls.repository.UserRepository;

@Service
public class OrderService {
    @Autowired
    UserRepository userRepository;

    @Autowired
    BrandRepository brandRepository;

    @Autowired
    ModelRepository modelRepository;

    @Autowired
    OrderRepository orderRepository;

    public MessageResponse save(OrderRequest orderRequestModel) throws ResourceNotFoundException {
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
        //todo sop
        user.getOrders().add(order);

        userRepository.save(user);

        return (new MessageResponse("order registered successfully!"));

    }

}

