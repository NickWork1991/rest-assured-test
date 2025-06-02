import java.util.HashMap;
import java.util.Map;

public class BasePayload {

    public Map<String, Object> getBaseBookingPayload(){
        Map<String,Object> booking = new HashMap<>();
        booking.put("firstname", "Nik");
        booking.put("lastname","Mecho");
        booking.put("totalprice",145);
        booking.put("depositpaid", true);
        Map<String,String> dates = new HashMap<>();
        dates.put("checkin", "2025-05-08");
        dates.put("checkout", "2025-05-11");

        booking.put("bookingdates", dates);
        booking.put("additionalneeds","Don't need anything");
        return booking;
    }
}
