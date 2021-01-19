package sbal.stels;

import javax.persistence.*;

@Entity
@Table(name="MenuTable")
public class Menu {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(nullable = false)
    private String name;

    private Integer cost;

    private Double weight;

    @Column(name="isDiscountAvailable")
    private Boolean isDiscount;

    public Menu() {
    }

    public Menu(String name) {
        this.name = name;
    }

    public Menu(String name, Integer cost, Double weight) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
    }

    public Menu(String name, Integer cost, Double weight, Boolean isDiscount) {
        this.name = name;
        this.cost = cost;
        this.weight = weight;
        this.isDiscount = isDiscount;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCost() {
        return cost;
    }

    public void setCost(Integer cost) {
        this.cost = cost;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Boolean getDiscount() {
        return isDiscount;
    }

    public void setDiscount(Boolean discount) {
        isDiscount = discount;
    }

    @Override
    public String toString() {
        return "Menu{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cost=" + cost +
                ", weight=" + weight +
                ", isDiscount=" + isDiscount +
                '}';
    }

}
