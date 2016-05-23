package notDeafult;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;
import org.apache.commons.io.FileUtils;

import java.io.*;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class logic {
    changeVersion ch = new changeVersion();
    public File tmpDir;

    public logic() {
    }

    public String copyFile(File fileToConvert){
        System.out.println(System.getProperty("java.io.tmpdir"));
        try {
            tmpDir = Files.createTempDirectory("working").toFile();
            //Delete Path After Exit
            recursiveDeleteOnExit(tmpDir.toPath());
            System.out.println(tmpDir.getAbsolutePath());
            FileUtils.copyFileToDirectory(fileToConvert, tmpDir, true);
            //Re-Register New Files
            recursiveDeleteOnExit(tmpDir.toPath());
            File [] flr = tmpDir.listFiles();
            System.out.println(flr[0].getName());
            //Uncompress the project
            String info = extractFile(flr[0]);
            recursiveDeleteOnExit(tmpDir.toPath());
            return info;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "nothing";

    }

    public String extractFile(File fileToExtract){
        //https://commons.apache.org/proper/commons-compress/examples.html
        try {
            FileInputStream fin = new FileInputStream(fileToExtract);
            BufferedInputStream in = new BufferedInputStream(fin);
            FileOutputStream out = new FileOutputStream(fileToExtract.getParent()+"/xmlFile.xml");
            GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
            final  byte[] buffer = new byte[4096];
            int n=0;
            while(-1!=(n=gzIn.read(buffer))){
                out.write(buffer, 0,n);
            }

            out.close();
            gzIn.close();
            System.out.println("Extracted");
//            File xmlProj = new File(fileToExtract.getPath());

            String name = fileToExtract.getName();
            String size = Long.toString(FileUtils.sizeOf(fileToExtract)/1024)+"kb";
            int saveVersion = ch.getInfo(new File(tmpDir.toPath()+"/xmlFile.xml"));
            String premVersion = saveCalculate(saveVersion);

            return "Name: "+name+"\nSize: "+size+"\nSave Version: "+saveVersion+"\nPremiere Version: "+premVersion;



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "nothing";

    }



    public static void recursiveDeleteOnExit(Path path) throws IOException {
        //http://stackoverflow.com/a/20280989/1649917
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file,
                                             @SuppressWarnings("unused") BasicFileAttributes attrs) {
                file.toFile().deleteOnExit();
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult preVisitDirectory(Path dir,
                                                     @SuppressWarnings("unused") BasicFileAttributes attrs) {
                dir.toFile().deleteOnExit();
                return FileVisitResult.CONTINUE;
            }
        });
    }

    public String saveCalculate(int i){
        if(i<29){
            return "Premiere Pro CC 2014 or lower";
        }
        else {
            return "Premiere Pro CC 2015";

        }
    }


    public void compressGz(File output) throws IOException {
        FileInputStream fin = new FileInputStream(tmpDir.toPath()+"/updatedVersion.xml");
        BufferedInputStream in = new BufferedInputStream(fin);
        FileOutputStream out = new FileOutputStream(output);
        GzipCompressorOutputStream gzout = new GzipCompressorOutputStream(out);
        final byte[] buffer = new byte[4096];
        int n = 0;
        while (-1 != (n = in.read(buffer))) {
            gzout.write(buffer, 0, n);
        }
        gzout.close();
        out.close();
//        gzout.close();

    }
}
