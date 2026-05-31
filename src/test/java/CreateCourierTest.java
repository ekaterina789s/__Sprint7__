import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import model.CourierModel;
import org.junit.After;
import org.junit.Test;
import static data.CourierData.*;
import static org.hamcrest.CoreMatchers.equalTo;
import static steps.CourierSteps.*;

public class CreateCourierTest extends BaseApiTest {

    @Test
    @DisplayName("Успешное создание курьера") //имя теста
    @Description("Курьер будет создан с тремя валидными полями") //описание теста

    public void testCreateCourierSuccess() {
        CourierModel courier = new CourierModel(LOGIN, PASSWORD, FIRST_NAME);
        System.out.println(LOGIN);
        createCourier(courier)
                .then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));
    }


        @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Запрос с повторяющимся логином вернет ошибку")
        public void testCreateCourierReplayLogin(){
     CourierModel courier2 = new CourierModel(LOGIN_REPLAY, PASSWORD, FIRST_NAME);
            createCourier2(courier2)
                    .then()
                    .log().all()
                    .statusCode(409)
                    .body("message", equalTo("Этот логин уже используется"));
        }

        @Test
    @DisplayName("Создание курьера без логина")
    @Description("Если создать курьера без логина, то вернется ошибка от сервера")
    public void testCreateCourierWithoutLogin(){
        CourierModel courier3 = new CourierModel(null, PASSWORD, FIRST_NAME);
        createCourier3(courier3)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        }

        @Test
    @DisplayName("Создание курьера без пароля")
    @Description("Если создать курьера без пароля, то вернется ошибка от сервера")
    public void testCreateCourierWithoutPassword(){
        CourierModel courier4 = new CourierModel(LOGIN, null, FIRST_NAME);
            createCourier4(courier4)
                    .then()
                    .log().all()
                    .statusCode(400)
                    .body("message", equalTo("Недостаточно данных для создания учетной записи"));
        }
}









