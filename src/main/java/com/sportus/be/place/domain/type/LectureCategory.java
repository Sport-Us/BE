package com.sportus.be.place.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum LectureCategory {
    ALL("전체"),
    TAEKWONDO("태권도"),
    JUDO("유도"),
    BOXING("복싱"),
    JUJITSU("주짓수"),
    KENDO("검도"),
    HAPKIDO("합기도"),
    HEALTH("헬스"),
    YOGA("요가"),
    PILATES("필라테스"),
    CROSSFIT("크로스핏"),
    AEROBICS("에어로빅"),
    DANCE("댄스(줌바 등)"),
    SOCCER("축구(풋살)"),
    BASKETBALL("농구"),
    VOLLEYBALL("배구"),
    BASEBALL("야구"),
    TABLE_TENNIS("탁구"),
    SQUASH("스쿼시"),
    BADMINTON("배드민턴"),
    TENNIS("테니스"),
    GOLF("골프"),
    BAWLING("볼링"),
    BILLIARDS("당구"),
    CLIIMBING("클라이밍"),
    ROLLER_SKATING("롤러인라인"),
    ICE_SKATING("빙상(스케이트)"),
    ETC("기타종목"),
    COMPREHENSIVE("종합체육시설"),
    BALLET("무용(발레 등)"),
    JUMPING_ROPE("줄넘기"),
    PENCING("펜싱"),
    SWIMMING("수영"),
    RIDING("승마"),
    ;

    private final String name;

    public static LectureCategory findByName(String name) {
        for (LectureCategory lectureCategory : LectureCategory.values()) {
            if (lectureCategory.getName().equals(name)) {
                return lectureCategory;
            }
        }
        return null;
    }
}
