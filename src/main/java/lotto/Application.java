package lotto;

import camp.nextstep.edu.missionutils.Console;
import camp.nextstep.edu.missionutils.Randoms;

import java.util.*;

public class Application {
    public static void main(String[] args) {
        // TODO: 프로그램 구현
        System.out.print("구입 금액 입력 : ");
        String answer = Console.readLine();
        System.out.println(answer);
        int ticket = buyLotto(answer);

        System.out.print("당첨 번호를 입력해 주세요 : ");
        String prizes=Console.readLine();
        System.out.println(prizes);

        System.out.print("보너스 번호를 입력해 주세요 : ");
        String bonusPrize=Console.readLine();
        System.out.println(bonusPrize);

        List<Integer> prizeNumbers = setLottoPrize(prizes,bonusPrize);

        validate(prizeNumbers);
        totalStats(ticket , prizeNumbers);

    }

    private static int buyLotto(String money){
        int ticket = 0;
        try {
            ticket = Integer.parseInt(money)/1000;
        }
        catch (IllegalArgumentException e){
            ticket=-1;
            throw new IllegalArgumentException("[ERROR] 숫자만 기입");
        }
        return ticket;
    }

    private static List<Integer> setLottoPrize(String prizes
            , String bonusPrize
    ){
        List<Integer> result = new ArrayList<>();
        List<String> prizesList = Arrays.asList(prizes.split(","));
        for(String p : prizesList){
            Integer prizeNumber = Integer.parseInt(p);
            result.add(prizeNumber);
        }
        Integer bonusNumber = Integer.parseInt(bonusPrize);
        result.add(bonusNumber);
        return result;
    }


    private static boolean validate(List<Integer> numbers) {
        boolean check = true;
        Set<Integer> duplicationNumber = new HashSet<>(numbers);
        numbers = new ArrayList<>(duplicationNumber);

        if (numbers.size() != 7) {
            check = false;
            System.err.println("[ERROR] 숫자 확인");
            throw new IllegalArgumentException();
        }

        return check;
    }

    private static void totalStats(int ticket , List<Integer> prizeNumbers){
        System.out.println("당첨 통계");
        System.out.println("------------------------");
        System.out.println(ticket+"개를 구매했습니다.");
        List<Integer>prizeList= resultPrizeList(checkPrize(ticket,prizeNumbers));
        List<String> prizeRank = List.of("3개 일치 (5,000원) - ","4개 일치 (50,000원) - ", "5개 일치 (1,500,000원) - " , "5개 일치, 보너스 볼 일치 (30,000,000원) - " ,"6개 일치 (2,000,000,000원) - ");
        for(int i =0; i<prizeList.size(); i++){
            System.out.println(prizeRank.get(i)+prizeList.get(i)+"개");
        }
        yield(prizeList,ticket);
    }

    private static int[] checkPrize(int ticket , List<Integer> prizeNumbers ){
        int[] tempResult = new int[5];
        int bonusNum = prizeNumbers.get(6);
        for(int i=0; i<ticket; i++){
            List<Integer> numbers = Randoms.pickUniqueNumbersInRange(1, 45, 6);
            List<Integer> temp = new ArrayList<>(numbers);
            System.out.println(temp);
            temp.retainAll(prizeNumbers);
            if(temp.size()>2)
                tempResult[temp.size()-3]++;
            if(temp.size()==6 && !temp.contains(bonusNum)){
                tempResult[3]--;
                tempResult[4]++;
            }
        }
        return tempResult;
    }

    private static List<Integer> resultPrizeList(int[] tempResult){
        List<Integer> result = new ArrayList<>();
        for(int temp : tempResult){
            result.add(temp);
        }
        return result;
    }

    private static void yield(List<Integer> prizeList , int ticket){
        int total=0;
        double yield=0;

        List<Integer> prizeMoney = List.of(5000,50000,1500000,30000000,2000000000);
        for(int i=0; i<prizeList.size(); i++){
            total +=prizeMoney.get(i)*prizeList.get(i);
        }
        yield=(double) total/(double) ticket/10.0;
        System.out.println(String.format("총 수익률은 %.1f%%입니다.",yield));
    }

}
