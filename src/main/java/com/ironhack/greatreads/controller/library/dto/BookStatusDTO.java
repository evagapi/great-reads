package com.ironhack.greatreads.controller.library.dto;

import com.ironhack.greatreads.model.library.Status;
import lombok.Data;

@Data
public class BookStatusDTO {
    private int bookId;
    private Status status;
}
