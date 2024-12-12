//package pl.lodz.p.controller;
//
//import jakarta.validation.Valid;
//import lombok.AllArgsConstructor;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.validation.BindingResult;
//import org.springframework.validation.annotation.Validated;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import pl.lodz.p.dto.StringDTO;
//
//@AllArgsConstructor
//@RestController
//@RequestMapping("/test")
//@Validated
//public class TestController {
//
//    @PostMapping
//    public ResponseEntity<Object> Test(@Valid @RequestBody StringDTO stringDTO, BindingResult bindingResult) {
//        if (bindingResult.hasErrors()) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bindingResult.getAllErrors());
//        }
//        return ResponseEntity.ok(stringDTO.getString());
//    }
//}