package com.online.service.bugfindings;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.univocity.parsers.tsv.TsvParser; 
import com.univocity.parsers.tsv.TsvParserSettings; 

@RestController
public class BugFinder {

	@Autowired
	private Tree tree;

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

		int max=0;
		int max1=0;
		double mostAbundant=0;
		for(int j=0;j<res.size();j++)
		{
			if(j>0 && !res.get(j)[0].equals(res.get(j-1)[0]))
			{
				max1=(int) mostAbundant;
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
			if(max1<(int) mostAbundant)
			{
				max=(int) mostAbundant;
				val.setMostAbunduntBug(Integer.parseInt(res.get(j)[0]));
				val.setOccurrences(max);
			}
		}


		/**
		for(int i=0;i<res.size();i++)
		{

			if(i==0)
			{
				tree.root=new Node(Integer.parseInt(res.get(i)[1]));
				if(Integer.parseInt(res.get(i)[0])>tree.root.data)
					tree.root=tree.insert(tree.root, Integer.parseInt(res.get(i)[0]));
				else
					tree.root=tree.insert(tree.root, Integer.parseInt(res.get(i)[0]));

				//System.out.println(tree.root.data+" "+tree.root.right.data +" "+Integer.parseInt(res.get(i)[0]));
				continue;
			}
			for(int j=1;j>=0;j--)
			{
				if(tree.root.right!=null && Integer.parseInt(res.get(i)[j])>tree.root.right.data)
				{
					System.out.println(Integer.parseInt(res.get(i)[j]));
					tree.root.right=tree.insert(tree.root.right, Integer.parseInt(res.get(i)[j]));

				}
				if(tree.root.left!=null && Integer.parseInt(res.get(i)[j])<tree.root.left.data)
				{
					tree.root.left=tree.insert(tree.root.left, Integer.parseInt(res.get(i)[j]));

				}
			}

		}
		 **/
	

		return val;
	}
}
