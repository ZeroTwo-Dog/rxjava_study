package com.rxJava.chacpter01;

import io.reactivex.*;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import org.reactivestreams.*;

/**
 * 챕터1
 * 예제1_11
 * Flowable 로 구현한 예제
 * Created by park on 2023/03/19.
 */
public class Ex1_11 {

  public static void main(String[] args) throws Exception {

    //초과하는 데이터는 버퍼링
// 배얍전략
    Flowable<String> flowable = Flowable.create(emitter -> {
      String[] datas = {"Hello, World!", "안녕, RxJava!"};

      for (String data : datas) {
        //구독이 해지되면 처리 중단
        if (emitter.isCancelled()) {
          return;
        }
        //데이터 통지
        emitter.onNext(data);
      }
      //완료 통지
      emitter.onComplete();
    }, BackpressureStrategy.BUFFER);

    flowable
        // 스케쥴러 전략
        .observeOn(Schedulers.computation())
        // 1번 구독한다
        .subscribe(new Subscriber<String>() {
          private Subscription subscription;

          // 2번 구독 시작시 처리
          @Override
          public void onSubscribe(Subscription s) {
            this.subscription = s;
            //3번 1건의 데이터를 요청
            this.subscription.request(1L);
          }

          //4번 6번 데이터 받음
          @Override
          public void onNext(String s) {
            //5번 7번 데이터를 처리한다.
            String name = Thread.currentThread().getName();
            System.out.println(name + ": " + s);
            //6번 1건의 데이터를 요청
            //이 부분이 없으면 `Hello World`만 출력하고 끝남
            this.subscription.request(1L);
          }

          //에러처리
          @Override
          public void onError(Throwable e) {
            e.printStackTrace();
          }

          //8번 완료처리
          @Override
          public void onComplete() {
            String name = Thread.currentThread().getName();
            System.out.println(name + ": " + "완료");
          }
        });

    Thread.sleep(500L);

  }
}
