package app.service.captcha;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class CaptchaService {

    @Value("${recaptcha.secretKey}")
    private String secret;

    @Value("${recaptcha.url}")
    private String url;


    public float verify(String reCaptchaResponse) {

        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> map = new LinkedMultiValueMap<>();

        map.add("secret", secret);

        map.add("response", reCaptchaResponse);

        HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(map, headers);

        RestTemplate restTemplate = new RestTemplate();

        CaptchaResponse captchaResponse = restTemplate.postForObject(url, httpEntity, CaptchaResponse.class);

        return captchaResponse.getScore();

    }

}
