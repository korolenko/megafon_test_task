import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.*;

import static java.nio.file.StandardWatchEventKinds.*;

public class FileHelper {
    public static boolean isFileChanged(String fileName) throws IOException, URISyntaxException {
        boolean isChanged = false;
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
                        isChanged = true;
                    }
                }
                // reset the key
                boolean valid = wk.reset();
                if (!valid) {
                    System.out.println("Key has been unregistered");
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return isChanged;
    }

    public static Path getFilePath(String fileName) throws URISyntaxException {
        return Paths.get(System.getProperty("user.dir") + "/" +fileName);
    }
}
