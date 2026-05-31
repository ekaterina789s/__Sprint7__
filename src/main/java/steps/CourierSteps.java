package steps;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import model.CourierModel;
import static data.CourierData.CREATE_COURIER_PATH;
import static io.restassured.RestAssured.given;

//шаг для успешного создания курьера
public class CourierSteps {
    @Step("Успешное создание курьера, ручка /api/v1/courier")

    public static Response createCourier(CourierModel courier) {
        return given() //метод задает начальную точку для цепочки вызова
                .log().all() //логирование всех деталей запроса: выводит в консоль: заголовки запроса, тело запроса, URL эндпоинта, статус-код и тело ответа от сервера
                .contentType(ContentType.JSON) //устанавливает заголовок Content-Type: application/json. Сообщает серверу, что данные в теле запроса представлены в формате JSON
                .body(courier)//сериализует объект CourierModel в JSON и помещает его в тело запроса
                .when()//обозначает переход от настройки запроса к выполнению HTTP‑метода
                .post(CREATE_COURIER_PATH)//выполняет HTTP POST‑запрос(отправляет запрос на URL эндпоинта). Результат: сервер отправляет ответ
                .then()//начинает блок проверки (валидации) ответа. В данном случае используется не для проверок, а как переход к извлечению результата
                .extract().response(); //извлечение полного объекта ответа от сервера без валидации. .extract()-останавливает выполнение проверок, заданных через .then(), нужен,напр., когда не надо сразу проверять ответ от сервера, а сохранить полный ответ для последующей обработки. response()-извлекает весь ответ сервера как объект Response
    }


//шаг для создания курьера с повторным логином

    @Step("Создание курьера с уже имеющимся логином, ручка /api/v1/courier")
    public static Response createCourier2(CourierModel courier2) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier2)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Создание курьера без логина, ручка /api/v1/courier")
    public static Response createCourier3(CourierModel courier3) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier3)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

    @Step("Создание курьера без пароля, ручка /api/v1/courier")
    public static Response createCourier4(CourierModel courier4) {
        return given()
                .log().all()
                .contentType(ContentType.JSON)
                .body(courier4)
                .when()
                .post(CREATE_COURIER_PATH)
                .then()
                .extract().response();
    }

}


