package com.fyp.tradeservice.traderservice.Controller;

import com.fyp.tradeservice.traderservice.Component.ControllerHelper;
import com.fyp.tradeservice.traderservice.Component.UrlPropertyBundle;
import com.fyp.tradeservice.traderservice.DTO.CompleteOrderRequest;
import com.fyp.tradeservice.traderservice.DTO.EholeDTO;
import com.fyp.tradeservice.traderservice.DTO.OrderDTO;
import com.fyp.tradeservice.traderservice.DTO.PlaceorderRequest;
import com.fyp.tradeservice.traderservice.Service.OrderService;
import com.fyp.tradeservice.traderservice.Util.CustomPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Controller
@RequestMapping(value = "/api/v1/order")
public class PlaceorderController {

    @Autowired
    OrderService orderService;

    @Autowired
    ControllerHelper controllerHelper;

    @Autowired
    UrlPropertyBundle urlPropertyBundle;

    @Autowired
    RestTemplate restTemplate;

    @RequestMapping(value = "trade", method = RequestMethod.POST)
    public ResponseEntity placeTrade(@RequestBody PlaceorderRequest placeorderRequest, OAuth2Authentication authentication) {

        if (placeorderRequest.getPrice() > 0 && placeorderRequest.getSize() > 0 && placeorderRequest.getOrderAction() != null) {

            String scope = controllerHelper.getScope(authentication);
            CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);
            String accessToken = controllerHelper.getAccessToken(authentication);
            String url = urlPropertyBundle.getEholePaymentUrl() + "/ehole/find/" + placeorderRequest.getEholeId();

            HttpHeaders headers = new HttpHeaders();
            headers.add("Authorization", "Bearer " + accessToken);
            headers.add("Content-Type", "application/json");
            HttpEntity httpEntity = new HttpEntity(headers);
            ResponseEntity<EholeDTO> exchange = restTemplate.exchange(url, HttpMethod.GET, httpEntity, EholeDTO.class);
            if (!exchange.getStatusCode().is2xxSuccessful()) {
                return new ResponseEntity(exchange.getStatusCode());
            }
            double eholeInvestedAmount = orderService.getEholeInvestedAmount(placeorderRequest.getEholeId());
            if (placeorderRequest.getSize() < (exchange.getBody().getAmount() - eholeInvestedAmount)) {
                return new ResponseEntity(HttpStatus.BAD_REQUEST);
            }

            try {
                OrderDTO orderDTO = orderService.placeOrder(placeorderRequest, Long.parseLong(customPrincipal.getId()));
                orderDTO.setProfitStatus(null);
                return new <OrderDTO>ResponseEntity(orderDTO, HttpStatus.CREATED);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
            }


        }
        return new ResponseEntity(HttpStatus.BAD_REQUEST);
    }

    @RequestMapping(value = "trade", method = RequestMethod.PUT)
    public ResponseEntity endTrade(@RequestBody CompleteOrderRequest completeOrderRequest) {
        OrderDTO orderById = orderService.getOrderById(completeOrderRequest.getOrderId());
        if (orderById == null) {
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        OrderDTO orderDTO = orderService.endOrder(completeOrderRequest.getOrderId(), completeOrderRequest.getEndPrice());
        return new <OrderDTO>ResponseEntity(orderDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/trader")
    public ResponseEntity getOrdersByTrader(OAuth2Authentication authentication){
        CustomPrincipal customPrincipal = controllerHelper.getCustomPrincipal(authentication);
        List<OrderDTO> orderByTrader = orderService.getOrderByTrader(Long.parseLong(customPrincipal.getId()));
        return new ResponseEntity(orderByTrader, HttpStatus.OK);
    }

    @GetMapping(path = "/ehole/{id}")
    public ResponseEntity getOrdersByEhole(@PathVariable("id") long id){

        List<OrderDTO> orderByTrader = orderService.getOrderbyEhole(id);
        return new ResponseEntity(orderByTrader, HttpStatus.OK);
    }

}
