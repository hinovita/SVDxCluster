/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

/**
 *
 * @author User-pc
 */
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StopWordList
{
	List<String>lststopword;

	public StopWordList()
	{
		lststopword = new ArrayList<String>();
	}

	public void loadStopWord()
	{
		try
		{
//			FileReader freader = new FileReader("folderkamus\\stopwordlist.txt");
//			int data=0;
//			String str="";
                    BufferedReader in = new BufferedReader(new FileReader("folderkamus\\stopwordlist.txt"));
                    StringBuffer sb = new StringBuffer();
                    String s = null;
                    while((s = in.readLine()) != null){
                        sb.append(s);
                    }
                    lststopword.addAll(Arrays.asList(sb.toString().split(";")));
//			while((data=freader.read())!=-1)
//			{
//				if (data==59)
//				{
//					lststopword.add(str);
//
//					str="";
//				}
//				else 
//				{
//					str += (char)data;
//				}
//			}
		}catch(FileNotFoundException ex)
		{
                    System.out.println("File not found");
		}catch(IOException ex)
		{
                    System.out.println("IOexception");
		}
	}

	public boolean findStopWord(String str)
	{
		boolean ada=false;
		for(int i=0; i<lststopword.size(); i++)
		{
			if(lststopword.get(i).equals(str))
			{
				ada=true;
				break;
			}
		}
//                System.out.println(ada);
		return ada;
	}
        
        public void printStopWord(){
            for(int i=0; i<lststopword.size(); i++){
                System.out.println(i + ". " + lststopword.get(i));
            }
        }
}