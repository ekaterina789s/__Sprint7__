import io.restassured.response.Response;
import lombok.Getter;
import model.OrderModel;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import steps.OrderSteps;
import java.util.Arrays;
import java.util.Collection;
import static org.junit.Assert.assertNotNull;

@Getter
@RunWith(Parameterized.class)
public class CreatingOrderParam extends BaseApiTest {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private String[] color;

    public CreatingOrderParam(String firstName, String lastName, String address,
                              String metroStation, String phone, int rentTime,
                              String deliveryDate, String comment, String[] color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][] {
                // Тест 1: один цвет — BLACK
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5,
                        "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK"}},
                // Тест 2: один цвет — GREY
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5,
                        "2020-06-06", "Saske, come back to Konoha", new String[]{"GREY"}},
                // Тест 3: оба цвета
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5,
                        "2020-06-06", "Saske, come back to Konoha", new String[]{"BLACK", "GREY"}},
                // Тест 4: без цвета (null)
                {"Naruto", "Uchiha", "Konoha, 142 apt.", "4", "+7 800 355 35 35", 5,
                        "2020-06-06", "Saske, come back to Konoha", null}
        });
    }

    @Test
    public void testCreatingOrderParam() {
        //создаем модель заказа из полей текущего экземпляра
        OrderModel orderModel = new OrderModel(
                firstName, lastName, address, metroStation,
                phone, rentTime, deliveryDate, comment, color
        );

        //отправляем запрос на создание заказа через шаги
        Response response = OrderSteps.responseOrder(orderModel);

        //извлекаем track как Integer и преобразуем в String
        Integer trackNumber = response.path("track");
        assertNotNull("Поле track отсутствует в ответе", trackNumber);
        String track = trackNumber.toString();

        System.out.println("Track number: " + track);
    }
}

