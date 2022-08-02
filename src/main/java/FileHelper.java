import java.io.IOException;
import java.nio.file.*;

public class FileHelper {
    public static void isFileChanged(String fileName) throws IOException {
        final Path path = FileSystems.getDefault().getPath(System.getProperty("user.dir"));
        System.out.println(path);
        try (final WatchService watchService = FileSystems.getDefault().newWatchService()) {
            final WatchKey watchKey = path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);
            while (true) {
                final WatchKey wk = watchService.take();
                for (WatchEvent<?> event : wk.pollEvents()) {
                    final Path changed = (Path) event.context();
                    System.out.println(changed);
                    if (changed.endsWith(fileName)) {
                        System.out.println(fileName + " has changed");

                    }
                }
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregistered");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static Path getFilePath(String fileName) {
        return Paths.get(System.getProperty("user.dir") + "/" +fileName);
    }
}
