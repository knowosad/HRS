package pl.com.bottega.hrs.model.commands;

import lombok.Getter;

@Getter
public class CommandInvalidException extends RuntimeException {

    private ValidationErrors validationErrors;

    public CommandInvalidException(ValidationErrors validationErrors) {
        this.validationErrors = validationErrors;
    }
    }
