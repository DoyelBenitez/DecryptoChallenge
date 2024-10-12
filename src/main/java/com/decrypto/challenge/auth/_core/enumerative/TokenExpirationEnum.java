package com.decrypto.challenge.auth._core.enumerative;

import lombok.Getter;

/**
 * @Author dbenitez
 */
@Getter
public enum TokenExpirationEnum {

    ONE_HOUR(3600000),
    SIX_HOURS(21600000),
    TWELVE_HOURS(43200000),
    ONE_DAY(86400000),
    TWO_DAYS(172800000),
    THREE_DAYS(259200000),
    FOUR_DAYS(345600000),
    FIVE_DAYS(432000000),
    SIX_DAYS(518400000),
    SEVEN_DAYS(604800000);

    private final long expirationMillis;

    TokenExpirationEnum(long expirationMillis) {
        this.expirationMillis = expirationMillis;
    }

}
