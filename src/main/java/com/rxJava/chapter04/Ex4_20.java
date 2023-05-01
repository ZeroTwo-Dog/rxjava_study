package com.rxJava.chapter04;

import io.reactivex.Flowable;
import java.util.Locale;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

/**
 * flatMap 예제
 * flatMap(mapper, combiner)
 * combiner안에서 사용하는 newData는 mapper에서 가공하는 값
 * Created by park on 2023/05/01.
 */
public class Ex4_20 {

  public static void main(String[] args) {
  Flowable<String> flowable =
      Flowable.range(1, 3 )
      .flatMap(data -> {
            if (1 == data) {
              return Flowable.empty();
            } else {
              return Flowable.just(data+1);
            }
      },(sourceData, newData) ->"[" +sourceData+"]" +newData
          );
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
