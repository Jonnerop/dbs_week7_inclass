package model.enums;

public enum Rank {
    KYU_6(6), KYU_5(5), KYU_4(4), KYU_3(3), KYU_2(2), KYU_1(1),
    DAN_1(101), DAN_2(102), DAN_3(103), DAN_4(104), DAN_5(105);

    public final int code;
    Rank(int code){ this.code = code; }
    public static Rank fromCode(Integer code){
        if (code == null) return null;
        for (Rank r : values()) if (r.code == code) return r;
        throw new IllegalArgumentException("Unknown rank code: " + code);
    }
}