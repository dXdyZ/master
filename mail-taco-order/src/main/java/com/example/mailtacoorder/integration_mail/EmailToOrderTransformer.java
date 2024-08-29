package com.example.mailtacoorder.integration_mail;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;

import jakarta.mail.internet.MimeMultipart;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.integration.mail.transformer.AbstractMailMessageTransformer;
import org.springframework.integration.support.AbstractIntegrationMessageBuilder;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@Slf4j
@Component
public class EmailToOrderTransformer extends AbstractMailMessageTransformer<EmailOrder> {

    private static final String SUBJECT_KEYWORDS = "TACO ORDER";

    @Override
    protected AbstractIntegrationMessageBuilder<EmailOrder>
    doTransform(Message mailMessage) {
        EmailOrder tacoOrder = processPayLoad(mailMessage);
        return MessageBuilder.withPayload(tacoOrder);
    }

    private EmailOrder processPayLoad(Message mailMessage) {
        try {
            String subject = mailMessage.getSubject();
            if (subject.toUpperCase().contains(SUBJECT_KEYWORDS)) {//Вернет true только если строка содержит указанное значение
                String email =
                        ((InternetAddress) mailMessage.getFrom()[0]).getAddress();
                String content = mailMessage.getContent().toString();
                return parseEmailToOrder(email, content);
            }
        } catch (MessagingException e) {
            log.error("MessagingException: {}", e);
        } catch (IOException e) {
            log.error("IOException: {}", e);
        }
        return null;
    }

    private EmailOrder parseEmailToOrder(String email, String content) {
        EmailOrder order = new EmailOrder(email);
        log.info("lines start: {}", content);
        String[] lines = content.split("\\r?\\n");
        for (String line : lines) {
            if (line.trim().length() > 0 && line.contains(":")) { // trim - убирает пробелы в начале и в конце строки
                String[] lineSplit = line.split(":");
                String tacoName = lineSplit[0].trim();
                log.info("taco name: {}", lineSplit[0].trim());
                String ingredients = lineSplit[1].trim();
                log.info("ingredients: {}", lineSplit[1].trim());
                String[] ingredientsSplit = ingredients.split(",");
                log.info("ingredientsSplit: {}", Arrays.stream(ingredientsSplit).iterator().next());
                List<String> ingredientCodes = new ArrayList<>();
                for (String ingredientName : ingredientsSplit) {
                    String code = lookupIngredientCode(ingredientName.trim());
                    if (code != null) {
                        ingredientCodes.add(code);
                    } else {
                        log.warn("Ingredient not found: {}", ingredientName);
                    }
                }
                if (!ingredientCodes.isEmpty()) {
                    TacoEmail tacoEmail = new TacoEmail(tacoName);
                    tacoEmail.setIngredients(ingredientCodes);
                    order.addTaco(tacoEmail);
                } else {
                    log.warn("No valid ingredients found for taco: {}", tacoName);
                }
            }
        }
        return order;
    }

    private String lookupIngredientCode(String ingredientName) {
        for (IngredientEmail ingredient : ALL_INGREDIENTS) {
            String ucIngredientName = ingredientName.toUpperCase();
            if (LevenshteinDistance.getDefaultInstance()
                    .apply(ucIngredientName, ingredient.getName()) < 3 ||
                    ucIngredientName.contains(ingredient.getName()) ||
                    ingredient.getName().contains(ucIngredientName)) {
                return ingredient.getCode();
            }
        }
        log.warn("Ingredient not found: {}", ingredientName);
        return null;
    }

    private static final IngredientEmail[] ALL_INGREDIENTS = new IngredientEmail[]{
            new IngredientEmail("FLTO", "FLOUR TORTILLA"),
            new IngredientEmail("COTO", "CORN TORTILLA"),
            new IngredientEmail("GRBF", "GROUND BEEF"),
            new IngredientEmail("CARN", "CARNITAS"),
            new IngredientEmail("TMTO", "TOMATOES"),
            new IngredientEmail("LETC", "LETTUCE"),
            new IngredientEmail("CHED", "CHEDDAR"),
            new IngredientEmail("JACK", "MONTERREY JACK"),
            new IngredientEmail("SLSA", "SALSA"),
            new IngredientEmail("SRCR", "SOUR CREAM")
    };
}
