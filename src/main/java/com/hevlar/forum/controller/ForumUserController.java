package com.hevlar.forum.controller;

import com.hevlar.forum.controller.dto.ErrorDto;
import com.hevlar.forum.controller.dto.UserRegistrationDto;
import com.hevlar.forum.controller.dto.UserRoleDto;
import com.hevlar.forum.controller.mapper.ForumUserToUserRoleMapper;
import com.hevlar.forum.model.ForumUser;
import com.hevlar.forum.service.ForumUserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@CrossOrigin(origins = {"*"})
@RequestMapping("/api/v1/users")
public class ForumUserController {

    ForumUserService userService;

    public ForumUserController(ForumUserService userService){
        this.userService = userService;
    }

    @PostMapping("/registerUser")
    public UserRoleDto registerUser(@RequestBody @Valid UserRegistrationDto userRegistrationDto) {
        try {
            ForumUser user = this.userService.registerUser(userRegistrationDto);
            return ForumUserToUserRoleMapper.map(user);
        }catch (Exception e){
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        }
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public List<ErrorDto> handleValidationExceptions(MethodArgumentNotValidException ex){
        return ex.getBindingResult()
                .getAllErrors()
                .stream()
                .map(ForumUserController::getErrorDto)
                .toList();
    }

    private static ErrorDto getErrorDto(ObjectError error) {
        String field = error instanceof FieldError fieldError ? fieldError.getField() : null;
        String rejectedValue = error instanceof FieldError fieldError ? String.valueOf(fieldError.getRejectedValue()) : null;

        return new ErrorDto(
                error.getObjectName(),
                field,
                rejectedValue,
                error.getDefaultMessage());
    }
}
