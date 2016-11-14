package threads2edition.ch036.DeadLock;

public class DeadLock {
    public void removeUseless(Folder file) {
        synchronized (file) {
            if (file.isUseless()) {
                Cabinet directory = file.getCabinet();
                synchronized (directory) {
                    directory.remove(file);
                }
            }
        }
    }

    public void updateFolders(Cabinet dir) {
        synchronized (dir) {
            for (Folder f = dir.first(); f != null; f = dir.next(f)) {
                synchronized (f) {
                    f.update();
                }
            }
        }
    }
}
