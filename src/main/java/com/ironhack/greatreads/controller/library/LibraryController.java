package com.ironhack.greatreads.controller.library;

import com.ironhack.greatreads.controller.library.dto.BookStatusDTO;
import com.ironhack.greatreads.model.library.BookStatus;
import com.ironhack.greatreads.model.library.Library;
import com.ironhack.greatreads.model.user.User;
import com.ironhack.greatreads.service.library.LibraryService;
import com.ironhack.greatreads.service.user.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
public class LibraryController {

    @Autowired
    UserService userService;

    @Autowired
    LibraryService libraryService;

    @PostMapping("me/library")
    @ResponseStatus(HttpStatus.OK)
    public void getLibraryById(@RequestBody BookStatusDTO bookStatusDTO) throws Exception {
        User user = userService.getAuthenticatedUser();
        libraryService.addBookStatusToLibraryFromUser(user, bookStatusDTO);
    }

}
