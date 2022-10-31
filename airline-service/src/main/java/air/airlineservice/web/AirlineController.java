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

package air.airlineservice.web;

import air.airlineservice.service.airline.Airline;
import air.airlineservice.service.airline.AirlineService;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/airlines", produces = "application/json")
@CrossOrigin(origins = "*")
public class AirlineController {
    private final AirlineService airlineService;

    @Autowired
    public AirlineController(AirlineService airlineService) {
        this.airlineService = airlineService;
    }

    @GetMapping
    public List<Airline> getAll() {
        return airlineService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Airline getById(@PathVariable Long id) {
        return airlineService.findById(id)
                .orElseThrow(() -> new  NoSuchElementException("No airline with ID " + id));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('USER') and #airline.owner == authentication.name or hasAuthority('ADMIN')")
    public Airline post(@RequestBody @Valid Airline airline) {
        return airlineService.save(airline);
    }

    @PatchMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('USER') and #airline.owner == authentication.name or hasAuthority('ADMIN')")
    public Airline patchById(@PathVariable Long id,
                             @RequestBody Airline airline) {
        airline.setId(id);
        return airlineService.update(airline);
    }
}
