package blog.surapong.example.logrequestresponse.api.controller;

import blog.surapong.example.logrequestresponse.api.dto.ToDoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.zalando.logbook.Logbook;
import org.zalando.logbook.spring.LogbookClientHttpRequestInterceptor;

@RestController
public class TestThirdPartyController {

    private static final Logger logger = LoggerFactory.getLogger(TestThirdPartyController.class);

    @GetMapping("/todo/{id}")
    public ToDoDto getToDo(
            @PathVariable("id") String id
    ) {
        String url = "https://jsonplaceholder.typicode.com/todos/" + id;
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<ToDoDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ToDoDto.class);
        return responseEntity.getBody();
    }


    @GetMapping("/todo/log/{id}")
    public ToDoDto getToDoLog(
            @PathVariable("id") String id
    ) {
        String url = "https://jsonplaceholder.typicode.com/todos/" + id;
        RestTemplate restTemplate = createRestTemplate();
        ResponseEntity<ToDoDto> responseEntity = restTemplate.exchange(url, HttpMethod.GET, HttpEntity.EMPTY, ToDoDto.class);
        return responseEntity.getBody();
    }






    private RestTemplate createRestTemplate() {

        SimpleClientHttpRequestFactory simpleClientHttpRequestFactory = new SimpleClientHttpRequestFactory();
        simpleClientHttpRequestFactory.setOutputStreaming(false);

        ClientHttpRequestFactory factory =
                new BufferingClientHttpRequestFactory(simpleClientHttpRequestFactory);

        RestTemplate restTemplate = new RestTemplate(factory);

        Logbook logbook = Logbook.create();
        LogbookClientHttpRequestInterceptor interceptor = new LogbookClientHttpRequestInterceptor(logbook);
        restTemplate.getInterceptors().add(interceptor);
        return  restTemplate;
    }

}
