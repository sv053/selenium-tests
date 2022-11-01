/*
 * Copyright 2002-2021 Solera.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package air.airlineservice.web.flights;

import air.airlineservice.service.flight.Flight;
import air.airlineservice.service.flight.FlightService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/flights", produces = "application/json")
@CrossOrigin(origins = "*")
public class FlightController {
    private final FlightService flightService;

    @Autowired
    public FlightController(FlightService flightService) {
        this.flightService = flightService;
    }

    @GetMapping
    public List<Flight> getAll() {
        return flightService.findAll();
    }

    @GetMapping(params = "airline")
    public List<Flight> getAllByAirline(@RequestParam Long airline) {
        return flightService.findByAirlineId(airline);
    }

    @GetMapping(value = "/{id}")
    public Flight getById(@PathVariable Long id) {
        return flightService.findById(id)
                .orElseThrow(() -> new  NoSuchElementException("No flight with ID " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("@flightAccessHandler.canPost(#flight.airline.id)")
    public Flight post(@RequestBody @Valid Flight flight) {
        return flightService.save(flight);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("@flightAccessHandler.canPatch(#flight.airline.id)")
    public Flight patchById(@PathVariable Long id,
                             @RequestBody Flight flight) {
        flight.setId(id);
        return flightService.update(flight);
    }
}
