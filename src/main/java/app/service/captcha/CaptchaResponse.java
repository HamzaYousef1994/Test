package app.service.captcha;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonPropertyOrder({
        "success",
        "score",
        "action",
        "challenge_ts",
        "hostname",
        "error-codes"
})
public class CaptchaResponse {


    @JsonProperty("success")
    private boolean success;

    @JsonProperty("score")
    private float score;

    @JsonProperty("action")
    private String action;

    @JsonProperty("challenge_ts")
    private String challengeTs;

    @JsonProperty("hostname")
    private String hostname;

    @JsonProperty("error-codes")
    private String[] errorCodes;


    public boolean isSuccess() {
        return success;
    }

    public float getScore() {
        return score;
    }

    public String getAction() {
        return action;
    }

    public String getChallengeTs() {
        return challengeTs;
    }

    public String getHostname() {
        return hostname;
    }

    public String[] getErrorCodes() {
        return errorCodes;
    }

}