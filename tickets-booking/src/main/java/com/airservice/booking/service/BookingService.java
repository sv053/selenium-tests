package com.airservice.booking.service;

import com.airservice.booking.exception.EntityNotFoundException;
import com.airservice.booking.model.Booking;
import com.airservice.booking.repository.BookingRepository;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {
   private final Logger logger = LogManager.getLogger(BookingService.class);

   private final BookingRepository bookingRepository;
   private final KafkaTemplate<String, String> kafkaTemplate;

   @Autowired
   public BookingService(KafkaTemplate<String, String> kafkaTemplate,
                         BookingRepository bookingRepository) {
      this.bookingRepository = bookingRepository;
      this.kafkaTemplate = kafkaTemplate;
   }

   public List<Booking> findAllBookings() {
      return bookingRepository.findAll();
   }

   public void removeBooking(Long bookingId) {
      bookingRepository.deleteById(bookingId);
   }

   public Booking updateBooking(Booking booking) {
      return bookingRepository.save(booking);
   }

   public Booking findBookingById(Long id) {
      return bookingRepository.findById(id).orElseThrow(()-> new EntityNotFoundException(id.toString()));
   }

   public Optional<Booking> findById(Long id) {
      return bookingRepository.findById(id);
   }

   public Booking createBooking(Booking booking) {
      Booking saved = bookingRepository.saveAndFlush(booking);
      sendMessage("Booking made: " + saved.getId());
      return saved;
   }

   private void sendMessage(String message) {
      try {
         kafkaTemplate.send("order_statistics", message);
      } catch (Exception e) {
         logger.error("Kafka error:" + e);
      }
   }
}

