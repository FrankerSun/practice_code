package annotationtest.inherited;

/**
 * @author f.s.
 * @date 2019/2/8
 */
@NoInheritedTest
public class SubClass extends SuperClass{

    private String privateFieldSubX;

    public String publicFieldSubY;

    public SubClass(){}

    public SubClass(String subName){}

    public String getPrivateFieldSubX() {
        return privateFieldSubX;
    }

    private void setPrivateFieldSubX(String privateFieldSubX) {
        this.privateFieldSubX = privateFieldSubX;
    }
}
