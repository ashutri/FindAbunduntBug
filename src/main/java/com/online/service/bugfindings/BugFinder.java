package com.online.service.bugfindings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.online.service.bugfindings.WeightedGraph.Graph;
import com.univocity.parsers.tsv.TsvParser; 
import com.univocity.parsers.tsv.TsvParserSettings;  

@RestController
public class BugFinder {

	@Autowired
	private WeightedGraph wGraph;




	@Autowired
	private BugManager val;

	@GetMapping("/abundunt-bug")
	public BugManager findAbunduntBug() 
	{
		//String fileName="/data.tsv";
		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:data.tsv");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TsvParserSettings settings = new TsvParserSettings(); 
		settings.getFormat().setLineSeparator("/t"); 
		TsvParser parser = new TsvParser(settings); 
		List<String[]> allRows = parser.parseAll(file); 
		List<String[]> res=new ArrayList<String[]>();
		int n=allRows.size();

		for(int i=1;i<n;i++)
		{
			int temp=0;
			String[] col=new String[2];
			for(int j=1;j<n;j++)
			{

				if(allRows.get(i)[2].equals(allRows.get(j)[1]))
				{
					temp=1;				
					break;
				}			
			}
			if(temp==0)
			{
				col[0]=allRows.get(i)[2];
				col[1]=allRows.get(i)[1];
				res.add(col);
			}

		}
		Map<String, Integer> cache=new HashMap<>();
		Integer max=0;
		double mostAbundant=0;
		for(int j=0;j<res.size();j++)
		{
			if(j>0 && !res.get(j)[0].equals(res.get(j-1)[0]))
			{
				max=(int) mostAbundant;
				mostAbundant=0;
			}	
			for(int i=1;i<n;i++)
			{
				if(allRows.get(i)[2].equals(res.get(j)[1]))
				{
					if(!allRows.get(i)[3].toLowerCase().equals("null"))
					{
						mostAbundant+=Integer.parseInt(allRows.get(i)[3]);
					}
				}	
			}
			for(int i=1;i<n;i++)
			{
				if(allRows.get(i)[1].equals(res.get(j)[1]) && allRows.get(i)[2].equals(res.get(j)[0]))
				{
					mostAbundant=mostAbundant*(Integer.parseInt(allRows.get(i)[4]));
				}
			}
			mostAbundant/=100;
			if(!allRows.get(j)[2].equals(res.get(j)[0]))
			{
				if(max<(int) mostAbundant)
				{
					max=(int) mostAbundant;
					cache.put(res.get(j)[0], max);

				}
			}

		}
		String strKey = null;
		for(Map.Entry entry: cache.entrySet())
		{

			if(max.equals(entry.getValue())){
				strKey = (String) entry.getKey();
				val.setMostAbunduntBug(Integer.parseInt(strKey));
				val.setOccurrences((int) entry.getValue());
				break; //breaking because its one to one map
			}
		}


		return val;
	}

	@GetMapping("/abundunt")
	public BugManager findAbundunt() 
	{
		//String fileName="/data.tsv";
		File file = null;
		try {
			file = ResourceUtils.getFile("classpath:data.tsv");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		TsvParserSettings settings = new TsvParserSettings(); 
		settings.getFormat().setLineSeparator("/t"); 
		TsvParser parser = new TsvParser(settings); 
		List<String[]> allRows = parser.parseAll(file);
		int n=allRows.size();
		int arrSize=allRows.get(0).length;
		Graph graph=wGraph.new Graph(n);
		for(int i=1;i<n;i++)
		{

			graph.addEgde(Integer.parseInt(allRows.get(i)[2]), 
					Integer.parseInt(allRows.get(i)[1]), allRows.get(i)[arrSize-2], Integer.parseInt(allRows.get(i)[arrSize-1]));
		}

		graph.printGraph();


		return val;
	}
}
