package com.rxJava.chapter02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * 2개의 스레드로 접근해 처리하는 예제
 * Created by park on 2023/04/03.
 */
public class Ex2_06 {

  public static class Counter {
    //volatile 키워드는 업데이트한 값은 반드시 메모리에 반영돼 다른 스레드에서 참조할 때 같은 값을 얻을 수 있게하는 제한자
    //volatile 을 추가하면 결과값이 20000으로 보장
//    private volatile int count;

    //volatile 이 없을때는 10000이상으로 랜덤값으로 출력
    private int count;

    void increment() {
      count++;
    }

    public int getCount() {
      return count;
    }
  }

  public static void main(String[] args) throws Exception {
    final Counter counter = new Counter();

    //10000번 계산하는 비동기 처리 작업
    Runnable task = () -> {
      for (int i = 0; i < 10000; i++) {
        counter.increment();
      }
    };

    //비동기 처리 작업 생성 준비
    ExecutorService executorService = Executors.newCachedThreadPool();

    //Future 는 비동기 연산 처리결과를 보기 위한 인터페이스

    //새로운 스레드 시작
    Future<Boolean> future1 = executorService.submit(task, true);

    //새로운 스레드 시작
    Future<Boolean> future2 = executorService.submit(task, true);

    //스레드가 둘다 종료되었을때 처리
    if (future1.get() && future2.get()) {
      System.out.println(counter.getCount());
    } else {
      System.out.println("실패");
    }

    //비동기 처리 작업 종료
    executorService.shutdown();
  }
}
