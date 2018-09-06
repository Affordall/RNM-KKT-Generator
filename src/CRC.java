import org.jetbrains.annotations.NotNull;

/**
 * Created by v.degtev on 10.11.2017.
 */
public class CRC {

    private final static String NINE_ZEROS = "000000000";
    private final static String COUNT_KKM = "1";

    public static String calculate(@NotNull String inn, @NotNull String factoryNumber) {
        if (inn.length() < 12) {
            throw new IllegalArgumentException("ИНН должен быть из 12 цифр. Например: 009715225506");
        }
        if (factoryNumber.length() < 14) {
            throw new IllegalArgumentException("Заводской номер кассы должен быть из 14 цифр. Например: 00308300087104");
        }
        StringBuilder builder = new StringBuilder();
        builder.append(NINE_ZEROS); // 9 zeros
        builder.append(COUNT_KKM); // порядковый номер кассы
        builder.append(inn); // inn 12 number's
        //builder.append("009715225506");
        builder.append("000000"); // 6
        builder.append(factoryNumber); // factory number 14
        //builder.append("00307900004456");

        //"000000000100971522550600000000307900004456";
        int sum = calculateCRC16CCITT(builder.toString());
        return NINE_ZEROS + COUNT_KKM + "0" + String.valueOf(sum);
    }

    private static int calculateCRC16CCITT(String inputValue) {

        int crc = 0xFFFF;          // initial value
        int polynomial = 0x1021;   // 0001 0000 0010 0001  (0, 5, 12)

        byte[] bytes = inputValue.getBytes();

        for (byte b : bytes) {
            for (int i = 0; i < 8; i++) {
                boolean bit = ((b >> (7 - i) & 1) == 1);
                boolean c15 = ((crc >> 15 & 1) == 1);
                crc <<= 1;
                if (c15 ^ bit) crc ^= polynomial;
            }
        }

        crc &= 0xffff;
        return crc;
    }

}
