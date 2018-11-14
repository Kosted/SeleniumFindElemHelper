package sample;

import org.junit.rules.ErrorCollector;

import static org.hamcrest.CoreMatchers.is;


public class AssertMCS<T> {

    private ErrorCollector errorCollector;
    private String standartErrorMessege;

    public void setStandartErrorMessege(String standartErrorMessege) {
    this.standartErrorMessege=standartErrorMessege;
    }

    public AssertMCS(ErrorCollector errorCollector) {

        this.errorCollector = errorCollector;
        standartErrorMessege="";

    }

    public void equals(T first, T second, String errorMessege) {

        errorCollector.checkThat(errorMessege + standartErrorMessege, first, is(second));

    }

    public void notEquals(T first, T second, String errorMessege) {
        if (first == null && second == null)
            errorCollector.addError(new Throwable("null == null " + errorMessege + standartErrorMessege));
        else if ((first == null && second != null) || (first != null && second == null))
            return;
        else if (first.equals(second))
            errorCollector.addError(new Throwable(errorMessege + first.toString() + standartErrorMessege));

    }

    public void equalsTrue(Boolean condition, String errorMessege) {
        errorCollector.checkThat(errorMessege + standartErrorMessege, true, is(condition));

    }

    public void equalsFalse(Boolean condition, String errorMessege) {
        errorCollector.checkThat(errorMessege + standartErrorMessege, false, is(condition));

    }

//    public void result() {
//        // errorCollector.verify();
//    }

}
