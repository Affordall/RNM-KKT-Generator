

/**
 * Created by v.degtev on 10.11.2017.
 */
public class CRC {

    private final static String NINE_ZEROS = "000000000";
    private final static String COUNT_KKM = "1";

    public static String calculate(String inn, String... factoryNumbers) {
        if (inn.length() < 12) {
            throw new IllegalArgumentException("ИНН должен быть из 12 цифр. Например: 009715225506");
        }

        StringBuilder resultBuilder = new StringBuilder(512);

        for (String factoryNumber : factoryNumbers) {
            if (factoryNumber.length() < 14) {
                throw new IllegalArgumentException("Заводской номер кассы должен быть из 14 цифр. Например: 00308300087104");
            }

            StringBuilder innerBuilder = new StringBuilder();
            innerBuilder.append(NINE_ZEROS); // 9 zeros
            innerBuilder.append(COUNT_KKM); // порядковый номер кассы
            innerBuilder.append(inn); // inn 12 number's
            innerBuilder.append("000000"); // 6
            innerBuilder.append(factoryNumber); // factory number 14

            int sum = calculateCRC16CCITT(innerBuilder.toString());
            String resultRnm = NINE_ZEROS + COUNT_KKM + "0" + String.format("%05d", sum);
            resultBuilder.append(formatRnm(resultRnm)).append("\n");
        }

        return resultBuilder.toString();
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

    private static String formatRnm(String incomingRnm) {
        return String.format("%s %s %s %s", incomingRnm.substring(0, 4), incomingRnm.substring(4, 8),
                incomingRnm.substring(8, 12), incomingRnm.substring(12, 16));
    }

}
