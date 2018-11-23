import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Scanner;

import javax.swing.JFileChooser;

	/*
	 * Robert Graham Hazlett
	 * CS51HW8q1
	 * file transfer code taken from IO.java provided by CS51.
	 */

public class HW8q1 {

	// arrays carrying the cache tags, corresponding cache valid bits, and queues
	// representing the last used values to be evicted first.
	public static Integer[][] cachetags;
	public static Boolean[][] cachevalids;
	public static ArrayList<ArrayDeque<Integer>> lastused;
	
	public static void main(String[] args)
	{	
		/* 
		 * the argument for runVals is the log base 2 of associativity:
		 * e.g. direct mapped has associativity of 1, so it is represented by runVals(0);
		 * 2 way associative has associativity of 2, so it is represented by runVals(1);
		 * 4 way associative has associativity of 4, so it is represented by runVals(2);
		 * fully associative for a 64 entry cache is represented by runVals(6);
		 *
		 */
		runVals(0);
		runVals(1);
		runVals(2);
		runVals(6);
		
	}
	
	public static void runVals(int associativity)
	{
		int misses = 0, hits = 0;
		//initialize cache arrays with appropriate size:
		cachetags = new Integer[(int)(Math.pow(2,6-associativity))][(int)(Math.pow(2,associativity))];
		for(int i=0; i<cachetags.length; i++)
		{
			cachetags[i] = new Integer[(int)(Math.pow(2,associativity))];
			for(int j=0; j<cachetags[i].length; j++)
			{
				cachetags[i][j] = 0;
			}
		}
		cachevalids = new Boolean[(int)(Math.pow(2,6-associativity))][(int)(Math.pow(2,associativity))];
		for(int i=0; i<cachevalids.length; i++)
		{
			cachevalids[i] = new Boolean[(int)(Math.pow(2,associativity))];
			for(int j=0; j<cachevalids[i].length; j++)
			{
				cachevalids[i][j] = false;
			}
		}
		lastused = new ArrayList<ArrayDeque<Integer>>();
		for(int i=0; i<(int)(Math.pow(2,6-associativity)); i++)
		{
			lastused.add(new ArrayDeque<Integer>());
		}
		//retrieve data
		Long[] addresses = new Long[10000000];
		int numAddresses = read(addresses);
		int tagmask = 0xffffffff;
		tagmask = tagmask << 4 + (6-associativity);
		System.out.println("tagmask: " + String.format("0x%8X", tagmask));
		int setmask = 0x000003f0;
		setmask = setmask >> associativity;
		setmask = setmask & 0x000003f0;
		System.out.println("setmask: " + String.format("0x%8X", setmask));
		for(int i=0; i<numAddresses; i++)
		{
			System.out.print(String.format("0x%16X", addresses[i]));
			boolean miss = true;
			long tagi = tagmask & addresses[i];
			int tag = (int)(tagi >> 4 + (6-associativity));
			long seti = setmask & addresses[i];
			int set = (int)(seti >> 4);
			System.out.print(", tag = " + String.format("0x%08X", tag));
			System.out.print(", set = " + set + "   ");
			for(int j=0; j<cachetags[set].length; j++)
			{
				if(cachetags[set][j].equals(tag))
				{
					if(cachevalids[set][j].equals(true))
					{
						miss = false;
						System.out.println("cache hit! on line " + j);
						hits ++;
					}
				}
			}
			if(miss)
			{
				System.out.print("cache miss: ");
				misses ++;
				boolean needtoevict = true;
				for(int j=0; j<cachevalids[set].length; j++)
				{
					if(cachevalids[set][j].equals(false))
					{
						cachetags[set][j] = tag;
						cachevalids[set][j] = true;
						System.out.println("line " + j + " empty, putting there");
						lastused.get(set).add(j);
						needtoevict = false;
						break;
					}
				}
				if(needtoevict)
				{
					int evict = lastused.get(set).poll();
					System.out.println("no room, evicting from line " + evict);
					cachetags[set][evict] = tag;
					lastused.get(set).add(evict);
				}
			}
		}
		System.out.println(hits + " hits, " + misses + " misses, " + (hits+misses) + " total.");
	}
	
	/*
	 * The following code came from the code samples from the cs51 page:
	 */
	
	// Read addresses from a file into array a, returning how many were read.
    // Doesn't read more than there is room for in the array.
    public static int read(Long[] a) {
        int n = 0; // count of items read into array
        Scanner input = null;

        // Put up a file chooser window to select a file to read.
        JFileChooser chooser = new JFileChooser();
        int status = chooser.showOpenDialog(null);

        boolean fileOpened = true;    // OK unless proven otherwise

        do {
            // Get a file from the file chooser.  If the user hits the Cancel button,
            // end the program.
            while (status != JFileChooser.APPROVE_OPTION) {
                if (status == JFileChooser.CANCEL_OPTION)
                    System.exit(0);
                else {
                    System.out.println("No file chosen.  Try again.");
                    status = chooser.showOpenDialog(null);
                }
            }
            // We have a file from the file chooser.  We should be able to create a
            // Scanner to read the file.
            File inputFile = chooser.getSelectedFile();
            try {
                input = new Scanner(inputFile);
            } catch (FileNotFoundException e) {
                fileOpened = false;
                System.out.println("Could not open file.  Try again.");
            }
        }
        while (!fileOpened);

        // Now the file has been opened.  Read it until we read the last address or
        // we've filled the array a.
        while (input.hasNext() && n < a.length) {
            input.next();
            a[n++] = input.nextLong(16);
        }
        return n;
    }
    
    

    // Write the first n elements of array a.
    public static void write(Long[] a, int n) {
        int i;

        System.out.println();

        for (i = 0; i < n; i++) {
            System.out.format("%d\t%08x\n", i, a[i]);
        }
        System.out.println();
    }

}
