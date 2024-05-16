package shop.bookbom.shop.domain.order.shceduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import shop.bookbom.shop.domain.order.service.OrderService;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderScheduler {
    private final OrderService orderService;

    @Scheduled(fixedRate = 600000) // 10분마다 실행 (1분 = 60000 밀리초)
    public void recoverStock() {
        // 실행할 작업 내용
        log.info("10분마다 실행되는 주문 재고 회복 작업을 수행합니다.");
        try {
            orderService.recoverStock();
        } catch (Exception e) {
            //todo 예외 처리
            throw new RuntimeException(e);
        }

    }

    //@Scheduled(cron = "0 0 9 * * ")
    //매일 9시 실행
    @Scheduled(cron = "0 0 9 * * ?")
    public void changeOrderStatusToDeliverying() {
        // 실행할 작업 내용
        log.info("하루마다 실행되는 주문 배송중으로 변경 작업을 수행합니다.");
        try {
            orderService.changeToDeliverying();
        } catch (Exception e) {
            //todo 예외 처리
            throw new RuntimeException(e);
        }

    }

    //@Scheduled(cron = "0 0 9 * * ")
    //매일 9시 실행
    @Scheduled(cron = "0 0 9 * * ?")
    public void changeOrderStatusToComplete() {
        // 실행할 작업 내용
        log.info("하루마다 실행되는 주문 완료로 변경 작업을 수행합니다.");
        try {
            orderService.changeToComplete();
        } catch (Exception e) {
            //todo 예외 처리
            throw new RuntimeException(e);
        }

    }


}
