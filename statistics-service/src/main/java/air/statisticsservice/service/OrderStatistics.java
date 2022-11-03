package air.statisticsservice.service;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import java.util.Objects;

@Entity
@Table(name = "statistics")
public class OrderStatistics {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "average_per_day")
    private Long averagePerDay;

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

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }

        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        OrderStatistics that = (OrderStatistics) other;
        return Objects.equals(averagePerDay, that.averagePerDay);
    }

    @Override
    public int hashCode() {
        return Objects.hash(averagePerDay);
    }

    @Override
    public String toString() {
        return "OrderStatistics{" +
                "id=" + id +
                ", averagePerDay=" + averagePerDay +
                '}';
    }
}
