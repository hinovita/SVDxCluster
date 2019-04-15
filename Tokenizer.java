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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import entity.Document;
import entity.Term;

public class Tokenizer
{
	TermList lsttoken;
	TermList lsttokencurrent;
	StopWordList stpwlist;
	List<Document>lstDoc;
        Stemmer stemming = new Stemmer();

	public Tokenizer()
	{
		lsttoken = new TermList();
		stpwlist = new StopWordList();
		stpwlist.loadStopWord();
	}

	public void setTokenList(TermList lstTokenList)
	{
		lsttoken = lstTokenList;
	}

	public void tokenize(Document doc)
	{
//		String[] t=doc.getIsi().toLowerCase().replaceAll("[\\W&&[^\\s]]"," ").split("\\W+");
                String[] t= removePunctuation(doc.getIsi().toLowerCase()).split("\\s+");
                String[] s=stemming.Stemming(t);
		lsttokencurrent = new TermList();
//		for(int i=0; i<t.length; i++)
//		{
//                    System.out.println("Token: " +t[i]);
////			if(t[i].length()>1)
////			{
////				if(!stpwlist.findStopWord(t[i]))
////				{
////					t[i] = t[i].toLowerCase();
////					t[i] = removeReadMark(t[i]);
////					lsttokencurrent.addTerm(new Term(t[i]));
////					Term tkn = lsttoken.checkTerm(t[i]);
////					if(tkn==null)
////					{
////						lsttoken.addTerm(new Term(t[i]));
////					}
////				}
////			}
//		}
                for(int i=0; i<s.length; i++){
//                    System.out.println("Stem: "+s[i]);
			if(s[i].length()>1)
			{
				if(!stpwlist.findStopWord(s[i]))
				{
					s[i] = s[i].toLowerCase();
					//s[i] = removePunctuation(s[i]);
					lsttokencurrent.addTerm(new Term(s[i]));
					Term tkn = lsttoken.checkTerm(s[i]);
					if(tkn==null)
					{
						lsttoken.addTerm(new Term(s[i]));
					}
				}
			}
                }
	}

        private static String removePunctuation(String isiDokumen){
            // Filter Punctuation
            String rslt = isiDokumen;
            String P = "[!\"$%&'()*\\+,./:;<=>? \\[\\]^~_\\-`{|}…0987654321]";
            return rslt.replaceAll(P, " ");
        }
        
	private String removeReadMark(String text)
	{
		String rm[] = new String[]{".",",","?","!",";","/","(",")","{","}","*","+","-","%","\n"};
		char tb = text.charAt(text.length()-1);
		boolean foundmark = false;
		for(int i=0; i<rm.length; i++)
		{
			if(tb==rm[i].toCharArray()[0])
			{
				foundmark = true;
				break;
			}
		}
		if (foundmark)
		{
			text = text.substring(0, text.length()-1);
//                        text = text.replace(String.valueOf(tb), " ");
		}
		return text;
	}

	public TermList getCurrentTokenList()
	{
		return lsttokencurrent;
	}

	public TermList getTokenList()
	{
		return lsttoken;
	}

}
