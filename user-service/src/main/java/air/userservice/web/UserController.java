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

package air.userservice.web;

import air.userservice.service.User;
import air.userservice.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

import java.util.NoSuchElementException;

@RestController
@RequestMapping(path = "/users", produces = "application/json")
@CrossOrigin(origins = "*")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(params = "email")
    @PreAuthorize("hasAuthority('USER') and #email == authentication.name or hasAuthority('ADMIN')")
    public User getByEmail(@RequestParam String email) {
        return userService.findByEmail(email)
                .orElseThrow(() -> new  NoSuchElementException("No user with email " + email));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User post(@RequestBody @Valid User user) {
        return userService.save(user);
    }

    @PatchMapping(params = "email")
    @PreAuthorize("hasAuthority('USER') and #email == authentication.name or hasAuthority('ADMIN')")
    public User patchByEmail(@RequestParam String email,
                             @RequestBody User user) {
        user.setEmail(email);
        return userService.update(user);
    }

    @DeleteMapping(params = "email")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('USER') and #email == authentication.name or hasAuthority('ADMIN')")
    public void deleteByEmail(@RequestParam String email) {
        userService.deleteByEmail(email);
    }
}
