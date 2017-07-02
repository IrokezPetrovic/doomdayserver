package rxjava;

import org.junit.Test;

import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxTest {

	@Test
	public void test1(){
		Subject<String> subj = PublishSubject.create();		
	}
}
