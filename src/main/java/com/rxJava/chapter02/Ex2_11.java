package com.rxJava.chapter02;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * java.util.concurrent.atomic
 * 으로 Ex2_06 동일하게 해결
 * Created by park on 2023/04/03.
 */
public class Ex2_11 {

  /**
   * java.util.concurrent.atomic
   * 패키지 안에 있는 클래스들을 이용하면
   * 원자성을 보장함
   *
   */
  public static class AtomicCounter {
    //volatile 키워드와 동일한 효과
    private final AtomicInteger count = new AtomicInteger(0);

    void increment() {
      count.incrementAndGet();
    }

    public int getCount() {
      return count.get();
    }
  }

  public static void main(String[] args) throws Exception {
    final AtomicCounter counter = new AtomicCounter();

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
