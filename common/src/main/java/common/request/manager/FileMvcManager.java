//package common.request.manager;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
///**
// * @author I Nhrl
// */
//@Slf4j
//@Component
//public class FileMvcManager {
//    /**
//     * 存储位置
//     */
//    private static String storageLocation;
//    /**
//     * 路径
//     */
//    private static String path;
//
//    @Value("${spring.mvc.static-path-pattern}")
//    public void setPathConfig(String path){
//        log.info("spring.mvc.static-path-pattern:  {}",path);
//        FileMvcManager.setPath(path.substring(0,path.length()-2));
//        log.info("spring.mvc.static-path-pattern---new:  {}",getPath());
//    }
//
//    @Value("${spring.web.resources.static-locations}")
//    public void setStorageLocationConfig(String storageLocation){
//        log.info("spring.web.resources.static-locations:  {}",storageLocation);
//        FileMvcManager.setStorageLocation(storageLocation.substring(5));
//        log.info("spring.web.resources.static-locations---new:  {}",getStorageLocation());
//    }
//
//    public static void setPath(String path) {
//        FileMvcManager.path = path;
//    }
//
//    public static String getPath() {
//        return path;
//    }
//
//    public static void setStorageLocation(String storageLocation) {
//        FileMvcManager.storageLocation = storageLocation;
//    }
//
//    public static String getStorageLocation() {
//        return storageLocation;
//    }
//}
