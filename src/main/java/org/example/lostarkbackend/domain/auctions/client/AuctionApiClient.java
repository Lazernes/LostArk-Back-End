package org.example.lostarkbackend.domain.auctions.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.lostarkbackend.domain.auctions.dto.request.AuctionItemSearchRequest;
import org.example.lostarkbackend.domain.auctions.dto.response.AuctionItemSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuctionApiClient {

    private final WebClient webClient;

    @Value("${lostark.api.token}")
    private String apiToken;

    public AuctionItemSearchResponse searchItems(AuctionItemSearchRequest request) {

        return webClient.post()
                .uri("/auctions/items")
                .header("Authorization", "Bearer " + apiToken)
                .bodyValue(request)
                .retrieve()
                .bodyToMono(AuctionItemSearchResponse.class)
                .block();
    }
}
