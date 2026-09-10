package riot.lcgs.riotlcgsbe.util;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum GloryTool {

    BARON("baron", "바론 슬레이어", 3, "Y"),
    DRAGON("dragon", "드래곤 슬레이어", 3, "Y"),
    ONLY("only", "유일무이", 5, "Y"),
    WINNER("winner", "승리자", 4, "Y"),
    CS("cs", "CS 장인", 3, "Y"),
    DEMOLISHER("demolisher", "철거반장", 3, "Y"),
    GOLD("gold", "골드 부자", 3, "Y"),
    KILL("kill", "킬링머신", 3, "Y"),
    DEATH("death", "데스 왕", 2, "Y"),
    ASSIST("assist", "어시스트 왕", 3, "Y"),
    WARD("ward", "와드 사냥꾼", 3, "Y"),
    PINK("pink", "핑와 애호가", 3, "Y"),
    CROWD("crowd", "CC성애자", 3, "Y"),
    MULTI("multi", "멀티킬 기계", 4, "Y"),
    TOP("top", "TOPKING", 4, "Y"),
    JUG("jug", "JUGKING", 4, "Y"),
    MID("mid", "MIDKING", 4, "Y"),
    ADC("adc", "ADCKING", 4, "Y"),
    SUP("sup", "SUPKING", 4, "Y"),
    ATTENDANCE("attendance", "개근상", 2, "N"),
    PENTA("penta", "펜타킬 기계", 4, "N"),
    PERFECT("perfect", "절대자", 5, "N"),
    MASTER("master", "장인", 4, "N");

    private final String id;
    private final String title;
    private final int grade;
    private final String unique;

    public static GloryTool findById(String id) {
        for (GloryTool glory : values()) {
            if (glory.id.equals(id)) {
                return glory;
            }
        }

        throw new IllegalArgumentException("존재하지 않는 gloryId입니다. id : " + id);
    }
}
