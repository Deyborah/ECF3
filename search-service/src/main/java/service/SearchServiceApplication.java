
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SearchServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(SearchServiceApplication.class, args);
    }
}


@Service
public class SearchService {
@Autowired
private RestTemplate restTemplate;

public List<Suggestion> getSuggestions(String query) {
    String url = "http://common-service:9000/api/products";  // ou /products/search selon ton API

    ResponseEntity<Product[]> response = restTemplate.getForEntity(url, Product[].class);
    Product[] products = response.getBody();

    if (products == null) return Collections.emptyList();

    return Arrays.stream(products)
            .filter(p -> p.getName().toLowerCase().contains(query.toLowerCase()))
            .map(p -> new Suggestion(p.getName(), p.getId()))
            .collect(Collectors.toList());
}
}