package com.airservice.booking.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
public class Booking {

    @Id
    @GeneratedValue
    private Long id;
    private String userId;
    @Column(nullable = false)
    @NotBlank()
    private String flightId;
    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private LocalDateTime dateTime;

    private Booking(Builder builder) {
        this.userId = builder.userId;
        this.flightId = builder.flightId;
        this.dateTime = builder.dateTime;
    }

    public Booking() {

    }

    public String getUserId() {
        return userId;
    }

    public String getFlightId() {
        return flightId;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Booking booking = (Booking) o;
        return Objects.equals(id, booking.id) && Objects.equals(userId, booking.userId) && Objects.equals(dateTime, booking.dateTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, userId, flightId, dateTime);
    }

    @Override
    public String toString() {
        return "UserOrder{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", flightId=" + flightId +
                ", dateTime=" + dateTime +
                '}';
    }

    public static class Builder {
        private Long id;
        private String userId;
        private String flightId;
        private LocalDateTime dateTime;

        public Builder(String userId) {
            this.userId = userId;
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder flightId(String flightId) {
            this.flightId = flightId;
            return this;
        }

        public Builder bookingDateTime(LocalDateTime dateTime) {
            this.dateTime = dateTime;
            return this;
        }

        public Booking build() {
            return new Booking(this);
        }
    }
}

