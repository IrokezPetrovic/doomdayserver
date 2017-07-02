package rxjava;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.junit.Test;

import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxTest {

	@Test
	public void test1(){
		ExecutorService e = Executors.newFixedThreadPool(10);
		Subject<Object> subj = PublishSubject.create();	
		subj
		//.ofType(Integer.class)
		.observeOn(Schedulers.from(e))
		.map(s->"MESSAGE:"+s)
		.subscribe(m->{		
			System.out.println(Thread.currentThread().getName());
			System.out.println(m);
		});		
		subj.onNext("TEST");
		subj.onNext(new Integer(12345));
		System.out.println("CURRENT:"+Thread.currentThread().getName());
	
	}
}
