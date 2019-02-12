package policy;

import lombok.extern.slf4j.Slf4j;

import java.io.FileWriter;
import java.io.IOException;

/**
 * -Djava.security.manager -Djava.security.policy=/Users/Documents/sourcecode/dynamic_proxy/src/main/java/policy/policy.txt
 *
 * @author f.s.
 * @date 2018/12/5
 */
@Slf4j
@SuppressWarnings("unchecked")
public class TestPolicy {

    public static void main(String[] args) {

//        SecurityManager sm = new SecurityManager();
//        System.setSecurityManager(sm);
//        System.setProperty("java.security.policy", "/Users/Documents/sourcecode/dynamic_proxy/src/main/java/policy/policy.txt");

        try (FileWriter writer = new FileWriter("/Users/Desktop/policy.txt")) {
            writer.write("policy");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
