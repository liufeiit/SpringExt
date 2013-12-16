package org.matrix.pack200;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.SortedMap;
import java.util.jar.JarFile;
import java.util.jar.JarOutputStream;
import java.util.jar.Pack200;
import java.util.jar.Pack200.Packer;
import java.util.jar.Pack200.Unpacker;

/**
 * 
 * @author 刘飞 E-mail:liufei_it@126.com
 * @version 1.0
 * @since 2013-12-15 下午4:58:30
 */
public class Pack200Tester {

	public static void main(String[] args) {
		Packer packer = Pack200.newPacker();
	    // Initialize the state by setting the desired properties
		SortedMap<String,String> p = packer.properties();
	    // take more time choosing codings for better compression
	    p.put(Packer.EFFORT, "7");  // default is "5"
	    // use largest-possible archive segments (>10% better compression).
	    p.put(Packer.SEGMENT_LIMIT, "-1");
	    // reorder files for better compression.
	    p.put(Packer.KEEP_FILE_ORDER, Packer.FALSE);
	    // smear modification times to a single value.
	    p.put(Packer.MODIFICATION_TIME, Packer.LATEST);
	    // ignore all JAR deflation requests,
	    // transmitting a single request to use "store" mode.
	    p.put(Packer.DEFLATE_HINT, Packer.FALSE);
	    // discard debug attributes
	    p.put(Packer.CODE_ATTRIBUTE_PFX+"LineNumberTable", Packer.STRIP);
	    // throw an error if an attribute is unrecognized
	    p.put(Packer.UNKNOWN_ATTRIBUTE, Packer.ERROR);
	    // pass one class file uncompressed:
	    p.put(Packer.PASS_FILE_PFX+0, "mutants/Rogue.class");
	    try {
	        JarFile jarFile = new JarFile("/home/lf/tmp/tangosol.jar");
	        FileOutputStream fos = new FileOutputStream("/home/lf/tmp/tangosol.pack");
	        // Call the packer
	        packer.pack(jarFile, fos);
	        jarFile.close();
	        fos.close();
	        
	        File f = new File("/home/lf/tmp/tangosol.pack");
	        FileOutputStream fostream = new FileOutputStream("/home/lf/tmp/tangosol_0.jar");
	        JarOutputStream jostream = new JarOutputStream(fostream);
	        Unpacker unpacker = Pack200.newUnpacker();
	        // Call the unpacker
	        unpacker.unpack(f, jostream);
	        // Must explicitly close the output.
	        jostream.close();
	    } catch (IOException ioe) {
	        ioe.printStackTrace();
	    }
	}
}
