package shop.bookbom.shop.order.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import shop.bookbom.shop.order.dto.request.preOrderRquest;
import shop.bookbom.shop.order.service.OrderService;

@RestController
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @GetMapping("/selectWrapper")
    public ResponseEntity<?>

    @PostMapping("/selcetWrapper")
    public ResponseEntity<?> selectWrapper(@RequestBody preOrderRquest preOrderRquest) {

    }
}
