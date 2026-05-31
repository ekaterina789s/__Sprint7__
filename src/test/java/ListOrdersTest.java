import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.instanceOf;
import static steps.OrderSteps.getOrderList;

//проверить, что в тело ответа возвращается список заказов
public class ListOrdersTest extends BaseApiTest{

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяем, что в тело ответа возвращается список заказов")
    public void getOrderListReturnsOrders() {
        getOrderList()
                .then()
                .statusCode(200)
                .body("orders", instanceOf(java.util.List.class));
    }
}
