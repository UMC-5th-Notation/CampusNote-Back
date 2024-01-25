package UMC.campusNote.lesson.converter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class CrawlingHelper {
    public static String getHeight(Element e) {
        String style = e.attr("style"); // 스타일 속성 값 얻기
        String[] styles = style.split(";"); // 세미콜론(;)을 기준으로 스타일 속성 분리

        for (String s : styles) {
            if (s.trim().startsWith("height")) { // height로 시작하는 스타일 속성 찾기
                String height = s.split(":")[1].trim(); // 콜론(:)을 기준으로 속성 이름과 값 분리
                return height;
            }
        }

        return null;
    }

    public static String getTop(Element e) {
        String style = e.attr("style"); // 스타일 속성 값 얻기
        String[] styles = style.split(";"); // 세미콜론(;)을 기준으로 스타일 속성 분리

        for (String s : styles) {
            if (s.trim().startsWith("top")) { // height로 시작하는 스타일 속성 찾기
                String top = s.split(":")[1].trim(); // 콜론(:)을 기준으로 속성 이름과 값 분리
                return top;
            }
        }

        return null;
    }

    // map이 더 유리할듯
    public static List<String> getContents(Element e) {

        List<String> results = new ArrayList<>();

        String contentHtml = e.html();
        Document docs = org.jsoup.Jsoup.parse(contentHtml);
        Elements childElements = docs.getAllElements();
        for (Element child : childElements) {
            if (child.tagName().equals("h3")) {
                // System.out.println("과목명 : " + child.text());
                results.add(child.text());
            }
            if (child.tagName().equals("em")) {
                // System.out.println("교수명 : " + child.text());
                results.add(child.text());
            }
            if (child.tagName().equals("span")) {
                // System.out.println("강의실 : " + child.text());
                results.add(child.text());
            }
        }

        return results;
    }

    public static double heightToRunningTime(String stringValue) {
        // 문자열 길이 구하기
        int length = stringValue.length();

        // 뒤에서 2자리를 제외한 부분 추출
        String numericPart = stringValue.substring(0, length - 2);

        int intValue = Integer.parseInt(numericPart);

        //30분당 25PX, +1 고려
        intValue--;

        double result = (double) intValue / 50;

        return result; // 시간단위
    }

    public static double topToStartClock(String stringValue) {
        // 문자열 길이 구하기
        int length = stringValue.length();

        // 뒤에서 2자리를 제외한 부분 추출
        String numericPart = stringValue.substring(0, length - 2);

        int intValue = Integer.parseInt(numericPart);

        double result = (double) intValue / 50; // 1시간 50px

        return result; // 시간단위
    }
}
