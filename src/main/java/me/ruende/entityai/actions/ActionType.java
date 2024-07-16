package me.ruende.entityai.actions;

// 스태프 기능의 동작 트리거는 좌클릭, 우클릭, 쉬프트-좌클릭, 쉬프트-우클릭으로 고유하다.
// 그렇다면 열거형을 이용하여 미리 상수로 선언해두자.
public enum ActionType {

    LEFT, RIGHT, SHIFT_LEFT, SHIFT_RIGHT;

    // 입력 값을 바탕으로 매칭되는 액션 타입을 반환한다.
    public static ActionType match(boolean leftClick, boolean sneaking) {
        if (leftClick) {
            if (sneaking) {
                return SHIFT_LEFT;
            }
            return LEFT;
        } else {
            if (sneaking) {
                return SHIFT_RIGHT;
            }
            return RIGHT;
        }
    }
}
