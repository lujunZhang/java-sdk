package com.binance.dex.api.client.encoding.message;

import com.binance.dex.api.client.encoding.EncodeUtils;

import java.util.Arrays;

/**
 * Binance dex standard transactiont types.
 */
public enum MessageType {
    Send("2A2C87FA"),
    NewOrder("CE6DC043"),
    CancelOrder("166E681B"),
    TokenFreeze("E774B32D"),
    TokenUnfreeze("6515FF0D"),
    StdSignature(null),
    PubKey("EB5AE987"),
    StdTx("F0625DEE"),
    Vote("A1CADD36"),
    Issue("17EFAB80"),
    Burn("7ED2D2A0"),
    Mint("467E0829"),
    SubmitProposal("B42D614E"),
    Deposit("A18A56E5"),
    CreateValidator("DB6A19FD"),
    RemoveValidator("C1AFE85F"),
    Listing("B41DE13F"),
    TimeLock("07921531"),
    TimeUnlock("C4050C6C"),
    TimeRelock("504711DA"),
    SetAccountFlag("BEA6E301");

    private byte[] typePrefixBytes;

    MessageType(String typePrefix) {
        if (typePrefix == null) {
            this.typePrefixBytes = new byte[0];
        } else
            this.typePrefixBytes = EncodeUtils.hexStringToByteArray(typePrefix);
    }

    public byte[] getTypePrefixBytes() {
        return typePrefixBytes;
    }

    public static MessageType getMessageType(byte[] bytes) {
        if (null == bytes || bytes.length < 4) {
            return null;
        }
        return Arrays.stream(MessageType.values())
                .filter(type -> {
                    if (null == type.getTypePrefixBytes() || type.getTypePrefixBytes().length < 4) {
                        return false;
                    }
                    for (int i = 0; i < 4; i++) {
                        if (type.getTypePrefixBytes()[i] != bytes[i]) {
                            return false;
                        }
                    }
                    return true;
                })
                .findAny().orElse(null);
    }

}
