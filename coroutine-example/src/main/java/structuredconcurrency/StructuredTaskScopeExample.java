//package structuredconcurrency;
//
//import java.util.concurrent.Callable;
//import java.util.concurrent.StructuredTaskScope;
//
//public class StructuredTaskScopeExample {
//
//    public static void main(String[] args) {
//        var example = new StructuredTaskScopeExample();
//        example.sendMessage();
//    }
//
//    public void sendMessage() {
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            scope.fork((Callable<Void>) () -> {
//                sendEmail();
//                return null;
//            });
//
//            scope.fork((Callable<Void>) () -> {
//                sendSms();
//                return null;
//            });
//
//            scope.join();
//            scope.throwIfFailed((e) -> new RuntimeException("Failed to send"));
//
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public static void sendEmail() {
//        System.out.println("이메일 발송 완료");
//    }
//
//    public static void sendSms() {
//        System.out.println("SMS 발송 완료");
//    }
//}
//
//
