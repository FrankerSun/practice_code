package annotationtest.inherited;

/**
 * @author f.s.
 * @date 2019/2/8
 */
@InheritedTest
public class SuperClass {

    private String x;

    public String publicField;

    public SuperClass() {
    }

    public String insertPubSuper() {
        return x;
    }

    private void deletePriSuper(){
        this.x = "deleted";
    }
}
