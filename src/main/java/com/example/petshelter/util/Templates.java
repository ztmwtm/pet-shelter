package com.example.petshelter.util;

import com.example.petshelter.entity.User;

public class Templates {

    private static final String CONGRATULATION_OF_ADOPTION = """
            Уважаемый %s, поздравляем вас с окончательным усыновлением!
            Теперь вы, полноправный владелец, окружите вашего питомца заботой и любовью, а он ответит вам взаимностью.
            """;

    private static final String BAD_REPORT = """
            Дорогой усыновитель, мы заметили, что ты заполняешь отчет не так подробно, как необходимо.
            Пожалуйста, подойди ответственнее к этому занятию.
            В противном случае волонтеры приюта будут обязаны самолично проверять условия содержания животного
            """;

    private static final String ADDITIONAL_TIME = """
            Уважаемый %s, мы рады что вы взяли себе одного из наших питомцев!
            Но к сожалению, пока что наши волонтеры не уверены в том, что он полностью адаптировался.
            Поэтому просим вас продолжить отправку отчетов в течении %d дней.
            """;

    private static final String ADOPTION_FAIL = """
            Уважаемый %s, к сожалению волонтерами нашего питомника принято решение о необходимости
            возврата животного обратно в питомник.
            Ниже приведена инструкция о возврате животного.
            """;

    public static String getCongratulationText(User user) {
        return String.format(CONGRATULATION_OF_ADOPTION, user.getFirstName());
    }

    public static String getAdditionalTimeText(User user, int days) {
        return String.format(ADDITIONAL_TIME, user.getFirstName(), days);
    }

    public static String getBadReportText() {
        return BAD_REPORT;
    }

    public static String getAdoptionFailText(User user) {
        return String.format(ADOPTION_FAIL, user.getFirstName());
    }
}
