package org.example.lostarkbackend.domain.markets.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.markets.dto.MarketItemDetailResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class MarketApiClient {

    private final WebClient lostArkWebClient;

    // 특정 itemId의 거래소 시세 조회
    public List<MarketItemDetailResponse> getMarketItemDetail(Long itemId) {
        String uri = "/markets/items/" + itemId;

        try {
            return lostArkWebClient.get()
                    .uri(uri)
                    .retrieve()
                    .bodyToFlux(MarketItemDetailResponse.class)
                    .collectList()
                    .retryWhen(
                            Retry.backoff(3, Duration.ofSeconds(10))
                                    .filter(e -> e instanceof WebClientResponseException.TooManyRequests)
                    )
                    .block();
        } catch (WebClientResponseException e) {
            log.error("Lost Ark API 오류 발생: status={}, body={}",
                    e.getRawStatusCode(), e.getResponseBodyAsString());
            throw e;
        } catch (Exception e) {
            log.error("Lost Ark API 호출 중 예외 발생", e);
            throw e;
        }
    }
}
