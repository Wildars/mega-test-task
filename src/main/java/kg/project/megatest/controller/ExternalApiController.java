package kg.project.megatest.controller;

import kg.project.megatest.service.ExternalApiService;
import kg.project.megatest.service.impl.ExternalApiServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/external")
public class ExternalApiController {

    private final ExternalApiServiceImpl externalApiService;

    @Autowired
    public ExternalApiController(ExternalApiServiceImpl externalApiService) {
        this.externalApiService = externalApiService;
    }

    @GetMapping("/fetch-api-data")
    public ResponseEntity<String> fetchData() {
        String data = externalApiService.fetchAndLogData();

        return ResponseEntity.ok(data);
    }
}
