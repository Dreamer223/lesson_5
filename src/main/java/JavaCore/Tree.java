package JavaCore;

import java.io.File;

public class Tree {

    /**
     * TODO: TODO: Доработать метод print, необходимо распечатывать директории и файлы
     * @param args
     */
    public static void main(String[] args) {
        print(new File("."), "", true);
    }

    static void print(File file, String indent, boolean isLast){
        System.out.print(indent);
        if (isLast){
            System.out.print("└─");
            indent += "  ";
        }
        else {
            System.out.print("├─");
            indent += "│ ";
        }
        System.out.println(file.getName());

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files == null)
                return;

            int subFileTotal = 0;
            for (File f : files) {
                if (f.isDirectory() || f.isFile()) {
                    subFileTotal++;
                }
            }

            int subFileCounter = 0;
            for (File f : files) {
                if (f.isDirectory() || f.isFile()) {
                    print(f, indent, subFileTotal == ++subFileCounter);
                }
            }
        }
    }
}
