package com.rxJava.chapter04;

import io.reactivex.Flowable;
import java.util.Locale;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * map 예제
 * 데이터를 받으면 반드시 null이 아닌 데이터 하나를 반환
 * Created by park on 2023/05/01.
 */
public class Ex4_16 {

  public static void main(String[] args) {
  Flowable<String> flowable =
      Flowable.just("A", "B" , "C", "D", "E")
          .map(data -> data.toLowerCase(Locale.ROOT));

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
