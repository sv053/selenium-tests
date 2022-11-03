package air.statisticsservice.service;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "order_statistics")
public class OrderStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "average_per_day")
    private Long averagePerDay;

    @Column(name = "average_per_day")
    @JsonIgnore
    private Long orders;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAveragePerDay() {
        return averagePerDay;
    }

    public void setAveragePerDay(Long averagePerDay) {
        this.averagePerDay = averagePerDay;
    }

    public Long getOrders() {
        return orders;
    }

    public void setOrders(Long orders) {
        this.orders = orders;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        OrderStatistics that = (OrderStatistics) other;
        return Objects.equals(averagePerDay, that.averagePerDay)
                && Objects.equals(orders, that.orders);
    }

    @Override
    public int hashCode() {
        return Objects.hash(averagePerDay, orders);
    }

    @Override
    public String toString() {
        return "OrderStatistics{" +
                "id=" + id +
                ", averagePerDay=" + averagePerDay +
                ", orders=" + orders +
                '}';
    }
}
