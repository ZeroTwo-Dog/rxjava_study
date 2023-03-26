package com.rxJava.chacpter01;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * 챕터1
 * 예제1_11
 * Observable 로 구현한 예제
 * Created by park on 2023/03/19.
 */
public class Ex1_18 {

  public static void main(String[] args) throws Exception {


    //생산쪽 Flowable와 거의 동일하며 구도해지 메소드만 다름
    Observable<String> observable = Observable.create(emitter -> {
      String[] datas = {"Hello, World!", "안녕, RxJava!"};

      for (String data : datas) {
        //구독이 해지되면 처리 중단
        if (emitter.isDisposed()) {
          return;
        }
        //데이터 통지
        emitter.onNext(data);
      }
      //완료 통지
      emitter.onComplete();
    });

    observable
        // 스케쥴러 전략
        .observeOn(Schedulers.computation())
        .subscribe(new Observer<String>() {


          @Override
          public void onSubscribe(Disposable disposable) {
            //dispose `배치하다`라는 뜻
            //아무것도 하지않는다.
            //구독을 해지 않으므로 아무것도 안함 해지처리가 있으면 필요
          }

          //데이터 처리
          @Override
          public void onNext(String s) {
            //Flowable 와 다르게 request 로 몇개 데이터를 처리할지 요청을 하지 않는다.
            String name = Thread.currentThread().getName();
            System.out.println(name + ": " + s);
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
