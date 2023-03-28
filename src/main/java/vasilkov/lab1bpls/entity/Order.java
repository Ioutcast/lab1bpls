package vasilkov.lab1bpls.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import javax.validation.constraints.NotNull;


@Builder
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Entity
@ToString
@Table(name = "_order")
public class Order {
    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    @JsonIgnore
    private User user;

    @NotNull
    @Column(name = "description")
    private String description;

    @NotNull
    @Column(name = "color")
    private String color;

    @NotNull
    @Column(name = "material")
    private String material;

    @NotNull
    @Column(name = "number_of_pieces_in_a_package")
    private Integer number_of_pieces_in_a_package;

    @NotNull
    @Column(name = "country_of_origin")
    private String country_of_origin;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "brand_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Brand brand;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "model_id", referencedColumnName = "id")
    @JsonIgnoreProperties(value = {"applications", "hibernateLazyInitializer"})
    private Model model;

    @NotNull
    @Column(name = "guarantee_period")
    private Integer guarantee_period;

}
