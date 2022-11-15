package lotto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Lotto {

    //내가 구매한 로또 용지
    private final List<Integer> numbers;

    public Lotto(List<Integer> numbers) {
        validate(numbers);
        this.numbers = numbers;
    }

    private void validate(List<Integer> numbers) {
        Set<Integer> duplicationNumber = new HashSet<>(numbers);
        numbers = new ArrayList<>(duplicationNumber);

        if (numbers.size() != 6) {
            System.err.println("[ERROR] 숫자 확인");
            throw new IllegalArgumentException();
        }
    }

    // TODO: 추가 기능 구현
}