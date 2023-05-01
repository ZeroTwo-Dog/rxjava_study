package com.rxJava.chapter04;

import io.reactivex.Flowable;
import java.util.Locale;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * flatMap 예제
 * flatMap(mapper)
 * map 메서드와 달리 여러 데이터 담긴 Flowable/Observable 을
 * 반환하므로 데이터 한개로 여러 데이터를 통지 가능
 * Created by park on 2023/05/01.
 */
public class Ex4_18 {

  public static void main(String[] args) {
  Flowable<String> flowable =
      Flowable.just("A", "" , "C", "", "E")
          .flatMap(data -> {
            if ("".equals(data)) {
              return Flowable.empty();
            } else {
              return Flowable.just(data.toLowerCase(Locale.ROOT));
            }
          });

  flowable.subscribe(new Subscriber<>() {
    private Subscription subscription;

    @Override
    public void onSubscribe(Subscription subscription) {
      this.subscription = subscription;
      this.subscription.request(1L);

    }

    @Override
    public void onNext(String s) {
      System.out.println(s);
      this.subscription.request(1L);

    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onComplete() {

    }
  });


  }



}
