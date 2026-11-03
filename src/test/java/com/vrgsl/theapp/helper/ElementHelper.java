package com.vrgsl.theapp.helper;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.vrgsl.theapp.model.ElementInfo;
import io.appium.java_client.AppiumBy;
import org.openqa.selenium.By;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

public final class ElementHelper {

    private static final String ELEMENT_INFOS_DIR = "element-infos";
    private static final Gson GSON = new Gson();
    private static final Type ELEMENT_LIST_TYPE = new TypeToken<List<ElementInfo>>() {}.getType();
    private static final Map<String, ElementInfo> ELEMENTS = loadElements();

    private ElementHelper() {
    }

    public static By getBy(String key) {
        ElementInfo element = ELEMENTS.get(key);
        if (element == null) {
            throw new IllegalArgumentException("Element not found: " + key);
        }
        return switch (element.type()) {
            case "accessibilityId" -> AppiumBy.accessibilityId(element.value());
            case "id" -> By.id(element.value());
            case "uiAutomator" -> AppiumBy.androidUIAutomator(element.value());
            case "xpath" -> By.xpath(element.value());
            default -> throw new IllegalArgumentException(
                    "Unsupported locator type '" + element.type() + "' for element: " + key);
        };
    }

    // Reads every .json file under element-infos, including subdirectories
    private static Map<String, ElementInfo> loadElements() {
        URL url = ElementHelper.class.getClassLoader().getResource(ELEMENT_INFOS_DIR);
        if (url == null) {
            throw new IllegalStateException("Resource directory not found: " + ELEMENT_INFOS_DIR);
        }
        Map<String, ElementInfo> elements = new HashMap<>();
        try (Stream<Path> paths = Files.walk(Path.of(url.toURI()))) {
            paths.filter(path -> path.toString().endsWith(".json"))
                    .flatMap(path -> readElements(path).stream())
                    .forEach(element -> register(elements, element));
        } catch (IOException | URISyntaxException e) {
            throw new IllegalStateException("Cannot read resource directory: " + ELEMENT_INFOS_DIR, e);
        }
        return elements;
    }

    private static List<ElementInfo> readElements(Path path) {
        try (Reader reader = Files.newBufferedReader(path)) {
            return GSON.fromJson(reader, ELEMENT_LIST_TYPE);
        } catch (IOException e) {
            throw new UncheckedIOException("Cannot read element file: " + path, e);
        }
    }

    private static void register(Map<String, ElementInfo> elements, ElementInfo element) {
        if (elements.putIfAbsent(element.key(), element) != null) {
            throw new IllegalStateException("Duplicate element key: " + element.key());
        }
    }
}
