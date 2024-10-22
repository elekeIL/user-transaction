package com.transaction.dto.recaptcha;

import com.google.gson.annotations.SerializedName;
import lombok.Data;

@Data
public class ReCaptchaResponse {
    @SerializedName("success")
    public Boolean success;
    @SerializedName("challenge-ts")
    public String challengeTS;
    @SerializedName("score")
    private int score;
    @SerializedName("action")
    public String action;
    @SerializedName("host-name")
    public String hostName;
    @SerializedName("error-codes")
    public String[] errorCodes;
}
