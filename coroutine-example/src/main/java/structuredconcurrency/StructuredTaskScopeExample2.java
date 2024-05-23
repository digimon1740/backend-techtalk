//package structuredconcurrency;
//
//import virtualthread.UserResponse;
//
//import java.util.List;
//import java.util.concurrent.Callable;
//import java.util.concurrent.StructuredTaskScope;
//
//public class StructuredTaskScopeExample2 {
//
//    public static void main(String[] args) {
//        var example = new StructuredTaskScopeExample2();
//        example.getUsers();
//    }
//
//    public List<UserResponse> getUsers() {
//        try (var scope = new StructuredTaskScope.ShutdownOnFailure()) {
//            var fork1 = scope.fork(() -> callApi(1L));
//            var fork2 = scope.fork(() -> callApi(2L));
//
//            scope.join();
//            scope.throwIfFailed((e) -> new RuntimeException("Failed to get user"));
//
//            return List.of(fork1.get(), fork2.get());
//        } catch (InterruptedException e) {
//            throw new RuntimeException(e);
//        }
//    }
//
//    public UserResponse callApi(Long id) {
//        var name = "";
//        if (id == 1L) {
//            name = "아이언맨";
//        } else {
//            name = "캡틴 아메리카";
//        }
//        return new UserResponse(id, name);
//    }
//}
//
//
