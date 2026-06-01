import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginModel;
import model.CourierModel;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static data.CourierData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierLoginSteps.*;
import static steps.CourierLoginSteps.responseBodyLoginCourier;
import static steps.CourierSteps.createCourier;

public class LoginCourierTest  extends BaseApiTest {
    private Integer courierId;
    private String uniqueLogin;
    CourierLoginModel courierLogin;
    CourierLoginModel courierWithoutLogin;
    CourierLoginModel courierWithoutPassword;
    CourierLoginModel courierInvalidLogin;
    CourierLoginModel courierInvalidPassword;
    CourierLoginModel courierInvalidLoginAndPassword;

    @Before
    public  void initializeCourier() {
        uniqueLogin = "courier_" + System.currentTimeMillis();
        // Создаём курьера перед выполнением тестов
        CourierModel courier = new CourierModel(uniqueLogin, PASSWORD, FIRST_NAME);
        Response createResponse = createCourier(courier);
        createResponse.then()
                .log().all()
                .statusCode(201)
                .body("ok", equalTo(true));

        System.out.println("Курьер успешно создан для тестов авторизации с логином: " + uniqueLogin);

        // ИСПРАВЛЕНО: все модели теперь используют uniqueLogin вместо фиксированного LOGIN
        courierLogin = new CourierLoginModel(uniqueLogin, PASSWORD);
        courierWithoutLogin = new CourierLoginModel(null, PASSWORD);
        courierWithoutPassword = new CourierLoginModel(uniqueLogin, null);
        courierInvalidLogin = new CourierLoginModel("invalid_login", PASSWORD);
        courierInvalidPassword = new CourierLoginModel(uniqueLogin, "invalid_password");
        courierInvalidLoginAndPassword = new CourierLoginModel("invalid_login", "invalid_password");
    }


    @Test
    @DisplayName("Получение ID курьера")
    @Description("Нужно сначала посмотреть значение ключа id, чтобы проверить, корректно ли сервер отображает это значение")
    public void testLoginCourierSuccess() {
        Response response = loginCourier(courierLogin)
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("id", notNullValue())
                .extract().response();

        this.courierId = response.body().path("id");
        System.out.println("Получен ID курьера: " + courierId);
    }

    @Test
    @DisplayName("Проверка на корректное отображение тела ответа при авторизации курьера")
    @Description("Сверяем корректное отображение значения ключа id, а также в целом весь ответ от сервера")
    public void courierAuthorizationWithLogin() {
        Response response = responseBodyLoginCourier(courierLogin);

        response.then()
                .log().all()
                .statusCode(200)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Запрос на авторизацию курьера без логина")
    @Description("Должно возвращаться сообщение об ошибке")
    public void courierAuthorizationWithoutLogin() {
        responseCourierWithoutLogin(courierWithoutLogin)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }


    @Test
    @DisplayName("Запрос на авторизацию курьера без пароля")
    @Description("Должно возвращаться сообщение об ошибке")
    public void courierAuthorizationWithoutPassword() {
        responseCourierWithoutPassword(courierWithoutPassword)
                .then()
                .log().all()
                .statusCode(400)
                .body("message", equalTo("Недостаточно данных для входа"));
    }
    @Test
    @DisplayName("Неправильное указание логина при авторизации курьера")
    @Description("Система вернет ошибку")
    public void courierAuthorizationInvalidLogin(){
        responseCourierNonExistentLogin(courierInvalidLogin)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

    }

    @Test
    @DisplayName("Неправильное указание пароля при авторизации курьера")
    @Description("Система вернет ошибку")
    public void courierAuthorizationInvalidPassword(){
        responseCourierNonExistentPassword(courierInvalidPassword)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Неправильное указание пароля и логина при авторизации курьера")
    @Description("Система вернет ошибку")
    public void courierAuthorizationInvalidLoginAndPassword(){
        responseCourierNonExistentLoginAndPassword(courierInvalidLoginAndPassword)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }

    @After
    public void cleanUp() {
        if (courierId != null) {
            System.out.println("Удаляем курьера с ID: " + courierId);
            deleteCourier(courierId);
        }
    }
}





