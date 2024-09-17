package org.examen.g6.ex3.client;

import org.examen.g6.ex3.aggregates.response.ReniecResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "reniec-client", url = "https://api.apis.net.pe/v2/reniec/")
public interface ReniecClient {

    @GetMapping("/dni")
    ReniecResponse getPersonaReniec(@RequestParam("numero") String numero,
                                    @RequestHeader("Authorization") String authorization);
}