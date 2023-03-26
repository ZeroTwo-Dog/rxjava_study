package com.rxJava.chacpter01;

import io.reactivex.Flowable;
import java.util.concurrent.TimeUnit;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * 챕터1
 * 예제1_17
 * Flowable - 구독을 중도 해지하는 예제
 * Created by park on 2023/03/26
 */
public class Ex1_17 {

  public static void main(String[] args) throws Exception {
    Flowable.interval(200L, TimeUnit.MILLISECONDS)
        .subscribe(new Subscriber<Long>() {

          private Subscription  subscription;
          private Long startTime;

          @Override
          public void onSubscribe(Subscription s) {
            this.subscription = s;
            this.startTime = System.currentTimeMillis();
            this.subscription.request(Long.MAX_VALUE);
          }

          @Override
          public void onNext(Long aLong) {

            //특정시간 기준 넘으면 구독해지
            if (System.currentTimeMillis() - startTime > 500) {
              //구독해지
              subscription.cancel();
              System.out.println("구독해지");
              //리턴 안하면 data: 2까지 나옴
              return;
            }
            System.out.println("data: " + aLong);
          }

          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }


          @Override
          public void onComplete() {
            String name = Thread.currentThread().getName();
            System.out.println(name + ": " + "완료");
          }
        });

    Thread.sleep(2000L);

  }
}
