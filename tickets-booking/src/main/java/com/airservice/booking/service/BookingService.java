package com.airservice.booking.service;

import com.airservice.booking.exception.EntityNotFoundException;
import com.airservice.booking.model.Booking;
import com.airservice.booking.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService {

   private final BookingRepository bookingRepository;

   public BookingService(BookingRepository bookingRepository) {
      this.bookingRepository = bookingRepository;
   }

   public List<Booking> findAllBookings() {
      return (List<Booking>) bookingRepository.findAll();
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

   public Booking createBooking(Booking booking) {
      return bookingRepository.saveAndFlush(booking);
   }
}

