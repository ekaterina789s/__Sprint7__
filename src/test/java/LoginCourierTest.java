import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierLoginModel;
import org.junit.After;
import org.junit.Test;
import static data.CourierData.*;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.Matchers.equalTo;
import static steps.CourierLoginSteps.*;

public class LoginCourierTest  extends BaseApiTest {
    private Integer courierId;

    CourierLoginModel courierLogin = new CourierLoginModel(LOGIN_REPLAY, PASSWORD);
    CourierLoginModel courierWithoutLogin = new CourierLoginModel(null, PASSWORD);
    CourierLoginModel courierWithoutPassword = new CourierLoginModel(LOGIN_REPLAY, null);
    CourierLoginModel courierInvalideLogin = new CourierLoginModel(LOGIN_INVALIDE, PASSWORD);
    CourierLoginModel courierInvalidePassword = new CourierLoginModel(LOGIN_REPLAY, PASSWORD_INVALIDE);
    CourierLoginModel courierInvalideLoginAndPassword = new CourierLoginModel(LOGIN_INVALIDE, PASSWORD_INVALIDE);

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
    public void getResponseBodyLoginCourier() {
        given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courierLogin)
                .when()
                .post(LOGIN_COURIER_PATH)
                .then()
                .log().all()
                .statusCode(200)
                .body("id", equalTo(750546));
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
    public void courierAuthorizationInvalideLogin(){
    responseCourierNonExistentLogin(courierInvalideLogin)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));

}

    @Test
    @DisplayName("Неправильное указание пароля при авторизации курьера")
    @Description("Система вернет ошибку")
    public void courierAuthorizationInvalidePassword(){
        responseCourierNonExistentPassword(courierInvalidePassword)
                .then()
                .log().all()
                .statusCode(404)
                .body("message", equalTo("Учетная запись не найдена"));
    }
    @Test
    @DisplayName("Неправильное указание пароля и логина при авторизации курьера")
    @Description("Система вернет ошибку")
    public void courierAuthorizationInvalideLoginAndPassword(){
        responseCourierNonExistentLoginAndPassword(courierInvalideLoginAndPassword)
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





