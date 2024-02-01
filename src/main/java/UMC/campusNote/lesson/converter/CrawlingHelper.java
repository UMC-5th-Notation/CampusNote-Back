package UMC.campusNote.lesson.converter;

import UMC.campusNote.user.entity.User;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;
import java.util.Map;

public class CrawlingHelper {
    public static String getHeight(Element e) {
        String style = e.attr("style");
        String[] styles = style.split(";");

        for (String s : styles) {
            if (s.trim().startsWith("height")) {
                String height = s.split(":")[1].trim();
                return height;
            }
        }
        return null;
    }

    public static String getTop(Element e) {
        String style = e.attr("style");
        String[] styles = style.split(";");

        for (String s : styles) {
            if (s.trim().startsWith("top")) {
                String top = s.split(":")[1].trim();
                return top;
            }
        }
        return null;
    }

    public static Map<String, String> getContents(Element e) {

        Map<String, String> results = new HashMap<>();

        String contentHtml = e.html();
        Document docs = org.jsoup.Jsoup.parse(contentHtml);
        Elements childElements = docs.getAllElements();
        for (Element child : childElements) {
            if (child.tagName().equals("h3")) {
                results.put("lessonName", child.text());
            }
            if (child.tagName().equals("em")) {
                results.put("professorName", child.text());
            }
            if (child.tagName().equals("span")) {
                results.put("location", child.text());
            }
        }
        return results;
    }

    public static String heightToRunningTime(String stringValue) {
        int length = stringValue.length();
        String numericPart = stringValue.substring(0, length - 2);
        int intValue = Integer.parseInt(numericPart);
        intValue--;
        double result = (double) intValue / 50;
        return String.valueOf(result);
    }

    public static String topToStartClock(String stringValue) {
        int length = stringValue.length();
        String numericPart = stringValue.substring(0, length - 2);
        int intValue = Integer.parseInt(numericPart);
        double result = (double) intValue / 50;
        return String.valueOf(result);
    }

    public static String getSemester(WebElement menu) {
        String hamburger_outer = menu.getAttribute("outerHTML");
        Document menu_doc = org.jsoup.Jsoup.parse(hamburger_outer);
        Elements activeSemester = menu_doc.select(".active");
        return activeSemester.text();
    }

    public static Elements extractInnerHtml(Element table_col) {
        String col_html = table_col.html();
        Document document = org.jsoup.Jsoup.parse(col_html);
        Elements elements = document.select("div.subject");
        return elements;
    }

    public static Elements extractOuterHtml(WebDriver driver) {
        WebElement tableBody = driver.findElement(By.cssSelector(".tablebody"));
        String outer = tableBody.getAttribute("outerHTML");
        Document doc = org.jsoup.Jsoup.parse(outer);
        Elements table_cols = doc.select(".cols");
        return table_cols;
    }

    public static Map<String, String> extractConstValue(WebElement menu, User user) {
        // const val
        String semester = getSemester(menu);
        String university = user.getUniversity();
        Map<String, String> result = new HashMap<>();
        result.put("semester", semester);
        result.put("university", university);
        return result;
    }
}
